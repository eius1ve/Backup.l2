/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.database.DatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecondPasswordAuth {
    private static final Logger cG = LoggerFactory.getLogger(SecondPasswordAuth.class);
    private final String dQ;
    private String dR;
    private int pJ;
    private long cO;

    public SecondPasswordAuth(String string) {
        this.dQ = string;
        this.dR = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String h() {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block5: {
            if (this.dR != null) {
                return this.dR;
            }
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT `password`, `tryLine`, `blockEndTime` FROM `second_auth` WHERE `login` = ?");
                preparedStatement.setString(1, this.dQ);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block5;
                this.dR = resultSet.getString("password");
                this.pJ = Math.min(Config.SECOND_AUTH_MAX_TRYS, resultSet.getInt("tryLine"));
                this.cO = resultSet.getLong("blockEndTime");
            }
            catch (SQLException sQLException) {
                try {
                    cG.warn("Database error on retreiving second password for login '" + this.dQ + "' :", (Throwable)sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return this.dR;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void store() {
        if (this.dR == null) {
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("REPLACE INTO `second_auth`(`login`, `password`, `tryLine`, `blockEndTime`) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, this.dQ);
            preparedStatement.setString(2, this.dR);
            preparedStatement.setInt(3, this.getTrysCount());
            preparedStatement.setLong(4, this.cO);
            preparedStatement.executeUpdate();
        }
        catch (SQLException sQLException) {
            try {
                cG.warn("Database error on storing second password for login '" + this.dQ + "' :", (Throwable)sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public boolean isSecondPasswordSet() {
        return this.h() != null;
    }

    public boolean isBlocked() {
        if (this.cO == 0L) {
            return false;
        }
        if (this.cO * 1000L < System.currentTimeMillis()) {
            this.cO = 0L;
            this.pJ = 0;
            this.store();
            return false;
        }
        return true;
    }

    public int getBlockTimeLeft() {
        return (int)Math.max(0L, this.cO - System.currentTimeMillis() / 1000L);
    }

    public int getTrysCount() {
        return Math.min(Config.SECOND_AUTH_MAX_TRYS, this.pJ);
    }

    public boolean isValidSecondPassword(String string) {
        if (string == null && this.h() == null) {
            return true;
        }
        if (string.equalsIgnoreCase(this.h())) {
            this.cO = 0L;
            this.pJ = 0;
            this.store();
            return true;
        }
        ++this.pJ;
        if (this.pJ >= Config.SECOND_AUTH_MAX_TRYS) {
            this.cO = System.currentTimeMillis() / 1000L + Config.SECOND_AUTH_BLOCK_TIME;
            this.pJ = Config.SECOND_AUTH_MAX_TRYS;
        }
        this.store();
        return false;
    }

    public boolean changePassword(String string, String string2) {
        if (!this.isValidSecondPassword(string)) {
            return false;
        }
        this.dR = string2;
        this.store();
        return true;
    }
}
