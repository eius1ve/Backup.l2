/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.instancemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.templates.StatsSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerVariables {
    private static final Logger bt = LoggerFactory.getLogger(ServerVariables.class);
    private static StatsSet b = null;

    private static StatsSet b() {
        if (b == null) {
            b = new StatsSet();
            ServerVariables.aB();
        }
        return b;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void aB() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM `server_variables`");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                b.set(resultSet.getString("name"), resultSet.getString("value"));
            }
        }
        catch (Exception exception) {
            try {
                bt.error("", (Throwable)exception);
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
    private static void b(String string) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            String string2 = ServerVariables.b().getString(string, "");
            if (string2.isEmpty()) {
                preparedStatement = connection.prepareStatement("DELETE FROM `server_variables` WHERE `name` = ?");
                preparedStatement.setString(1, string);
                preparedStatement.execute();
            } else {
                preparedStatement = connection.prepareStatement("REPLACE INTO `server_variables` (`name`, `value`) VALUES (?,?)");
                preparedStatement.setString(1, string);
                preparedStatement.setString(2, string2);
                preparedStatement.execute();
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        catch (Exception exception) {
            bt.error("", (Throwable)exception);
        }
        finally {
            DbUtils.closeQuietly(connection, preparedStatement);
        }
    }

    public static boolean getBool(String string) {
        return ServerVariables.b().getBool(string);
    }

    public static boolean getBool(String string, boolean bl) {
        return ServerVariables.b().getBool(string, bl);
    }

    public static int getInt(String string) {
        return ServerVariables.b().getInteger(string);
    }

    public static int getInt(String string, int n) {
        return ServerVariables.b().getInteger(string, n);
    }

    public static long getLong(String string) {
        return ServerVariables.b().getLong(string);
    }

    public static long getLong(String string, long l) {
        return ServerVariables.b().getLong(string, l);
    }

    public static double getFloat(String string) {
        return ServerVariables.b().getDouble(string);
    }

    public static double getFloat(String string, double d) {
        return ServerVariables.b().getDouble(string, d);
    }

    public static String getString(String string) {
        return ServerVariables.b().getString(string);
    }

    public static String getString(String string, String string2) {
        return ServerVariables.b().getString(string, string2);
    }

    public static void set(String string, boolean bl) {
        ServerVariables.b().set(string, bl);
        ServerVariables.b(string);
    }

    public static void set(String string, int n) {
        ServerVariables.b().set(string, n);
        ServerVariables.b(string);
    }

    public static void set(String string, long l) {
        ServerVariables.b().set(string, l);
        ServerVariables.b(string);
    }

    public static void set(String string, double d) {
        ServerVariables.b().set(string, d);
        ServerVariables.b(string);
    }

    public static void set(String string, String string2) {
        ServerVariables.b().set(string, string2);
        ServerVariables.b(string);
    }

    public static void unset(String string) {
        ServerVariables.b().unset(string);
        ServerVariables.b(string);
    }
}
