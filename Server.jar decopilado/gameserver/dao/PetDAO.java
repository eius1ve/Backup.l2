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
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.model.items.ItemInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetDAO {
    private static final Logger aL = LoggerFactory.getLogger(PetDAO.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void deletePet(ItemInstance itemInstance, Creature creature) {
        int n = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Player player;
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `objId` FROM `pets` WHERE `item_obj_id`=?");
            preparedStatement.setInt(1, itemInstance.getObjectId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                n = resultSet.getInt("objId");
            }
            DbUtils.close(preparedStatement, resultSet);
            Summon summon = creature.getPet();
            if (summon != null && summon.getObjectId() == n) {
                summon.unSummon();
            }
            if ((player = creature.getPlayer()) != null && player.isMounted() && player.getMountObjId() == n) {
                player.setMount(0, 0, 0);
            }
            preparedStatement = connection.prepareStatement("DELETE FROM `pets` WHERE `item_obj_id`=?");
            preparedStatement.setInt(1, itemInstance.getObjectId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aL.error("could not restore pet objectid:", (Throwable)exception);
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
    public static void unSummonPet(ItemInstance itemInstance, Creature creature) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block8: {
            int n;
            block7: {
                n = 0;
                connection = null;
                preparedStatement = null;
                resultSet = null;
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT `objId` FROM `pets` WHERE `item_obj_id`=?");
                preparedStatement.setInt(1, itemInstance.getObjectId());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    n = resultSet.getInt("objId");
                }
                if (creature != null) break block7;
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                return;
            }
            try {
                Player player;
                Summon summon = creature.getPet();
                if (summon != null && summon.getObjectId() == n) {
                    summon.unSummon();
                }
                if ((player = creature.getPlayer()) == null || !player.isMounted() || player.getMountObjId() != n) break block8;
                player.setMount(0, 0, 0);
            }
            catch (Exception exception) {
                try {
                    aL.error("could not restore pet objectid:", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }
}
