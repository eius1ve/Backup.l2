/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.database.DatabaseFactory
 */
package achievements;

import achievements.AchievementCounter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;

private static final class AchievementCounter.AchievementCounterDb
extends AchievementCounter {
    private volatile Integer a = null;

    public AchievementCounter.AchievementCounterDb(int n, int n2) {
        super(n, n2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int b() {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block4: {
            int n;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT `value` AS `value` FROM `ex_achievements` WHERE  `objId` = ? AND `achId` = ?");
                preparedStatement.setInt(1, this.getObjid());
                preparedStatement.setInt(2, this.getAchId());
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                n = resultSet.getInt("value");
            }
            catch (SQLException sQLException) {
                try {
                    a.error("Can't load counter for " + this.getObjid() + "(" + this.getAchId() + ")", (Throwable)sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
            return n;
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
        return 0;
    }

    @Override
    public int getVal() {
        if (this.a == null) {
            this.a = this.b();
        }
        return this.a;
    }

    @Override
    public void setVal(int n) {
        this.a = n;
    }

    @Override
    public boolean isStorable() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void store() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("REPLACE INTO `ex_achievements` (`objId`, `achId`, `value`) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, this.getObjid());
            preparedStatement.setInt(2, this.getAchId());
            preparedStatement.setInt(3, this.getVal());
            preparedStatement.executeUpdate();
        }
        catch (SQLException sQLException) {
            try {
                a.error("Can't store counter for " + this.getObjid() + "(" + this.getAchId() + ")");
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
    }
}
