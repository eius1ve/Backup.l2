/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.entity.oly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.entity.oly.CompetitionController;
import l2.gameserver.model.entity.oly.CompetitionType;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.entity.oly.ParticipantPool;
import l2.gameserver.model.entity.oly.StadiumPool;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class OlyController {
    private static final Logger bZ = LoggerFactory.getLogger(OlyController.class);
    private static final SimpleDateFormat e = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
    private static OlyController a;
    private int lE;
    private boolean dn = false;
    private boolean do = false;
    private ScheduledFuture<?> X;
    private ScheduledFuture<?> Y;
    private ScheduledFuture<?> Z;
    private ScheduledFuture<?>[][] a;
    private ScheduledFuture<?>[] a;
    private long ci;
    private long cj;
    private long ck;
    private long[] b;
    private int lF;
    private long[][] a = new long[31][];
    private int lG;
    private int lH;
    private static final String dq = "oly_season_id";
    private static final String dr = "oly_season_calc";
    private static final String ds = "SELECT `season_id`,`season_start_time`,`season_end_time`,`nominate_start`,`b_idx`,`b_s0`,`b_s1`,`b_s2`,`b_s3`,`c_idx`,`c_s0`,`c_e0`,`c_s1`,`c_e1`,`c_s2`,`c_e2`,`c_s3`,`c_e3`,`c_s4`,`c_e4`,`c_s5`,`c_e5`,`c_s6`,`c_e6`,`c_s7`,`c_e7`,`c_s8`,`c_e8`,`c_s9`,`c_e9`,`c_s10`,`c_e10`,`c_s11`,`c_e11`,`c_s12`,`c_e12`,`c_s13`,`c_e13`,`c_s14`,`c_e14`,`c_s15`,`c_e15`,`c_s16`,`c_e16`,`c_s17`,`c_e17`,`c_s18`,`c_e18`,`c_s19`,`c_e19`,`c_s20`,`c_e20`,`c_s21`,`c_e21`,`c_s22`,`c_e22`,`c_s23`,`c_e23`,`c_s24`,`c_e24`,`c_s25`,`c_e25`,`c_s26`,`c_e26`,`c_s27`,`c_e27`,`c_s28`,`c_e28`,`c_s29`,`c_e29`,`c_s30`,`c_e30` FROM `oly_season` WHERE `season_id` = ?";
    private static final String dt = "REPLACE INTO `oly_season`(`season_id`,`season_start_time`,`season_end_time`,`nominate_start`,`b_idx`,`b_s0`,`b_s1`,`b_s2`,`b_s3`,`c_idx`,`c_s0`,`c_e0`,`c_s1`,`c_e1`,`c_s2`,`c_e2`,`c_s3`,`c_e3`,`c_s4`,`c_e4`,`c_s5`,`c_e5`,`c_s6`,`c_e6`,`c_s7`,`c_e7`,`c_s8`,`c_e8`,`c_s9`,`c_e9`,`c_s10`,`c_e10`,`c_s11`,`c_e11`,`c_s12`,`c_e12`,`c_s13`,`c_e13`,`c_s14`,`c_e14`,`c_s15`,`c_e15`,`c_s16`,`c_e16`,`c_s17`,`c_e17`,`c_s18`,`c_e18`,`c_s19`,`c_e19`,`c_s20`,`c_e20`,`c_s21`,`c_e21`,`c_s22`,`c_e22`,`c_s23`,`c_e23`,`c_s24`,`c_e24`,`c_s25`,`c_e25`,`c_s26`,`c_e26`,`c_s27`,`c_e27`,`c_s28`,`c_e28`,`c_s29`,`c_e29`,`c_s30`,`c_e30`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String du = "@OlyPartCnt";
    private static final String dv = "oly_chero_season";
    private int lI = -1;

    public static final OlyController getInstance() {
        if (a == null) {
            a = new OlyController();
        }
        return a;
    }

    private OlyController() {
        this.b = new long[4];
        this.a = new ScheduledFuture[this.a.length][];
        this.a = new ScheduledFuture[this.b.length];
        this.load();
        this.schedule();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void load() {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block6: {
            this.lE = ServerVariables.getInt(dq, 0);
            this.dn = ServerVariables.getBool(dr, false);
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(ds);
                preparedStatement.setInt(1, this.lE);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    this.ci = resultSet.getLong("season_start_time");
                    this.cj = resultSet.getLong("season_end_time");
                    this.ck = resultSet.getLong("nominate_start");
                    this.lF = resultSet.getInt("b_idx");
                    this.b[0] = resultSet.getLong("b_s0");
                    this.b[1] = resultSet.getLong("b_s1");
                    this.b[2] = resultSet.getLong("b_s2");
                    this.b[3] = resultSet.getLong("b_s3");
                    this.lG = resultSet.getInt("c_idx");
                    for (int i = 0; i < this.a.length; ++i) {
                        long[] lArray = new long[]{resultSet.getLong("c_s" + i), resultSet.getLong("c_e" + i)};
                        this.a[i] = lArray;
                    }
                    break block6;
                }
                bZ.info("Oly: Generating a new season " + this.lE);
                this.bG();
                this.save();
            }
            catch (Exception exception) {
                try {
                    exception.printStackTrace();
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        this.lH = ServerVariables.getInt(du, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void save() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            int n;
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(dt);
            preparedStatement.setInt(1, this.lE);
            preparedStatement.setLong(2, this.ci);
            preparedStatement.setLong(3, this.cj);
            preparedStatement.setLong(4, this.ck);
            preparedStatement.setInt(5, this.lF);
            for (n = 0; n < this.b.length; ++n) {
                preparedStatement.setLong(6 + n, this.b[n]);
            }
            preparedStatement.setInt(10, this.lG);
            for (n = 0; n < this.a.length; ++n) {
                preparedStatement.setLong(11 + n * 2, this.a[n][0]);
                preparedStatement.setLong(12 + n * 2, this.a[n][1]);
            }
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            try {
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        ServerVariables.set(dq, this.lE);
        ServerVariables.set(dr, this.dn);
        ServerVariables.set(du, this.lH);
    }

    private void schedule() {
        int n;
        long l = System.currentTimeMillis() / 1000L;
        int n2 = this.lE;
        long l2 = Math.max(0L, this.ci - l);
        if (l2 == 0L) {
            this.X = null;
            this.l(n2);
        } else {
            this.X = ThreadPoolManager.getInstance().schedule(new SeasonStartTask(n2), l2 * 1000L);
            bZ.info("OlyController: Season " + n2 + " start schedule at " + OlyController.a(this.X));
        }
        long l3 = Math.max(0L, this.cj - l);
        if (l3 == 0L) {
            this.Y = null;
            this.m(n2);
        } else {
            this.Y = ThreadPoolManager.getInstance().schedule(new SeasonEndTask(n2), l3 * 1000L);
            bZ.info("OlyController: Season " + n2 + " end schedule at " + OlyController.a(this.Y));
        }
        long l4 = Math.max(0L, this.ck - l);
        if (l4 == 0L) {
            this.Z = null;
            this.n(n2);
        } else {
            this.Z = ThreadPoolManager.getInstance().schedule(new NominationTask(n2), l4 * 1000L);
            bZ.info("OlyController: Season " + n2 + " nomination schedule at " + OlyController.a(this.Z));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (n = this.lG; n < this.a.length; ++n) {
            if (this.a[n] == null || this.a[n][0] < l && this.a[n][1] < l) continue;
            if (n != this.lG) {
                stringBuilder.append(';');
            }
            this.a[n] = new ScheduledFuture[]{ThreadPoolManager.getInstance().schedule(new CompetitionStartTask(n2, n), Math.max(60L, this.a[n][0] - l) * 1000L), ThreadPoolManager.getInstance().schedule(new CompetitionEndTask(n2, n), Math.max(60L, this.a[n][1] - l) * 1000L)};
            stringBuilder.append(OlyController.a(this.a[n][0]) + "-" + OlyController.a(this.a[n][1]));
        }
        bZ.info("OlyController: Season " + n2 + " competitions schedule at [" + stringBuilder.toString() + "]");
        stringBuilder.delete(0, stringBuilder.length());
        for (n = this.lF; n < this.b.length; ++n) {
            if (this.b[n] <= l) continue;
            if (n != this.lF) {
                stringBuilder.append(';');
            }
            this.a[n] = ThreadPoolManager.getInstance().schedule(new BonusTask(n2, n), Math.max((long)((3 + n - this.lF) * 60), this.b[n] - l) * 1000L);
            stringBuilder.append(OlyController.a(this.a[n]));
        }
        bZ.info("OlyController: Season " + n2 + " bonuses schedule at [" + stringBuilder.toString() + "]");
        stringBuilder = null;
    }

    private synchronized void l(int n) {
        try {
            this.dn = false;
            if (Config.OLY_ENABLED) {
                Announcements.getInstance().announceToAll((SystemMessage)new SystemMessage(SystemMsg.ROUND_S1_OF_THE_GRAND_OLYMPIAD_GAMES_HAS_STARTED).addNumber(n));
            }
            bZ.info("OlyController: Season " + n + " started.");
        }
        catch (Exception exception) {
            bZ.warn("Exception while starting of " + n + " season", (Throwable)exception);
        }
    }

    private synchronized void m(int n) {
        try {
            if (!this.dn) {
                this.dn = true;
                if (ServerVariables.getInt(dv, -1) != n) {
                    bZ.info("OlyController: calculation heroes for " + n + " season");
                    HeroController.getInstance().ComputeNewHeroNobleses();
                    ServerVariables.set(dv, n);
                }
                this.save();
                if (Config.OLY_ENABLED) {
                    Announcements.getInstance().announceToAll((SystemMessage)new SystemMessage(SystemMsg.ROUND_S1_OF_THE_GRAND_OLYMPIAD_GAMES_HAS_NOW_ENDED).addNumber(n));
                }
            } else {
                bZ.warn("OlyController: Unexpected season calculated. Canceling computation.");
            }
            bZ.info("OlyController: Season " + n + " ended.");
        }
        catch (Exception exception) {
            bZ.warn("Exception while ending of " + n + " season", (Throwable)exception);
        }
    }

    private synchronized void n(int n) {
        try {
            if (this.dn) {
                this.dn = false;
                this.save();
            } else {
                bZ.warn("OlyController: Season not calculated. Run calculation manualy.");
            }
            bZ.info("OlyController: Season " + n + " nomination started.");
            ThreadPoolManager.getInstance().execute(new NewSeasonCalcTask(n + 1));
        }
        catch (Exception exception) {
            bZ.warn("Exception while nominating in " + n + " season", (Throwable)exception);
        }
    }

    private synchronized void c(int n, int n2) {
        try {
            if (!this.do) {
                this.do = true;
                StadiumPool.getInstance().AllocateStadiums();
                ParticipantPool.getInstance().AllocatePools();
                CompetitionController.getInstance();
                CompetitionController.getInstance().scheduleStartTask();
                if (Config.OLY_ENABLED) {
                    Announcements.getInstance().announceToAll(SystemMsg.SHARPEN_YOUR_SWORDS_TIGHTEN_THE_STITCHING_IN_YOUR_ARMOR_AND_MAKE_HASTE_TO_A_GRAND_OLYMPIAD_MANAGER__BATTLES_IN_THE_GRAND_OLYMPIAD_GAMES_ARE_NOW_TAKING_PLACE);
                }
                bZ.info("OlyController: Season " + n + " comp " + n2 + " started.");
                this.lI = n2;
            } else {
                bZ.warn("OlyController: Can't start new competitions. Old comps in progress.");
            }
        }
        catch (Exception exception) {
            bZ.warn("Exception while start comp " + n2 + " in " + n + " season", (Throwable)exception);
        }
    }

    private synchronized void d(int n, int n2) {
        try {
            if (this.do) {
                CompetitionController.getInstance().cancelStartTask();
                this.do = false;
                StadiumPool.getInstance().FreeStadiums();
                ParticipantPool.getInstance().FreePools();
                this.lI = -1;
                ++this.lG;
                if (Config.OLY_ENABLED) {
                    Announcements.getInstance().announceToAll(new SystemMessage(SystemMsg.THE_GRAND_OLYMPIAD_REGISTRATION_PERIOD_HAS_ENDED));
                    Announcements.getInstance().announceToAll(SystemMsg.MUCH_CARNAGE_HAS_BEEN_LEFT_FOR_THE_CLEANUP_CREW_OF_THE_OLYMPIAD_STADIUM);
                }
                bZ.info("OlyController: Season " + n + " comp " + n2 + " ended.");
                this.save();
            } else {
                bZ.warn("OlyController: Can't stop competitions. Competitions not in progress.");
            }
        }
        catch (Exception exception) {
            bZ.warn("Exception while end comp " + n2 + " in " + n + " season", (Throwable)exception);
        }
    }

    private synchronized void e(int n, int n2) {
        try {
            NoblesController.getInstance().AddWeaklyBonus();
            ++this.lF;
            bZ.info("OlyController: Season " + n + " bonus " + n2 + " applied.");
            this.save();
        }
        catch (Exception exception) {
            bZ.warn("Exception while bonus " + n2 + " in " + n + " season", (Throwable)exception);
        }
    }

    private synchronized void o(int n) {
        try {
            this.save();
            this.lE = n;
            if (Config.OLY_RECALC_NEW_SEASON) {
                this.bG();
                this.save();
            } else {
                ServerVariables.set(dq, this.lE);
                this.load();
            }
            this.schedule();
        }
        catch (Exception exception) {
            bZ.warn("Exception while calculating new " + n + " season", (Throwable)exception);
        }
    }

    public boolean isCompetitionsActive() {
        return this.do;
    }

    public boolean isRegAllowed() {
        if (Config.ALT_OLY_COMPETITION_DAYS.isEmpty() || !Config.ALT_OLY_COMPETITION_DAYS.contains(Calendar.getInstance().get(7))) {
            return false;
        }
        if (this.do && this.lI >= 0) {
            return System.currentTimeMillis() < (this.a[this.lI][1] - 300L) * 1000L;
        }
        return this.do;
    }

    public boolean isCalculationPeriod() {
        return this.dn;
    }

    public int getCurrentSeason() {
        return this.lE;
    }

    public int getCurrentPeriod() {
        return this.lF;
    }

    public void shutdown() {
        if (this.do) {
            CompetitionController.getInstance().cancelStartTask();
            StadiumPool.getInstance().FreeStadiums();
            ParticipantPool.getInstance().FreePools();
        }
    }

    public void announceCompetition(CompetitionType competitionType, int n) {
        for (NpcInstance npcInstance : GameObjectsStorage.getAllByNpcId(31688, false)) {
            if (!Config.NPC_OLYMPIAD_GAME_ANNOUNCE) continue;
            switch (competitionType) {
                case CLASS_FREE: {
                    Functions.npcShoutCustomMessage(npcInstance, "l2p.gameserver.model.entity.OlympiadGame.OlympiadNonClassed", String.valueOf(n + 1));
                    break;
                }
                case CLASS_INDIVIDUAL: 
                case CLASS_TYPE_INDIVIDUAL: {
                    Functions.npcShoutCustomMessage(npcInstance, "l2p.gameserver.model.entity.OlympiadGame.OlympiadClassed", String.valueOf(n + 1));
                    break;
                }
                case TEAM_CLASS_FREE: {
                    Functions.npcShoutCustomMessage(npcInstance, "l2p.gameserver.model.entity.OlympiadGame.OlympiadTeam", String.valueOf(n + 1));
                    break;
                }
            }
        }
    }

    public int getPartCount() {
        return this.lH;
    }

    public int getCurrentPartCount() {
        return CompetitionController.getInstance().getCompetitions().size();
    }

    public void incPartCount() {
        ++this.lH;
    }

    private final synchronized void bG() {
        Calendar calendar = Calendar.getInstance();
        if (Config.OLY_SEASON_TIME_CALC_MODE == Config.OlySeasonTimeCalcMode.NORMAL) {
            calendar.set(5, 1);
        } else {
            calendar.set(5, calendar.get(5));
        }
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long l = calendar.getTimeInMillis();
        this.ci = this.a(l, Config.OLY_SEASON_START_TIME);
        this.cj = this.a(l, Config.OLY_SEASON_END_TIME);
        this.ck = this.a(l, Config.OLY_NOMINATE_TIME);
        l = this.ci * 1000L;
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(l);
        for (int i = 0; i < this.b.length; ++i) {
            this.b[i] = this.a(calendar2, Config.OLY_BONUS_TIME);
        }
        this.lF = 0;
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTimeInMillis(l);
        for (int i = 0; i < this.a.length; ++i) {
            this.a[i] = new long[2];
            this.a[i][0] = this.a(calendar3, Config.OLY_COMPETITION_START_TIME);
            this.a[i][1] = this.a(calendar3, Config.OLY_COMPETITION_END_TIME);
            if (this.a[i][0] > this.cj) {
                this.a[i][0] = -1L;
                this.a[i][1] = -1L;
            }
            if (this.a[i][1] < this.cj - 300L) continue;
            long[] lArray = this.a[i];
            lArray[1] = lArray[1] - 300L;
        }
        this.lG = 0;
    }

    private long a(long l, String string) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(l));
        return this.a(calendar, string);
    }

    private long a(Calendar calendar, String string) {
        String[] stringArray;
        String[] stringArray2 = string.split("\\s+");
        if (stringArray2.length == 2) {
            String string2;
            stringArray = stringArray2[0];
            String[] stringArray3 = stringArray.split(":");
            if (stringArray3.length == 2) {
                if (stringArray3[0].startsWith("+")) {
                    calendar.add(2, Integer.parseInt(stringArray3[0].substring(1)));
                } else {
                    calendar.set(2, Integer.parseInt(stringArray3[0]) - 1);
                }
            }
            if ((string2 = stringArray3[stringArray3.length - 1]).startsWith("+")) {
                calendar.add(5, Integer.parseInt(string2.substring(1)));
            } else {
                calendar.set(5, Integer.parseInt(string2));
            }
        }
        if ((stringArray = stringArray2[stringArray2.length - 1].split(":"))[0].startsWith("+")) {
            calendar.add(11, Integer.parseInt(stringArray[0].substring(1)));
        } else {
            calendar.set(11, Integer.parseInt(stringArray[0]));
        }
        if (stringArray[1].startsWith("+")) {
            calendar.add(12, Integer.parseInt(stringArray[1].substring(1)));
        } else {
            calendar.set(12, Integer.parseInt(stringArray[1]));
        }
        return calendar.getTimeInMillis() / 1000L;
    }

    public long getSeasonStartTime() {
        return this.ci;
    }

    public long getSeasonEndTime() {
        return this.cj;
    }

    private static String a(ScheduledFuture<?> scheduledFuture) {
        return OlyController.d(System.currentTimeMillis() / 1000L + scheduledFuture.getDelay(TimeUnit.SECONDS));
    }

    private static String d(long l) {
        return e.format(new Date((l + 1L) * 1000L));
    }

    static {
        e.setTimeZone(TimeZone.getDefault());
        a = null;
    }

    private class SeasonStartTask
    implements Runnable {
        private int lJ;

        public SeasonStartTask(int n) {
            this.lJ = n;
        }

        @Override
        public void run() {
            OlyController.getInstance().l(this.lJ);
        }
    }

    private class SeasonEndTask
    implements Runnable {
        private int lJ;

        public SeasonEndTask(int n) {
            this.lJ = n;
        }

        @Override
        public void run() {
            OlyController.getInstance().m(this.lJ);
        }
    }

    private class NominationTask
    implements Runnable {
        private int lJ;

        public NominationTask(int n) {
            this.lJ = n;
        }

        @Override
        public void run() {
            OlyController.getInstance().n(this.lJ);
        }
    }

    private class CompetitionStartTask
    implements Runnable {
        private int lJ;
        private int lL;

        public CompetitionStartTask(int n, int n2) {
            this.lJ = n;
            this.lL = n2;
        }

        @Override
        public void run() {
            OlyController.getInstance().c(this.lJ, this.lL);
        }
    }

    private class CompetitionEndTask
    implements Runnable {
        private int lJ;
        private int lL;

        public CompetitionEndTask(int n, int n2) {
            this.lJ = n;
            this.lL = n2;
        }

        @Override
        public void run() {
            OlyController.getInstance().d(this.lJ, this.lL);
        }
    }

    private class BonusTask
    implements Runnable {
        private int lJ;
        private int lK;

        public BonusTask(int n, int n2) {
            this.lJ = n;
            this.lK = n2;
        }

        @Override
        public void run() {
            OlyController.getInstance().e(this.lJ, this.lK);
        }
    }

    private class NewSeasonCalcTask
    implements Runnable {
        private int lJ;

        public NewSeasonCalcTask(int n) {
            this.lJ = n;
        }

        @Override
        public void run() {
            OlyController.getInstance().o(this.lJ);
        }
    }
}
