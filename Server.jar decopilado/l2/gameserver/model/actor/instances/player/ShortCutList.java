/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.actor.instances.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExAutoSoulShot;
import l2.gameserver.network.l2.s2c.ShortCutInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShortCutList {
    private static final Logger bN = LoggerFactory.getLogger(ShortCutList.class);
    private final Player j;
    private Map<Integer, ShortCut> aR = new ConcurrentHashMap<Integer, ShortCut>();

    public ShortCutList(Player player) {
        this.j = player;
    }

    public Collection<ShortCut> getAllShortCuts() {
        return this.aR.values();
    }

    public void validate() {
        for (ShortCut shortCut : this.aR.values()) {
            if (shortCut.getType() != 1 || this.j.getInventory().getItemByObjectId(shortCut.getId()) != null) continue;
            this.deleteShortCut(shortCut.getSlot(), shortCut.getPage());
        }
    }

    public ShortCut getShortCut(int n, int n2) {
        ShortCut shortCut = this.aR.get(n + n2 * 12);
        if (shortCut != null && shortCut.getType() == 1 && this.j.getInventory().getItemByObjectId(shortCut.getId()) == null) {
            this.j.sendPacket((IStaticPacket)SystemMsg.THERE_ARE_NO_MORE_ITEMS_IN_THE_SHORTCUT);
            this.deleteShortCut(shortCut.getSlot(), shortCut.getPage());
            shortCut = null;
        }
        return shortCut;
    }

    public ShortCut[] getShortCuts() {
        return this.aR.values().toArray(new ShortCut[this.aR.values().size()]);
    }

    public void registerShortCut(ShortCut shortCut) {
        ShortCut shortCut2 = this.aR.put(shortCut.getSlot() + 12 * shortCut.getPage(), shortCut);
        this.a(shortCut, shortCut2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private synchronized void a(ShortCut shortCut, ShortCut shortCut2) {
        if (shortCut2 != null) {
            this.a(shortCut2);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("REPLACE INTO `character_shortcuts` SET `object_id`=?,`slot`=?,`page`=?,`type`=?,`shortcut_id`=?,`level`=?,`character_type`=?,`class_index`=?");
            preparedStatement.setInt(1, this.j.getObjectId());
            preparedStatement.setInt(2, shortCut.getSlot());
            preparedStatement.setInt(3, shortCut.getPage());
            preparedStatement.setInt(4, shortCut.getType());
            preparedStatement.setInt(5, shortCut.getId());
            preparedStatement.setInt(6, shortCut.getLevel());
            preparedStatement.setInt(7, shortCut.getCharacterType());
            preparedStatement.setInt(8, this.j.getActiveClassId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                bN.error("could not store shortcuts:", (Throwable)exception);
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
    private void a(ShortCut shortCut) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `character_shortcuts` WHERE `object_id`=? AND `slot`=? AND `page`=? AND `class_index`=?");
            preparedStatement.setInt(1, this.j.getObjectId());
            preparedStatement.setInt(2, shortCut.getSlot());
            preparedStatement.setInt(3, shortCut.getPage());
            preparedStatement.setInt(4, this.j.getActiveClassId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                bN.error("could not delete shortcuts:", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public void deleteShortCut(int n, int n2) {
        ShortCut shortCut = this.aR.remove(n + n2 * 12);
        if (shortCut == null) {
            return;
        }
        this.a(shortCut);
        if (shortCut.getType() == 2) {
            this.j.sendPacket((IStaticPacket)new ShortCutInit(this.j));
            for (int n3 : this.j.getAutoSoulShot()) {
                this.j.sendPacket((IStaticPacket)new ExAutoSoulShot(n3, true, 0));
            }
        }
    }

    public void deleteShortCutByObjectId(int n) {
        for (ShortCut shortCut : this.aR.values()) {
            if (shortCut == null || shortCut.getType() != 1 || shortCut.getId() != n) continue;
            this.deleteShortCut(shortCut.getSlot(), shortCut.getPage());
        }
    }

    public void deleteShortCutBySkillId(int n) {
        for (ShortCut shortCut : this.aR.values()) {
            if (shortCut == null || shortCut.getType() != 2 || shortCut.getId() != n) continue;
            this.deleteShortCut(shortCut.getSlot(), shortCut.getPage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void restore() {
        this.aR.clear();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `character_type`, `slot`, `page`, `type`, `shortcut_id`, `level` FROM `character_shortcuts` WHERE `object_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, this.j.getObjectId());
            preparedStatement.setInt(2, this.j.getActiveClassId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("slot");
                int n2 = resultSet.getInt("page");
                int n3 = resultSet.getInt("type");
                int n4 = resultSet.getInt("shortcut_id");
                int n5 = resultSet.getInt("level");
                int n6 = resultSet.getInt("character_type");
                this.aR.put(n + n2 * 12, new ShortCut(n, n2, n3, n4, n5, n6));
            }
        }
        catch (Exception exception) {
            try {
                bN.error("could not store shortcuts:", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }
}
