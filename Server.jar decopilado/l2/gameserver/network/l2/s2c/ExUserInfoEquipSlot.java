/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.InventorySlot;
import l2.gameserver.network.l2.s2c.mask.AbstractMaskPacket;

public class ExUserInfoEquipSlot
extends AbstractMaskPacket<InventorySlot> {
    private final byte[] x = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
    private int objectId;
    private final List<ExUserInfoEquipSlotItem> cG = new ArrayList<ExUserInfoEquipSlotItem>();

    public ExUserInfoEquipSlot(Player player, InventorySlot ... inventorySlotArray) {
        this.add(player, inventorySlotArray);
    }

    public ExUserInfoEquipSlot add(Player player, InventorySlot ... inventorySlotArray) {
        this.objectId = player.getObjectId();
        for (InventorySlot inventorySlot : inventorySlotArray) {
            ItemInstance itemInstance = player.getInventory().getPaperdollItem(inventorySlot.getSlot());
            ExUserInfoEquipSlotItem exUserInfoEquipSlotItem = new ExUserInfoEquipSlotItem(player, itemInstance, inventorySlot);
            this.cG.add(exUserInfoEquipSlotItem);
            this.addComponentType(new InventorySlot[]{inventorySlot});
        }
        return this;
    }

    @Override
    protected byte[] getMasks() {
        return this.x;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(342);
        this.writeD(this.objectId);
        this.writeH(InventorySlot.VALUES.length);
        this.writeB(this.x);
        for (ExUserInfoEquipSlotItem exUserInfoEquipSlotItem : this.cG) {
            this.writeH(22);
            this.writeD(exUserInfoEquipSlotItem.objId);
            this.writeD(exUserInfoEquipSlotItem.itemId);
            this.writeD(exUserInfoEquipSlotItem.var1);
            this.writeD(exUserInfoEquipSlotItem.var2);
            this.writeD(exUserInfoEquipSlotItem.visualId);
        }
    }

    protected static class ExUserInfoEquipSlotItem {
        int objId;
        int itemId;
        int var1;
        int var2;
        int visualId;

        public ExUserInfoEquipSlotItem(Player player, ItemInstance itemInstance, InventorySlot inventorySlot) {
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
}
