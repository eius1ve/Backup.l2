/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.accounts;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import l2.authserver.database.L2DatabaseFactory;
import l2.commons.dbutils.DbUtils;
import l2.commons.net.utils.NetList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {
    private static final Logger O = LoggerFactory.getLogger(Account.class);
    private static final String as = "{CALL `lip_AccountLoad`(?)}";
    private static final String at = "{CALL `lip_AccountCreate`(?, ?)}";
    private static final String au = "{CALL `lip_AccountUpdate`(?, ?, ?, ?, ?, ?, ?, ?)}";
    private final String av;
    private String aw;
    private NetList a = new NetList();
    private int df;
    private int dg;
    private String ax;
    private String ay;
    private int dh;
    private int di;
    private String az;

    public Account(String string) {
        this.av = string;
    }

    public String getLogin() {
        return this.av;
    }

    public String getEmail() {
        return this.az;
    }

    public void setEmail(String string) {
        this.az = string;
    }

    public String getPasswordHash() {
        return this.aw;
    }

    public void setPasswordHash(String string) {
        this.aw = string;
    }

    public boolean isAllowedIP(String string) {
        return this.a.isEmpty() || this.a.isInRange(string);
    }

    public int getAccessLevel() {
        return this.df;
    }

    public void setAccessLevel(int n) {
        this.df = n;
    }

    public int getBanExpire() {
        return this.dg;
    }

    public void setBanExpire(int n) {
        this.dg = n;
    }

    public void setLastIP(String string) {
        this.ax = string;
    }

    public void setLastHWID(String string) {
        this.ay = string;
    }

    public String getLastHWID() {
        return this.ay;
    }

    public String getLastIP() {
        return this.ax;
    }

    public int getLastAccess() {
        return this.dh;
    }

    public void setLastAccess(int n) {
        this.dh = n;
    }

    public int getLastServer() {
        return this.di;
    }

    public void setLastServer(int n) {
        this.di = n;
    }

    public String toString() {
        return this.av;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void restore() {
        ResultSet resultSet;
        CallableStatement callableStatement;
        Connection connection;
        block4: {
            connection = null;
            callableStatement = null;
            resultSet = null;
            try {
                connection = L2DatabaseFactory.getInstance().getConnection();
                callableStatement = connection.prepareCall(as);
                callableStatement.setString(1, this.av);
                resultSet = callableStatement.executeQuery();
                if (!resultSet.next()) break block4;
                this.setPasswordHash(resultSet.getString("password").trim());
                this.setAccessLevel(resultSet.getInt("accessLevel"));
                this.setLastServer(resultSet.getInt("lastServerId"));
                this.setLastIP(resultSet.getString("lastIP"));
                this.setLastHWID(resultSet.getString("lastHWID"));
                this.setLastAccess(resultSet.getInt("lastactive"));
                this.setEmail(resultSet.getString("email"));
            }
            catch (Exception exception) {
                try {
                    O.error("", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, callableStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, callableStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, callableStatement, resultSet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void save() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = L2DatabaseFactory.getInstance().getConnection();
            callableStatement = connection.prepareCall(at);
            callableStatement.setString(1, this.getLogin());
            callableStatement.setString(2, this.getPasswordHash());
            callableStatement.execute();
        }
        catch (Exception exception) {
            try {
                O.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, callableStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, callableStatement);
        }
        DbUtils.closeQuietly(connection, callableStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void update() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = L2DatabaseFactory.getInstance().getConnection();
            callableStatement = connection.prepareCall(au);
            callableStatement.setString(1, this.getLogin());
            callableStatement.setString(2, this.getPasswordHash());
            callableStatement.setInt(3, this.getAccessLevel());
            callableStatement.setInt(4, this.getLastServer());
            callableStatement.setString(5, this.getLastIP());
            callableStatement.setString(6, this.getLastHWID());
            callableStatement.setInt(7, this.getLastAccess());
            callableStatement.setString(8, this.getEmail());
            callableStatement.execute();
        }
        catch (Exception exception) {
            try {
                O.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, callableStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, callableStatement);
        }
        DbUtils.closeQuietly(connection, callableStatement);
    }
}
