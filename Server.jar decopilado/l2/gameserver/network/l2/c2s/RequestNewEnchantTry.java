/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.commons.util.Rnd;
import l2.gameserver.data.xml.holder.CombinationDataHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExEnchantFail;
import l2.gameserver.network.l2.s2c.ExEnchantSucess;
import l2.gameserver.templates.item.support.CombinationItem;
import l2.gameserver.utils.ItemFunctions;

public class RequestNewEnchantTry
extends L2GameClientPacket {
    @Override
    protected void readImpl() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isActionsDisabled() || player.isInStoreMode() || player.isInTrade() || player.isFishing() || player.isAlikeDead() || player.isBlocked()) {
            player.sendPacket((IStaticPacket)ExEnchantFail.STATIC);
            return;
        }
        if (player.getSlotOneId() == 0 || player.getSlotTwoId() == 0) {
            player.sendPacket((IStaticPacket)ExEnchantFail.STATIC);
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(player.getSlotOneId());
        ItemInstance itemInstance2 = player.getInventory().getItemByObjectId(player.getSlotTwoId());
        if (itemInstance == null || itemInstance2 == null || itemInstance == itemInstance2 && itemInstance.getCount() < 2L) {
            player.sendPacket((IStaticPacket)ExEnchantFail.STATIC);
            return;
        }
        CombinationItem combinationItem = CombinationDataHolder.getInstance().getDataBySlowOneAndTwoItemId(itemInstance.getItemId(), itemInstance2.getItemId());
        if (combinationItem == null) {
            player.sendPacket((IStaticPacket)ExEnchantFail.STATIC);
            return;
        }
        PcInventory pcInventory = player.getInventory();
        pcInventory.writeLock();
        try {
            if (pcInventory.getItemByObjectId(itemInstance.getObjectId()) == null) {
                player.sendPacket((IStaticPacket)ExEnchantFail.STATIC);
                return;
            }
            if (pcInventory.getItemByObjectId(itemInstance2.getObjectId()) == null) {
                player.sendPacket((IStaticPacket)ExEnchantFail.STATIC);
                return;
            }
            ItemFunctions.removeItem(player, itemInstance.getItemId(), 1L, true);
            ItemFunctions.removeItem(player, itemInstance2.getItemId(), 1L, true);
            if (Rnd.chance(combinationItem.getChance())) {
                ItemFunctions.addItem((Playable)player, combinationItem.getOnsuccess(), 1L, true);
                player.sendPacket((IStaticPacket)new ExEnchantSucess(combinationItem.getOnsuccess()));
            } else {
                ItemFunctions.addItem((Playable)player, combinationItem.getOnfail(), 1L, true);
                player.sendPacket((IStaticPacket)new ExEnchantFail(itemInstance.getItemId(), itemInstance2.getItemId()));
            }
        }
        finally {
            pcInventory.writeUnlock();
        }
    }
}
