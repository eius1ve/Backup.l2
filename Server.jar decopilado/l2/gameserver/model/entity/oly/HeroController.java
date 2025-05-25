/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.mutable.MutableInt
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.entity.oly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.xml.holder.SkillAcquireHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.database.mysql;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.entity.HeroDiary;
import l2.gameserver.model.entity.oly.CompetitionController;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.entity.oly.OlyController;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.Strings;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeroController {
    private static final Logger bX = LoggerFactory.getLogger(HeroController.class);
    public static final int[] HERO_WEAPONS = new int[]{6611, 6612, 6613, 6614, 6615, 6616, 6617, 6618, 6619, 6620, 6621, 9388, 9389, 9390};
    private static final String dk = "SELECT  `oly_heroes`.`char_id` AS `char_id`, `oly_nobles`.`char_name` AS `name`, `oly_nobles`.`class_id` AS `class_id`, `oly_heroes`.`count` AS `count`, `oly_heroes`.`played` AS `played`, `oly_heroes`.`active` AS `active`, `oly_heroes`.`message` AS `message` FROM    `oly_heroes`,`oly_nobles` WHERE   `oly_heroes`.`char_id` = `oly_nobles`.`char_id`";
    private static final String dl = "REPLACE INTO `oly_heroes` (`char_id`, `count`, `played`, `active`, `message`) VALUES (?, ?, ?, ?, ?)";
    private static final String dm = "DELETE FROM `oly_heroes` WHERE `char_id` = ?";
    private static HeroController a;
    private ArrayList<HeroRecord> d = new ArrayList();
    private ArrayList<HeroRecord> e = new ArrayList();
    private static Map<Integer, List<HeroDiary>> ba;
    private static Map<Integer, String> bb;

    public static HeroController getInstance() {
        if (a == null) {
            a = new HeroController();
        }
        return a;
    }

    private HeroController() {
        ba = new ConcurrentHashMap<Integer, List<HeroDiary>>();
        bb = new ConcurrentHashMap<Integer, String>();
        this.bD();
    }

    private synchronized Collection<NoblesController.NobleRecord> a() {
        bX.info("HeroController: Calculating heroes contenders.");
        HashMap<ClassId, NoblesController.NobleRecord> hashMap = new HashMap<ClassId, NoblesController.NobleRecord>();
        for (NoblesController.NobleRecord nobleRecord : NoblesController.getInstance().getNoblesRecords()) {
            try {
                if (nobleRecord.comp_done < Config.OLY_MIN_HERO_COMPS || nobleRecord.comp_win < Config.OLY_MIN_HERO_WIN) continue;
                ClassId classId = null;
                for (ClassId classId2 : ClassId.values()) {
                    if (classId2.getId() != nobleRecord.class_id || classId2.level() != 3) continue;
                    classId = classId2;
                }
                if (classId == null) {
                    bX.warn("HeroController: Not third or null ClassID for character '" + nobleRecord.char_name + "'");
                    continue;
                }
                if (hashMap.containsKey((Object)classId)) {
                    NoblesController.NobleRecord nobleRecord2 = (NoblesController.NobleRecord)hashMap.get((Object)classId);
                    if (nobleRecord.points_current <= nobleRecord2.points_current && (nobleRecord.points_current != nobleRecord2.points_current || nobleRecord.comp_win <= nobleRecord2.comp_win) && (nobleRecord.points_current != nobleRecord2.points_current || nobleRecord.comp_win != nobleRecord2.comp_win || nobleRecord.comp_done <= nobleRecord2.comp_done)) continue;
                    hashMap.put(classId, nobleRecord);
                    continue;
                }
                hashMap.put(classId, nobleRecord);
            }
            catch (Exception exception) {
                bX.warn("HeroController: Exception while claculating new heroes", (Throwable)exception);
            }
        }
        for (NoblesController.NobleRecord nobleRecord : hashMap.values()) {
            Log.add(String.format("HeroController: %s(%d) pretended to be a hero. points_current = %d", nobleRecord.char_name, nobleRecord.char_id, nobleRecord.points_current), "olympiad");
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(hashMap.values());
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bD() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(dk);
            while (resultSet.next()) {
                int n = resultSet.getInt("char_id");
                String string = resultSet.getString("name");
                int n2 = resultSet.getInt("class_id");
                int n3 = resultSet.getInt("count");
                boolean bl = resultSet.getInt("played") != 0;
                boolean bl2 = resultSet.getInt("active") != 0;
                String string2 = resultSet.getString("message");
                HeroRecord heroRecord = new HeroRecord(n, string, n2, n3, bl2, bl, string2);
                if (bl) {
                    this.d.add(heroRecord);
                }
                this.e.add(heroRecord);
            }
            this.bF();
        }
        catch (Exception exception) {
            try {
                bX.warn("Exception while loading heroes", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, statement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, statement, resultSet);
        }
        DbUtils.closeQuietly(connection, statement, resultSet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveHeroes() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(dl);
            for (HeroRecord heroRecord : this.e) {
                preparedStatement.setInt(1, heroRecord.char_id);
                preparedStatement.setInt(2, heroRecord.count);
                preparedStatement.setInt(3, heroRecord.played ? 1 : 0);
                preparedStatement.setInt(4, heroRecord.active ? 1 : 0);
                preparedStatement.setString(5, heroRecord.message);
                preparedStatement.executeUpdate();
            }
        }
        catch (Exception exception) {
            try {
                bX.warn("Exception while saving heroes", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public void renameHero(int n, String string) {
        for (HeroRecord heroRecord : this.d) {
            if (heroRecord == null || heroRecord.char_id != n) continue;
            heroRecord.name = string;
        }
    }

    private void bE() {
        bX.info("HeroController: Clearing previus season heroes.");
        mysql.set("UPDATE `oly_heroes` SET `played` = 0, `active` = 0");
        if (!this.d.isEmpty()) {
            for (HeroRecord heroRecord : this.d) {
                if (!heroRecord.active) continue;
                Player player = GameObjectsStorage.getPlayer(heroRecord.char_id);
                if (player != null) {
                    player.getInventory().unEquipItemInBodySlot(256L);
                    player.getInventory().unEquipItemInBodySlot(128L);
                    player.getInventory().unEquipItemInBodySlot(16384L);
                    player.getInventory().unEquipItemInBodySlot(65536L);
                    player.getInventory().unEquipItemInBodySlot(524288L);
                    player.getInventory().unEquipItemInBodySlot(262144L);
                    for (ItemInstance itemInstance : player.getInventory().getItems()) {
                        if (itemInstance == null || !itemInstance.isHeroWeapon()) continue;
                        player.getInventory().destroyItem(itemInstance);
                    }
                    for (ItemInstance itemInstance : player.getWarehouse().getItems()) {
                        if (itemInstance == null || itemInstance.isEquipable() || !itemInstance.isHeroWeapon()) continue;
                        player.getWarehouse().destroyItem(itemInstance);
                    }
                    player.unsetVar("CustomHeroEndTime");
                    player.setHero(false);
                    player.updatePledgeClass();
                    player.broadcastUserInfo(true, new UserInfoType[0]);
                    HeroController.Z(player);
                    HeroController.removeSkills(player, true);
                }
                heroRecord.played = false;
                heroRecord.active = false;
            }
        }
        this.saveHeroes();
        this.d.clear();
    }

    public synchronized void ComputeNewHeroNobleses() {
        bX.info("HeroController: Computing new heroes.");
        try {
            NoblesController.getInstance().SaveNobleses();
            this.saveHeroes();
            Collection<NoblesController.NobleRecord> collection = this.a();
            this.bE();
            for (NoblesController.NobleRecord nobleRecord : collection) {
                HeroRecord heroRecord = null;
                for (HeroRecord heroRecord2 : this.e) {
                    if (nobleRecord.char_id != heroRecord2.char_id) continue;
                    heroRecord = heroRecord2;
                }
                if (heroRecord == null) {
                    heroRecord = new HeroRecord(nobleRecord.char_id, nobleRecord.char_name, nobleRecord.class_id, 0, false, true, "");
                    this.e.add(heroRecord);
                }
                ++heroRecord.count;
                heroRecord.played = true;
                this.d.add(heroRecord);
            }
            this.saveHeroes();
            NoblesController.getInstance().TransactNewSeason();
            NoblesController.getInstance().ComputeRanks();
            NoblesController.getInstance().SaveNobleses();
            this.bF();
        }
        catch (Exception exception) {
            bX.warn("HeroController: Can't compute heroes.", (Throwable)exception);
        }
    }

    private void bF() {
        for (HeroRecord heroRecord : this.d) {
            if (heroRecord == null) continue;
            Map.Entry<Clan, Alliance> entry = ClanTable.getInstance().getClanAndAllianceByCharId(heroRecord.char_id);
            if (entry.getKey() != null) {
                heroRecord.clan_name = entry.getKey().getName();
                heroRecord.clan_crest = entry.getKey().getCrestId();
            } else {
                heroRecord.clan_name = "";
                heroRecord.clan_crest = 0;
            }
            if (entry.getValue() != null) {
                heroRecord.ally_name = entry.getValue().getAllyName();
                heroRecord.ally_crest = entry.getValue().getAllyCrestId();
                continue;
            }
            heroRecord.ally_name = "";
            heroRecord.ally_crest = 0;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void removeHero(Player player) {
        HeroRecord heroRecord = null;
        for (HeroRecord object2 : this.e) {
            if (object2.char_id != player.getObjectId()) continue;
            heroRecord = object2;
            break;
        }
        if (heroRecord == null) {
            return;
        }
        Object object3 = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            object3 = DatabaseFactory.getInstance().getConnection();
            PreparedStatement preparedStatement = object3.prepareStatement(dm);
            preparedStatement.setInt(1, heroRecord.char_id);
            preparedStatement.executeUpdate();
            this.e.remove(heroRecord);
            DbUtils.closeQuietly((Connection)object3, preparedStatement, resultSet);
        }
        catch (SQLException sQLException) {
            bX.warn("HeroController: Can't remove Hero ", (Throwable)sQLException);
        }
        finally {
            DbUtils.closeQuietly((Connection)object3, statement, resultSet);
        }
    }

    public Collection<HeroRecord> getCurrentHeroes() {
        return this.d;
    }

    public boolean isCurrentHero(Player player) {
        if (player == null) {
            return false;
        }
        if (this.d.isEmpty()) {
            return false;
        }
        return this.isCurrentHero(player.getObjectId());
    }

    public boolean isInactiveHero(Player player) {
        if (player == null) {
            return false;
        }
        return this.isInactiveHero(player.getObjectId());
    }

    public void activateHero(Player player) {
        if (player == null) {
            return;
        }
        if (this.d.isEmpty()) {
            return;
        }
        for (HeroRecord heroRecord : this.d) {
            if (heroRecord.char_id != player.getObjectId() || !heroRecord.played) continue;
            heroRecord.active = true;
            if (player.getBaseSubClass().getClassId() == player.getActiveClassId()) {
                HeroController.addSkills(player);
                player.sendSkillList();
            }
            player.setHero(true);
            player.broadcastPacket(new SocialAction(player.getObjectId(), 20016));
            player.updatePledgeClass();
            player.getPlayer().sendUserInfo(true);
            GameServer.getInstance().getListeners().fireEvent("activateHero", player);
            Clan clan = player.getClan();
            if (clan != null && clan.getLevel() >= Config.MIN_CLAN_LEVEL_FOR_REPUTATION && Config.ADD_CLAN_REPUTATION_POINT_ON_HERO_GAIN) {
                int n = player.getClan().incReputation(Config.AMOUNT_CLAN_REPUTATION_POINT_ON_HERO_GAIN, true, "Hero:activateHero:" + player);
                clan.broadcastToOtherOnlineMembers((L2GameServerPacket)((SystemMessage)new SystemMessage(SystemMsg.CLAN_MEMBER_C1_WAS_NAMED_A_HERO_S2_POINTS_HAVE_BEEN_ADDED_TO_YOUR_CLANS_REPUTATION_SCORE).addString(player.getName())).addNumber(n), player);
            }
            player.broadcastUserInfo(true, new UserInfoType[0]);
            this.saveHeroes();
        }
    }

    public boolean isCurrentHero(int n) {
        if (this.d.isEmpty()) {
            return false;
        }
        for (HeroRecord heroRecord : this.d) {
            if (heroRecord.char_id != n) continue;
            return heroRecord.active && heroRecord.played;
        }
        return false;
    }

    public boolean isInactiveHero(int n) {
        if (this.d.isEmpty()) {
            return false;
        }
        for (HeroRecord heroRecord : this.d) {
            if (heroRecord.char_id != n) continue;
            return heroRecord.played && !heroRecord.active;
        }
        return false;
    }

    public static void addSkills(Player player) {
        HeroController.addSkills(player, false);
    }

    public static void addSkills(Player player, boolean bl) {
        for (SkillLearn skillLearn : SkillAcquireHolder.getInstance().getAvailableSkills(player, AcquireType.HERO)) {
            Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
            if (skill == null || player.getSkillLevel(skill.getId()) >= skill.getLevel()) continue;
            player.addSkill(skill, false);
        }
        if (bl) {
            player.sendSkillList();
        }
    }

    public static void removeSkills(Player player) {
        HeroController.removeSkills(player, false);
    }

    public static void removeSkills(Player player, boolean bl) {
        for (SkillLearn skillLearn : SkillAcquireHolder.getInstance().getAllSkillLearn(player, player.getClassId(), AcquireType.HERO)) {
            Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
            if (skill == null) continue;
            player.removeSkill(skill, false);
        }
        if (bl) {
            player.sendSkillList();
        }
    }

    public void showHistory(Player player, int n, int n2) {
        CompetitionController.CompetitionResults competitionResults2;
        HeroRecord heroRecord = null;
        for (HeroRecord object2 : this.getCurrentHeroes()) {
            if (!object2.active || !object2.played || object2.class_id != n) continue;
            heroRecord = object2;
        }
        if (heroRecord == null) {
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("oly/monument_hero_history.htm");
        npcHtmlMessage.replace("%title%", StringHolder.getInstance().getNotNull(player, "hero.history"));
        Collection<CompetitionController.CompetitionResults> collection = heroRecord.getCompetitions();
        CompetitionController.CompetitionResults[] competitionResultsArray = collection.toArray(new CompetitionController.CompetitionResults[collection.size()]);
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        for (CompetitionController.CompetitionResults competitionResults2 : competitionResultsArray) {
            if (competitionResults2.result > 0) {
                ++n3;
                continue;
            }
            if (competitionResults2.result < 0) {
                ++n4;
                continue;
            }
            if (competitionResults2.result != 0) continue;
            ++n5;
        }
        npcHtmlMessage.replace("%wins%", String.valueOf(n3));
        npcHtmlMessage.replace("%ties%", String.valueOf(n5));
        npcHtmlMessage.replace("%losses%", String.valueOf(n4));
        int n6 = 15 * (n2 - 1);
        int n7 = 15 * n2;
        MutableInt mutableInt = new MutableInt(0);
        competitionResults2 = new MutableInt(0);
        MutableInt mutableInt2 = new MutableInt(0);
        StringBuilder stringBuilder = new StringBuilder(500);
        for (int i = 0; i < competitionResultsArray.length; ++i) {
            CompetitionController.CompetitionResults competitionResults3 = competitionResultsArray[i];
            if (competitionResults3.result > 0) {
                mutableInt.increment();
            } else if (competitionResults3.result < 0) {
                competitionResults2.increment();
            } else if (competitionResults3.result == 0) {
                mutableInt2.increment();
            }
            if (i < n6) continue;
            if (i >= n7) break;
            stringBuilder.append("<tr><td>");
            stringBuilder.append(competitionResults3.toString(player, mutableInt, (MutableInt)competitionResults2, mutableInt2));
            stringBuilder.append("</td></tr");
        }
        if (n6 > 0) {
            npcHtmlMessage.replace("%buttprev%", "<button value=\"&$1037;\" action=\"bypass %prev_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
            npcHtmlMessage.replace("%prev_bypass%", "_match?class=" + n + "&page=" + (n2 - 1));
        } else {
            npcHtmlMessage.replace("%buttprev%", "");
        }
        if (competitionResultsArray.length > n7) {
            npcHtmlMessage.replace("%buttnext%", "<button value=\"&$1038;\" action=\"bypass %next_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
            npcHtmlMessage.replace("%prev_bypass%", "_match?class=" + n + "&page=" + (n2 + 1));
        } else {
            npcHtmlMessage.replace("%buttnext%", "");
        }
        npcHtmlMessage.replace("%list%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadDiary(int n) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block5: {
            ArrayList<HeroDiary> arrayList = new ArrayList<HeroDiary>();
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM  `heroes_diary` WHERE `charId`=? ORDER BY `time` ASC");
                preparedStatement.setInt(1, n);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    long l = resultSet.getLong("time");
                    int n2 = resultSet.getInt("action");
                    int n3 = resultSet.getInt("param");
                    HeroDiary heroDiary = new HeroDiary(n2, l, n3);
                    arrayList.add(heroDiary);
                }
                ba.put(n, arrayList);
                if (!Config.DEBUG) break block5;
                bX.info("Hero System: Loaded " + arrayList.size() + " diary entries for Hero(object id: #" + n + ")");
            }
            catch (SQLException sQLException) {
                try {
                    bX.warn("Hero System: Couldnt load Hero Diary for CharId: " + n, (Throwable)sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    public void showHeroDiary(Player player, int n, int n2) {
        HeroRecord heroRecord = null;
        for (HeroRecord object : this.getCurrentHeroes()) {
            if (!object.active || !object.played || object.class_id != n) continue;
            heroRecord = object;
        }
        if (heroRecord == null) {
            return;
        }
        List<HeroDiary> list = ba.get(heroRecord.char_id);
        if (list != null) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
            npcHtmlMessage.setFile("oly/monument_hero_info.htm");
            npcHtmlMessage.replace("%title%", StringHolder.getInstance().getNotNull(player, "hero.diary"));
            npcHtmlMessage.replace("%heroname%", heroRecord.name);
            npcHtmlMessage.replace("%message%", heroRecord.message);
            ArrayList arrayList = new ArrayList(list);
            Collections.reverse(arrayList);
            boolean bl = true;
            StringBuilder stringBuilder = new StringBuilder(500);
            int n3 = 0;
            int n4 = 0;
            for (int i = (n2 - 1) * 10; i < arrayList.size(); ++i) {
                n4 = i;
                HeroDiary heroDiary = (HeroDiary)arrayList.get(i);
                Map.Entry<String, String> entry = heroDiary.toString(player);
                stringBuilder.append("<tr><td>");
                if (bl) {
                    stringBuilder.append("<table width=270 bgcolor=\"131210\">");
                } else {
                    stringBuilder.append("<table width=270>");
                }
                stringBuilder.append("<tr><td width=270><font color=\"LEVEL\">" + entry.getKey() + "</font></td></tr>");
                stringBuilder.append("<tr><td width=270>" + entry.getValue() + "</td></tr>");
                stringBuilder.append("<tr><td>&nbsp;</td></tr></table>");
                stringBuilder.append("</td></tr>");
                boolean bl2 = bl = !bl;
                if (++n3 >= 10) break;
            }
            if (n4 < arrayList.size() - 1) {
                npcHtmlMessage.replace("%buttprev%", "<button value=\"&$1037;\" action=\"bypass %prev_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
                npcHtmlMessage.replace("%prev_bypass%", "_diary?class=" + n + "&page=" + (n2 + 1));
            } else {
                npcHtmlMessage.replace("%buttprev%", "");
            }
            if (n2 > 1) {
                npcHtmlMessage.replace("%buttnext%", "<button value=\"&$1038;\" action=\"bypass %next_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
                npcHtmlMessage.replace("%next_bypass%", "_diary?class=" + n + "&page=" + (n2 - 1));
            } else {
                npcHtmlMessage.replace("%buttnext%", "");
            }
            npcHtmlMessage.replace("%list%", stringBuilder.toString());
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        }
    }

    public void addHeroDiary(int n, int n2, int n3) {
        this.a(n, n2, n3);
        List<HeroDiary> list = ba.get(n);
        if (list != null) {
            list.add(new HeroDiary(n2, System.currentTimeMillis(), n3));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(int n, int n2, int n3) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `heroes_diary` (`charId`, `time`, `action`, `param`) values(?,?,?,?)");
            preparedStatement.setInt(1, n);
            preparedStatement.setLong(2, System.currentTimeMillis());
            preparedStatement.setInt(3, n2);
            preparedStatement.setInt(4, n3);
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException sQLException) {
            try {
                bX.error("SQL exception while saving DiaryData.", (Throwable)sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public void setHeroMessage(int n, String string) {
        HeroRecord heroRecord = null;
        for (HeroRecord heroRecord2 : this.getCurrentHeroes()) {
            if (!heroRecord2.active || !heroRecord2.played || heroRecord2.char_id != n) continue;
            heroRecord = heroRecord2;
        }
        heroRecord.message = Strings.stripSlashes(string);
    }

    public static boolean isHaveHeroWeapon(Player player) {
        for (int n : HERO_WEAPONS) {
            if (player.getInventory().getCountOf(n) <= 0L) continue;
            return true;
        }
        return false;
    }

    private static void Z(Player player) {
        boolean bl = false;
        for (int n : HERO_WEAPONS) {
            while (player.getInventory().destroyItemByItemId(n, 1L)) {
                bl = true;
            }
            if (bl) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_DISAPPEARED_BECAUSE_ITS_TIME_PERIOD_HAS_EXPIRED).addItemName(n));
            }
            bl = false;
        }
    }

    public static void checkHeroWeaponary(Player player) {
        if (!player.isHero()) {
            HeroController.Z(player);
        }
    }

    public class HeroRecord {
        public int char_id;
        public int class_id;
        public int count;
        public boolean active;
        public boolean played;
        public String name;
        public String message;
        public String clan_name;
        public String ally_name;
        public int clan_crest;
        public int ally_crest;
        public Collection<CompetitionController.CompetitionResults> competitions;

        private HeroRecord(int n, String string, int n2, int n3, boolean bl, boolean bl2, String string2) {
            this.char_id = n;
            this.name = string;
            this.count = n3;
            this.class_id = n2;
            this.active = bl;
            this.played = bl2;
            this.message = string2;
        }

        public Collection<CompetitionController.CompetitionResults> getCompetitions() {
            if (this.competitions == null) {
                this.competitions = CompetitionController.getInstance().getCompetitionResults(this.char_id, OlyController.getInstance().getCurrentSeason() - 1);
            }
            return this.competitions;
        }
    }
}
