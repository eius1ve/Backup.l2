/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.taskmanager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.dbutils.DbUtils;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelayedItemsManager
extends RunnableImpl {
    private static final Logger dw = LoggerFactory.getLogger(DelayedItemsManager.class);
    private static DelayedItemsManager a;
    private static final String ga = "{CALL `lip_ItemsDelayedAdd`(?,?,?,?,?,?,?)}";
    private static final String gb = "{CALL `lip_LoadItemsDelayedByOwnerAndStatus`(?, ?)}";
    private static final String gc = "{CALL `lip_UpdateItemsDelayedPaymentStatus`(?, ?)}";
    private static final String gd = "{CALL `lip_GetItemsDelayedMaxPaymentId`()}";
    private static final String ge = "{CALL `lip_LoadItemsDelayedOwners`(?)}";
    private static final Object j;
    private AtomicInteger t = new AtomicInteger(0);

    public static DelayedItemsManager getInstance() {
        if (a == null) {
            a = new DelayedItemsManager();
        }
        return a;
    }

    public DelayedItemsManager() {
        this.t.set(this.A());
        ThreadPoolManager.getInstance().schedule(this, 10000L);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int A() {
        int n;
        Connection connection = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            n = this.a(connection);
        }
        catch (Exception exception) {
            try {
                dw.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection);
                throw throwable;
            }
            DbUtils.closeQuietly(connection);
            return 0;
        }
        DbUtils.closeQuietly(connection);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int a(Connection connection) {
        int n;
        CallableStatement callableStatement;
        ResultSet resultSet;
        block4: {
            resultSet = null;
            callableStatement = null;
            n = this.t.get();
            try {
                callableStatement = connection.prepareCall(gd);
                resultSet = callableStatement.executeQuery();
                if (!resultSet.next()) break block4;
                n = resultSet.getInt("last");
            }
            catch (Exception exception) {
                try {
                    dw.error("", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(callableStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(callableStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(callableStatement, resultSet);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void runImpl() throws Exception {
        Player player = null;
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            int n;
            int n2;
            connection = DatabaseFactory.getInstance().getConnection();
            while ((n2 = this.t.get()) != (n = this.a(connection))) {
                Object object = j;
                synchronized (object) {
                    callableStatement = connection.prepareCall(ge);
                    callableStatement.setInt(1, this.t.get());
                    resultSet = callableStatement.executeQuery();
                    while (resultSet.next()) {
                        player = GameObjectsStorage.getPlayer(resultSet.getInt("owner_id"));
                        if (player == null) continue;
                        this.loadDelayed(player, true);
                    }
                }
                if (!this.t.compareAndSet(n2, n)) continue;
                break;
            }
        }
        catch (Exception exception) {
            dw.error("", (Throwable)exception);
        }
        finally {
            DbUtils.closeQuietly(connection, callableStatement, resultSet);
        }
        ThreadPoolManager.getInstance().schedule(this, 10000L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int loadDelayed(Player player, boolean bl) {
        if (player == null) {
            return 0;
        }
        int n = player.getObjectId();
        PcInventory pcInventory = player.getInventory();
        if (pcInventory == null) {
            return 0;
        }
        int n2 = 0;
        Connection connection = null;
        CallableStatement callableStatement = null;
        CallableStatement callableStatement2 = null;
        ResultSet resultSet = null;
        Object object = j;
        synchronized (object) {
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                callableStatement = connection.prepareCall(gb);
                callableStatement.setInt(1, n);
                callableStatement.setInt(2, 0);
                resultSet = callableStatement.executeQuery();
                callableStatement2 = connection.prepareCall(gc);
                while (resultSet.next()) {
                    int n3 = resultSet.getInt("item_id");
                    long l = resultSet.getLong("count");
                    int n4 = resultSet.getInt("enchant_level");
                    int n5 = resultSet.getInt("variationId1");
                    int n6 = resultSet.getInt("variationId2");
                    int n7 = resultSet.getInt("payment_id");
                    int n8 = resultSet.getInt("flags");
                    boolean bl2 = ItemHolder.getInstance().getTemplate(n3).isStackable();
                    boolean bl3 = false;
                    int n9 = 0;
                    while ((long)n9 < (bl2 ? 1L : l)) {
                        ItemInstance itemInstance;
                        ItemInstance itemInstance2 = ItemFunctions.createItem(n3);
                        if (itemInstance2.isStackable()) {
                            itemInstance2.setCount(l);
                        } else {
                            itemInstance2.setEnchantLevel(n4);
                            itemInstance2.setVariationStat1(n5);
                            itemInstance2.setVariationStat2(n6);
                        }
                        itemInstance2.setLocation(ItemInstance.ItemLocation.INVENTORY);
                        itemInstance2.setCustomFlags(n8);
                        if (l > 0L && (itemInstance = pcInventory.addItem(itemInstance2)) == null) {
                            dw.warn("Unable to delayed create item " + n3 + " request " + n7);
                        } else {
                            bl3 = true;
                            ++n2;
                            if (bl && l > 0L) {
                                player.sendPacket((IStaticPacket)SystemMessage.obtainItems(n3, bl2 ? l : 1L, n4));
                            }
                            player.sendMessage(new CustomMessage("l2.gameserver.taskmanager.DelayedItemsManager.ItemSendMessage", player, new Object[0]));
                        }
                        ++n9;
                    }
                    if (!bl3) continue;
                    Log.add("<add owner_id=" + n + " item_id=" + n3 + " count=" + l + " enchant_level=" + n4 + " variation_1=" + n5 + " variation_2=" + n6 + " payment_id=" + n7 + "/>", "delayed_add");
                    callableStatement2.setInt(1, n7);
                    callableStatement2.setInt(2, 1);
                    callableStatement2.execute();
                }
            }
            catch (Exception exception) {
                try {
                    dw.error("Could not load delayed items for player " + player + "!", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, callableStatement, callableStatement2, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, callableStatement, callableStatement2, resultSet);
            }
            DbUtils.closeQuietly(connection, callableStatement, callableStatement2, resultSet);
        }
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addDelayed(int n, int n2, int n3, int n4, int n5, int n6, String string) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Object object = j;
        synchronized (object) {
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                callableStatement = connection.prepareCall(ga);
                callableStatement.setInt(1, n);
                callableStatement.setInt(2, n2);
                callableStatement.setInt(3, n3);
                callableStatement.setInt(4, n4);
                callableStatement.setInt(5, n5);
                callableStatement.setInt(6, n6);
                callableStatement.setString(7, string);
                callableStatement.execute();
            }
            catch (Exception exception) {
                try {
                    dw.error("Could not add delayed items " + n2 + " " + n3 + "(+" + n4 + ")( aug 1" + n5 + ")( aug 2" + n6 + ") + for objId " + n + " desc \"" + string + "\" !", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, callableStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, callableStatement);
            }
            DbUtils.closeQuietly(connection, callableStatement);
        }
    }

    static {
        j = new Object();
    }
}
