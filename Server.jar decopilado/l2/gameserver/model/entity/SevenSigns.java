/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicReference;
import l2.commons.dbutils.DbUtils;
import l2.commons.listener.ListenerList;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.instancemanager.RaidBossSpawnManager;
import l2.gameserver.listener.GameListener;
import l2.gameserver.listener.game.OnSSPeriodListener;
import l2.gameserver.listener.game.OnStartListener;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.SevenSignsFestival.SevenSignsFestival;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SSQInfo;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SevenSigns {
    private static final Logger bS = LoggerFactory.getLogger(SevenSigns.class);
    private static SevenSigns a;
    private ScheduledFuture<?> U;
    public static final String SEVEN_SIGNS_HTML_PATH = "seven_signs/";
    public static final int CABAL_NULL = 0;
    public static final int CABAL_DUSK = 1;
    public static final int CABAL_DAWN = 2;
    public static final int SEAL_NULL = 0;
    public static final int SEAL_AVARICE = 1;
    public static final int SEAL_GNOSIS = 2;
    public static final int SEAL_STRIFE = 3;
    public static final int PERIOD_COMP_RECRUITING = 0;
    public static final int PERIOD_COMPETITION = 1;
    public static final int PERIOD_COMP_RESULTS = 2;
    public static final int PERIOD_SEAL_VALIDATION = 3;
    public static final int PERIOD_START_HOUR = 18;
    public static final int PERIOD_START_MINS = 0;
    public static final int PERIOD_START_DAY = 2;
    public static final int PERIOD_MINOR_LENGTH = 900000;
    public static final int PERIOD_MAJOR_LENGTH = 603900000;
    public static final int ANCIENT_ADENA_ID = 5575;
    public static final int RECORD_SEVEN_SIGNS_ID = 5707;
    public static final int CERTIFICATE_OF_APPROVAL_ID = 6388;
    public static final int RECORD_SEVEN_SIGNS_COST = 500;
    public static final int ADENA_JOIN_DAWN_COST = 50000;
    public static final Set<Integer> ORATOR_NPC_IDS;
    public static final Set<Integer> PREACHER_NPC_IDS;
    public static final int SEAL_STONE_BLUE_ID = 6360;
    public static final int SEAL_STONE_GREEN_ID = 6361;
    public static final int SEAL_STONE_RED_ID = 6362;
    public static final int SEAL_STONE_BLUE_VALUE = 3;
    public static final int SEAL_STONE_GREEN_VALUE = 5;
    public static final int SEAL_STONE_RED_VALUE = 10;
    public static final int BLUE_CONTRIB_POINTS = 3;
    public static final int GREEN_CONTRIB_POINTS = 5;
    public static final int RED_CONTRIB_POINTS = 10;
    private final Calendar a;
    protected int _activePeriod;
    protected int _currentCycle;
    protected volatile long _dawnStoneScore;
    protected volatile long _duskStoneScore;
    private AtomicReference<Pair<Long, Long>> c;
    protected int _compWinner;
    protected int _previousWinner;
    private Map<Integer, StatsSet> aT;
    private Map<Integer, Integer> aU;
    private Map<Integer, Integer> aV;
    private Map<Integer, Integer> aW;
    private SSListenerList a = Calendar.getInstance();

    public SevenSigns() {
        this.c = new AtomicReference<Pair>(Pair.of((Object)0L, (Object)0L));
        this.a = new SSListenerList();
        GameServer.getInstance().addListener(new OnStartListenerImpl());
        this.aT = new ConcurrentHashMap<Integer, StatsSet>();
        this.aU = new ConcurrentHashMap<Integer, Integer>();
        this.aV = new ConcurrentHashMap<Integer, Integer>();
        this.aW = new ConcurrentHashMap<Integer, Integer>();
        try {
            this.restoreSevenSignsData();
        }
        catch (Exception exception) {
            bS.error("SevenSigns: Failed to load configuration: " + exception);
            bS.error("", (Throwable)exception);
        }
        bS.info("SevenSigns: Currently in the " + this.getCurrentPeriodName() + " period!");
        this.initializeSeals();
        if (this.isSealValidationPeriod()) {
            if (this.getCabalHighestScore() == 0) {
                bS.info("SevenSigns: The Competition last week ended with a tie.");
            } else {
                bS.info("SevenSigns: The " + SevenSigns.getCabalName(this.getCabalHighestScore()) + " were victorious last week.");
            }
        } else if (this.getCabalHighestScore() == 0) {
            bS.info("SevenSigns: The Competition this week, if the trend continue, will end with a tie.");
        } else {
            bS.info("SevenSigns: The " + SevenSigns.getCabalName(this.getCabalHighestScore()) + " are in the lead this week.");
        }
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        this.setCalendarForNextPeriodChange();
        long l = this.getMilliToPeriodChange();
        if (l < 10L) {
            l = 10L;
        }
        this.U = ThreadPoolManager.getInstance().schedule(new SevenSignsPeriodChange(), l);
        double d = l / 1000L % 60L;
        double d2 = ((double)(l / 1000L) - d) / 60.0;
        n = (int)Math.floor(d2 % 60.0);
        d2 = (d2 - (double)n) / 60.0;
        n2 = (int)Math.floor(d2 % 24.0);
        n3 = (int)Math.floor((d2 - (double)n2) / 24.0);
        bS.info("SevenSigns: Next period begins in " + n3 + " days, " + n2 + " hours and " + n + " mins.");
        if (Config.SS_ANNOUNCE_PERIOD > 0) {
            ThreadPoolManager.getInstance().schedule(new SevenSignsAnnounce(), (long)Config.SS_ANNOUNCE_PERIOD * 1000L * 60L);
        }
    }

    public static SevenSigns getInstance() {
        if (a == null) {
            a = new SevenSigns();
        }
        return a;
    }

    public static long calcContributionScore(long l, long l2, long l3) {
        long l4 = l * 3L;
        l4 += l2 * 5L;
        return l4 += l3 * 10L;
    }

    public static long calcAncientAdenaReward(long l, long l2, long l3) {
        long l4 = l * 3L;
        l4 += l2 * 5L;
        return l4 += l3 * 10L;
    }

    public static int getCabalNumber(String string) {
        if ("dawn".equalsIgnoreCase(string)) {
            return 2;
        }
        if ("dusk".equalsIgnoreCase(string)) {
            return 1;
        }
        return 0;
    }

    public static String getCabalShortName(int n) {
        switch (n) {
            case 2: {
                return "dawn";
            }
            case 1: {
                return "dusk";
            }
        }
        return "No Cabal";
    }

    public static String getCabalName(int n) {
        switch (n) {
            case 2: {
                return "Lords of Dawn";
            }
            case 1: {
                return "Revolutionaries of Dusk";
            }
        }
        return "No Cabal";
    }

    public static String getSealName(int n, boolean bl) {
        Object object = !bl ? "Seal of " : "";
        switch (n) {
            case 1: {
                object = (String)object + "Avarice";
                break;
            }
            case 2: {
                object = (String)object + "Gnosis";
                break;
            }
            case 3: {
                object = (String)object + "Strife";
            }
        }
        return object;
    }

    public static String capitalizeWords(String string) {
        char[] cArray = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        cArray[0] = Character.toUpperCase(cArray[0]);
        for (int i = 0; i < cArray.length; ++i) {
            if (Character.isWhitespace(cArray[i]) && i != cArray.length - 1) {
                cArray[i + 1] = Character.toUpperCase(cArray[i + 1]);
            }
            stringBuilder.append(Character.toString(cArray[i]));
        }
        return stringBuilder.toString();
    }

    public final int getCurrentCycle() {
        return this._currentCycle;
    }

    public final int getCurrentPeriod() {
        return this._activePeriod;
    }

    private int v() {
        int n = this.a.get(7) - 2;
        if (n < 0) {
            return 0 - n;
        }
        return 7 - n;
    }

    public final long getMilliToPeriodChange() {
        return this.a.getTimeInMillis() - System.currentTimeMillis();
    }

    protected void setCalendarForNextPeriodChange() {
        switch (this.getCurrentPeriod()) {
            case 1: 
            case 3: {
                int n = this.v();
                if (n == 7) {
                    if (this.a.get(11) < 18) {
                        n = 0;
                    } else if (this.a.get(11) == 18 && this.a.get(12) < 0) {
                        n = 0;
                    }
                }
                if (n > 0) {
                    this.a.add(5, n);
                }
                this.a.set(11, 18);
                this.a.set(12, 0);
                break;
            }
            case 0: 
            case 2: {
                this.a.add(14, 900000);
            }
        }
    }

    public final String getCurrentPeriodName() {
        String string = null;
        switch (this._activePeriod) {
            case 0: {
                string = "Quest Event Initialization";
                break;
            }
            case 1: {
                string = "Competition (Quest Event)";
                break;
            }
            case 2: {
                string = "Quest Event Results";
                break;
            }
            case 3: {
                string = "Seal Validation";
            }
        }
        return string;
    }

    public final boolean isSealValidationPeriod() {
        return this._activePeriod == 3;
    }

    public final boolean isCompResultsPeriod() {
        return this._activePeriod == 2;
    }

    public final long getCurrentScore(int n) {
        double d = this._dawnStoneScore + this._duskStoneScore;
        Pair<Long, Long> pair = this.c.get();
        switch (n) {
            case 0: {
                return 0L;
            }
            case 2: {
                return Math.round((double)this._dawnStoneScore / (d == 0.0 ? 1.0 : d) * 500.0) + (Long)pair.getLeft();
            }
            case 1: {
                return Math.round((double)this._duskStoneScore / (d == 0.0 ? 1.0 : d) * 500.0) + (Long)pair.getRight();
            }
        }
        return 0L;
    }

    public final long getCurrentStoneScore(int n) {
        switch (n) {
            case 0: {
                return 0L;
            }
            case 2: {
                return this._dawnStoneScore;
            }
            case 1: {
                return this._duskStoneScore;
            }
        }
        return 0L;
    }

    public final long getCurrentFestivalScore(int n) {
        Pair<Long, Long> pair = this.c.get();
        switch (n) {
            case 0: {
                return 0L;
            }
            case 2: {
                return (Long)pair.getLeft();
            }
            case 1: {
                return (Long)pair.getRight();
            }
        }
        return 0L;
    }

    public final int getCabalHighestScore() {
        long l = this.getCurrentScore(1) - this.getCurrentScore(2);
        if (l == 0L) {
            return 0;
        }
        if (l > 0L) {
            return 1;
        }
        return 2;
    }

    public final int getSealOwner(int n) {
        if (this.aU == null || !this.aU.containsKey(n)) {
            return 0;
        }
        return this.aU.get(n);
    }

    public final int getSealProportion(int n, int n2) {
        if (n2 == 0) {
            return 0;
        }
        if (n2 == 1) {
            return this.aV.get(n);
        }
        return this.aW.get(n);
    }

    public final int getTotalMembers(int n) {
        int n2 = 0;
        for (StatsSet statsSet : this.aT.values()) {
            if (statsSet.getInteger("cabal") != n) continue;
            ++n2;
        }
        return n2;
    }

    public final StatsSet getPlayerStatsSet(Player player) {
        if (!this.d(player.getObjectId())) {
            return null;
        }
        return this.aT.get(player.getObjectId());
    }

    public long getPlayerStoneContrib(Player player) {
        if (!this.d(player.getObjectId())) {
            return 0L;
        }
        long l = 0L;
        StatsSet statsSet = this.aT.get(player.getObjectId());
        if (this.getPlayerCabal(player) == 2) {
            l += statsSet.getLong("dawn_red_stones");
            l += statsSet.getLong("dawn_green_stones");
            l += statsSet.getLong("dawn_blue_stones");
        } else {
            l += statsSet.getLong("dusk_red_stones");
            l += statsSet.getLong("dusk_green_stones");
            l += statsSet.getLong("dusk_blue_stones");
        }
        return l;
    }

    public long getPlayerContribScore(Player player) {
        if (!this.d(player.getObjectId())) {
            return 0L;
        }
        StatsSet statsSet = this.aT.get(player.getObjectId());
        if (this.getPlayerCabal(player) == 2) {
            return statsSet.getInteger("dawn_contribution_score");
        }
        return statsSet.getInteger("dusk_contribution_score");
    }

    public long getPlayerAdenaCollect(Player player) {
        if (!this.d(player.getObjectId())) {
            return 0L;
        }
        return this.aT.get(player.getObjectId()).getLong(this.getPlayerCabal(player) == 2 ? "dawn_ancient_adena_amount" : "dusk_ancient_adena_amount");
    }

    public int getPlayerSeal(Player player) {
        if (!this.d(player.getObjectId())) {
            return 0;
        }
        return this.aT.get(player.getObjectId()).getInteger("seal");
    }

    public int getPlayerCabal(Player player) {
        if (!this.d(player.getObjectId())) {
            return 0;
        }
        return this.aT.get(player.getObjectId()).getInteger("cabal");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void restoreSevenSignsData() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT char_obj_id, cabal, seal, dawn_red_stones, dawn_green_stones, dawn_blue_stones, dawn_ancient_adena_amount, dawn_contribution_score, dusk_red_stones, dusk_green_stones, dusk_blue_stones, dusk_ancient_adena_amount, dusk_contribution_score FROM seven_signs");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("char_obj_id");
                StatsSet statsSet = new StatsSet();
                statsSet.set("char_obj_id", n);
                statsSet.set("cabal", SevenSigns.getCabalNumber(resultSet.getString("cabal")));
                statsSet.set("seal", resultSet.getInt("seal"));
                statsSet.set("dawn_red_stones", resultSet.getInt("dawn_red_stones"));
                statsSet.set("dawn_green_stones", resultSet.getInt("dawn_green_stones"));
                statsSet.set("dawn_blue_stones", resultSet.getInt("dawn_blue_stones"));
                statsSet.set("dawn_ancient_adena_amount", resultSet.getInt("dawn_ancient_adena_amount"));
                statsSet.set("dawn_contribution_score", resultSet.getInt("dawn_contribution_score"));
                statsSet.set("dusk_red_stones", resultSet.getInt("dusk_red_stones"));
                statsSet.set("dusk_green_stones", resultSet.getInt("dusk_green_stones"));
                statsSet.set("dusk_blue_stones", resultSet.getInt("dusk_blue_stones"));
                statsSet.set("dusk_ancient_adena_amount", resultSet.getInt("dusk_ancient_adena_amount"));
                statsSet.set("dusk_contribution_score", resultSet.getInt("dusk_contribution_score"));
                this.aT.put(n, statsSet);
            }
            DbUtils.close(preparedStatement, resultSet);
            preparedStatement = connection.prepareStatement("SELECT * FROM `seven_signs_status`");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                this._currentCycle = resultSet.getInt("current_cycle");
                this._activePeriod = resultSet.getInt("active_period");
                this._previousWinner = resultSet.getInt("previous_winner");
                this.c.set((Pair<Long, Long>)Pair.of((Object)resultSet.getLong("dawn_festival_score"), (Object)resultSet.getLong("dusk_festival_score")));
                this._dawnStoneScore = resultSet.getLong("dawn_stone_score");
                this._duskStoneScore = resultSet.getLong("dusk_stone_score");
                this.aU.put(1, resultSet.getInt("avarice_owner"));
                this.aU.put(2, resultSet.getInt("gnosis_owner"));
                this.aU.put(3, resultSet.getInt("strife_owner"));
                this.aW.put(1, resultSet.getInt("avarice_dawn_score"));
                this.aW.put(2, resultSet.getInt("gnosis_dawn_score"));
                this.aW.put(3, resultSet.getInt("strife_dawn_score"));
                this.aV.put(1, resultSet.getInt("avarice_dusk_score"));
                this.aV.put(2, resultSet.getInt("gnosis_dusk_score"));
                this.aV.put(3, resultSet.getInt("strife_dusk_score"));
            }
            DbUtils.close(preparedStatement, resultSet);
            preparedStatement = connection.prepareStatement("UPDATE `seven_signs_status` SET `date`=?");
            preparedStatement.setInt(1, Calendar.getInstance().get(7));
            preparedStatement.execute();
        }
        catch (SQLException sQLException) {
            try {
                bS.error("Unable to load Seven Signs Data: " + sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void saveSevenSignsData(int n, boolean bl) {
        PreparedStatement preparedStatement;
        Connection connection;
        block9: {
            connection = null;
            preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("UPDATE `seven_signs` SET `cabal`=?, `seal`=?, `dawn_red_stones`=?, `dawn_green_stones`=?, `dawn_blue_stones`=?, `dawn_ancient_adena_amount`=?, `dawn_contribution_score`=?, `dusk_red_stones`=?, `dusk_green_stones`=?, `dusk_blue_stones`=?, `dusk_ancient_adena_amount`=?, `dusk_contribution_score`=? WHERE `char_obj_id`=?");
                if (n > 0) {
                    SevenSigns.a(preparedStatement, this.aT.get(n));
                } else {
                    for (StatsSet statsSet : this.aT.values()) {
                        SevenSigns.a(preparedStatement, statsSet);
                    }
                }
                DbUtils.close(preparedStatement);
                if (!bl) break block9;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("UPDATE `seven_signs_status` SET `current_cycle`=?, `active_period`=?, `previous_winner`=?, `dawn_stone_score`=?, `dawn_festival_score`=?, `dusk_stone_score`=?, `dusk_festival_score`=?, `avarice_owner`=?, `gnosis_owner`=?, `strife_owner`=?, `avarice_dawn_score`=?, `gnosis_dawn_score`=?, `strife_dawn_score`=?, `avarice_dusk_score`=?, `gnosis_dusk_score`=?, `strife_dusk_score`=?, `festival_cycle`=?, ");
                for (int i = 0; i < 5; ++i) {
                    stringBuilder.append("accumulated_bonus" + String.valueOf(i) + "=?, ");
                }
                stringBuilder.append("date=?");
                Pair<Long, Long> pair = this.c.get();
                preparedStatement = connection.prepareStatement(stringBuilder.toString());
                preparedStatement.setInt(1, this._currentCycle);
                preparedStatement.setInt(2, this._activePeriod);
                preparedStatement.setInt(3, this._previousWinner);
                preparedStatement.setLong(4, this._dawnStoneScore);
                preparedStatement.setLong(5, (Long)pair.getLeft());
                preparedStatement.setLong(6, this._duskStoneScore);
                preparedStatement.setLong(7, (Long)pair.getRight());
                preparedStatement.setInt(8, this.aU.get(1));
                preparedStatement.setInt(9, this.aU.get(2));
                preparedStatement.setInt(10, this.aU.get(3));
                preparedStatement.setInt(11, this.aW.get(1));
                preparedStatement.setInt(12, this.aW.get(2));
                preparedStatement.setInt(13, this.aW.get(3));
                preparedStatement.setInt(14, this.aV.get(1));
                preparedStatement.setInt(15, this.aV.get(2));
                preparedStatement.setInt(16, this.aV.get(3));
                preparedStatement.setInt(17, this.getCurrentCycle());
                for (int i = 0; i < 5; ++i) {
                    preparedStatement.setLong(18 + i, SevenSignsFestival.getInstance().getAccumulatedBonus(i));
                }
                preparedStatement.setInt(23, Calendar.getInstance().get(7));
                preparedStatement.executeUpdate();
            }
            catch (SQLException sQLException) {
                try {
                    bS.error("Unable to save Seven Signs data: " + sQLException);
                    bS.error("", (Throwable)sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    private static void a(PreparedStatement preparedStatement, StatsSet statsSet) throws SQLException {
        preparedStatement.setString(1, SevenSigns.getCabalShortName(statsSet.getInteger("cabal")));
        preparedStatement.setInt(2, statsSet.getInteger("seal"));
        preparedStatement.setInt(3, statsSet.getInteger("dawn_red_stones"));
        preparedStatement.setInt(4, statsSet.getInteger("dawn_green_stones"));
        preparedStatement.setInt(5, statsSet.getInteger("dawn_blue_stones"));
        preparedStatement.setInt(6, statsSet.getInteger("dawn_ancient_adena_amount"));
        preparedStatement.setInt(7, statsSet.getInteger("dawn_contribution_score"));
        preparedStatement.setInt(8, statsSet.getInteger("dusk_red_stones"));
        preparedStatement.setInt(9, statsSet.getInteger("dusk_green_stones"));
        preparedStatement.setInt(10, statsSet.getInteger("dusk_blue_stones"));
        preparedStatement.setInt(11, statsSet.getInteger("dusk_ancient_adena_amount"));
        preparedStatement.setInt(12, statsSet.getInteger("dusk_contribution_score"));
        preparedStatement.setInt(13, statsSet.getInteger("char_obj_id"));
        preparedStatement.executeUpdate();
    }

    protected void resetPlayerData() {
        for (StatsSet statsSet : this.aT.values()) {
            int n = statsSet.getInteger("char_obj_id");
            if (statsSet.getInteger("cabal") == this.getCabalHighestScore()) {
                switch (this.getCabalHighestScore()) {
                    case 2: {
                        statsSet.set("dawn_red_stones", 0);
                        statsSet.set("dawn_green_stones", 0);
                        statsSet.set("dawn_blue_stones", 0);
                        statsSet.set("dawn_contribution_score", 0);
                        break;
                    }
                    case 1: {
                        statsSet.set("dusk_red_stones", 0);
                        statsSet.set("dusk_green_stones", 0);
                        statsSet.set("dusk_blue_stones", 0);
                        statsSet.set("dusk_contribution_score", 0);
                    }
                }
            } else if (statsSet.getInteger("cabal") == 2 || statsSet.getInteger("cabal") == 0) {
                statsSet.set("dusk_red_stones", 0);
                statsSet.set("dusk_green_stones", 0);
                statsSet.set("dusk_blue_stones", 0);
                statsSet.set("dusk_contribution_score", 0);
            } else if (statsSet.getInteger("cabal") == 1 || statsSet.getInteger("cabal") == 0) {
                statsSet.set("dawn_red_stones", 0);
                statsSet.set("dawn_green_stones", 0);
                statsSet.set("dawn_blue_stones", 0);
                statsSet.set("dawn_contribution_score", 0);
            }
            statsSet.set("cabal", 0);
            statsSet.set("seal", 0);
            this.aT.put(n, statsSet);
        }
    }

    private boolean d(int n) {
        return this.aT.containsKey(n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public int setPlayerInfo(int n, int n2, int n3) {
        StatsSet statsSet;
        block12: {
            PreparedStatement preparedStatement;
            Connection connection;
            statsSet = null;
            if (this.d(n)) {
                statsSet = this.aT.get(n);
                statsSet.set("cabal", n2);
                statsSet.set("seal", n3);
                this.aT.put(n, statsSet);
            } else {
                statsSet = new StatsSet();
                statsSet.set("char_obj_id", n);
                statsSet.set("cabal", n2);
                statsSet.set("seal", n3);
                statsSet.set("dawn_red_stones", 0);
                statsSet.set("dawn_green_stones", 0);
                statsSet.set("dawn_blue_stones", 0);
                statsSet.set("dawn_ancient_adena_amount", 0);
                statsSet.set("dawn_contribution_score", 0);
                statsSet.set("dusk_red_stones", 0);
                statsSet.set("dusk_green_stones", 0);
                statsSet.set("dusk_blue_stones", 0);
                statsSet.set("dusk_ancient_adena_amount", 0);
                statsSet.set("dusk_contribution_score", 0);
                this.aT.put(n, statsSet);
                connection = null;
                preparedStatement = null;
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("INSERT INTO `seven_signs` (`char_obj_id`, `cabal`, `seal`) VALUES (?,?,?)");
                preparedStatement.setInt(1, n);
                preparedStatement.setString(2, SevenSigns.getCabalShortName(n2));
                preparedStatement.setInt(3, n3);
                preparedStatement.execute();
                DbUtils.closeQuietly(connection, preparedStatement);
            }
            break block12;
            catch (SQLException sQLException) {
                try {
                    bS.error("SevenSigns: Failed to save data: " + sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
        }
        long l = 0L;
        switch (n2) {
            case 2: {
                l = SevenSigns.calcContributionScore(statsSet.getInteger("dawn_blue_stones"), statsSet.getInteger("dawn_green_stones"), statsSet.getInteger("dawn_red_stones"));
                this._dawnStoneScore += l;
                break;
            }
            case 1: {
                l = SevenSigns.calcContributionScore(statsSet.getInteger("dusk_blue_stones"), statsSet.getInteger("dusk_green_stones"), statsSet.getInteger("dusk_red_stones"));
                this._duskStoneScore += l;
            }
        }
        if (statsSet.getInteger("cabal") == 2) {
            this.aW.put(n3, this.aW.get(n3) + 1);
        } else {
            this.aV.put(n3, this.aV.get(n3) + 1);
        }
        this.saveSevenSignsData(n, true);
        return n2;
    }

    public int getAncientAdenaReward(Player player, boolean bl) {
        int n = player.getObjectId();
        StatsSet statsSet = this.aT.get(n);
        int n2 = 0;
        if (statsSet.getInteger("cabal") == 2) {
            n2 = statsSet.getInteger("dawn_ancient_adena_amount");
            statsSet.set("dawn_ancient_adena_amount", 0);
        } else {
            n2 = statsSet.getInteger("dusk_ancient_adena_amount");
            statsSet.set("dusk_ancient_adena_amount", 0);
        }
        if (bl) {
            this.aT.put(n, statsSet);
            this.saveSevenSignsData(n, false);
        }
        return n2;
    }

    public long addPlayerStoneContrib(Player player, long l, long l2, long l3) {
        return this.addPlayerStoneContrib(player.getObjectId(), l, l2, l3);
    }

    public long addPlayerStoneContrib(int n, long l, long l2, long l3) {
        StatsSet statsSet = this.aT.get(n);
        long l4 = SevenSigns.calcContributionScore(l, l2, l3);
        long l5 = 0L;
        long l6 = 0L;
        if (statsSet.getInteger("cabal") == 2) {
            l5 = (long)statsSet.getInteger("dawn_ancient_adena_amount") + SevenSigns.calcAncientAdenaReward(l, l2, l3);
            l6 = (long)statsSet.getInteger("dawn_contribution_score") + l4;
            if (l6 > Config.MAXIMUM_CONTRIBUTION_SEAL_STONES) {
                return -1L;
            }
            statsSet.set("dawn_red_stones", (long)statsSet.getInteger("dawn_red_stones") + l3);
            statsSet.set("dawn_green_stones", (long)statsSet.getInteger("dawn_green_stones") + l2);
            statsSet.set("dawn_blue_stones", (long)statsSet.getInteger("dawn_blue_stones") + l);
            statsSet.set("dawn_ancient_adena_amount", l5);
            statsSet.set("dawn_contribution_score", l6);
            this.aT.put(n, statsSet);
            this._dawnStoneScore += l4;
        } else {
            l5 = (long)statsSet.getInteger("dusk_ancient_adena_amount") + SevenSigns.calcAncientAdenaReward(l, l2, l3);
            l6 = (long)statsSet.getInteger("dusk_contribution_score") + l4;
            if (l6 > Config.MAXIMUM_CONTRIBUTION_SEAL_STONES) {
                return -1L;
            }
            statsSet.set("dusk_red_stones", (long)statsSet.getInteger("dusk_red_stones") + l3);
            statsSet.set("dusk_green_stones", (long)statsSet.getInteger("dusk_green_stones") + l2);
            statsSet.set("dusk_blue_stones", (long)statsSet.getInteger("dusk_blue_stones") + l);
            statsSet.set("dusk_ancient_adena_amount", l5);
            statsSet.set("dusk_contribution_score", l6);
            this.aT.put(n, statsSet);
            this._duskStoneScore += l4;
        }
        this.saveSevenSignsData(n, true);
        return l4;
    }

    public void updateFestivalScore() {
        long l;
        long l2;
        Pair<Long, Long> pair;
        do {
            pair = this.c.get();
            l2 = 0L;
            l = 0L;
            for (int i = 0; i < 5; ++i) {
                long l3;
                long l4 = SevenSignsFestival.getInstance().getHighestScore(1, i);
                if (l4 > (l3 = SevenSignsFestival.getInstance().getHighestScore(2, i))) {
                    l += (long)SevenSignsFestival.FESTIVAL_LEVEL_SCORES[i];
                    continue;
                }
                if (l3 <= l4) continue;
                l2 += (long)SevenSignsFestival.FESTIVAL_LEVEL_SCORES[i];
            }
        } while (!this.c.compareAndSet(pair, (Pair<Long, Long>)Pair.of((Object)l2, (Object)l)));
    }

    public void sendCurrentPeriodMsg(Player player) {
        switch (this._activePeriod) {
            case 0: {
                player.sendPacket((IStaticPacket)SystemMsg.SEVEN_SIGNS_PREPARATIONS_HAVE_BEGUN_FOR_THE_NEXT_QUEST_EVENT);
                return;
            }
            case 1: {
                player.sendPacket((IStaticPacket)SystemMsg.SEVEN_SIGNS_THE_QUEST_EVENT_PERIOD_HAS_BEGUN_SPEAK_WITH_A_PRIEST_OF_DAWN_OR_DUSK_PRIESTESS_IF_YOU_WISH_TO_PARTICIPATE_IN_THE_EVENT);
                return;
            }
            case 2: {
                player.sendPacket((IStaticPacket)SystemMsg.SEVEN_SIGNS_QUEST_EVENT_HAS_ENDED_RESULTS_ARE_BEING_TALLIED);
                return;
            }
            case 3: {
                player.sendPacket((IStaticPacket)SystemMsg.SEVEN_SIGNS_THIS_IS_THE_SEAL_VALIDATION_PERIOD_A_NEW_QUEST_EVENT_PERIOD_BEGINS_NEXT_MONDAY);
                return;
            }
        }
    }

    public void sendMessageToAll(IStaticPacket iStaticPacket) {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            player.sendPacket(iStaticPacket);
        }
    }

    protected void initializeSeals() {
        for (Integer n : this.aU.keySet()) {
            int n2 = this.aU.get(n);
            if (n2 != 0) {
                if (this.isSealValidationPeriod()) {
                    bS.info("SevenSigns: The " + SevenSigns.getCabalName(n2) + " have won the " + SevenSigns.getSealName(n, false) + ".");
                    continue;
                }
                bS.info("SevenSigns: The " + SevenSigns.getSealName(n, false) + " is currently owned by " + SevenSigns.getCabalName(n2) + ".");
                continue;
            }
            bS.info("SevenSigns: The " + SevenSigns.getSealName(n, false) + " remains unclaimed.");
        }
    }

    protected void resetSeals() {
        this.aW.put(1, 0);
        this.aW.put(2, 0);
        this.aW.put(3, 0);
        this.aV.put(1, 0);
        this.aV.put(2, 0);
        this.aV.put(3, 0);
    }

    protected void calcNewSealOwners() {
        for (Integer n : this.aW.keySet()) {
            int n2 = this.aU.get(n);
            int n3 = 0;
            int n4 = this.getSealProportion(n, 2);
            int n5 = this.getTotalMembers(2) == 0 ? 1 : this.getTotalMembers(2);
            int n6 = this.getSealProportion(n, 1);
            int n7 = this.getTotalMembers(1) == 0 ? 1 : this.getTotalMembers(1);
            block0 : switch (n2) {
                case 0: {
                    switch (this.getCabalHighestScore()) {
                        case 0: {
                            if ((long)n4 >= Math.round(0.35 * (double)n5) && n4 > n6) {
                                n3 = 2;
                                break;
                            }
                            if ((long)n6 >= Math.round(0.35 * (double)n7) && n6 > n4) {
                                n3 = 1;
                                break;
                            }
                            n3 = n2;
                            break;
                        }
                        case 2: {
                            if ((long)n4 >= Math.round(0.35 * (double)n5)) {
                                n3 = 2;
                                break;
                            }
                            if ((long)n6 >= Math.round(0.35 * (double)n7)) {
                                n3 = 1;
                                break;
                            }
                            n3 = n2;
                            break;
                        }
                        case 1: {
                            n3 = (long)n6 >= Math.round(0.35 * (double)n7) ? 1 : ((long)n4 >= Math.round(0.35 * (double)n5) ? 2 : n2);
                        }
                    }
                    break;
                }
                case 2: {
                    switch (this.getCabalHighestScore()) {
                        case 0: {
                            if ((long)n4 >= Math.round(0.1 * (double)n5)) {
                                n3 = n2;
                                break;
                            }
                            if ((long)n6 >= Math.round(0.35 * (double)n7)) {
                                n3 = 1;
                                break;
                            }
                            n3 = 0;
                            break;
                        }
                        case 2: {
                            if ((long)n4 >= Math.round(0.1 * (double)n5)) {
                                n3 = n2;
                                break;
                            }
                            if ((long)n6 >= Math.round(0.35 * (double)n7)) {
                                n3 = 1;
                                break;
                            }
                            n3 = 0;
                            break;
                        }
                        case 1: {
                            n3 = (long)n6 >= Math.round(0.1 * (double)n7) ? 1 : ((long)n4 >= Math.round(0.35 * (double)n5) ? n2 : 0);
                        }
                    }
                    break;
                }
                case 1: {
                    switch (this.getCabalHighestScore()) {
                        case 0: {
                            if ((long)n6 >= Math.round(0.1 * (double)n7)) {
                                n3 = n2;
                                break block0;
                            }
                            if ((long)n4 >= Math.round(0.35 * (double)n5)) {
                                n3 = 2;
                                break block0;
                            }
                            n3 = 0;
                            break block0;
                        }
                        case 2: {
                            if ((long)n4 >= Math.round(0.35 * (double)n5)) {
                                n3 = 2;
                                break block0;
                            }
                            if ((long)n6 >= Math.round(0.1 * (double)n7)) {
                                n3 = n2;
                                break block0;
                            }
                            n3 = 0;
                            break block0;
                        }
                        case 1: {
                            n3 = (long)n6 >= Math.round(0.1 * (double)n7) ? n2 : ((long)n4 >= Math.round(0.35 * (double)n5) ? 2 : 0);
                        }
                    }
                }
            }
            this.aU.put(n, n3);
            if (!Config.SEND_SSQ_SEAL_STATUS) continue;
            switch (n) {
                case 1: {
                    if (n3 == 2) {
                        this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_LORDS_OF_DAWN_HAVE_OBTAINED_THE_SEAL_OF_AVARICE);
                        break;
                    }
                    if (n3 != 1) break;
                    this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_REVOLUTIONARIES_OF_DUSK_HAVE_OBTAINED_THE_SEAL_OF_AVARICE);
                    break;
                }
                case 2: {
                    if (n3 == 2) {
                        this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_LORDS_OF_DAWN_HAVE_OBTAINED_THE_SEAL_OF_GNOSIS);
                        break;
                    }
                    if (n3 != 1) break;
                    this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_REVOLUTIONARIES_OF_DUSK_HAVE_OBTAINED_THE_SEAL_OF_GNOSIS);
                    break;
                }
                case 3: {
                    if (n3 == 2) {
                        this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_LORDS_OF_DAWN_HAVE_OBTAINED_THE_SEAL_OF_STRIFE);
                        break;
                    }
                    if (n3 != 1) break;
                    this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_REVOLUTIONARIES_OF_DUSK_HAVE_OBTAINED_THE_SEAL_OF_STRIFE);
                }
            }
        }
    }

    public int getPriestCabal(int n) {
        switch (n) {
            case 31078: 
            case 31079: 
            case 31080: 
            case 31081: 
            case 31082: 
            case 31083: 
            case 31084: 
            case 31168: 
            case 31692: 
            case 31694: 
            case 31997: {
                return 2;
            }
            case 31085: 
            case 31086: 
            case 31087: 
            case 31088: 
            case 31089: 
            case 31090: 
            case 31091: 
            case 31169: 
            case 31693: 
            case 31695: 
            case 31998: {
                return 1;
            }
        }
        return 0;
    }

    public void changePeriod() {
        this.U = ThreadPoolManager.getInstance().schedule(new SevenSignsPeriodChange(), 10L);
    }

    public void changePeriod(int n) {
        this.changePeriod(n, 1);
    }

    public void changePeriod(int n, int n2) {
        this._activePeriod = n - 1;
        if (this._activePeriod < 0) {
            this._activePeriod += 4;
        }
        this.U = ThreadPoolManager.getInstance().schedule(new SevenSignsPeriodChange(), (long)n2 * 1000L);
    }

    public void setTimeToNextPeriodChange(int n) {
        this.a.setTimeInMillis(System.currentTimeMillis() + (long)n * 1000L * 60L);
        if (this.U != null) {
            this.U.cancel(false);
        }
        this.U = ThreadPoolManager.getInstance().schedule(new SevenSignsPeriodChange(), this.getMilliToPeriodChange());
    }

    public SSListenerList getListenerEngine() {
        return this.a;
    }

    public <T extends GameListener> boolean addListener(T t) {
        return this.a.add(t);
    }

    public <T extends GameListener> boolean removeListener(T t) {
        return this.a.remove(t);
    }

    static {
        ORATOR_NPC_IDS = new HashSet<Integer>(Arrays.asList(31093, 31172, 31174, 31176, 31178, 31180, 31182, 31184, 31186, 31188, 31190, 31192, 31194, 31196, 31198, 31200, 31231, 31232, 31233, 31234, 31235, 31236, 31237, 31238, 31239, 31240, 31241, 31242, 31243, 31244, 31245, 31246, 31713, 31714, 31715, 31716, 31717, 31718, 31719, 31720));
        PREACHER_NPC_IDS = new HashSet<Integer>(Arrays.asList(31094, 31173, 31175, 31177, 31179, 31181, 31183, 31185, 31187, 31189, 31191, 31193, 31195, 31197, 31199, 31201, 31247, 31248, 31249, 31250, 31251, 31252, 31253, 31254, 31721, 31722, 31723, 31724, 31725, 31726, 31727, 31728, 32003, 32004, 32005, 32006));
    }

    protected class SSListenerList
    extends ListenerList<GameServer> {
        protected SSListenerList() {
        }

        public void onPeriodChange() {
            if (SevenSigns.getInstance().getCurrentPeriod() == 3) {
                SevenSigns.getInstance().getCabalHighestScore();
            }
            this.forEachListener(OnSSPeriodListener.class, onSSPeriodListener -> onSSPeriodListener.onPeriodChange(SevenSigns.getInstance().getCurrentPeriod()));
        }
    }

    private class OnStartListenerImpl
    implements OnStartListener {
        private OnStartListenerImpl() {
        }

        @Override
        public void onStart() {
            SevenSigns.this.getListenerEngine().onPeriodChange();
        }
    }

    public class SevenSignsPeriodChange
    extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            _log.info("SevenSignsPeriodChange: old=" + SevenSigns.this._activePeriod);
            int n = SevenSigns.this._activePeriod++;
            switch (n) {
                case 0: {
                    SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_COMPETITION_PERIOD_HAS_BEGUN__VISIT_A_PRIEST_OF_DAWN_OR_PRIESTESS_OF_DUSK_TO_PARTICIPATE_IN_THE_EVENT);
                    RaidBossSpawnManager.getInstance().distributeRewards();
                    break;
                }
                case 1: {
                    SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_COMPETITION_PERIOD_HAS_ENDED_THE_NEXT_QUEST_EVENT_WILL_START_IN_ONE_WEEK);
                    int n2 = SevenSigns.this.getCabalHighestScore();
                    SevenSigns.this.calcNewSealOwners();
                    if (n2 == 1) {
                        SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_REVOLUTIONARIES_OF_DUSK_HAVE_WON);
                    } else {
                        SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_LORDS_OF_DAWN_HAVE_WON);
                    }
                    SevenSigns.this._previousWinner = n2;
                    break;
                }
                case 2: {
                    SevenSignsFestival.getInstance().distribAccumulatedBonus();
                    SevenSignsFestival.getInstance().rewardHighestRanked();
                    SevenSigns.this.initializeSeals();
                    RaidBossSpawnManager.getInstance().distributeRewards();
                    SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_SEAL_VALIDATION_PERIOD_HAS_BEGUN);
                    _log.info("SevenSigns: The " + SevenSigns.getCabalName(SevenSigns.this._previousWinner) + " have won the competition with " + SevenSigns.this.getCurrentScore(SevenSigns.this._previousWinner) + " points!");
                    break;
                }
                case 3: {
                    SevenSigns.this._activePeriod = 0;
                    SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_SEAL_VALIDATION_PERIOD_HAS_ENDED);
                    SevenSigns.this.resetPlayerData();
                    SevenSigns.this.resetSeals();
                    SevenSigns.this._dawnStoneScore = 0L;
                    SevenSigns.this._duskStoneScore = 0L;
                    SevenSigns.this.c.set((Pair<Long, Long>)Pair.of((Object)0L, (Object)0L));
                    ++SevenSigns.this._currentCycle;
                    SevenSignsFestival.getInstance().resetFestivalData(false);
                }
            }
            SevenSigns.this.saveSevenSignsData(0, true);
            _log.info("SevenSignsPeriodChange: new=" + SevenSigns.this._activePeriod);
            try {
                _log.info("SevenSigns: Change Catacomb spawn...");
                SevenSigns.this.getListenerEngine().onPeriodChange();
                SSQInfo sSQInfo = new SSQInfo();
                for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                    player.sendPacket((IStaticPacket)sSQInfo);
                }
                _log.info("SevenSigns: Spawning NPCs...");
                _log.info("SevenSigns: The " + SevenSigns.this.getCurrentPeriodName() + " period has begun!");
                _log.info("SevenSigns: Calculating next period change time...");
                SevenSigns.this.setCalendarForNextPeriodChange();
                _log.info("SevenSignsPeriodChange: time to next change=" + Util.formatTime((int)(SevenSigns.this.getMilliToPeriodChange() / 1000L)));
                SevenSignsPeriodChange sevenSignsPeriodChange = new SevenSignsPeriodChange();
                SevenSigns.this.U = ThreadPoolManager.getInstance().schedule(sevenSignsPeriodChange, SevenSigns.this.getMilliToPeriodChange());
            }
            catch (Exception exception) {
                _log.error("", (Throwable)exception);
            }
        }
    }

    public class SevenSignsAnnounce
    extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            if (Config.SEND_SSQ_WELCOME_MESSAGE) {
                for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                    SevenSigns.this.sendCurrentPeriodMsg(player);
                }
                ThreadPoolManager.getInstance().schedule(new SevenSignsAnnounce(), (long)Config.SS_ANNOUNCE_PERIOD * 1000L * 60L);
            }
        }
    }
}
