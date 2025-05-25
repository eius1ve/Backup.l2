/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import l2.commons.math.SafeMath;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.model.items.Warehouse;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.WareHouseDone;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendWareHouseWithDrawList
extends L2GameClientPacket {
    private static final Logger cX = LoggerFactory.getLogger(SendWareHouseWithDrawList.class);
    private int gT;
    private int[] aP;
    private long[] c;

    @Override
    protected void readImpl() {
        this.gT = this.readD();
        if (this.gT * 12 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aP = new int[this.gT];
        this.c = new long[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            this.aP[i] = this.readD();
            this.c[i] = this.readQ();
            if (this.c[i] >= 1L && ArrayUtils.indexOf((int[])this.aP, (int)this.aP[i]) >= i) continue;
            this.gT = 0;
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.gT == 0) {
            return;
        }
        if (!player.getPlayerAccess().UseWarehouse) {
            player.sendActionFailed();
            return;
        }
        if (player.isActionsDisabled()) {
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.sendPacket(SystemMsg.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM, WareHouseDone.FAIL);
            return;
        }
        if (player.isInTrade()) {
            player.sendActionFailed();
            return;
        }
        if (player.isTradeBannedByGM()) {
            player.sendActionFailed();
            return;
        }
        NpcInstance npcInstance = player.getLastNpc();
        if (!(npcInstance != null && npcInstance.isInActingRange(player) || Config.ALT_ALLOW_REMOTE_USE_CARGO_BOX)) {
            player.sendPacket(SystemMsg.YOU_HAVE_MOVED_TOO_FAR_AWAY_FROM_THE_WAREHOUSE_TO_PERFORM_THAT_ACTION, WareHouseDone.FAIL);
            return;
        }
        Warehouse warehouse = null;
        Log.ItemLog itemLog = null;
        if (player.getUsingWarehouseType() == Warehouse.WarehouseType.PRIVATE) {
            warehouse = player.getWarehouse();
            itemLog = Log.ItemLog.WarehouseWithdraw;
        } else if (player.getUsingWarehouseType() == Warehouse.WarehouseType.CLAN) {
            itemLog = Log.ItemLog.ClanWarehouseWithdraw;
            if (player.getClan() == null || player.getClan().getLevel() == 0) {
                player.sendPacket(SystemMsg.ONLY_CLANS_OF_CLAN_LEVEL_1_OR_HIGHER_CAN_USE_A_CLAN_WAREHOUSE, WareHouseDone.FAIL);
                return;
            }
            boolean bl = false;
            if (player.getClan() != null && (player.getClanPrivileges() & 8) == 8 && (Config.ALT_ALLOW_OTHERS_WITHDRAW_FROM_CLAN_WAREHOUSE || player.isClanLeader() || player.getVarB("canWhWithdraw"))) {
                bl = true;
            }
            if (!bl) {
                player.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_THE_RIGHT_TO_USE_THE_CLAN_WAREHOUSE, WareHouseDone.FAIL);
                return;
            }
            warehouse = player.getClan().getWarehouse();
        } else if (player.getUsingWarehouseType() == Warehouse.WarehouseType.FREIGHT) {
            warehouse = player.getFreight();
            itemLog = Log.ItemLog.FreightWithdraw;
        } else {
            cX.warn("Error retrieving a warehouse object for char " + player.getName() + " - using warehouse type: " + player.getUsingWarehouseType());
            return;
        }
        PcInventory pcInventory = player.getInventory();
        pcInventory.writeLock();
        warehouse.writeLock();
        try {
            ItemInstance itemInstance;
            int n;
            long l = 0L;
            int n2 = 0;
            for (n = 0; n < this.gT; ++n) {
                itemInstance = warehouse.getItemByObjectId(this.aP[n]);
                if (itemInstance == null || itemInstance.getCount() < this.c[n]) {
                    player.sendPacket(SystemMsg.INCORRECT_ITEM_COUNT, WareHouseDone.FAIL);
                    return;
                }
                l = SafeMath.addAndCheck(l, SafeMath.mulAndCheck((long)itemInstance.getTemplate().getWeight(), this.c[n]));
                if (itemInstance.isStackable() && pcInventory.getItemByItemId(itemInstance.getItemId()) != null) continue;
                ++n2;
            }
            if (!player.getInventory().validateCapacity(n2)) {
                player.sendPacket(SystemMsg.YOUR_INVENTORY_IS_FULL, WareHouseDone.FAIL);
                return;
            }
            if (!player.getInventory().validateWeight(l)) {
                player.sendPacket(SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT, WareHouseDone.FAIL);
                return;
            }
            for (n = 0; n < this.gT; ++n) {
                itemInstance = warehouse.removeItemByObjectId(this.aP[n], this.c[n]);
                Log.LogItem(player, itemLog, itemInstance);
                player.getInventory().addItem(itemInstance);
            }
        }
        catch (ArithmeticException arithmeticException) {
            player.sendPacket(SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED, WareHouseDone.FAIL);
            return;
        }
        finally {
            warehouse.writeUnlock();
            pcInventory.writeUnlock();
        }
        player.sendChanges();
        player.sendPacket(SystemMsg.THE_TRANSACTION_IS_COMPLETE, WareHouseDone.SUCCESS);
    }
}
