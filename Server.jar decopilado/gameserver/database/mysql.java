/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class mysql {
    private static final Logger bb = LoggerFactory.getLogger(mysql.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean setEx(DatabaseFactory databaseFactory, String string, Object ... objectArray) {
        Statement statement;
        Statement statement2;
        Connection connection;
        block6: {
            connection = null;
            statement2 = null;
            statement = null;
            try {
                if (databaseFactory == null) {
                    databaseFactory = DatabaseFactory.getInstance();
                }
                connection = databaseFactory.getConnection();
                if (objectArray.length == 0) {
                    statement2 = connection.createStatement();
                    statement2.executeUpdate(string);
                    break block6;
                }
                statement = connection.prepareStatement(string);
                mysql.setVars((PreparedStatement)statement, objectArray);
                statement.executeUpdate();
            }
            catch (Exception exception) {
                boolean bl;
                try {
                    bb.warn("Could not execute update '" + string + "': " + exception);
                    exception.printStackTrace();
                    bl = false;
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, objectArray.length == 0 ? statement2 : statement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, objectArray.length == 0 ? statement2 : statement);
                return bl;
            }
        }
        DbUtils.closeQuietly(connection, objectArray.length == 0 ? statement2 : statement);
        return true;
    }

    public static void setVars(PreparedStatement preparedStatement, Object ... objectArray) throws SQLException {
        for (int i = 0; i < objectArray.length; ++i) {
            if (objectArray[i] instanceof Number) {
                double d;
                Number number = (Number)objectArray[i];
                long l = number.longValue();
                if ((double)l == (d = number.doubleValue())) {
                    preparedStatement.setLong(i + 1, l);
                    continue;
                }
                preparedStatement.setDouble(i + 1, d);
                continue;
            }
            if (!(objectArray[i] instanceof String)) continue;
            preparedStatement.setString(i + 1, (String)objectArray[i]);
        }
    }

    public static boolean set(String string, Object ... objectArray) {
        return mysql.setEx(null, string, objectArray);
    }

    public static boolean set(String string) {
        return mysql.setEx(null, string, new Object[0]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object get(String string) {
        ResultSet resultSet;
        Statement statement;
        Connection connection;
        HashMap<String, Object> hashMap;
        block6: {
            hashMap = null;
            connection = null;
            statement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery(string + " LIMIT 1");
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                if (!resultSet.next()) break block6;
                if (resultSetMetaData.getColumnCount() > 1) {
                    HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
                    for (int i = resultSetMetaData.getColumnCount(); i > 0; --i) {
                        hashMap2.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                    }
                    hashMap = hashMap2;
                    break block6;
                }
                hashMap = resultSet.getObject(1);
            }
            catch (Exception exception) {
                try {
                    bb.warn("Could not execute query '" + string + "': " + exception);
                    exception.printStackTrace();
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, statement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, statement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, statement, resultSet);
        return hashMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<Map<String, Object>> getAll(String string) {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(string);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                for (int i = resultSetMetaData.getColumnCount(); i > 0; --i) {
                    hashMap.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                }
                arrayList.add(hashMap);
            }
        }
        catch (Exception exception) {
            try {
                bb.warn("Could not execute query '" + string + "': " + exception);
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, statement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, statement, resultSet);
        }
        DbUtils.closeQuietly(connection, statement, resultSet);
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<Object> get_array(DatabaseFactory databaseFactory, String string) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            if (databaseFactory == null) {
                databaseFactory = DatabaseFactory.getInstance();
            }
            connection = databaseFactory.getConnection();
            preparedStatement = connection.prepareStatement(string);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                if (resultSetMetaData.getColumnCount() > 1) {
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    for (int i = 0; i < resultSetMetaData.getColumnCount(); ++i) {
                        hashMap.put(resultSetMetaData.getColumnName(i + 1), resultSet.getObject(i + 1));
                    }
                    arrayList.add(hashMap);
                    continue;
                }
                arrayList.add(resultSet.getObject(1));
            }
        }
        catch (Exception exception) {
            try {
                bb.warn("Could not execute query '" + string + "': " + exception);
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return arrayList;
    }

    public static List<Object> get_array(String string) {
        return mysql.get_array(null, string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int simple_get_int(String string, String string2, String string3) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        int n;
        block4: {
            String string4 = "SELECT " + string + " FROM `" + string2 + "` WHERE " + string3 + " LIMIT 1;";
            n = 0;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(string4);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                n = resultSet.getInt(1);
            }
            catch (Exception exception) {
                try {
                    bb.warn("mSGI: Error in query '" + string4 + "':" + exception);
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
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Integer[][] simple_get_int_array(DatabaseFactory databaseFactory, String[] stringArray, String string, String string2) {
        ResultSet resultSet;
        String string3 = null;
        for (String string4 : stringArray) {
            if (string3 != null) {
                string3 = string3 + ",";
                string3 = string3 + "`" + string4 + "`";
                continue;
            }
            string3 = "`" + string4 + "`";
        }
        String string5 = "SELECT " + string3 + " FROM `" + string + "` WHERE " + string2;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet2 = null;
        Integer[][] integerArray = null;
        try {
            if (databaseFactory == null) {
                databaseFactory = DatabaseFactory.getInstance();
            }
            connection = databaseFactory.getConnection();
            preparedStatement = connection.prepareStatement(string5);
            resultSet = preparedStatement.executeQuery();
            ArrayList<Integer[]> arrayList = new ArrayList<Integer[]>();
            int n = 0;
            while (resultSet.next()) {
                Integer[] integerArray2 = new Integer[stringArray.length];
                for (int i = 0; i < stringArray.length; ++i) {
                    integerArray2[i] = resultSet.getInt(i + 1);
                }
                arrayList.add(n, integerArray2);
                ++n;
            }
            integerArray = (Integer[][])arrayList.toArray((T[])new Integer[n][stringArray.length]);
        }
        catch (Exception exception) {
            try {
                bb.warn("mSGIA: Error in query '" + string5 + "':" + exception);
                exception.printStackTrace();
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet2);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet2);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return integerArray;
    }

    public static Integer[][] simple_get_int_array(String[] stringArray, String string, String string2) {
        return mysql.simple_get_int_array(null, stringArray, string, string2);
    }
}
