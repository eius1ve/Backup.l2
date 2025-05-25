/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import l2.authserver.Config;
import l2.authserver.accounts.Account;
import l2.authserver.database.L2DatabaseFactory;
import l2.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger ab = LoggerFactory.getLogger(Log.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void LogAccount(Account account) {
        if (!Config.LOGIN_LOG) {
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = L2DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `account_log` (`time`, `login`, `lastServerId`, `ip`, `hwid`) VALUES(?,?,?,?,?)");
            preparedStatement.setInt(1, account.getLastAccess());
            preparedStatement.setString(2, account.getLogin());
            preparedStatement.setInt(3, account.getLastServer());
            preparedStatement.setString(4, account.getLastIP());
            preparedStatement.setString(5, account.getLastHWID());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                ab.error("", (Throwable)exception);
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
