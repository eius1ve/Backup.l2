/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.entity;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.DeleteObject;
import l2.gameserver.network.l2.s2c.MonRaceInfo;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.npc.NpcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MonsterRace {
    private static final Logger bQ = LoggerFactory.getLogger(MonsterRace.class);
    private static final Set<String> u = new HashSet<String>(Arrays.asList(Config.SERVICE_MONSTER_RACE_ALLOWED_ZONES));
    private static MonsterRace a;
    private static final PlaySound a;
    private static final PlaySound b;
    private static final int[][] i;
    private static final List<Integer> bu;
    private static final List<HistoryInfo> bv;
    private static final Map<Integer, Long> aS;
    private static final List<Double> bw;
    private static int le;
    private static int lf;
    private static RaceState a;
    private static MonRaceInfo a;
    private final NpcInstance[] c = new NpcInstance[8];
    private int[][] j = new int[8][20];
    private final int[] aF = new int[2];
    private final int[] aG = new int[2];

    private MonsterRace() {
        if (!Config.SERVICE_MONSTER_RACE_ENABLED) {
            return;
        }
        this.bt();
        this.bu();
        for (int i = 31003; i < 31027; ++i) {
            bu.add(i);
        }
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new Announcement(), 0L, 1000L);
    }

    public void newRace() {
        bv.add(new HistoryInfo(le, 0, 0, 0.0));
        Collections.shuffle(bu);
        for (int i = 0; i < 8; ++i) {
            int n = 31003;
            int n2 = Rnd.get(24);
            for (int j = i - 1; j >= 0; --j) {
                if (this.c[j].getTemplate().npcId != n + n2) continue;
                n2 = Rnd.get(24);
            }
            try {
                NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(n + n2);
                if (npcTemplate == null) continue;
                Constructor<? extends NpcInstance> constructor = npcTemplate.getInstanceConstructor();
                int n3 = IdFactory.getInstance().getNextId();
                this.c[i] = constructor.newInstance(n3, npcTemplate);
                continue;
            }
            catch (Exception exception) {
                bQ.error("Error creating NPC for race", (Throwable)exception);
            }
        }
        this.newSpeeds();
    }

    public void newSpeeds() {
        this.j = new int[8][1];
        int n = 0;
        this.aF[1] = 0;
        this.aG[1] = 0;
        for (int i = 0; i < 8; ++i) {
            int n2;
            this.j[i][0] = n2 = Rnd.get(80, 124);
            if (n2 >= this.aF[1]) {
                this.aG[0] = this.aF[0];
                this.aG[1] = this.aF[1];
                this.aF[0] = 8 - i;
                this.aF[1] = n2;
            } else if (n2 >= this.aG[1]) {
                this.aG[0] = 8 - i;
                this.aG[1] = n2;
            }
            if (n2 <= n) continue;
            n = n2;
        }
    }

    private void bt() {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `mr_history`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bv.add(new HistoryInfo(resultSet.getInt("race_id"), resultSet.getInt("first"), resultSet.getInt("second"), resultSet.getDouble("odd_rate")));
                ++le;
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException sQLException) {
            bQ.warn("MonsterRace: Can't load history: " + sQLException.getMessage(), (Throwable)sQLException);
        }
        bQ.info("MonsterRace: loaded " + bv.size() + " records, currently on race #" + le);
    }

    private void a(HistoryInfo historyInfo) {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO `mr_history` (`race_id`, `first`, `second`, `odd_rate`) VALUES (?,?,?,?)");
            preparedStatement.setInt(1, historyInfo.getRaceId());
            preparedStatement.setInt(2, historyInfo.getFirst());
            preparedStatement.setInt(3, historyInfo.getSecond());
            preparedStatement.setDouble(4, historyInfo.getOddRate());
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException sQLException) {
            bQ.warn("MonsterRace: Can't save history: " + sQLException.getMessage(), (Throwable)sQLException);
        }
    }

    private void bu() {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `mr_bets`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                this.setBetOnLane(resultSet.getInt("lane_id"), resultSet.getLong("bet"), false);
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException sQLException) {
            bQ.warn("MonsterRace: Can't load bets: " + sQLException.getMessage(), (Throwable)sQLException);
        }
    }

    private void b(int n, long l) {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO `mr_bets` (`lane_id`, `bet`) VALUES (?,?)");
            preparedStatement.setInt(1, n);
            preparedStatement.setLong(2, l);
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException sQLException) {
            bQ.warn("MonsterRace: Can't save bet: " + sQLException.getMessage(), (Throwable)sQLException);
        }
    }

    private void bv() {
        Object object = aS.keySet().iterator();
        while (object.hasNext()) {
            int n = object.next();
            aS.put(n, 0L);
        }
        try {
            object = DatabaseFactory.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = object.prepareStatement("UPDATE `mr_bets` SET `bet` = 0");
                preparedStatement.execute();
                preparedStatement.close();
            }
            finally {
                if (object != null) {
                    object.close();
                }
            }
        }
        catch (SQLException sQLException) {
            bQ.warn("MonsterRace: Can't clear bets: " + sQLException.getMessage(), (Throwable)sQLException);
        }
    }

    public void setBetOnLane(int n, long l, boolean bl) {
        long l2 = aS.containsKey(n) ? aS.get(n) + l : l;
        aS.put(n, l2);
        if (bl) {
            this.b(n, l2);
        }
    }

    private void bw() {
        long l;
        bw.clear();
        TreeMap<Integer, Long> treeMap = new TreeMap<Integer, Long>(aS);
        long l2 = 0L;
        Iterator iterator = treeMap.values().iterator();
        while (iterator.hasNext()) {
            l = (Long)iterator.next();
            l2 += l;
        }
        iterator = treeMap.values().iterator();
        while (iterator.hasNext()) {
            l = (Long)iterator.next();
            bw.add(l == 0L ? 0.0 : Math.max(1.25, (double)l2 * 0.7 / (double)l));
        }
    }

    public NpcInstance[] getMonsters() {
        return this.c;
    }

    public int[][] getSpeeds() {
        return this.j;
    }

    public int getFirstPlace() {
        return 8 - this.aF[0] + 1;
    }

    public int getSecondPlace() {
        return 8 - this.aG[0] + 1;
    }

    public MonRaceInfo getRacePacket() {
        return a;
    }

    public RaceState getCurrentRaceState() {
        return a;
    }

    public int getRaceNumber() {
        return le;
    }

    public List<HistoryInfo> getHistory() {
        return bv;
    }

    public List<Double> getOdds() {
        return bw;
    }

    public void sendPacketToPlayersInZones(IStaticPacket ... iStaticPacketArray) {
        block0: for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player == null) continue;
            for (String string : u) {
                if (!player.isInZone(string)) continue;
                player.sendPacket(iStaticPacketArray);
                continue block0;
            }
        }
    }

    public static MonsterRace getInstance() {
        if (a == null) {
            a = new MonsterRace();
        }
        return a;
    }

    static {
        a = new PlaySound("S_Race");
        b = new PlaySound("ItemSound2.race_start");
        i = new int[][]{{-1, 0}, {0, 15322}, {13765, -1}};
        bu = new ArrayList<Integer>();
        bv = new ArrayList<HistoryInfo>();
        aS = new ConcurrentHashMap<Integer, Long>();
        bw = new ArrayList<Double>();
        le = 1;
        lf = 0;
        a = RaceState.RACE_END;
    }

    private class Announcement
    implements Runnable {
        private Announcement() {
        }

        @Override
        public void run() {
            if (lf > 1200) {
                lf = 0;
            }
            switch (lf) {
                case 0: {
                    MonsterRace.this.newRace();
                    a = RaceState.ACCEPTING_BETS;
                    a = new MonRaceInfo(i[0][0], i[0][1], MonsterRace.this.getMonsters(), MonsterRace.this.getSpeeds());
                    SystemMessage systemMessage = new SystemMessage(SystemMsg.TICKETS_ARE_NOW_AVAILABLE_FOR_MONSTER_RACE_S1);
                    systemMessage.addNumber(le);
                    MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                    break;
                }
                case 30: 
                case 60: 
                case 90: 
                case 120: 
                case 150: 
                case 180: 
                case 210: 
                case 240: 
                case 270: 
                case 330: 
                case 360: 
                case 390: 
                case 420: 
                case 450: 
                case 480: 
                case 510: 
                case 540: 
                case 570: 
                case 630: 
                case 660: 
                case 690: 
                case 720: 
                case 750: 
                case 780: 
                case 810: 
                case 870: {
                    SystemMessage systemMessage = new SystemMessage(SystemMsg.TICKETS_ARE_NOW_AVAILABLE_FOR_MONSTER_RACE_S1);
                    a = new MonRaceInfo(i[0][0], i[0][1], MonsterRace.this.getMonsters(), MonsterRace.this.getSpeeds());
                    systemMessage.addNumber(le);
                    MonsterRace.this.sendPacketToPlayersInZones(systemMessage, a);
                    break;
                }
                case 300: {
                    SystemMessage systemMessage = new SystemMessage(SystemMsg.NOW_SELLING_TICKETS_FOR_MONSTER_RACE_S1);
                    systemMessage.addNumber(le);
                    MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                    break;
                }
                case 600: {
                    SystemMessage systemMessage = new SystemMessage(SystemMsg.NOW_SELLING_TICKETS_FOR_MONSTER_RACE_S1);
                    systemMessage.addNumber(le);
                    MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                    break;
                }
                case 840: {
                    SystemMessage systemMessage = new SystemMessage(SystemMsg.NOW_SELLING_TICKETS_FOR_MONSTER_RACE_S1);
                    systemMessage.addNumber(le);
                    MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                    break;
                }
                case 900: {
                    a = RaceState.WAITING;
                    MonsterRace.this.bw();
                    SystemMessage systemMessage = new SystemMessage(SystemMsg.TICKETS_ARE_NOW_AVAILABLE_FOR_MONSTER_RACE_S1);
                    systemMessage.addNumber(le);
                    SystemMessage systemMessage2 = new SystemMessage(SystemMsg.TICKETS_SALES_ARE_CLOSED_FOR_MONSTER_RACE_S1_ODDS_ARE_POSTED);
                    systemMessage2.addNumber(le);
                    MonsterRace.this.sendPacketToPlayersInZones(systemMessage, systemMessage2);
                    break;
                }
                case 960: 
                case 1020: {
                    int n = lf == 960 ? 2 : 1;
                    SystemMessage systemMessage = new SystemMessage(SystemMsg.MONSTER_RACE_S2_WILL_BEGIN_IN_S1_MINUTES);
                    systemMessage.addNumber(n);
                    systemMessage.addNumber(le);
                    MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                    break;
                }
                case 1050: {
                    SystemMessage systemMessage = new SystemMessage(SystemMsg.MONSTER_RACE_S1_WILL_BEGIN_IN_30_SECONDS);
                    systemMessage.addNumber(le);
                    MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                    break;
                }
                case 1070: {
                    SystemMessage systemMessage = new SystemMessage(SystemMsg.MONSTER_RACE_S1_IS_ABOUT_TO_BEGIN_COUNTDOWN_IN_FIVE_SECONDS);
                    systemMessage.addNumber(le);
                    MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                    break;
                }
                case 1075: 
                case 1076: 
                case 1077: 
                case 1078: 
                case 1079: {
                    int n = 1080 - lf;
                    SystemMessage systemMessage = new SystemMessage(SystemMsg.THE_RACE_WILL_BEGIN_IN_S1_SECONDS);
                    systemMessage.addNumber(n);
                    MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                    break;
                }
                case 1080: {
                    a = RaceState.STARTING_RACE;
                    a = new MonRaceInfo(i[1][0], i[1][1], MonsterRace.this.getMonsters(), MonsterRace.this.getSpeeds());
                    MonsterRace.this.sendPacketToPlayersInZones(new SystemMessage(SystemMsg.THEYRE_OFF), a, b, a);
                    break;
                }
                case 1085: {
                    a = new MonRaceInfo(i[2][0], i[2][1], MonsterRace.this.getMonsters(), MonsterRace.this.getSpeeds());
                    MonsterRace.this.sendPacketToPlayersInZones(a);
                    break;
                }
                case 1115: {
                    a = RaceState.RACE_END;
                    HistoryInfo historyInfo = bv.get(bv.size() - 1);
                    historyInfo.setFirst(MonsterRace.this.getFirstPlace());
                    historyInfo.setSecond(MonsterRace.this.getSecondPlace());
                    historyInfo.setOddRate(bw.get(MonsterRace.this.getFirstPlace() - 1));
                    MonsterRace.this.a(historyInfo);
                    MonsterRace.this.bv();
                    SystemMessage systemMessage = new SystemMessage(SystemMsg.FIRST_PRIZE_GOES_TO_THE_PLAYER_IN_LANE_S1_SECOND_PRIZE_GOES_TO_THE_PLAYER_IN_LANE_S2);
                    systemMessage.addNumber(MonsterRace.this.getFirstPlace());
                    systemMessage.addNumber(MonsterRace.this.getSecondPlace());
                    SystemMessage systemMessage3 = new SystemMessage(SystemMsg.MONSTER_RACE_S1_IS_FINISHED);
                    systemMessage3.addNumber(le);
                    MonsterRace.this.sendPacketToPlayersInZones(systemMessage, systemMessage3);
                    ++le;
                    break;
                }
                case 1140: {
                    MonsterRace.this.sendPacketToPlayersInZones(new DeleteObject(MonsterRace.this.getMonsters()[0]), new DeleteObject(MonsterRace.this.getMonsters()[1]), new DeleteObject(MonsterRace.this.getMonsters()[2]), new DeleteObject(MonsterRace.this.getMonsters()[3]), new DeleteObject(MonsterRace.this.getMonsters()[4]), new DeleteObject(MonsterRace.this.getMonsters()[5]), new DeleteObject(MonsterRace.this.getMonsters()[6]), new DeleteObject(MonsterRace.this.getMonsters()[7]));
                }
            }
            ++lf;
        }
    }

    public static class HistoryInfo {
        private final int lg;
        private int lh;
        private int li;
        private double z;

        public HistoryInfo(int n, int n2, int n3, double d) {
            this.lg = n;
            this.lh = n2;
            this.li = n3;
            this.z = d;
        }

        public int getRaceId() {
            return this.lg;
        }

        public int getFirst() {
            return this.lh;
        }

        public int getSecond() {
            return this.li;
        }

        public double getOddRate() {
            return this.z;
        }

        public void setFirst(int n) {
            this.lh = n;
        }

        public void setSecond(int n) {
            this.li = n;
        }

        public void setOddRate(double d) {
            this.z = d;
        }
    }

    public static final class RaceState
    extends Enum<RaceState> {
        public static final /* enum */ RaceState ACCEPTING_BETS = new RaceState();
        public static final /* enum */ RaceState WAITING = new RaceState();
        public static final /* enum */ RaceState STARTING_RACE = new RaceState();
        public static final /* enum */ RaceState RACE_END = new RaceState();
        private static final /* synthetic */ RaceState[] a;

        public static RaceState[] values() {
            return (RaceState[])a.clone();
        }

        public static RaceState valueOf(String string) {
            return Enum.valueOf(RaceState.class, string);
        }

        private static /* synthetic */ RaceState[] a() {
            return new RaceState[]{ACCEPTING_BETS, WAITING, STARTING_RACE, RACE_END};
        }

        static {
            a = RaceState.a();
        }
    }
}
