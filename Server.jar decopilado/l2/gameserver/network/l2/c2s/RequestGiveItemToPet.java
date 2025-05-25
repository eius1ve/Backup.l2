/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.PetInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.model.items.PetInventory;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.utils.Log;

public class RequestGiveItemToPet
extends L2GameClientPacket {
    private int fW;
    private long cP;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
        this.cP = this.readD();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.cP < 1L) {
            return;
        }
        PetInstance petInstance = (PetInstance)player.getPet();
        if (petInstance == null) {
            player.sendActionFailed();
            return;
        }
        if (player.isOutOfControl()) {
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.sendPacket((IStaticPacket)SystemMsg.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM);
            return;
        }
        if (player.isInTrade() || player.isProcessingRequest()) {
            player.sendActionFailed();
            return;
        }
        if (player.isFishing()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING_);
            return;
        }
        if (petInstance.isDead()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_PET_IS_DEAD_AND_ANY_ATTEMPT_YOU_MAKE_TO_GIVE_IT_SOMETHING_GOES_UNRECOGNIZED);
            return;
        }
        if (this.fW == petInstance.getControlItemObjId()) {
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.sendActionFailed();
            return;
        }
        PetInventory petInventory = petInstance.getInventory();
        PcInventory pcInventory = player.getInventory();
        petInventory.writeLock();
        pcInventory.writeLock();
        try {
            ItemInstance itemInstance = pcInventory.getItemByObjectId(this.fW);
            if (itemInstance == null || itemInstance.getCount() < this.cP || !itemInstance.canBeDropped(player, false)) {
                player.sendActionFailed();
                return;
            }
            int n = 0;
            long l = (long)itemInstance.getTemplate().getWeight() * this.cP;
            if (!itemInstance.getTemplate().isStackable() || petInstance.getInventory().getItemByItemId(itemInstance.getItemId()) == null) {
                n = 1;
            }
            if (!petInstance.getInventory().validateWeight(l)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_PET_CANNOT_CARRY_ANY_MORE_ITEMS_);
                return;
            }
            if (!petInstance.getInventory().validateCapacity(n)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_PET_CANNOT_CARRY_ANY_MORE_ITEMS_);
                return;
            }
            itemInstance = pcInventory.removeItemByObjectId(this.fW, this.cP);
            Log.LogItem(player, Log.ItemLog.ToPet, itemInstance);
            petInventory.addItem(itemInstance);
            petInstance.sendChanges();
            player.sendChanges();
        }
        finally {
            pcInventory.writeUnlock();
            petInventory.writeUnlock();
        }
    }
}
