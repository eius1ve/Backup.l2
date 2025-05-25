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
import java.util.concurrent.TimeUnit;
import l2.commons.dbutils.DbUtils;
import l2.commons.threading.RunnableImpl;
import l2.commons.time.cron.SchedulingPattern;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LotteryManager {
    public static final long SECOND = 1000L;
    public static final long MINUTE = 60000L;
    private static LotteryManager a;
    private static final Logger bw;
    private static final String cy = "INSERT INTO `games`(`id`, `idnr`, `enddate`, `prize`, `newprize`) VALUES (?, ?, ?, ?, ?)";
    private static final String cz = "UPDATE `games` SET `prize`=?, `newprize`=? WHERE `id` = 1 AND `idnr` = ?";
    private static final String cA = "UPDATE `games` SET `finished`=1, `prize`=?, `newprize`=?, `number1`=?, `number2`=?, `prize1`=?, `prize2`=?, `prize3`=? WHERE `id`=1 AND `idnr`=?";
    private static final String cB = "SELECT `idnr`, `prize`, `newprize`, `enddate`, `finished` FROM `games` WHERE `id` = 1 ORDER BY `idnr` DESC LIMIT 1";
    private static final String cC = "SELECT `items`.`enchant` AS `enchant`, `items_options`.`damaged` AS `damaged` FROM `items`, `items_options` WHERE `items_options`.`blessed` = ? AND `items`.`item_id` = `items_options`.`item_id` AND `items`.`item_type` = 4442";
    private static final String cD = "SELECT `number1`, `number2`, `prize1`, `prize2`, `prize3` FROM `games` WHERE `id` = 1 AND `idnr` = ?";
    protected int _number = 1;
    protected int _prize = Config.SERVICES_LOTTERY_PRIZE;
    protected boolean _isSellingTickets = false;
    protected boolean _isStarted = false;
    protected long _enddate = System.currentTimeMillis();

    public LotteryManager() {
        if (Config.SERVICES_ALLOW_LOTTERY) {
            new startLottery().run();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void increasePrize(int n) {
        this._prize += n;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(cz);
            preparedStatement.setInt(1, this.getPrize());
            preparedStatement.setInt(2, this.getPrize());
            preparedStatement.setInt(3, this.getId());
            preparedStatement.execute();
        }
        catch (SQLException sQLException) {
            try {
                bw.warn("Lottery: Could not increase current lottery prize: " + sQLException);
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
    private boolean r() {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block7: {
            boolean bl;
            long l;
            block8: {
                connection = null;
                preparedStatement = null;
                resultSet = null;
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(cB);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block7;
                l = System.currentTimeMillis();
                this._number = resultSet.getInt("idnr");
                if (resultSet.getInt("finished") == 1) {
                    ++this._number;
                    this._prize = resultSet.getInt("newprize");
                    break block7;
                }
                this._prize = resultSet.getInt("prize");
                this._enddate = resultSet.getLong("enddate");
                if (this._enddate > l + TimeUnit.MINUTES.toMillis(2L)) break block8;
                new finishLottery().run();
                boolean bl2 = false;
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                return bl2;
            }
            try {
                if (this._enddate <= l) break block7;
                this._isStarted = true;
                ThreadPoolManager.getInstance().schedule(new finishLottery(), this._enddate - l);
                if (this._enddate > l + TimeUnit.MINUTES.toMillis(12L)) {
                    this._isSellingTickets = true;
                    ThreadPoolManager.getInstance().schedule(new stopSellingTickets(), this._enddate - l - TimeUnit.MINUTES.toMillis(10L));
                }
                bl = false;
            }
            catch (SQLException sQLException) {
                try {
                    bw.warn("Lottery: Could not restore lottery data: " + sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            return bl;
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return true;
    }

    private void aH() {
        if (Config.SERVICES_ALLOW_LOTTERY) {
            bw.info("Lottery: Starting ticket sell for lottery #" + this.getId() + ".");
        }
        this._isSellingTickets = true;
        this._isStarted = true;
        Announcements.getInstance().announceToAll("Lottery tickets are now available for Lucky Lottery #" + this.getId() + ".");
    }

    private void aI() {
        long l = System.currentTimeMillis();
        if (this._enddate < l) {
            while ((this._enddate = new SchedulingPattern(Config.SERVICES_LOTTERY_END_CRON).next(this._enddate)) < l) {
            }
        }
        ThreadPoolManager.getInstance().schedule(new stopSellingTickets(), this._enddate - l - TimeUnit.MINUTES.toMillis(10L));
        ThreadPoolManager.getInstance().schedule(new finishLottery(), this._enddate - l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void aJ() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(cy);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, this.getId());
            preparedStatement.setLong(3, this.getEndDate());
            preparedStatement.setInt(4, this.getPrize());
            preparedStatement.setInt(5, this.getPrize());
            preparedStatement.execute();
        }
        catch (SQLException sQLException) {
            try {
                bw.warn("Lottery: Could not store new lottery data: " + sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public int[] decodeNumbers(int n, int n2) {
        int n3;
        int[] nArray = new int[5];
        int n4 = 0;
        int n5 = 1;
        while (n > 0) {
            n3 = n / 2;
            if ((double)n3 != (double)n / 2.0) {
                nArray[n4++] = n5;
            }
            n /= 2;
            ++n5;
        }
        n5 = 17;
        while (n2 > 0) {
            n3 = n2 / 2;
            if ((double)n3 != (double)n2 / 2.0) {
                nArray[n4++] = n5;
            }
            n2 /= 2;
            ++n5;
        }
        return nArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int[] checkTicket(int n, int n2, int n3) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        int[] nArray;
        block14: {
            int n4;
            int n5;
            block15: {
                nArray = new int[]{0, 0};
                connection = null;
                preparedStatement = null;
                resultSet = null;
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(cD);
                preparedStatement.setInt(1, n);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block14;
                n5 = resultSet.getInt("number1") & n2;
                n4 = resultSet.getInt("number2") & n3;
                if (n5 != 0 || n4 != 0) break block15;
                int[] nArray2 = nArray;
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                return nArray2;
            }
            try {
                int n6 = 0;
                for (int i = 1; i <= 16; ++i) {
                    int n7;
                    int n8 = n5 / 2;
                    if ((double)n8 != (double)n5 / 2.0) {
                        ++n6;
                    }
                    if ((double)(n7 = n4 / 2) != (double)n4 / 2.0) {
                        ++n6;
                    }
                    n5 = n8;
                    n4 = n7;
                }
                switch (n6) {
                    case 0: {
                        break;
                    }
                    case 5: {
                        nArray[0] = 1;
                        nArray[1] = resultSet.getInt("prize1");
                        break;
                    }
                    case 4: {
                        nArray[0] = 2;
                        nArray[1] = resultSet.getInt("prize2");
                        break;
                    }
                    case 3: {
                        nArray[0] = 3;
                        nArray[1] = resultSet.getInt("prize3");
                        break;
                    }
                    default: {
                        nArray[0] = 4;
                        nArray[1] = 200;
                    }
                }
            }
            catch (SQLException sQLException) {
                try {
                    bw.warn("Lottery: Could not check lottery ticket #" + n + ": " + sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return nArray;
    }

    public int[] checkTicket(ItemInstance itemInstance) {
        return this.checkTicket(itemInstance.getBlessed(), itemInstance.getEnchantLevel(), itemInstance.getDamaged());
    }

    public boolean isSellableTickets() {
        return this._isSellingTickets;
    }

    public boolean isStarted() {
        return this._isStarted;
    }

    public static LotteryManager getInstance() {
        if (a == null) {
            a = new LotteryManager();
        }
        return a;
    }

    public int getId() {
        return this._number;
    }

    public int getPrize() {
        return this._prize;
    }

    public long getEndDate() {
        return this._enddate;
    }

    static {
        bw = LoggerFactory.getLogger(LotteryManager.class);
    }

    private class startLottery
    extends RunnableImpl {
        protected startLottery() {
        }

        @Override
        public void runImpl() throws Exception {
            if (LotteryManager.this.r()) {
                LotteryManager.this.aH();
                LotteryManager.this.aI();
                LotteryManager.this.aJ();
            }
        }
    }

    private class finishLottery
    extends RunnableImpl {
        protected finishLottery() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void runImpl() throws Exception {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            if (Config.SERVICES_ALLOW_LOTTERY) {
                _log.info("Lottery: Ending lottery #" + LotteryManager.this.getId() + ".");
            }
            int[] nArray = new int[5];
            int n8 = 0;
            for (n7 = 0; n7 < 5; ++n7) {
                n6 = 1;
                block10: while (n6 != 0) {
                    n8 = Rnd.get(20) + 1;
                    n6 = 0;
                    for (n5 = 0; n5 < n7; ++n5) {
                        if (nArray[n5] != n8) continue;
                        n6 = 1;
                        continue block10;
                    }
                }
                nArray[n7] = n8;
            }
            if (Config.SERVICES_ALLOW_LOTTERY) {
                _log.info("Lottery: The lucky numbers are " + nArray[0] + ", " + nArray[1] + ", " + nArray[2] + ", " + nArray[3] + ", " + nArray[4] + ".");
            }
            n7 = 0;
            n6 = 0;
            for (n5 = 0; n5 < 5; ++n5) {
                if (nArray[n5] < 17) {
                    n7 = (int)((double)n7 + Math.pow(2.0, nArray[n5] - 1));
                    continue;
                }
                n6 = (int)((double)n6 + Math.pow(2.0, nArray[n5] - 17));
            }
            if (Config.SERVICES_ALLOW_LOTTERY) {
                _log.info("Lottery: Encoded lucky numbers are " + n7 + ", " + n6);
            }
            n5 = 0;
            int n9 = 0;
            int n10 = 0;
            int n11 = 0;
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(LotteryManager.cC);
                preparedStatement.setInt(1, LotteryManager.this.getId());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int n12 = resultSet.getInt("enchant") & n7;
                    n4 = resultSet.getInt("damaged") & n6;
                    if (n12 == 0 && n4 == 0) continue;
                    n3 = 0;
                    for (n2 = 1; n2 <= 16; ++n2) {
                        int n13;
                        n = n12 / 2;
                        if ((double)n != (double)n12 / 2.0) {
                            ++n3;
                        }
                        if ((double)(n13 = n4 / 2) != (double)n4 / 2.0) {
                            ++n3;
                        }
                        n12 = n;
                        n4 = n13;
                    }
                    if (n3 == 5) {
                        ++n5;
                        continue;
                    }
                    if (n3 == 4) {
                        ++n9;
                        continue;
                    }
                    if (n3 == 3) {
                        ++n10;
                        continue;
                    }
                    if (n3 <= 0) continue;
                    ++n11;
                }
            }
            catch (SQLException sQLException) {
                try {
                    _log.warn("Lottery: Could restore lottery data: " + sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            int n14 = n11 * Config.SERVICES_LOTTERY_2_AND_1_NUMBER_PRIZE;
            n4 = 0;
            n3 = 0;
            n2 = 0;
            if (n5 > 0) {
                n4 = (int)((double)(LotteryManager.this.getPrize() - n14) * Config.SERVICES_LOTTERY_5_NUMBER_RATE / (double)n5);
            }
            if (n9 > 0) {
                n3 = (int)((double)(LotteryManager.this.getPrize() - n14) * Config.SERVICES_LOTTERY_4_NUMBER_RATE / (double)n9);
            }
            if (n10 > 0) {
                n2 = (int)((double)(LotteryManager.this.getPrize() - n14) * Config.SERVICES_LOTTERY_3_NUMBER_RATE / (double)n10);
            }
            n = n4 == 0 && n3 == 0 && n2 == 0 ? LotteryManager.this.getPrize() : LotteryManager.this.getPrize() + n4 + n3 + n2;
            if (Config.SERVICES_ALLOW_LOTTERY) {
                _log.info("Lottery: Jackpot for next lottery is " + n + ".");
            }
            if (n5 > 0) {
                SystemMessage systemMessage = new SystemMessage(SystemMsg.THE_PRIZE_AMOUNT_FOR_THE_WINNER_OF_LOTTERY_S1_IS_S2_ADENA__WE_HAVE_S3_FIRST_PRIZE_WINNERS);
                systemMessage.addNumber(LotteryManager.this.getId());
                systemMessage.addNumber(LotteryManager.this.getPrize());
                systemMessage.addNumber(n5);
                Announcements.getInstance().announceToAll(systemMessage);
            } else {
                SystemMessage systemMessage = new SystemMessage(SystemMsg.THE_PRIZE_AMOUNT_FOR_LUCKY_LOTTERY_S1_IS_S2_ADENA_THERE_WAS_NO_FIRST_PRIZE_WINNER_IN_THIS_DRAWING_THEREFORE_THE_JACKPOT_WILL_BE_ADDED_TO_THE_NEXT_DRAWING);
                systemMessage.addNumber(LotteryManager.this.getId());
                systemMessage.addNumber(LotteryManager.this.getPrize());
                Announcements.getInstance().announceToAll(systemMessage);
            }
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(LotteryManager.cA);
                preparedStatement.setInt(1, LotteryManager.this.getPrize());
                preparedStatement.setInt(2, n);
                preparedStatement.setInt(3, n7);
                preparedStatement.setInt(4, n6);
                preparedStatement.setInt(5, n4);
                preparedStatement.setInt(6, n3);
                preparedStatement.setInt(7, n2);
                preparedStatement.setInt(8, LotteryManager.this.getId());
                preparedStatement.execute();
            }
            catch (SQLException sQLException) {
                _log.warn("Lottery: Could not store finished lottery data: " + sQLException);
            }
            finally {
                DbUtils.closeQuietly(connection, preparedStatement);
            }
            ThreadPoolManager.getInstance().schedule(new startLottery(), 60000L);
            ++LotteryManager.this._number;
            LotteryManager.this._isStarted = false;
        }
    }

    private class stopSellingTickets
    extends RunnableImpl {
        protected stopSellingTickets() {
        }

        @Override
        public void runImpl() throws Exception {
            if (Config.SERVICES_ALLOW_LOTTERY) {
                _log.info("Lottery: Stopping ticket sell for lottery #" + LotteryManager.this.getId() + ".");
            }
            LotteryManager.this._isSellingTickets = false;
            Announcements.getInstance().announceToAll(SystemMsg.LOTTERY_TICKET_SALES_HAVE_BEEN_TEMPORARILY_SUSPENDED);
        }
    }
}
