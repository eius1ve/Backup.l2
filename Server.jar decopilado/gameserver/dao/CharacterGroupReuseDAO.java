/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap$Entry
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.skills.TimeStamp;
import l2.gameserver.utils.SqlBatch;
import org.napile.primitive.maps.IntObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterGroupReuseDAO {
    private static final Logger aA = LoggerFactory.getLogger(CharacterGroupReuseDAO.class);
    private static CharacterGroupReuseDAO a = new CharacterGroupReuseDAO();
    public static final String DELETE_SQL_QUERY = "DELETE FROM `character_group_reuse` WHERE `object_id`=?";
    public static final String SELECT_SQL_QUERY = "SELECT * FROM `character_group_reuse` WHERE `object_id`=?";
    public static final String INSERT_SQL_QUERY = "REPLACE INTO `character_group_reuse` (`object_id`,`reuse_group`,`item_id`,`end_time`,`reuse`) VALUES";

    public static CharacterGroupReuseDAO getInstance() {
        return a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void select(Player player) {
        long l = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SQL_QUERY);
            preparedStatement.setInt(1, player.getObjectId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("reuse_group");
                int n2 = resultSet.getInt("item_id");
                long l2 = resultSet.getLong("end_time");
                long l3 = resultSet.getLong("reuse");
                if (l2 - l <= 500L) continue;
                TimeStamp timeStamp = new TimeStamp(n2, l2, l3);
                player.addSharedGroupReuse(n, timeStamp);
            }
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement(DELETE_SQL_QUERY);
            preparedStatement.setInt(1, player.getObjectId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aA.error("CharacterGroupReuseDAO.select(L2Player):", (Throwable)exception);
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
    public void insert(Player player) {
        PreparedStatement preparedStatement;
        Connection connection;
        block10: {
            Collection<IntObjectMap.Entry<TimeStamp>> collection;
            block9: {
                connection = null;
                preparedStatement = null;
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(DELETE_SQL_QUERY);
                preparedStatement.setInt(1, player.getObjectId());
                preparedStatement.execute();
                collection = player.getSharedGroupReuses();
                if (!collection.isEmpty()) break block9;
                DbUtils.closeQuietly(connection, preparedStatement);
                return;
            }
            try {
                SqlBatch sqlBatch = new SqlBatch(INSERT_SQL_QUERY);
                Collection<IntObjectMap.Entry<TimeStamp>> collection2 = collection;
                synchronized (collection2) {
                    for (IntObjectMap.Entry<TimeStamp> entry : collection) {
                        int n = entry.getKey();
                        TimeStamp timeStamp = (TimeStamp)entry.getValue();
                        if (!timeStamp.hasNotPassed()) continue;
                        StringBuilder stringBuilder = new StringBuilder("(");
                        stringBuilder.append(player.getObjectId()).append(",");
                        stringBuilder.append(n).append(",");
                        stringBuilder.append(timeStamp.getId()).append(",");
                        stringBuilder.append(timeStamp.getEndTime()).append(",");
                        stringBuilder.append(timeStamp.getReuseBasic()).append(")");
                        sqlBatch.write(stringBuilder.toString());
                    }
                }
                if (sqlBatch.isEmpty()) break block10;
                preparedStatement.executeUpdate(sqlBatch.close());
            }
            catch (Exception exception) {
                try {
                    aA.error("CharacterGroupReuseDAO.insert(L2Player):", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }
}
