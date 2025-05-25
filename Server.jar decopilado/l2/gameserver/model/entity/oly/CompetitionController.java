/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.mutable.MutableInt
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.entity.oly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.StringHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.entity.oly.Competition;
import l2.gameserver.model.entity.oly.CompetitionState;
import l2.gameserver.model.entity.oly.CompetitionType;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.entity.oly.OlyController;
import l2.gameserver.model.entity.oly.Participant;
import l2.gameserver.model.entity.oly.ParticipantPool;
import l2.gameserver.model.entity.oly.Stadium;
import l2.gameserver.model.entity.oly.StadiumPool;
import l2.gameserver.model.entity.oly.participants.SinglePlayerParticipant;
import l2.gameserver.model.entity.oly.participants.TeamParticipant;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExReceiveOlympiadGameList;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.TimeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompetitionController {
    private static final Logger bW = LoggerFactory.getLogger(CompetitionController.class);
    public static final int COMPETITION_PAUSE = 30000;
    public static final int COMPETITION_PREPARATION_DELAY = 60;
    public static final int BACKPORT_DELAY = 20;
    private static CompetitionController a;
    private ConcurrentLinkedQueue<Competition> f = new ConcurrentLinkedQueue();
    private ScheduledFuture<?> W;
    private int lA = 0;
    private static final String di = "SELECT `oc`.`char_id` AS `char_obj_id`, `on1`.`char_name` AS `char_name`, `on1`.`class_id` AS `char_class_id`, `on2`.`char_id` AS `rival_obj_id`, `on2`.`char_name` AS `rival_name`, `on2`.`class_id` AS `rival_class_id`, `oc`.`result` AS `result`, `oc`.`rule` AS `rules`, `oc`.`elapsed_time` AS `elapsed_time`, `oc`.`mtime` AS `mtime` FROM `oly_comps` AS `oc` JOIN `oly_nobles` AS `on1` ON `oc`.`char_id` = `on1`.`char_id` JOIN `oly_nobles` AS `on2` ON `oc`.`rival_id` = `on2`.`char_id` WHERE `oc`.`char_id` = ? AND `oc`.`season` = ? ";
    private static final String dj = "INSERT INTO `oly_comps` (`season`, `char_id`, `rival_id`, `rule`, `result`, `elapsed_time`, `mtime`) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final CompetitionController getInstance() {
        if (a == null) {
            a = new CompetitionController();
        }
        return a;
    }

    private CompetitionController() {
    }

    public boolean isActiveCompetitionInPrgress() {
        return !this.f.isEmpty();
    }

    public Collection<Competition> getCompetitions() {
        return this.f;
    }

    private final synchronized boolean a(CompetitionType competitionType, int n, boolean bl) {
        if (!StadiumPool.getInstance().isStadiumAvailable()) {
            bW.warn("OlyCompetitionController: not enough stadiums.");
            return false;
        }
        if (!ParticipantPool.getInstance().isEnough(competitionType, n, bl)) {
            ParticipantPool.getInstance().broadcastToEntrys(competitionType, SystemMsg.THE_MATCH_MAY_BE_DELAYED_DUE_TO_NOT_ENOUGH_COMBATANTS, n);
            return false;
        }
        Player[][] playerArray = ParticipantPool.getInstance().retrieveEntrys(competitionType, n, bl);
        while (OlyController.getInstance().isRegAllowed() && StadiumPool.getInstance().isStadiumAvailable() && playerArray != null && playerArray[0] != null && playerArray[1] != null) {
            Stadium stadium = StadiumPool.getInstance().pollStadium();
            if (stadium == null) {
                bW.error("OlyCompetitionController: stadium == null wtf?");
                return false;
            }
            this.a(competitionType, stadium, playerArray[0], playerArray[1]);
            playerArray = ParticipantPool.getInstance().retrieveEntrys(competitionType, n, bl);
        }
        return true;
    }

    private void a(CompetitionType competitionType, Stadium stadium, Player[] playerArray, Player[] playerArray2) {
        Competition competition = new Competition(competitionType, stadium);
        if (competitionType == CompetitionType.TEAM_CLASS_FREE) {
            competition.setPlayers(new Participant[]{new TeamParticipant(Participant.SIDE_BLUE, competition, playerArray), new TeamParticipant(Participant.SIDE_RED, competition, playerArray2)});
        } else if (competitionType == CompetitionType.CLASS_FREE || competitionType == CompetitionType.CLASS_INDIVIDUAL || competitionType == CompetitionType.CLASS_TYPE_INDIVIDUAL) {
            competition.setPlayers(new Participant[]{new SinglePlayerParticipant(Participant.SIDE_BLUE, competition, playerArray[0]), new SinglePlayerParticipant(Participant.SIDE_RED, competition, playerArray2[0])});
        }
        competition.scheduleTask(new StadiumTeleportTask(competition), 100L);
        competition.start();
        this.f.add(competition);
    }

    public void FinishCompetition(Competition competition) {
        if (competition == null) {
            return;
        }
        try {
            competition.finish();
            if (competition.getState() != CompetitionState.INIT) {
                competition.teleportParticipantsBack();
                competition.getStadium().closeDoors();
            }
            StadiumPool.getInstance().putStadium(competition.getStadium());
            this.f.remove(competition);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private boolean a(CompetitionType competitionType) {
        if (competitionType == CompetitionType.CLASS_INDIVIDUAL) {
            boolean bl = false;
            for (ClassId classId : ClassId.values()) {
                if (classId.level() != 3 || !this.a(competitionType, classId.getId(), false)) continue;
                bl = true;
            }
            return bl;
        }
        if (competitionType == CompetitionType.CLASS_TYPE_INDIVIDUAL) {
            boolean bl = false;
            if (this.a(competitionType, 0, false)) {
                bl = true;
            }
            if (this.a(competitionType, 0, true)) {
                bl = true;
            }
            return bl;
        }
        return this.a(competitionType, 0, false);
    }

    public void scheduleStartTask() {
        if (OlyController.getInstance().isRegAllowed()) {
            this.W = ThreadPoolManager.getInstance().schedule(new CompetitionStarterTask(), Math.min(30000 * (this.lA + 1), 60000));
        }
    }

    public void cancelStartTask() {
        if (this.W != null) {
            this.W.cancel(true);
            this.W = null;
        }
    }

    public synchronized SystemMessage AddParticipationRequest(CompetitionType competitionType, Player[] playerArray) {
        if (!OlyController.getInstance().isRegAllowed()) {
            return new SystemMessage(SystemMsg.THE_GRAND_OLYMPIAD_GAMES_ARE_NOT_CURRENTLY_IN_PROGRESS);
        }
        for (Player player : playerArray) {
            if (!player.isNoble()) {
                return (SystemMessage)new SystemMessage(SystemMsg.C1_DOES_NOT_MEET_THE_PARTICIPATION_REQUIREMENTS_ONLY_NOBLESSE_CHARACTERS_CAN_PARTICIPATE_IN_THE_OLYMPIAD).addName(player);
            }
            if (player.isInDuel()) {
                return new SystemMessage(SystemMsg.YOU_CANNOT_OBSERVE_WHILE_YOU_ARE_IN_COMBAT);
            }
            int n = Calendar.getInstance().get(11) * 100 + Calendar.getInstance().get(12);
            if (Config.OLY_COMPETITION_CUSTOM_START_TIME > 0 && (n < Config.OLY_COMPETITION_CUSTOM_START_TIME || n >= Config.OLY_COMPETITION_CUSTOM_END_TIME)) {
                return new SystemMessage(SystemMsg.THE_GRAND_OLYMPIAD_GAMES_ARE_NOT_CURRENTLY_IN_PROGRESS);
            }
            if (player.getBaseSubClass().getClassId() != player.getClassId().getId() || player.getClassId().getLevel() < 4) {
                return (SystemMessage)new SystemMessage(SystemMsg.YOU_CANT_JOIN_THE_OLYMPIAD_WITH_A_SUB_JOB_CHARACTER).addName(player);
            }
            if (ParticipantPool.getInstance().isRegistred(player)) {
                return (SystemMessage)new SystemMessage(SystemMsg.C1_IS_ALREADY_REGISTERED_ON_THE_MATCH_WAITING_LIST).addName(player);
            }
            if (player.isOlyParticipant()) {
                return (SystemMessage)new SystemMessage(SystemMsg.C1_IS_ALREADY_REGISTERED_ON_THE_MATCH_WAITING_LIST).addName(player);
            }
            if ((double)player.getInventoryLimit() * 0.8 <= (double)player.getInventory().getSize()) {
                return (SystemMessage)new SystemMessage(SystemMsg.C1_SINCE_80_PERCENT_OR_MORE_OF_YOUR_INVENTORY_SLOTS_ARE_FULL_YOU_CANNOT_PARTICIPATE_IN_THE_OLYMPIAD).addName(player);
            }
            if (player.isCursedWeaponEquipped()) {
                return (SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.SINCE_YOU_NOW_OWN_S1_YOU_CANNOT_PARTICIPATE_IN_THE_OLYMPIAD).addName(player)).addItemName(player.getCursedWeaponEquippedId());
            }
            if (NoblesController.getInstance().getPointsOf(player.getObjectId()) < 1) {
                return (SystemMessage)new SystemMessage(SystemMsg.S1).addString(new CustomMessage("THE_REQUEST_CANNOT_BE_COMPLETED_BECAUSE_THE_REQUIREMENTS_ARE_NOT_MET_IN_ORDER_TO_PARTICIPATE_IN", player, new Object[0]).toString());
            }
            if (Config.OLY_RESTRICT_CLASS_IDS.length > 0 && ArrayUtils.contains((int[])Config.OLY_RESTRICT_CLASS_IDS, (int)player.getActiveClassId())) {
                return (SystemMessage)new SystemMessage(SystemMsg.S1).addString(new CustomMessage("olympiad.restrictedclasses", player, new Object[0]).toString());
            }
            if (Config.OLY_RESTRICT_HWID && player.getNetConnection().getHwid() != null && ParticipantPool.getInstance().isHWIDRegistred(player.getNetConnection().getHwid())) {
                return (SystemMessage)new SystemMessage(SystemMsg.S1).addString(new CustomMessage("olympiad.iphwid.check", player, new Object[0]).toString());
            }
            if (!Config.OLY_RESTRICT_IP || player.getNetConnection().getIpAddr() == null || !ParticipantPool.getInstance().isIPRegistred(player.getNetConnection().getIpAddr())) continue;
            return (SystemMessage)new SystemMessage(SystemMsg.S1).addString(new CustomMessage("olympiad.iphwid.check", player, new Object[0]).toString());
        }
        ParticipantPool.getInstance().createEntry(competitionType, playerArray);
        switch (competitionType) {
            case CLASS_INDIVIDUAL: 
            case CLASS_TYPE_INDIVIDUAL: {
                return new SystemMessage(SystemMsg.YOU_HAVE_BEEN_REGISTERED_FOR_THE_GRAND_OLYMPIAD_WAITING_LIST_FOR_A_CLASS_SPECIFIC_MATCH);
            }
            case CLASS_FREE: 
            case TEAM_CLASS_FREE: {
                return new SystemMessage(SystemMsg.YOU_ARE_CURRENTLY_REGISTERED_FOR_A_1V1_CLASS_IRRELEVANT_MATCH);
            }
        }
        return null;
    }

    public void scheduleFinishCompetition(Competition competition, int n, long l) {
        competition.scheduleTask(new FinishCompetitionTask(competition, n), l);
    }

    public void scheduleCompetitionPreparation(Competition competition) {
        competition.scheduleTask(new CompetitionPreparationTask(competition), 1000L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addCompetitionResult(int n, NoblesController.NobleRecord nobleRecord, int n2, NoblesController.NobleRecord nobleRecord2, int n3, CompetitionType competitionType, boolean bl, boolean bl2, long l) {
        if (nobleRecord == null || nobleRecord2 == null || competitionType == null) {
            return;
        }
        if (bl2) {
            Log.add(String.format("CompetitionResult: %s(%d) - %d disconnected against %s(%d) in %s", nobleRecord2.char_name, nobleRecord2.char_id, n3, nobleRecord.char_name, nobleRecord.char_id, competitionType.name()), "olympiad");
        } else if (!bl) {
            Log.add(String.format("CompetitionResult: %s(%d) + %d win against %s(%d) - %d in %s", nobleRecord.char_name, nobleRecord.char_id, n2, nobleRecord2.char_name, nobleRecord2.char_id, n3, competitionType.name()), "olympiad");
        } else {
            Log.add(String.format("CompetitionResult: %s(%d) tie against %s(%d) in %s", nobleRecord.char_name, nobleRecord.char_id, nobleRecord2.char_name, nobleRecord2.char_id, competitionType.name()), "olympiad");
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(dj);
            if (!bl2) {
                preparedStatement.setInt(1, n);
                preparedStatement.setInt(2, nobleRecord.char_id);
                preparedStatement.setInt(3, nobleRecord2.char_id);
                preparedStatement.setInt(4, competitionType.getTypeIdx());
                preparedStatement.setByte(5, bl ? (byte)0 : 1);
                preparedStatement.setInt(6, (int)l);
                preparedStatement.setInt(7, (int)(System.currentTimeMillis() / 1000L));
                preparedStatement.executeUpdate();
            }
            preparedStatement.setInt(1, n);
            preparedStatement.setInt(2, nobleRecord2.char_id);
            preparedStatement.setInt(3, nobleRecord.char_id);
            preparedStatement.setInt(4, competitionType.getTypeIdx());
            preparedStatement.setByte(5, bl ? (byte)0 : -1);
            preparedStatement.setInt(6, (int)l);
            preparedStatement.setInt(7, (int)(System.currentTimeMillis() / 1000L));
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            try {
                bW.warn("Can't save competition result", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Collection<CompetitionResults> getCompetitionResults(int n, int n2) {
        ArrayList<CompetitionResults> arrayList = new ArrayList<CompetitionResults>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(di);
            preparedStatement.setInt(1, n);
            preparedStatement.setInt(2, n2);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                arrayList.add(new CompetitionResults(resultSet.getInt("char_obj_id"), resultSet.getInt("char_class_id"), resultSet.getString("char_name"), resultSet.getInt("rival_obj_id"), resultSet.getInt("rival_class_id"), resultSet.getString("rival_name"), CompetitionType.getTypeOf(resultSet.getByte("rules")), resultSet.getByte("result"), resultSet.getInt("elapsed_time"), resultSet.getLong("mtime")));
            }
        }
        catch (Exception exception) {
            try {
                bW.warn("Can't load competitions records", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return arrayList;
    }

    public void showCompetitionList(Player player) {
        if (!OlyController.getInstance().isRegAllowed()) {
            player.sendPacket((IStaticPacket)SystemMsg.THE_GRAND_OLYMPIAD_GAMES_ARE_NOT_CURRENTLY_IN_PROGRESS);
            return;
        }
        if (player.isOlyParticipant() || ParticipantPool.getInstance().isRegistred(player)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_OBSERVE_A_GRAND_OLYMPIAD_GAMES_MATCH_WHILE_YOU_ARE_ON_THE_WAITING_LIST);
            return;
        }
        ExReceiveOlympiadGameList exReceiveOlympiadGameList = new ExReceiveOlympiadGameList();
        for (Competition object2 : this.f) {
            if (object2.getState() != CompetitionState.STAND_BY && object2.getState() != CompetitionState.PLAYING) continue;
            exReceiveOlympiadGameList.add(object2.getStadium(), object2.getType(), object2.getState().getStateId(), object2._participants[0].getName(), object2._participants[1].getName());
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Stadium stadium : StadiumPool.getInstance().getAllStadiums()) {
            stringBuilder.append("<a action=\"bypass -h _olympiad?command=move_op_field&field=").append(stadium.getStadiumId() + 1).append("\">");
            stringBuilder.append(new CustomMessage("Olympiad.CompetitionState.ARENA", player, new Object[0]).toString()).append(stadium.getStadiumId() + 1);
            stringBuilder.append("&nbsp;&nbsp;&nbsp;");
            boolean bl = true;
            for (Competition competition : this.f) {
                if (competition.getStadium() != stadium || competition.getState() == CompetitionState.INIT) continue;
                stringBuilder.append(competition._participants[0].getName()).append(" : ").append(competition._participants[1].getName());
                stringBuilder.append("&nbsp;");
                switch (competition.getState()) {
                    case STAND_BY: {
                        stringBuilder.append(new CustomMessage("Olympiad.CompetitionState.STAND_BY", player, new Object[0]).toString());
                        break;
                    }
                    case PLAYING: {
                        stringBuilder.append(new CustomMessage("Olympiad.CompetitionState.PLAYING", player, new Object[0]).toString());
                        break;
                    }
                    case FINISH: {
                        stringBuilder.append(new CustomMessage("Olympiad.CompetitionState.FINISH", player, new Object[0]).toString());
                    }
                }
                bl = false;
            }
            if (bl) {
                stringBuilder.append(new CustomMessage("Olympiad.CompetitionState.EMPTY", player, new Object[0]).toString());
            }
            stringBuilder.append("</a><br>");
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("oly/arenas.htm");
        npcHtmlMessage.replace("%arenas%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void watchCompetition(Player player, int n) {
        if (!OlyController.getInstance().isRegAllowed()) {
            player.sendPacket((IStaticPacket)SystemMsg.THE_GRAND_OLYMPIAD_GAMES_ARE_NOT_CURRENTLY_IN_PROGRESS);
            return;
        }
        if (player.getPet() != null || player.isMounted()) {
            return;
        }
        if (player.isInStoreMode()) {
            return;
        }
        if (n < 1 || n > StadiumPool.getInstance().getReflectionsCount()) {
            return;
        }
        if (player.isOlyParticipant() || ParticipantPool.getInstance().isRegistred(player)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_OBSERVE_A_GRAND_OLYMPIAD_GAMES_MATCH_WHILE_YOU_ARE_ON_THE_WAITING_LIST);
            return;
        }
        Stadium stadium = StadiumPool.getInstance().getStadium(n - 1);
        if (stadium.getObserverCount() > Config.OLY_MAX_SPECTATORS_PER_STADIUM) {
            player.sendMessage(new CustomMessage("CompetitionController.oly.ToManyObservers", player, new Object[0]));
            return;
        }
        if (player.isOlyObserver()) {
            player.switchOlympiadObserverArena(stadium);
        } else {
            player.enterOlympiadObserverMode(stadium);
        }
    }

    public class StadiumTeleportTask
    implements Runnable {
        private Competition a;
        private int lB;

        public StadiumTeleportTask(Competition competition) {
            this(competition, Config.OLYMPIAD_STADIUM_TELEPORT_DELAY);
        }

        public StadiumTeleportTask(Competition competition, int n) {
            this.a = competition;
            this.lB = n;
            if (this.a.getState() == null) {
                this.a.setState(CompetitionState.INIT);
            }
        }

        @Override
        public void run() {
            if (this.lB > 0) {
                this.a.broadcastPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_WILL_BE_MOVED_TO_THE_OLYMPIAD_STADIUM_IN_S1_SECONDS).addNumber(this.lB), true, false);
                long l = 1000L;
                switch (this.lB) {
                    case 30: 
                    case 45: {
                        this.lB -= 15;
                        l = 15000L;
                        break;
                    }
                    case 15: {
                        this.lB = 5;
                        l = 5000L;
                        break;
                    }
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: {
                        --this.lB;
                        l = 1000L;
                    }
                }
                if (this.a.ValidateParticipants()) {
                    return;
                }
                this.a.scheduleTask(new StadiumTeleportTask(this.a, this.lB), this.lB > 0 ? l : 1000L);
            } else {
                if (this.a.ValidateParticipants()) {
                    return;
                }
                this.a.getStadium().setZonesActive(false);
                this.a.teleportParticipantsOnStadium();
                this.a.setState(CompetitionState.STAND_BY);
                CompetitionController.getInstance().scheduleCompetitionPreparation(this.a);
                OlyController.getInstance().announceCompetition(this.a.getType(), this.a.getStadium().getStadiumId());
            }
        }
    }

    private class CompetitionStarterTask
    implements Runnable {
        private CompetitionStarterTask() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                if (!OlyController.getInstance().isRegAllowed()) {
                    return;
                }
                for (CompetitionType competitionType : CompetitionType.values()) {
                    if (CompetitionController.this.a(competitionType)) {
                        CompetitionController.getInstance().lA = 0;
                        continue;
                    }
                    if (CompetitionController.getInstance().lA >= 5) continue;
                    ++CompetitionController.getInstance().lA;
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            finally {
                CompetitionController.getInstance().scheduleStartTask();
            }
        }
    }

    public class FinishCompetitionTask
    implements Runnable {
        private Competition a;
        private int lB;

        public FinishCompetitionTask(Competition competition, int n) {
            this.a = competition;
            this.lB = n;
        }

        @Override
        public void run() {
            if (this.a.getState() != CompetitionState.FINISH) {
                this.a.setState(CompetitionState.FINISH);
                this.a.ValidateWinner();
                this.a.scheduleTask(new FinishCompetitionTask(this.a, 20), 100L);
            } else if (this.a.getState() == CompetitionState.FINISH) {
                if (this.lB > 0) {
                    this.a.broadcastPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_WILL_BE_MOVED_BACK_TO_TOWN_IN_S1_SECONDS).addNumber(this.lB), true, false);
                    int n = this.lB > 5 ? this.lB / 2 : 1;
                    this.lB -= n;
                    this.a.scheduleTask(new FinishCompetitionTask(this.a, this.lB), n * 1000);
                } else {
                    CompetitionController.getInstance().FinishCompetition(this.a);
                }
            }
        }
    }

    public class CompetitionPreparationTask
    implements Runnable {
        private Competition a;
        private int lB;

        public CompetitionPreparationTask(Competition competition) {
            this(competition, 60);
        }

        public CompetitionPreparationTask(Competition competition, int n) {
            this.a = competition;
            this.lB = n;
        }

        @Override
        public void run() {
            if (this.lB > 0) {
                if (this.lB < 10 || this.lB % 10 == 0) {
                    this.a.broadcastPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_MATCH_WILL_START_IN_S1_SECONDS).addNumber(this.lB), true, true);
                }
                long l = 1000L;
                switch (this.lB) {
                    case 60: {
                        this.a.getStadium().spawnAllNpcs();
                    }
                    case 55: {
                        this.lB -= 5;
                        l = 5000L;
                        break;
                    }
                    case 20: 
                    case 30: 
                    case 40: 
                    case 50: {
                        this.lB -= 10;
                        l = 10000L;
                        break;
                    }
                    case 10: {
                        this.a.getStadium().despawnAllNpcs();
                        this.a.getStadium().openDoors();
                        this.lB -= 5;
                        l = 5000L;
                        break;
                    }
                    case 5: {
                        this.a.applyBuffsIfNoOlympiadBufferInstance();
                    }
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: {
                        --this.lB;
                        l = 1000L;
                    }
                }
                this.a.scheduleTask(new CompetitionPreparationTask(this.a, this.lB), this.lB > 0 ? l : 2000L);
            } else {
                this.a.getStadium().setZonesActive(true);
                if (Config.OLY_RESTORE_HPCPMP_ON_START_MATCH) {
                    this.a.restoreHPCPMP();
                }
                this.a.broadcastEverybodyOlympiadUserInfo();
                this.a.broadcastEverybodyEffectIcons();
                this.a.broadcastPacket(new PlaySound("ns17_f"), true, true);
                this.a.broadcastPacket(SystemMsg.THE_MATCH_HAS_STARTED, true, true);
                this.a.setState(CompetitionState.PLAYING);
                CompetitionController.getInstance().scheduleFinishCompetition(this.a, -1, Config.OLYMPIAD_COMPETITION_TIME);
            }
        }
    }

    public class CompetitionResults {
        int char_id;
        int rival_id;
        String char_name;
        String rival_name;
        byte result;
        int char_class_id;
        int rival_class_id;
        int elapsed_time;
        CompetitionType type;
        long mtime;

        private CompetitionResults(int n, int n2, String string, int n3, int n4, String string2, CompetitionType competitionType, byte by, int n5, long l) {
            this.char_id = n;
            this.char_class_id = n2;
            this.char_name = string;
            this.rival_id = n3;
            this.rival_name = string2;
            this.rival_class_id = n4;
            this.type = competitionType;
            this.result = by;
            this.elapsed_time = n5;
            this.mtime = l;
        }

        public String toString(Player player, MutableInt mutableInt, MutableInt mutableInt2, MutableInt mutableInt3) {
            String string = null;
            if (this.result == 0) {
                string = StringHolder.getInstance().getNotNull(player, "hero.history.tie");
            } else if (this.result > 0) {
                string = StringHolder.getInstance().getNotNull(player, "hero.history.win");
            } else if (this.result < 2) {
                string = StringHolder.getInstance().getNotNull(player, "hero.history.loss");
            }
            if (this.result > 0) {
                mutableInt.increment();
            } else if (this.result == 0) {
                mutableInt3.increment();
            } else if (this.result < 0) {
                mutableInt2.increment();
            }
            string = string.replace("%classId%", String.valueOf(this.rival_class_id));
            string = string.replace("%name%", this.rival_name);
            string = string.replace("%date%", TimeUtils.toHeroRecordFormat(this.mtime));
            string = string.replace("%time%", String.format("%02d:%02d", this.elapsed_time / 60, this.elapsed_time % 60));
            string = string.replace("%victory_count%", mutableInt.toString());
            string = string.replace("%tie_count%", mutableInt3.toString());
            string = string.replace("%loss_count%", mutableInt2.toString());
            return string;
        }
    }
}
