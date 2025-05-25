/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.instancemanager.games;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import l2.commons.dbutils.DbUtils;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SystemMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FishingChampionShipManager {
    private static final Logger bv = LoggerFactory.getLogger(FishingChampionShipManager.class);
    private static final FishingChampionShipManager a = new FishingChampionShipManager();
    private long _enddate = 0L;
    private List<String> az = new ArrayList<String>();
    private List<String> aA = new ArrayList<String>();
    private List<String> aB = new ArrayList<String>();
    private List<String> aC = new ArrayList<String>();
    private List<Fisher> aD = new ArrayList<Fisher>();
    private List<Fisher> aE = new ArrayList<Fisher>();
    private double i = 0.0;
    private boolean bh = true;

    public static final FishingChampionShipManager getInstance() {
        return a;
    }

    private FishingChampionShipManager() {
        this.aD();
        this.aG();
        this.aE();
        if (this._enddate <= System.currentTimeMillis()) {
            this._enddate = System.currentTimeMillis();
            new finishChamp().run();
        } else {
            ThreadPoolManager.getInstance().schedule(new finishChamp(), this._enddate - System.currentTimeMillis());
        }
    }

    private void aC() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this._enddate);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.add(5, 6);
        calendar.set(7, 3);
        calendar.set(11, 19);
        this._enddate = calendar.getTimeInMillis();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void aD() {
        this._enddate = ServerVariables.getLong("fishChampionshipEnd", 0L);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `PlayerName`, `fishLength`, `rewarded` FROM `fishing_championship`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("rewarded");
                if (n == 0) {
                    this.aD.add(new Fisher(resultSet.getString("PlayerName"), resultSet.getDouble("fishLength"), 0));
                }
                if (n <= 0) continue;
                this.aE.add(new Fisher(resultSet.getString("PlayerName"), resultSet.getDouble("fishLength"), n));
            }
            resultSet.close();
        }
        catch (SQLException sQLException) {
            try {
                bv.warn("Exception: can't get fishing championship info: " + sQLException.getMessage());
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public synchronized void newFish(Player player, int n) {
        long l;
        if (!Config.ALT_FISH_CHAMPIONSHIP_ENABLED) {
            return;
        }
        double d = Rnd.get(60, 80);
        if (d < 90.0 && n > 8484 && n < 8486 && (l = Math.round(90.0 - d)) > 1L) {
            d += (double)Rnd.get(1L, l);
        }
        double d2 = (double)Rnd.get(100, 999) / 1000.0 + d;
        if (this.aD.size() < 5) {
            for (Fisher fisher : this.aD) {
                if (!fisher.getName().equalsIgnoreCase(player.getName())) continue;
                if (fisher.getLength() < d2) {
                    fisher.setLength(d2);
                    player.sendMessage(new CustomMessage("l2p.gameserver.instancemanager.games.FishingChampionShipManager.ResultImproveOn", player, new Object[0]));
                    this.aE();
                }
                return;
            }
            this.aD.add(new Fisher(player.getName(), d2, 0));
            player.sendMessage(new CustomMessage("l2p.gameserver.instancemanager.games.FishingChampionShipManager.YouInAPrizeList", player, new Object[0]));
            this.aE();
        } else if (this.i < d2) {
            for (Fisher fisher : this.aD) {
                if (!fisher.getName().equalsIgnoreCase(player.getName())) continue;
                if (fisher.getLength() < d2) {
                    fisher.setLength(d2);
                    player.sendMessage(new CustomMessage("l2p.gameserver.instancemanager.games.FishingChampionShipManager.ResultImproveOn", player, new Object[0]));
                    this.aE();
                }
                return;
            }
            Object object = null;
            double d3 = 99999.0;
            for (Fisher fisher : this.aD) {
                if (!(fisher.getLength() < d3)) continue;
                object = fisher;
                d3 = ((Fisher)object).getLength();
            }
            this.aD.remove(object);
            this.aD.add(new Fisher(player.getName(), d2, 0));
            player.sendMessage(new CustomMessage("l2p.gameserver.instancemanager.games.FishingChampionShipManager.YouInAPrizeList", player, new Object[0]));
            this.aE();
        }
    }

    private void aE() {
        double d = 99999.0;
        for (Fisher fisher : this.aD) {
            if (!(fisher.getLength() < d)) continue;
            d = fisher.getLength();
        }
        this.i = d;
    }

    public long getTimeRemaining() {
        return (this._enddate - System.currentTimeMillis()) / 60000L;
    }

    public String getWinnerName(int n) {
        if (this.aB.size() >= n) {
            return this.aB.get(n - 1);
        }
        return "\u2014";
    }

    public String getCurrentName(int n) {
        if (this.az.size() >= n) {
            return this.az.get(n - 1);
        }
        return "\u2014";
    }

    public String getFishLength(int n) {
        if (this.aC.size() >= n) {
            return this.aC.get(n - 1);
        }
        return "0";
    }

    public String getCurrentFishLength(int n) {
        if (this.aA.size() >= n) {
            return this.aA.get(n - 1);
        }
        return "0";
    }

    public void getReward(Player player) {
        String string = "fisherman/championship/getReward.htm";
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player.getObjectId());
        npcHtmlMessage.setFile(string);
        player.sendPacket((IStaticPacket)npcHtmlMessage);
        for (Fisher fisher : this.aE) {
            if (!fisher._name.equalsIgnoreCase(player.getName()) || fisher.getRewardType() == 2) continue;
            int n = 0;
            block8: for (int i = 0; i < this.aB.size(); ++i) {
                if (!this.aB.get(i).equalsIgnoreCase(player.getName())) continue;
                switch (i) {
                    case 0: {
                        n = Config.ALT_FISH_CHAMPIONSHIP_REWARD_1;
                        continue block8;
                    }
                    case 1: {
                        n = Config.ALT_FISH_CHAMPIONSHIP_REWARD_2;
                        continue block8;
                    }
                    case 2: {
                        n = Config.ALT_FISH_CHAMPIONSHIP_REWARD_3;
                        continue block8;
                    }
                    case 3: {
                        n = Config.ALT_FISH_CHAMPIONSHIP_REWARD_4;
                        continue block8;
                    }
                    case 4: {
                        n = Config.ALT_FISH_CHAMPIONSHIP_REWARD_5;
                    }
                }
            }
            fisher.setRewardType(2);
            if (n <= 0) continue;
            SystemMessage systemMessage = (SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S2_S1S).addItemName(Config.ALT_FISH_CHAMPIONSHIP_REWARD_ITEM)).addNumber(n);
            player.sendPacket((IStaticPacket)systemMessage);
            player.getInventory().addItem(Config.ALT_FISH_CHAMPIONSHIP_REWARD_ITEM, n);
            player.sendItemList(false);
        }
    }

    public void showMidResult(Player player) {
        if (this.bh) {
            this.aF();
            ThreadPoolManager.getInstance().schedule(new needRefresh(), 60000L);
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player.getObjectId());
        String string = "fisherman/championship/MidResult.htm";
        npcHtmlMessage.setFile(string);
        String string2 = null;
        for (int i = 1; i <= 5; ++i) {
            string2 = string2 + "<tr><td width=70 align=center>" + i + (player.isLangRus() ? " \u041c\u0435\u0441\u0442\u043e:" : " Position:") + "</td>";
            string2 = string2 + "<td width=110 align=center>" + this.getCurrentName(i) + "</td>";
            string2 = string2 + "<td width=80 align=center>" + this.getCurrentFishLength(i) + "</td></tr>";
        }
        npcHtmlMessage.replace("%TABLE%", string2);
        npcHtmlMessage.replace("%prizeItem%", ItemHolder.getInstance().getTemplate(Config.ALT_FISH_CHAMPIONSHIP_REWARD_ITEM).getName());
        npcHtmlMessage.replace("%prizeFirst%", String.valueOf(Config.ALT_FISH_CHAMPIONSHIP_REWARD_1));
        npcHtmlMessage.replace("%prizeTwo%", String.valueOf(Config.ALT_FISH_CHAMPIONSHIP_REWARD_2));
        npcHtmlMessage.replace("%prizeThree%", String.valueOf(Config.ALT_FISH_CHAMPIONSHIP_REWARD_3));
        npcHtmlMessage.replace("%prizeFour%", String.valueOf(Config.ALT_FISH_CHAMPIONSHIP_REWARD_4));
        npcHtmlMessage.replace("%prizeFive%", String.valueOf(Config.ALT_FISH_CHAMPIONSHIP_REWARD_5));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void showChampScreen(Player player, NpcInstance npcInstance) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player.getObjectId());
        String string = "fisherman/championship/champScreen.htm";
        npcHtmlMessage.setFile(string);
        String string2 = null;
        for (int i = 1; i <= 5; ++i) {
            string2 = string2 + "<tr><td width=70 align=center>" + i + (player.isLangRus() ? " \u041c\u0435\u0441\u0442\u043e:" : " Position:") + "</td>";
            string2 = string2 + "<td width=110 align=center>" + this.getWinnerName(i) + "</td>";
            string2 = string2 + "<td width=80 align=center>" + this.getFishLength(i) + "</td></tr>";
        }
        npcHtmlMessage.replace("%TABLE%", string2);
        npcHtmlMessage.replace("%prizeItem%", ItemHolder.getInstance().getTemplate(Config.ALT_FISH_CHAMPIONSHIP_REWARD_ITEM).getName());
        npcHtmlMessage.replace("%prizeFirst%", String.valueOf(Config.ALT_FISH_CHAMPIONSHIP_REWARD_1));
        npcHtmlMessage.replace("%prizeTwo%", String.valueOf(Config.ALT_FISH_CHAMPIONSHIP_REWARD_2));
        npcHtmlMessage.replace("%prizeThree%", String.valueOf(Config.ALT_FISH_CHAMPIONSHIP_REWARD_3));
        npcHtmlMessage.replace("%prizeFour%", String.valueOf(Config.ALT_FISH_CHAMPIONSHIP_REWARD_4));
        npcHtmlMessage.replace("%prizeFive%", String.valueOf(Config.ALT_FISH_CHAMPIONSHIP_REWARD_5));
        npcHtmlMessage.replace("%refresh%", String.valueOf(this.getTimeRemaining()));
        npcHtmlMessage.replace("%objectId%", String.valueOf(npcInstance.getObjectId()));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void shutdown() {
        ServerVariables.set("fishChampionshipEnd", this._enddate);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `fishing_championship`");
            preparedStatement.execute();
            preparedStatement.close();
            for (Fisher fisher : this.aE) {
                preparedStatement = connection.prepareStatement("INSERT INTO `fishing_championship`(`PlayerName`,`fishLength`,`rewarded`) VALUES (?,?,?)");
                preparedStatement.setString(1, fisher.getName());
                preparedStatement.setDouble(2, fisher.getLength());
                preparedStatement.setInt(3, fisher.getRewardType());
                preparedStatement.execute();
                preparedStatement.close();
            }
            for (Fisher fisher : this.aD) {
                preparedStatement = connection.prepareStatement("INSERT INTO `fishing_championship`(`PlayerName`,`fishLength`,`rewarded`) VALUES (?,?,?)");
                preparedStatement.setString(1, fisher.getName());
                preparedStatement.setDouble(2, fisher.getLength());
                preparedStatement.setInt(3, 0);
                preparedStatement.execute();
                preparedStatement.close();
            }
        }
        catch (SQLException sQLException) {
            try {
                bv.warn("Exception: can't update player vitality: " + sQLException.getMessage());
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    private synchronized void aF() {
        int n;
        this.bh = false;
        this.az.clear();
        this.aA.clear();
        Fisher fisher = null;
        Fisher fisher2 = null;
        for (n = 0; n <= this.aD.size() - 1; ++n) {
            for (int i = 0; i <= this.aD.size() - 2; ++i) {
                fisher = this.aD.get(i);
                fisher2 = this.aD.get(i + 1);
                if (!(fisher.getLength() < fisher2.getLength())) continue;
                this.aD.set(i, fisher2);
                this.aD.set(i + 1, fisher);
            }
        }
        for (n = 0; n <= this.aD.size() - 1; ++n) {
            this.az.add(this.aD.get((int)n)._name);
            this.aA.add(String.valueOf(this.aD.get(n).getLength()));
        }
    }

    private void aG() {
        int n;
        this.aB.clear();
        this.aC.clear();
        Fisher fisher = null;
        Fisher fisher2 = null;
        for (n = 0; n <= this.aE.size() - 1; ++n) {
            for (int i = 0; i <= this.aE.size() - 2; ++i) {
                fisher = this.aE.get(i);
                fisher2 = this.aE.get(i + 1);
                if (!(fisher.getLength() < fisher2.getLength())) continue;
                this.aE.set(i, fisher2);
                this.aE.set(i + 1, fisher);
            }
        }
        for (n = 0; n <= this.aE.size() - 1; ++n) {
            this.aB.add(this.aE.get((int)n)._name);
            this.aC.add(String.valueOf(this.aE.get(n).getLength()));
        }
    }

    private class finishChamp
    extends RunnableImpl {
        private finishChamp() {
        }

        @Override
        public void runImpl() throws Exception {
            FishingChampionShipManager.this.aE.clear();
            for (Fisher fisher : FishingChampionShipManager.this.aD) {
                fisher.setRewardType(1);
                FishingChampionShipManager.this.aE.add(fisher);
            }
            FishingChampionShipManager.this.aD.clear();
            FishingChampionShipManager.this.aG();
            FishingChampionShipManager.this.aC();
            FishingChampionShipManager.this.shutdown();
            _log.info("Fishing Championship Manager : start new event period.");
            ThreadPoolManager.getInstance().schedule(new finishChamp(), FishingChampionShipManager.this._enddate - System.currentTimeMillis());
        }
    }

    private class Fisher {
        private double j = 0.0;
        private String _name;
        private int fR = 0;

        public Fisher(String string, double d, int n) {
            this.setName(string);
            this.setLength(d);
            this.setRewardType(n);
        }

        public void setLength(double d) {
            this.j = d;
        }

        public void setName(String string) {
            this._name = string;
        }

        public void setRewardType(int n) {
            this.fR = n;
        }

        public String getName() {
            return this._name;
        }

        public int getRewardType() {
            return this.fR;
        }

        public double getLength() {
            return this.j;
        }
    }

    private class needRefresh
    extends RunnableImpl {
        private needRefresh() {
        }

        @Override
        public void runImpl() throws Exception {
            FishingChampionShipManager.this.bh = true;
        }
    }
}
