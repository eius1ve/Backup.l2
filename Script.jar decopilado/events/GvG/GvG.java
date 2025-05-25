/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.lang.reference.HardReferences
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.data.xml.holder.InstantZoneHolder
 *  l2.gameserver.data.xml.holder.ResidenceHolder
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.instancemanager.ServerVariables
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.entity.oly.ParticipantPool
 *  l2.gameserver.model.entity.residence.Castle
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.InstantZone
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.TimeUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.GvG;

import instances.GvGInstance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import l2.commons.dbutils.DbUtils;
import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.InstantZoneHolder;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.oly.ParticipantPool;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.InstantZone;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GvG
extends Functions
implements ScriptFile {
    private static final Logger o = LoggerFactory.getLogger(GvG.class);
    private static final String F = "GvG";
    public static final Location TEAM1_LOC = new Location(80296, 88504, -2880);
    public static final Location TEAM2_LOC = new Location(77704, 93400, -2880);
    public static final Location RETURN_LOC = new Location(43816, -48232, -822);
    private static boolean T = false;
    private static boolean V = false;
    private static long Z = 600000L;
    private static ScheduledFuture<?> u;
    private static ScheduledFuture<?> v;
    private static ScheduledFuture<?> w;
    private static ScheduledFuture<?> x;
    private static ScheduledFuture<?> y;
    private static List<HardReference<Player>> F;

    private static boolean isActive() {
        return GvG.IsActive((String)F);
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (GvG.SetActive((String)F, (boolean)true)) {
            System.out.println("Event: GvG started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.gvg.AnnounceEventStarted", null);
            o.info("Loaded Event: GvG");
            GvG.B();
        } else {
            player.sendMessage("Event 'Groupe vs Groupe' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (GvG.SetActive((String)F, (boolean)false)) {
            System.out.println("Event: GvG stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.gvg.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'Groupe vs Groupe' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    public void onLoad() {
        if (GvG.isActive()) {
            T = true;
            GvG.B();
            o.info("Loaded Event: GvG [state: activated]");
        } else {
            o.info("Loaded Event: GvG [state: deactivated]");
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    private static void B() {
        long l = 86400000L;
        Calendar calendar = Calendar.getInstance();
        String string = Config.EVENT_GVG_START_TIME;
        String[] stringArray = string.split(":");
        calendar.set(11, Integer.parseInt(stringArray[0]));
        calendar.set(12, stringArray.length > 1 ? Integer.parseInt(stringArray[1]) : 0);
        calendar.set(13, stringArray.length > 2 ? Integer.parseInt(stringArray[2]) : 0);
        long l2 = calendar.getTimeInMillis() - System.currentTimeMillis();
        if (l2 < 0L) {
            l2 += l;
        }
        if (u != null) {
            u.cancel(true);
        }
        u = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)((Object)new Launch()), l2, l);
        o.info("Event 'GvG' will start at " + TimeUtils.toSimpleFormat((long)(u.getDelay(TimeUnit.MILLISECONDS) + System.currentTimeMillis())) + ".");
    }

    private static boolean e() {
        for (Castle castle : ResidenceHolder.getInstance().getResidenceList(Castle.class)) {
            if (castle.getSiegeEvent() == null || !castle.getSiegeEvent().isInProgress()) continue;
            return false;
        }
        return true;
    }

    public static void activateEvent() {
        if (GvG.isActive() && GvG.e()) {
            v = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new RegTask()), Config.EVENT_GVG_REG_TIME);
            if (Config.EVENT_GVG_REG_TIME > 120000L) {
                if (Config.EVENT_GVG_REG_TIME > 300000L) {
                    y = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Countdown(5)), Config.EVENT_GVG_REG_TIME - 300000L);
                }
                w = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Countdown(2)), Config.EVENT_GVG_REG_TIME - 120000L);
                x = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Countdown(1)), Config.EVENT_GVG_REG_TIME - 60000L);
            }
            ServerVariables.set((String)F, (String)"on");
            o.info("Event 'GvG' activated.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.gvg.AnnounceEventStartRegistration", null);
            Announcements.getInstance().announceByCustomMessage("scripts.events.gvg.regtime", new String[]{String.valueOf(Config.EVENT_GVG_REG_TIME / 60000L)});
            T = true;
            V = true;
        }
        o.info("Event 'GvG' will start next at " + TimeUtils.toSimpleFormat((long)(u.getDelay(TimeUnit.MILLISECONDS) + System.currentTimeMillis())) + ".");
    }

    public static void deactivateEvent() {
        if (GvG.isActive()) {
            GvG.C();
            ServerVariables.unset((String)F);
            o.info("Event 'GvG' canceled.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.gvg.eventsiscanceled", null);
            T = false;
            V = false;
            F.clear();
        }
    }

    public void showStats() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (!GvG.isActive()) {
            player.sendMessage("Groupe vs Groupe event is not launched");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string = "<button value=\"Refresh\" action=\"bypass -h scripts_events.GvG.GvG:showStats\" width=60 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">";
        String string2 = "<button value=\"Start Now\" action=\"bypass -h scripts_events.GvG.GvG:startNow\" width=60 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">";
        int n = 0;
        if (!F.isEmpty()) {
            for (Player player2 : HardReferences.unwrap((Collection)((Object)F))) {
                if (!player2.isInParty()) continue;
                stringBuilder.append("*").append(player2.getName()).append("*").append(" | group members: ").append(player2.getParty().getMemberCount()).append("\n\n");
                ++n;
            }
            GvG.show((String)("There are " + n + " group leaders who registered for the event:\n\n" + stringBuilder + "\n\n" + string + "\n\n" + string2), (Player)player, null, (Object[])new Object[0]);
        } else {
            GvG.show((String)("There are no participants at the time\n\n" + string), (Player)player, null, (Object[])new Object[0]);
        }
    }

    public void startNow() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (!GvG.isActive() || !GvG.e()) {
            player.sendMessage("Groupe vs Groupe event is not launched");
            return;
        }
        GvG.prepare();
    }

    public void addGroup() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!V) {
            player.sendMessage(new CustomMessage("scripts.event.gvg.notactived", player, new Object[0]));
            return;
        }
        if (F.contains(player.getRef())) {
            player.sendMessage(new CustomMessage("scripts.event.gvg.registred", player, new Object[0]));
            return;
        }
        if (!player.isInParty()) {
            player.sendMessage(new CustomMessage("scripts.event.gvg.notinparty", player, new Object[0]));
            return;
        }
        if (!player.getParty().isLeader(player)) {
            player.sendMessage(new CustomMessage("scripts.event.gvg.onlypartyleader", player, new Object[0]));
            return;
        }
        if (player.getParty().isInCommandChannel()) {
            player.sendMessage(new CustomMessage("scripts.event.gvg.removecommandchannel", player, new Object[0]));
            return;
        }
        if (F.size() >= Config.EVENT_GVG_GROUPS_LIMIT) {
            player.sendMessage(new CustomMessage("scripts.event.gvg.limitpartycount", player, new Object[0]));
            return;
        }
        List list = player.getParty().getPartyMembers();
        String[] stringArray = new String[]{"\u043d\u0435 \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u0438\u0433\u0440\u0435", "\u043d\u0435 \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u0433\u0440\u0443\u043f\u043f\u0435", "\u0441\u043e\u0441\u0442\u043e\u0438\u0442 \u0432 \u043d\u0435\u043f\u043e\u043b\u043d\u043e\u0439 \u0433\u0440\u0443\u043f\u043f\u0435. \u041c\u0438\u043d\u0438\u043c\u0430\u043b\u044c\u043d\u043e\u0435 \u043a\u043e\u043b-\u0432\u043e \u0447\u043b\u0435\u043d\u043e\u0432 \u0433\u0440\u0443\u043f\u043f\u044b - 6.", "\u043d\u0435 \u044f\u0432\u043b\u044f\u0435\u0442\u0441\u044f \u043b\u0438\u0434\u0435\u0440\u043e\u043c \u0433\u0440\u0443\u043f\u043f\u044b, \u043f\u043e\u0434\u0430\u0432\u0430\u0432\u0448\u0435\u0439 \u0437\u0430\u044f\u0432\u043a\u0443", "\u043d\u0435 \u0441\u043e\u043e\u0442\u0432\u0435\u0442\u0441\u0442\u0432\u0443\u0435\u0442 \u0442\u0440\u0435\u0431\u043e\u0432\u0430\u043d\u0438\u044f\u043c \u0443\u0440\u043e\u0432\u043d\u0435\u0439 \u0434\u043b\u044f \u0442\u0443\u0440\u043d\u0438\u0440\u0430", "\u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0435\u0442 \u0435\u0437\u0434\u043e\u0432\u043e\u0435 \u0436\u0438\u0432\u043e\u0442\u043d\u043e\u0435, \u0447\u0442\u043e \u043f\u0440\u043e\u0442\u0438\u0432\u043e\u0440\u0435\u0447\u0438\u0442 \u0442\u0440\u0435\u0431\u043e\u0432\u0430\u043d\u0438\u044f\u043c \u0442\u0443\u0440\u043d\u0438\u0440\u0430", "\u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u0434\u0443\u044d\u043b\u0438, \u0447\u0442\u043e \u043f\u0440\u043e\u0442\u0438\u0432\u043e\u0440\u0435\u0447\u0438\u0442 \u0442\u0440\u0435\u0431\u043e\u0432\u0430\u043d\u0438\u044f\u043c \u0442\u0443\u0440\u043d\u0438\u0440\u0430", "\u043f\u0440\u0438\u043d\u0438\u043c\u0430\u0435\u0442 \u0443\u0447\u0430\u0441\u0442\u0438\u0435 \u0432 \u0434\u0440\u0443\u0433\u043e\u043c \u044d\u0432\u0435\u043d\u0442\u0435, \u0447\u0442\u043e \u043f\u0440\u043e\u0442\u0438\u0432\u043e\u0440\u0435\u0447\u0438\u0442 \u0442\u0440\u0435\u0431\u043e\u0432\u0430\u043d\u0438\u044f\u043c \u0442\u0443\u0440\u043d\u0438\u0440\u0430", "\u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u0441\u043f\u0438\u0441\u043a\u0435 \u043e\u0436\u0438\u0434\u0430\u043d\u0438\u044f \u041e\u043b\u0438\u043c\u043f\u0438\u0430\u0434\u044b \u0438\u043b\u0438 \u043f\u0440\u0438\u043d\u0438\u043c\u0430\u0435\u0442 \u0443\u0447\u0430\u0441\u0442\u0438\u0435 \u0432 \u043d\u0435\u0439", "\u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u0441\u043e\u0441\u0442\u043e\u044f\u043d\u0438\u0438 \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0430\u0446\u0438\u0438, \u0447\u0442\u043e \u043f\u0440\u043e\u0442\u0438\u0432\u043e\u0440\u0435\u0447\u0438\u0442 \u0442\u0440\u0435\u0431\u043e\u0432\u0430\u043d\u0438\u044f\u043c \u0442\u0443\u0440\u043d\u0438\u0440\u0430", "\u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 Dimensional Rift, \u0447\u0442\u043e \u043f\u0440\u043e\u0442\u0438\u0432\u043e\u0440\u0435\u0447\u0438\u0442 \u0442\u0440\u0435\u0431\u043e\u0432\u0430\u043d\u0438\u044f\u043c \u0442\u0443\u0440\u043d\u0438\u0440\u0430", "\u043e\u0431\u043b\u0430\u0434\u0430\u0435\u0442 \u041f\u0440\u043e\u043a\u043b\u044f\u0442\u044b\u043c \u041e\u0440\u0443\u0436\u0438\u0435\u043c, \u0447\u0442\u043e \u043f\u0440\u043e\u0442\u0438\u0432\u043e\u0440\u0435\u0447\u0438\u0442 \u0442\u0440\u0435\u0431\u043e\u0432\u0430\u043d\u0438\u044f\u043c \u0442\u0443\u0440\u043d\u0438\u0440\u0430", "\u043d\u0435 \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u043c\u0438\u0440\u043d\u043e\u0439 \u0437\u043e\u043d\u0435", "\u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u0440\u0435\u0436\u0438\u043c\u0435 \u043e\u0431\u043e\u0437\u0440\u0435\u0432\u0430\u043d\u0438\u044f"};
        for (Player player2 : list) {
            int n = GvG.a(player2, false);
            if (n == 0) continue;
            player.sendMessage("Player " + player2.getName() + " " + stringArray[n - 1]);
            return;
        }
        F.add(player.getRef());
        player.getParty().broadcastMessageToPartyMembers("\u0412\u0430\u0448\u0430 \u0433\u0440\u0443\u043f\u043f\u0430 \u0432\u043d\u0435\u0441\u0435\u043d\u0430 \u0432 \u0441\u043f\u0438\u0441\u043e\u043a \u043e\u0436\u0438\u0434\u0430\u043d\u0438\u044f. \u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430, \u043d\u0435 \u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u0443\u0439\u0442\u0435\u0441\u044c \u0432 \u0434\u0440\u0443\u0433\u0438\u0445 \u0438\u0432\u0435\u043d\u0442\u0430\u0445 \u0438 \u043d\u0435 \u0443\u0447\u0430\u0441\u0442\u0432\u0443\u0439\u0442\u0435 \u0432 \u0434\u0443\u044d\u043b\u044f\u0445 \u0434\u043e \u043d\u0430\u0447\u0430\u043b\u0430 \u0442\u0443\u0440\u043d\u0438\u0440\u0430.");
    }

    private static void C() {
        if (v != null) {
            v.cancel(false);
            v = null;
        }
        if (w != null) {
            w.cancel(false);
            w = null;
        }
        if (x != null) {
            x.cancel(false);
            x = null;
        }
        if (y != null) {
            y.cancel(false);
            y = null;
        }
    }

    private static void prepare() {
        GvG.E();
        GvG.D();
        if (GvG.isActive()) {
            GvG.C();
            T = false;
            V = false;
        }
        if (F.size() < 2) {
            F.clear();
            Announcements.getInstance().announceByCustomMessage("scripts.events.gvg.tournamentcanceled", null);
            return;
        }
        Announcements.getInstance().announceByCustomMessage("scripts.events.gvg.tiurnamentstated", null);
        GvG.start();
    }

    private static int a(Player player, boolean bl) {
        if (!player.isOnline()) {
            return 1;
        }
        if (!player.isInParty()) {
            return 2;
        }
        if (bl && (player.getParty() == null || !player.getParty().isLeader(player))) {
            return 4;
        }
        if (player.getParty() == null || player.getParty().getMemberCount() < Config.EVENT_GVG_MIN_PARTY_SIZE) {
            return 3;
        }
        if (player.getLevel() < Config.EVENT_GVG_MIN_LEVEL || player.getLevel() > Config.EVENT_GVG_MAX_LEVEL) {
            return 5;
        }
        if (player.isMounted()) {
            return 6;
        }
        if (player.isInDuel()) {
            return 7;
        }
        if (player.getTeam() != TeamType.NONE) {
            return 8;
        }
        if (player.isOlyParticipant() || ParticipantPool.getInstance().isRegistred(player)) {
            return 9;
        }
        if (player.isTeleporting()) {
            return 10;
        }
        if (player.getParty().isInDimensionalRift()) {
            return 11;
        }
        if (player.isCursedWeaponEquipped()) {
            return 12;
        }
        if (!player.isInPeaceZone()) {
            return 13;
        }
        if (player.isInObserverMode()) {
            return 14;
        }
        return 0;
    }

    private static void D() {
        int n;
        Player player;
        if (F.size() % 2 != 0 && (player = (Player)((HardReference)F.remove(n = Rnd.get((int)F.size()))).get()) != null) {
            player.sendMessage(new CustomMessage("scripts.events.gvg.partyExpelledSorry", player, new Object[0]));
        }
        for (n = 0; n < F.size(); ++n) {
            int n2 = Rnd.get((int)F.size());
            F.set(n, F.set(n2, (HardReference)F.get(n)));
        }
    }

    private static void E() {
        block0: for (Player player : HardReferences.unwrap((Collection)((Object)F))) {
            if (GvG.a(player, true) != 0) {
                F.remove(player.getRef());
                continue;
            }
            for (Player player2 : player.getParty().getPartyMembers()) {
                if (GvG.a(player2, false) == 0) continue;
                player.sendMessage(new CustomMessage("scripts.events.gvg.disqualified", player, new Object[0]));
                F.remove(player.getRef());
                continue block0;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateWinner(Player player) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO event_data(charId, score) VALUES (?,1) ON DUPLICATE KEY UPDATE score=score+1");
            preparedStatement.setInt(1, player.getObjectId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
    }

    private static void start() {
        int n = 504;
        InstantZone instantZone = InstantZoneHolder.getInstance().getInstantZone(n);
        if (instantZone == null) {
            o.warn("GvG: InstanceZone : " + n + " not found!");
            return;
        }
        for (int i = 0; i < F.size(); i += 2) {
            Player player = (Player)((HardReference)F.get(i)).get();
            Player player2 = (Player)((HardReference)F.get(i + 1)).get();
            GvGInstance gvGInstance = new GvGInstance();
            gvGInstance.setTeam1(player.getParty());
            gvGInstance.setTeam2(player2.getParty());
            gvGInstance.init(instantZone);
            gvGInstance.setReturnLoc(RETURN_LOC);
            for (Player player3 : player.getParty().getPartyMembers()) {
                Functions.unRide((Player)player3);
                Functions.unSummonPet((Player)player3, (boolean)true);
                player3.setTransformation(0);
                player3.setInstanceReuse(n, System.currentTimeMillis());
                player3.dispelBuffs();
                player3.teleToLocation(Location.findPointToStay((Location)TEAM1_LOC, (int)0, (int)150, (int)gvGInstance.getGeoIndex()), (Reflection)gvGInstance);
            }
            for (Player player3 : player2.getParty().getPartyMembers()) {
                Functions.unRide((Player)player3);
                Functions.unSummonPet((Player)player3, (boolean)true);
                player3.setTransformation(0);
                player3.setInstanceReuse(n, System.currentTimeMillis());
                player3.dispelBuffs();
                player3.teleToLocation(Location.findPointToStay((Location)TEAM2_LOC, (int)0, (int)150, (int)gvGInstance.getGeoIndex()), (Reflection)gvGInstance);
            }
            gvGInstance.start();
        }
        F.clear();
        o.info("GvG: Event started successfuly.");
    }

    static {
        F = new CopyOnWriteArrayList();
    }

    public static class Launch
    extends RunnableImpl {
        public void runImpl() {
            GvG.activateEvent();
        }
    }

    public static class RegTask
    extends RunnableImpl {
        public void runImpl() throws Exception {
            GvG.prepare();
        }
    }

    public static class Countdown
    extends RunnableImpl {
        int _timer;

        public Countdown(int n) {
            this._timer = n;
        }

        public void runImpl() throws Exception {
            Announcements.getInstance().announceByCustomMessage("scripts.events.gvg.timeexpend", new String[]{Integer.toString(this._timer)});
        }
    }
}
