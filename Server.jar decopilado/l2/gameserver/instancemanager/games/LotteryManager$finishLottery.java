/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.games;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import l2.commons.dbutils.DbUtils;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.instancemanager.games.LotteryManager;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

private class LotteryManager.finishLottery
extends RunnableImpl {
    protected LotteryManager.finishLottery() {
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
        ThreadPoolManager.getInstance().schedule(new LotteryManager.startLottery(LotteryManager.this), 60000L);
        ++LotteryManager.this._number;
        LotteryManager.this._isStarted = false;
    }
}
