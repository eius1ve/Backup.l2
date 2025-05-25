/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.CHashIntObjectMap
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.CHashIntObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterPostFriendDAO {
    private static final Logger aC = LoggerFactory.getLogger(CharacterPostFriendDAO.class);
    private static final CharacterPostFriendDAO a = new CharacterPostFriendDAO();
    private static final String be = "SELECT `pf`.`post_friend`, `c`.`char_name` FROM `character_post_friends` `pf` LEFT JOIN `characters` `c` ON `pf`.`post_friend` = `c`.`obj_Id` WHERE `pf`.`object_id` = ?";
    private static final String bf = "INSERT INTO `character_post_friends`(`object_id`, `post_friend`) VALUES (?,?)";
    private static final String bg = "DELETE FROM `character_post_friends` WHERE `object_id`=? AND `post_friend`=?";

    public static CharacterPostFriendDAO getInstance() {
        return a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IntObjectMap<String> select(Player player) {
        CHashIntObjectMap cHashIntObjectMap = new CHashIntObjectMap();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(be);
            preparedStatement.setInt(1, player.getObjectId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String string = resultSet.getString(2);
                if (string == null) continue;
                cHashIntObjectMap.put(resultSet.getInt(1), (Object)resultSet.getString(2));
            }
        }
        catch (Exception exception) {
            try {
                aC.error("CharacterPostFriendDAO.load(L2Player): " + exception, (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return cHashIntObjectMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void insert(Player player, int n) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bf);
            preparedStatement.setInt(1, player.getObjectId());
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aC.error("CharacterPostFriendDAO.insert(L2Player, int): " + exception, (Throwable)exception);
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
    public void delete(Player player, int n) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bg);
            preparedStatement.setInt(1, player.getObjectId());
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aC.error("CharacterPostFriendDAO.delete(L2Player, int): " + exception, (Throwable)exception);
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
