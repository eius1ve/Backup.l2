/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class PetInventoryUpdate
extends AbstractItemListPacket {
    public static final int UNCHANGED = 0;
    public static final int ADDED = 1;
    public static final int MODIFIED = 2;
    public static final int REMOVED = 3;
    private final List<ItemInfo> cQ = new ArrayList<ItemInfo>(1);

    public PetInventoryUpdate addNewItem(ItemInstance itemInstance) {
        this.a(itemInstance).setLastChange(1);
        return this;
    }

    public PetInventoryUpdate addModifiedItem(ItemInstance itemInstance) {
        this.a(itemInstance).setLastChange(2);
        return this;
    }

    public PetInventoryUpdate addRemovedItem(ItemInstance itemInstance) {
        this.a(itemInstance).setLastChange(3);
        return this;
    }

    private ItemInfo a(ItemInstance itemInstance) {
        ItemInfo itemInfo = new ItemInfo(itemInstance);
        this.cQ.add(itemInfo);
        return itemInfo;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(180);
        this.writeH(this.cQ.size());
        for (ItemInfo itemInfo : this.cQ) {
            this.writeH(itemInfo.getLastChange());
            this.writeItemInfo(itemInfo);
        }
    }
}
