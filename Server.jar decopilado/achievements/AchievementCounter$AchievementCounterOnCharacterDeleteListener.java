/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.listener.game.OnCharacterDeleteListener
 */
package achievements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.listener.game.OnCharacterDeleteListener;

private static class AchievementCounter.AchievementCounterOnCharacterDeleteListener
implements OnCharacterDeleteListener {
    private AchievementCounter.AchievementCounterOnCharacterDeleteListener() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onCharacterDelete(int n) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `ex_achievements` WHERE `objId` = ?");
            preparedStatement.setInt(1, n);
            preparedStatement.executeUpdate();
        }
        catch (SQLException sQLException) {
            try {
                a.error("Can't delete counter for " + n);
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
