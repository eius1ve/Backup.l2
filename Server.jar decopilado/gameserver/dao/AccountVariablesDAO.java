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

public class AccountVariablesDAO {
    private static final Logger as = LoggerFactory.getLogger(AccountVariablesDAO.class);
    private static final AccountVariablesDAO a = new AccountVariablesDAO();
    public static final String SELECT_SQL_QUERY = "SELECT value FROM `account_variables` WHERE `account_name`=? AND `var`=?";
    public static final String DELETE_SQL_QUERY = "DELETE FROM `account_variables` WHERE `account_name`=? AND `var`=?";
    public static final String DELETE_ALL_SQL_QUERY = "DELETE FROM `account_variables` WHERE `var`=?";
    public static final String INSERT_SQL_QUERY = "INSERT INTO `account_variables` VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE value=?";

    public static AccountVariablesDAO getInstance() {
        return a;
    }

    public String select(String string, String string2) {
        return this.select(string, string2, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String select(String string, String string2, String string3) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        String string4;
        block4: {
            string4 = string3;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(SELECT_SQL_QUERY);
                preparedStatement.setString(1, string);
                preparedStatement.setString(2, string2);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                string4 = resultSet.getString("value");
            }
            catch (Exception exception) {
                try {
                    as.info("AccountVariablesDAO.select(String, String): " + exception, (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return string4;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void delete(String string, String string2) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(DELETE_SQL_QUERY);
            preparedStatement.setString(1, string);
            preparedStatement.setString(2, string2);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                as.info("AccountVariablesDAO.delete(String, String): " + exception, (Throwable)exception);
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
    public void delete(String string) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(DELETE_ALL_SQL_QUERY);
            preparedStatement.setString(1, string);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                as.info("AccountVariablesDAO.delete(String): " + exception, (Throwable)exception);
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
    public void insert(String string, String string2, Object object) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_SQL_QUERY);
            preparedStatement.setString(1, string);
            preparedStatement.setString(2, string2);
            preparedStatement.setString(3, String.valueOf(object));
            preparedStatement.setString(4, String.valueOf(object));
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                as.info("AccountVariablesDAO.insert(String, String, String): " + exception, (Throwable)exception);
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
