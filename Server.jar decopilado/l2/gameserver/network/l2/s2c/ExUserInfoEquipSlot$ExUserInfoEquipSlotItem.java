/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.InventorySlot;

protected static class ExUserInfoEquipSlot.ExUserInfoEquipSlotItem {
    int objId;
    int itemId;
    int var1;
    int var2;
    int visualId;

    public ExUserInfoEquipSlot.ExUserInfoEquipSlotItem(Player player, ItemInstance itemInstance, InventorySlot inventorySlot) {
        if (itemInstance != null) {
            this.objId = itemInstance.getObjectId();
            this.itemId = itemInstance.getVisibleItemId();
            this.var1 = itemInstance.getVariationStat1();
            this.var2 = itemInstance.getVariationStat2();
            this.visualId = 0;
            Integer n = 0;
            if (player.getInventory().getOnDisplayListener() != null && (n = player.getInventory().getOnDisplayListener().onDisplay(inventorySlot.getSlot(), itemInstance, player)) != null) {
                this.visualId = n;
            }
        } else {
            this.objId = 0;
            this.itemId = 0;
            this.var1 = 0;
            this.var2 = 0;
            this.visualId = 0;
        }
    }
}
