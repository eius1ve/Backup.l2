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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CastleDoorUpgradeDAO {
    private static final CastleDoorUpgradeDAO a = new CastleDoorUpgradeDAO();
    private static final Logger av = LoggerFactory.getLogger(CastleDoorUpgradeDAO.class);
    public static final String SELECT_SQL_QUERY = "SELECT `hp` FROM `castle_door_upgrade` WHERE `door_id`=?";
    public static final String REPLACE_SQL_QUERY = "REPLACE INTO `castle_door_upgrade` (`door_id`, `hp`) VALUES (?,?)";
    public static final String DELETE_SQL_QUERY = "DELETE FROM `castle_door_upgrade` WHERE `door_id`=?";

    public static CastleDoorUpgradeDAO getInstance() {
        return a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int load(int n) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block4: {
            int n2;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(SELECT_SQL_QUERY);
                preparedStatement.setInt(1, n);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                n2 = resultSet.getInt("hp");
            }
            catch (Exception exception) {
                try {
                    av.error("CastleDoorUpgradeDAO:load(int): " + exception, (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            return n2;
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void insert(int n, int n2) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(REPLACE_SQL_QUERY);
            preparedStatement.setInt(1, n);
            preparedStatement.setInt(2, n2);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                av.error("CastleDoorUpgradeDAO:insert(int, int): " + exception, (Throwable)exception);
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
    public void delete(int n) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(DELETE_SQL_QUERY);
            preparedStatement.setInt(1, n);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                av.error("CastleDoorUpgradeDAO:delete(int): " + exception, (Throwable)exception);
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
