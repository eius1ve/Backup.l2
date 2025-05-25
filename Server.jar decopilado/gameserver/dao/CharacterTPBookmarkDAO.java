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
import java.util.ArrayList;
import java.util.List;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.TpBookMark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterTPBookmarkDAO {
    private static final Logger aD = LoggerFactory.getLogger(CharacterTPBookmarkDAO.class);
    private static final CharacterTPBookmarkDAO a = new CharacterTPBookmarkDAO();
    private static final String bh = "SELECT * FROM character_tp_bookmarks WHERE object_id=?";
    private static final String bi = "INSERT INTO character_tp_bookmarks(object_id, name, acronym, icon, x, y, z) VALUES (?,?,?,?,?,?,?)";
    private static final String bj = "UPDATE character_tp_bookmarks SET name=?, acronym=?, icon=? WHERE object_id=? AND name=? AND x=? AND y=? AND z=?";
    private static final String bk = "DELETE FROM character_tp_bookmarks WHERE object_id=? AND name=? AND x=? AND y=? AND z=? LIMIT 1";

    public static CharacterTPBookmarkDAO getInstance() {
        return a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<TpBookMark> select(Player player) {
        ArrayList<TpBookMark> arrayList = new ArrayList<TpBookMark>(Config.EX_MAX_TELEPORT_BOOKMARK_SIZE);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bh);
            preparedStatement.setInt(1, player.getObjectId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("x");
                int n2 = resultSet.getInt("y");
                int n3 = resultSet.getInt("z");
                int n4 = resultSet.getInt("icon");
                String string = resultSet.getString("name");
                String string2 = resultSet.getString("acronym");
                arrayList.add(new TpBookMark(n, n2, n3, n4, string, string2));
            }
        }
        catch (Exception exception) {
            try {
                aD.error("CharacterTPBookmarkDAO.select(Player): " + exception, (Throwable)exception);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void insert(Player player, TpBookMark tpBookMark) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bi);
            preparedStatement.setInt(1, player.getObjectId());
            preparedStatement.setString(2, tpBookMark.getName());
            preparedStatement.setString(3, tpBookMark.getAcronym());
            preparedStatement.setInt(4, tpBookMark.getIcon());
            preparedStatement.setInt(5, tpBookMark.getX());
            preparedStatement.setInt(6, tpBookMark.getY());
            preparedStatement.setInt(7, tpBookMark.getZ());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aD.error("CharacterTPBookmarkDAO.insert(Player, TpBookMark): " + exception, (Throwable)exception);
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
    public void delete(Player player, TpBookMark tpBookMark) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bk);
            preparedStatement.setInt(1, player.getObjectId());
            preparedStatement.setString(2, tpBookMark.getName());
            preparedStatement.setInt(3, tpBookMark.getX());
            preparedStatement.setInt(4, tpBookMark.getY());
            preparedStatement.setInt(5, tpBookMark.getZ());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aD.error("CharacterTPBookmarkDAO.delete(Player,TpBookMark): " + exception, (Throwable)exception);
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
    public void update(Player player, TpBookMark tpBookMark) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bj);
            preparedStatement.setString(1, tpBookMark.getName());
            preparedStatement.setString(2, tpBookMark.getAcronym());
            preparedStatement.setInt(3, tpBookMark.getIcon());
            preparedStatement.setInt(4, player.getObjectId());
            preparedStatement.setString(5, tpBookMark.getName());
            preparedStatement.setInt(6, tpBookMark.getX());
            preparedStatement.setInt(7, tpBookMark.getY());
            preparedStatement.setInt(8, tpBookMark.getZ());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aD.error("CharacterTPBookmarkDAO.update(Player,TpBookMark): " + exception, (Throwable)exception);
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
