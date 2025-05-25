/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.data.xml.holder.InstantZoneHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.database.mysql;
import l2.gameserver.model.Player;
import l2.gameserver.templates.InstantZone;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstanceReuseDAO {
    private static final Logger aI = LoggerFactory.getLogger(InstanceReuseDAO.class);
    private static final InstanceReuseDAO a = new InstanceReuseDAO();

    public static InstanceReuseDAO getInstance() {
        return a;
    }

    private InstanceReuseDAO() {
    }

    public Map<Integer, Long> load(Player player) {
        return this.load(player.getObjectId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<Integer, Long> load(int n) {
        HashMap<Integer, Long> hashMap = new HashMap<Integer, Long>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM `character_instances` WHERE `obj_id` = ?");
            preparedStatement.setInt(1, n);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n2 = resultSet.getInt("id");
                long l = resultSet.getLong("reuse");
                hashMap.put(n2, l);
            }
        }
        catch (SQLException sQLException) {
            try {
                aI.error(sQLException.getMessage(), (Throwable)sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return hashMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Long getHwidReuse(int n, String string) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block4: {
            Long l;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT `reuse` FROM `instances_hwid_reuse` WHERE `id` = ? AND `hwid` = ?");
                preparedStatement.setInt(1, n);
                preparedStatement.setString(2, string);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                l = resultSet.getLong("reuse");
            }
            catch (SQLException sQLException) {
                try {
                    aI.error(sQLException.getMessage(), (Throwable)sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            return l;
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return -1L;
    }

    public void setHwidReuse(int n, String string, long l) {
        mysql.set("REPLACE INTO `instances_hwid_reuse` (`id`, `hwid`, `reuse`) VALUES (?,?,?)", n, string, l);
    }

    public void removeReuse(Player player, int n) {
        this.removeReuse(player.getObjectId(), n);
    }

    public void removeReuse(int n, int n2) {
        mysql.set("DELETE FROM `character_instances` WHERE `obj_id`=? AND `id`=? LIMIT 1", n, n2);
    }

    public void removeAllReuse(Player player) {
        this.removeAllReuse(player.getObjectId());
    }

    public void removeAllReuse(int n) {
        mysql.set("DELETE FROM `character_instances` WHERE `obj_id`=?", n);
    }

    public void setReuse(Player player, int n, long l) {
        String string;
        this.setReuse(player.getObjectId(), n, l);
        InstantZone instantZone = InstantZoneHolder.getInstance().getInstantZone(n);
        if (instantZone != null && instantZone.isHwidLimited() && player.getNetConnection() != null && (string = player.getNetConnection().getHwid()) != null && !StringUtils.isBlank((CharSequence)string)) {
            this.setHwidReuse(n, string, l);
        }
    }

    public void setReuse(int n, int n2, long l) {
        mysql.set("REPLACE INTO character_instances (obj_id, id, reuse) VALUES (?,?,?)", n, n2, l);
    }
}
