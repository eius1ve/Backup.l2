/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 */
package quests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import l2.commons.dbutils.DbUtils;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;

public class _503_PursuitClanAmbition
extends Quest
implements ScriptFile {
    private final int blB = 3866;
    private final int blC = 3842;
    private final int blD = 3841;
    private final int blE = 3840;
    private final int blF = 3839;
    private final int blG = 3843;
    private final int blH = 3871;
    private final short b = (short)3867;
    private final short c = (short)3838;
    private final short d = (short)3846;
    private final short e = (short)3844;
    private final short f = (short)3845;
    private final int blI = 3868;
    private final int blJ = 3847;
    private final int blK = 3869;
    private final int blL = 3870;
    private final int[] bO = new int[]{3839, 3840, 3841, 3842};
    private final int blM = 30760;
    private final int blN = 30645;
    private final int blO = 30758;
    private final int blP = 30759;
    private final int blQ = 30761;
    private final int blR = 30762;
    private final int blS = 30763;
    private final int blT = 30512;
    private final int blU = 30764;
    private final int blV = 30868;
    private final int blW = 30765;
    private final int blX = 30766;
    private final int blY = 20282;
    private final int blZ = 20243;
    private final int bma = 20137;
    private final int bmb = 20285;
    private final int bmc = 27178;
    private final int bmd = 20668;
    private final int bme = 27179;
    private final int bmf = 27181;
    private final int bmg = 20654;
    private final int bmh = 20656;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _503_PursuitClanAmbition() {
        super(2);
        this.addStartNpc(30760);
        this.addTalkId(new int[]{30645});
        this.addTalkId(new int[]{30758});
        this.addTalkId(new int[]{30759});
        this.addTalkId(new int[]{30761});
        this.addTalkId(new int[]{30762});
        this.addTalkId(new int[]{30763});
        this.addTalkId(new int[]{30512});
        this.addTalkId(new int[]{30764});
        this.addTalkId(new int[]{30868});
        this.addTalkId(new int[]{30765});
        this.addTalkId(new int[]{30766});
        this.addKillId(new int[]{20282, 20243, 20137, 20285, 27178, 20654, 20656, 20668, 27179, 27181});
        int n = 3839;
        while (n <= 3848) {
            this.addQuestItem(new int[]{n++});
        }
        n = 3866;
        while (n <= 3869) {
            this.addQuestItem(new int[]{n++});
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void suscribe_members(QuestState questState) {
        block8: {
            int n = questState.getPlayer().getClan().getClanId();
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            PreparedStatement preparedStatement2 = null;
            ResultSet resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT obj_Id FROM characters WHERE clanid=? AND online=0");
                preparedStatement2 = connection.prepareStatement("REPLACE INTO character_quests (char_id,name,var,value) VALUES (?,?,?,?)");
                preparedStatement.setInt(1, n);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int n2 = resultSet.getInt("obj_Id");
                    try {
                        preparedStatement2.setInt(1, n2);
                        preparedStatement2.setString(2, this.getName());
                        preparedStatement2.setString(3, "<state>");
                        preparedStatement2.setString(4, "Started");
                        preparedStatement2.executeUpdate();
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                DbUtils.closeQuietly((Statement)preparedStatement2);
            }
            catch (Exception exception) {
                exception.printStackTrace();
                break block8;
            }
            finally {
                DbUtils.closeQuietly(preparedStatement2);
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void offlineMemberExit(QuestState questState) {
        int n = questState.getPlayer().getClan().getClanId();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM character_quests WHERE name=? AND char_id IN (SELECT obj_id FROM characters WHERE clanId=? AND online=0)");
            preparedStatement.setString(1, this.getName());
            preparedStatement.setInt(2, n);
            preparedStatement.executeUpdate();
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

    public Player getLeader(QuestState questState) {
        Player player = questState.getPlayer();
        if (player == null) {
            return null;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            return null;
        }
        return clan.getLeader().getPlayer();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getLeaderVar(QuestState questState, String string) {
        int n;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block13: {
            int n2;
            Player player;
            boolean bl = "cond".equalsIgnoreCase(string);
            try {
                player = this.getLeader(questState);
                if (player != null) {
                    if (bl) {
                        return player.getQuestState(this.getName()).getCond();
                    }
                    return player.getQuestState(this.getName()).getInt(string);
                }
            }
            catch (Exception exception) {
                return -1;
            }
            player = questState.getPlayer().getClan();
            if (player == null) {
                return -1;
            }
            int n3 = player.getLeaderId();
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT value FROM character_quests WHERE char_id=? AND var=? AND name=?");
                preparedStatement.setInt(1, n3);
                preparedStatement.setString(2, string);
                preparedStatement.setString(3, this.getName());
                int n4 = -1;
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    n4 = resultSet.getInt("value");
                    if (bl && (n4 & Integer.MIN_VALUE) != 0) {
                        n4 &= Integer.MAX_VALUE;
                        for (n2 = 1; n2 < 32; ++n2) {
                            if ((n4 >>= 1) != 0) continue;
                            n = n2;
                            break block13;
                        }
                    }
                }
                n2 = n4;
            }
            catch (Exception exception) {
                try {
                    exception.printStackTrace();
                    n2 = -1;
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
                return n2;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
            return n2;
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setLeaderVar(QuestState questState, String string, String string2) {
        Clan clan = questState.getPlayer().getClan();
        if (clan == null) {
            return;
        }
        Player player = clan.getLeader().getPlayer();
        if (player != null) {
            if ("cond".equalsIgnoreCase(string)) {
                player.getQuestState(this.getName()).setCond(Integer.parseInt(string2));
                return;
            }
            player.getQuestState(this.getName()).set(string, string2);
            return;
        }
        int n = questState.getPlayer().getClan().getLeaderId();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE character_quests SET value=? WHERE char_id=? AND var=? AND name=?");
            preparedStatement.setString(1, string2);
            preparedStatement.setInt(2, n);
            preparedStatement.setString(3, string);
            preparedStatement.setString(4, this.getName());
            preparedStatement.executeUpdate();
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
            return;
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
        return;
    }

    public boolean checkEggs(QuestState questState) {
        int n = 0;
        for (int n2 : this.bO) {
            if (questState.getQuestItemsCount(n2) <= 9L) continue;
            ++n;
        }
        return n > 3;
    }

    public void giveItem(int n, long l, QuestState questState) {
        Player player = questState.getPlayer();
        if (player == null) {
            return;
        }
        Player player2 = this.getLeader(questState);
        if (player2 == null) {
            return;
        }
        if (player.getDistance((GameObject)player2) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) {
            return;
        }
        QuestState questState2 = player2.getQuestState(((Object)((Object)this)).getClass());
        if (questState2 == null) {
            return;
        }
        long l2 = questState2.getQuestItemsCount(n);
        if (l2 < l) {
            questState2.giveItems(n, 1L);
            if (l2 == l - 1L) {
                questState2.playSound("ItemSound.quest_middle");
            } else {
                questState2.playSound("ItemSound.quest_itemget");
            }
        }
    }

    public String exit503(boolean bl, QuestState questState) {
        if (bl) {
            questState.giveItems(3870, 1L);
            questState.addExpAndSp(0L, 250000L);
            questState.unset("cond");
            questState.unset("Fritz");
            questState.unset("Lutz");
            questState.unset("Kurtz");
            questState.unset("ImpGraveKeeper");
            questState.exitCurrentQuest(false);
        } else {
            questState.exitCurrentQuest(true);
        }
        questState.takeItems(3869, -1L);
        try {
            List list = questState.getPlayer().getClan().getOnlineMembers(0);
            for (Player player : list) {
                QuestState questState2;
                if (player == null || (questState2 = player.getQuestState(this.getName())) == null) continue;
                questState2.exitCurrentQuest(true);
            }
            this.offlineMemberExit(questState);
        }
        catch (Exception exception) {
            return "You dont have any members in your Clan, so you can't finish the Pursuit of Aspiration";
        }
        return "Congratulations, you have finished the Pursuit of Clan Ambition";
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("30760-08.htm")) {
            questState.giveItems(3866, 1L);
            questState.setCond(1);
            questState.set("Fritz", "1");
            questState.set("Lutz", "1");
            questState.set("Kurtz", "1");
            questState.set("ImpGraveKeeper", "1");
            questState.setState(2);
        } else if (string.equalsIgnoreCase("30760-12.htm")) {
            questState.giveItems(3867, 1L);
            questState.setCond(4);
        } else if (string.equalsIgnoreCase("30760-16.htm")) {
            questState.giveItems(3868, 1L);
            questState.setCond(7);
        } else if (string.equalsIgnoreCase("30760-20.htm")) {
            this.exit503(true, questState);
        } else if (string.equalsIgnoreCase("30760-22.htm")) {
            questState.setCond(13);
        } else if (string.equalsIgnoreCase("30760-23.htm")) {
            this.exit503(true, questState);
        } else if (string.equalsIgnoreCase("30645-03.htm")) {
            questState.takeItems(3866, -1L);
            questState.setCond(2);
            this.suscribe_members(questState);
            List list = questState.getPlayer().getClan().getOnlineMembers(questState.getPlayer().getObjectId());
            for (Player player : list) {
                this.newQuestState(player, 2);
            }
        } else if (string.equalsIgnoreCase("30763-03.htm")) {
            if (questState.getInt("Kurtz") == 1) {
                string2 = "30763-02.htm";
                questState.giveItems(3839, 6L);
                questState.giveItems(3843, 1L);
                questState.set("Kurtz", "2");
            }
        } else if (string.equalsIgnoreCase("30762-03.htm")) {
            int n = questState.getInt("Lutz");
            if (n == 1) {
                string2 = "30762-02.htm";
                questState.giveItems(3839, 4L);
                questState.giveItems(3840, 3L);
                questState.set("Lutz", "2");
            }
            questState.addSpawn(27178, npcInstance.getLoc().x, npcInstance.getLoc().y, npcInstance.getLoc().z, Location.getRandomHeading(), 300, 120000);
            questState.addSpawn(27178, npcInstance.getLoc().x, npcInstance.getLoc().y, npcInstance.getLoc().z, Location.getRandomHeading(), 300, 120000);
        } else if (string.equalsIgnoreCase("30761-03.htm")) {
            int n = questState.getInt("Fritz");
            if (n == 1) {
                string2 = "30761-02.htm";
                questState.giveItems(3840, 3L);
                questState.set("Fritz", "2");
            }
            questState.addSpawn(27178, npcInstance.getLoc().x, npcInstance.getLoc().y, npcInstance.getLoc().z, Location.getRandomHeading(), 300, 120000);
            questState.addSpawn(27178, npcInstance.getLoc().x, npcInstance.getLoc().y, npcInstance.getLoc().z, Location.getRandomHeading(), 300, 120000);
        } else if (string.equalsIgnoreCase("30512-03.htm")) {
            questState.takeItems(3843, -1L);
            questState.giveItems(3871, 1L);
            questState.set("Kurtz", "3");
        } else if (string.equalsIgnoreCase("30764-03.htm")) {
            questState.takeItems(3867, -1L);
            questState.setCond(5);
            questState.set("Kurtz", "3");
        } else if (string.equalsIgnoreCase("30764-05.htm")) {
            questState.takeItems(3867, -1L);
            questState.setCond(5);
        } else if (string.equalsIgnoreCase("30764-06.htm")) {
            questState.takeItems(3871, -1L);
            questState.set("Kurtz", "4");
            questState.giveItems(3838, 1L);
        } else if (string.equalsIgnoreCase("30868-04.htm")) {
            questState.takeItems(3868, -1L);
            questState.setCond(8);
        } else if (string.equalsIgnoreCase("30868-06a.htm")) {
            questState.setCond(10);
        } else if (string.equalsIgnoreCase("30868-10.htm")) {
            questState.setCond(12);
        } else if (string.equalsIgnoreCase("30766-04.htm")) {
            questState.setCond(9);
            NpcInstance npcInstance2 = questState.findTemplate(30766);
            if (npcInstance2 != null) {
                Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"50338", (Object[])new Object[0]);
            }
            if ((npcInstance2 = questState.findTemplate(30759)) != null) {
                Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"50341", (Object[])new Object[0]);
            }
            if ((npcInstance2 = questState.findTemplate(30758)) != null) {
                Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"50340", (Object[])new Object[0]);
            }
        } else if (string.equalsIgnoreCase("30766-08.htm")) {
            questState.takeItems(3869, -1L);
            this.exit503(false, questState);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        String string = "noquest";
        boolean bl = questState.getPlayer().isClanLeader();
        if (n2 == 1 && n == 30760) {
            if (questState.getPlayer().getClan() != null) {
                if (bl) {
                    int n3 = questState.getPlayer().getClan().getLevel();
                    if (questState.getQuestItemsCount(3870) > 0L) {
                        string = "30760-03.htm";
                        questState.exitCurrentQuest(true);
                    } else if (n3 > 3) {
                        string = "30760-04.htm";
                    } else {
                        string = "30760-02.htm";
                        questState.exitCurrentQuest(true);
                    }
                } else {
                    string = "30760-04t.htm";
                    questState.exitCurrentQuest(true);
                }
            } else {
                string = "30760-01.htm";
                questState.exitCurrentQuest(true);
            }
            return string;
        }
        if (questState.getPlayer().getClan() != null && questState.getPlayer().getClan().getLevel() == 5) {
            return "completed";
        }
        if (bl) {
            if (questState.getCond() == 0) {
                questState.setCond(1);
            }
            if (questState.get("Kurtz") == null) {
                questState.set("Kurtz", "1");
            }
            if (questState.get("Lutz") == null) {
                questState.set("Lutz", "1");
            }
            if (questState.get("Fritz") == null) {
                questState.set("Fritz", "1");
            }
            int n4 = questState.getCond();
            int n5 = questState.getInt("Kurtz");
            int n6 = questState.getInt("Lutz");
            int n7 = questState.getInt("Fritz");
            if (n == 30760) {
                string = n4 == 1 ? "30760-09.htm" : (n4 == 2 ? "30760-10.htm" : (n4 == 3 ? "30760-11.htm" : (n4 == 4 ? "30760-13.htm" : (n4 == 5 ? "30760-14.htm" : (n4 == 6 ? "30760-15.htm" : (n4 == 7 ? "30760-17.htm" : (n4 == 12 ? "30760-19.htm" : (n4 == 13 ? "30760-24.htm" : "30760-18.htm"))))))));
            } else if (n == 30645) {
                if (n4 == 1) {
                    string = "30645-02.htm";
                } else if (n4 == 2) {
                    if (this.checkEggs(questState)) {
                        string = "30645-05.htm";
                        questState.setCond(3);
                        questState.playSound("ItemSound.quest_middle");
                        for (int n8 : this.bO) {
                            questState.takeItems(n8, -1L);
                        }
                    } else {
                        string = "30645-04.htm";
                    }
                } else {
                    string = n4 == 3 ? "30645-07.htm" : "30645-08.htm";
                }
            } else if (n == 30762 && n4 == 2) {
                string = "30762-01.htm";
            } else if (n == 30763 && n4 == 2) {
                string = "30763-01.htm";
            } else if (n == 30761 && n4 == 2) {
                string = "30761-01.htm";
            } else if (n == 30512) {
                string = n5 == 1 ? "30512-01.htm" : (n5 == 2 ? "30512-02.htm" : "30512-04.htm");
            } else if (n == 30764) {
                if (n4 == 4) {
                    string = n5 > 2 ? "30764-04.htm" : "30764-02.htm";
                } else if (n4 == 5) {
                    if (questState.getQuestItemsCount(3846) > 9L && questState.getQuestItemsCount(3844) > 9L) {
                        string = "30764-08.htm";
                        questState.takeItems(3846, -1L);
                        questState.takeItems(3844, -1L);
                        questState.takeItems(3843, -1L);
                        questState.setCond(6);
                    } else {
                        string = "30764-07.htm";
                    }
                } else if (n4 == 6) {
                    string = "30764-09.htm";
                }
            } else if (n == 30868) {
                if (n4 == 7) {
                    string = "30868-02.htm";
                } else if (n4 == 8) {
                    string = "30868-05.htm";
                } else if (n4 == 9) {
                    string = "30868-06.htm";
                } else if (n4 == 10) {
                    string = "30868-08.htm";
                } else if (n4 == 11) {
                    string = "30868-09.htm";
                } else if (n4 == 12) {
                    string = "30868-11.htm";
                }
            } else if (n == 30766) {
                if (n4 == 8) {
                    string = "30766-02.htm";
                } else if (n4 == 9) {
                    string = "30766-05.htm";
                } else if (n4 == 10) {
                    string = "30766-06.htm";
                } else if (n4 == 11 || n4 == 12 || n4 == 13) {
                    string = "30766-07.htm";
                }
            } else if (n == 30765) {
                if (questState.getCond() == 10) {
                    if (questState.getQuestItemsCount(3847) < 6L) {
                        string = "30765-03a.htm";
                    } else if (questState.getInt("ImpGraveKeeper") == 3) {
                        string = "30765-02.htm";
                        questState.setCond(11);
                        questState.takeItems(3847, 6L);
                        questState.giveItems(3869, 1L);
                    } else {
                        string = "<html><head><body>(You and your Clan didn't kill the Imperial Gravekeeper by your own, do it try again.)</body></html>";
                    }
                } else {
                    string = "<html><head><body>(You already have the Scepter of Judgement.)</body></html>";
                }
            } else if (n == 30759) {
                string = "30759-01.htm";
            } else if (n == 30758) {
                string = "30758-01.htm";
            }
            return string;
        }
        int n9 = this.getLeaderVar(questState, "cond");
        if (n == 30645 && (n9 == 1 || n9 == 2 || n9 == 3)) {
            string = "30645-01.htm";
        } else if (n == 30868) {
            if (n9 == 9 || n9 == 10) {
                string = "30868-07.htm";
            } else if (n9 == 7) {
                string = "30868-01.htm";
            }
        } else if (n == 30764 && n9 == 4) {
            string = "30764-01.htm";
        } else if (n == 30766 && n9 == 8) {
            string = "30766-01.htm";
        } else if (n == 30512 && n9 > 2 && n9 < 6) {
            string = "30512-01a.htm";
        } else if (n == 30765 && n9 == 10) {
            string = "30765-01.htm";
        } else if (n == 30760) {
            if (n9 == 3) {
                string = "30760-11t.htm";
            } else if (n9 == 4) {
                string = "30760-15t.htm";
            } else if (n9 == 12) {
                string = "30760-19t.htm";
            } else if (n9 == 13) {
                string = "30766-24t.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        block30: {
            int n = npcInstance.getNpcId();
            int n2 = this.getLeaderVar(questState, "cond");
            block0 : switch (n2) {
                case 2: {
                    switch (n) {
                        case 20282: {
                            if (!Rnd.chance((int)20)) break;
                            this.giveItem(3842, 10L, questState);
                            break;
                        }
                        case 20243: {
                            if (!Rnd.chance((int)15)) break;
                            this.giveItem(3842, 10L, questState);
                            break;
                        }
                        case 20137: {
                            if (!Rnd.chance((int)20)) break;
                            this.giveItem(3841, 10L, questState);
                            break;
                        }
                        case 20285: {
                            if (!Rnd.chance((int)25)) break;
                            this.giveItem(3841, 10L, questState);
                            break;
                        }
                        case 27178: {
                            this.giveItem(3840, 10L, questState);
                        }
                    }
                    break;
                }
                case 5: {
                    int n3 = 0;
                    switch (n) {
                        case 20654: {
                            n3 = 25;
                            break;
                        }
                        case 20656: {
                            n3 = 35;
                        }
                    }
                    if (n3 > 0 && Rnd.chance((int)n3)) {
                        switch (Rnd.get((int)3)) {
                            case 0: {
                                if (this.getLeaderVar(questState, "Kurtz") < 4) {
                                    return null;
                                }
                                this.giveItem(3845, 40L, questState);
                                break;
                            }
                            case 1: {
                                this.giveItem(3846, 10L, questState);
                                break;
                            }
                            case 2: {
                                this.giveItem(3844, 10L, questState);
                            }
                        }
                    }
                    break;
                }
                case 10: {
                    switch (n) {
                        case 20668: {
                            if (Rnd.chance((int)15)) {
                                questState.addSpawn(27179, 120000);
                                break block0;
                            }
                            break block30;
                        }
                        case 27179: {
                            if (Rnd.chance((int)80)) {
                                this.giveItem(3847, 6L, questState);
                                break block0;
                            }
                            break block30;
                        }
                        case 27181: {
                            NpcInstance npcInstance2 = questState.addSpawn(30765, 120000);
                            Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"50339", (Object[])new Object[0]);
                            this.setLeaderVar(questState, "ImpGraveKeeper", "3");
                        }
                    }
                }
            }
        }
        return null;
    }
}
