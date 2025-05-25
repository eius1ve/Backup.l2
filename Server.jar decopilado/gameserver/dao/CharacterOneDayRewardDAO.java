/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterOneDayRewardDAO {
    private static final Logger aB = LoggerFactory.getLogger(CharacterOneDayRewardDAO.class);
    public static final CharacterOneDayRewardDAO INSTANCE = new CharacterOneDayRewardDAO();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public OneDayRewardStatus select(int n, OneDayReward.ResetTime resetTime, int n2) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block4: {
            OneDayRewardStatus oneDayRewardStatus;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM character_onedayreward WHERE `id`=? AND `player_object_id`=? LIMIT 1");
                preparedStatement.setInt(1, n);
                preparedStatement.setInt(2, n2);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                int n3 = resultSet.getInt("progress");
                boolean bl = resultSet.getBoolean("received");
                oneDayRewardStatus = new OneDayRewardStatus(n, n2, n3, resetTime.ordinal(), bl);
            }
            catch (Exception exception) {
                try {
                    aB.error("Exception: " + exception, (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            return oneDayRewardStatus;
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateProgressAndState(OneDayRewardStatus oneDayRewardStatus) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("REPLACE INTO character_onedayreward(`id`,`player_object_id`,`reset_time`,`progress`,`received`) VALUES (?,?,?,?,?)");
            preparedStatement.setInt(1, oneDayRewardStatus.getId());
            preparedStatement.setInt(2, oneDayRewardStatus.getPlayerObjectId());
            preparedStatement.setInt(3, oneDayRewardStatus.getResetTime());
            preparedStatement.setInt(4, oneDayRewardStatus.getCurrentProgress());
            preparedStatement.setBoolean(5, oneDayRewardStatus.isReceived());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aB.error("Exception: " + exception, (Throwable)exception);
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
    public void deleteByResetTime(OneDayReward.ResetTime resetTime) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM character_onedayreward WHERE `reset_time`=?");
            preparedStatement.setInt(1, resetTime.ordinal());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aB.error("Exception: " + exception, (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }
}
