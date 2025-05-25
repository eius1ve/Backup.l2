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
import java.util.HashMap;
import java.util.Map;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.CharacterBlockListEntry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterBlockDAO {
    private static final Logger ax = LoggerFactory.getLogger(CharacterBlockDAO.class);
    private static final CharacterBlockDAO a = new CharacterBlockDAO();
    private static final String aX = "SELECT `target_Id`, `char_name`, `memo` FROM `character_blocklist` LEFT JOIN `characters`   ON (`character_blocklist`.`target_Id` = `characters`.`obj_Id`) WHERE   `character_blocklist`.`obj_Id` = ?";
    private static final String aY = "SELECT `obj_Id`, `target_Id`, `memo` FROM `character_blocklist` WHERE `obj_Id` = ? AND `target_Id` = ?";
    private static final String aZ = "DELETE FROM `character_blocklist` WHERE `obj_Id` = ? AND `target_Id` = ?";
    private static final String ba = "INSERT IGNORE INTO `character_blocklist` (`obj_Id`, `target_Id`, `memo`) VALUES (?, ?, ?)";
    private static final String bb = "UPDATE `character_blocklist` SET `memo` = ? WHERE `obj_Id` = ? AND `target_Id` = ?";

    public static CharacterBlockDAO getInstance() {
        return a;
    }

    public Map<Integer, CharacterBlockListEntry> select(Player player) {
        return this.select(player.getObjectId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public CharacterBlockListEntry fetchBlockListEntry(int n, int n2) {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(aX);){
            preparedStatement.setInt(1, n);
            preparedStatement.setInt(2, n2);
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                if (!resultSet.next()) return null;
                int n3 = resultSet.getInt("obj_Id");
                int n4 = resultSet.getInt("target_Id");
                String string = resultSet.getString("char_name");
                if (n4 == 0 || StringUtils.isBlank((CharSequence)string)) {
                    CharacterBlockListEntry characterBlockListEntry = null;
                    return characterBlockListEntry;
                }
                String string2 = resultSet.getString("memo");
                CharacterBlockListEntry characterBlockListEntry = new CharacterBlockListEntry(n3, n4, string, string2);
                return characterBlockListEntry;
            }
        }
        catch (Exception exception) {
            ax.warn("Error loading blocklist entry {} for {}: {}", new Object[]{n2, n, exception});
        }
        return null;
    }

    public Map<Integer, CharacterBlockListEntry> select(int n) {
        HashMap<Integer, CharacterBlockListEntry> hashMap = new HashMap<Integer, CharacterBlockListEntry>();
        try (Connection connection = DatabaseFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(aX);){
            preparedStatement.setInt(1, n);
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                while (resultSet.next()) {
                    int n2 = resultSet.getInt("target_Id");
                    String string = resultSet.getString("char_name");
                    if (n2 == 0 || StringUtils.isBlank((CharSequence)string)) continue;
                    String string2 = resultSet.getString("memo");
                    hashMap.put(n2, new CharacterBlockListEntry(n, n2, string, string2));
                }
            }
        }
        catch (Exception exception) {
            ax.warn("Error loading blocklist for {}: {}", (Object)n, (Object)exception);
        }
        return hashMap;
    }

    public void insert(Player player, CharacterBlockListEntry characterBlockListEntry) {
        this.insert(player.getObjectId(), characterBlockListEntry);
    }

    public void insert(int n, CharacterBlockListEntry characterBlockListEntry) {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ba);){
            preparedStatement.setInt(1, n);
            preparedStatement.setInt(2, characterBlockListEntry.getTargetObjId());
            preparedStatement.setString(3, StringUtils.trimToEmpty((String)characterBlockListEntry.getMemo()));
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            ax.warn("Error adding {} to blocklist of {}: {}", new Object[]{characterBlockListEntry.getName(), n, exception});
        }
    }

    public void delete(Player player, int n) {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(aZ);){
            preparedStatement.setInt(1, player.getObjectId());
            preparedStatement.setInt(2, n);
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            ax.warn("Error removing {} from blocklist {}: {}", new Object[]{n, player.getName(), exception});
        }
    }

    public void updateMemo(Player player, CharacterBlockListEntry characterBlockListEntry) {
        this.updateMemo(player.getObjectId(), characterBlockListEntry);
    }

    public void updateMemo(int n, CharacterBlockListEntry characterBlockListEntry) {
        this.updateMemo(n, characterBlockListEntry.getTargetObjId(), characterBlockListEntry.getMemo());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateMemo(int n, int n2, String string) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bb);
            preparedStatement.setString(1, string);
            preparedStatement.setInt(2, n);
            preparedStatement.setInt(3, n2);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                ax.warn("Could not update memo {} of {}", new Object[]{n2, n, exception});
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
