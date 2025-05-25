/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

import java.util.Comparator;
import l2.gameserver.model.items.ItemInstance;

public static class Inventory.ItemOrderComparator
implements Comparator<ItemInstance> {
    private static final Comparator<ItemInstance> d = new Inventory.ItemOrderComparator();

    public static final Comparator<ItemInstance> getInstance() {
        return d;
    }

    @Override
    public int compare(ItemInstance itemInstance, ItemInstance itemInstance2) {
        if (itemInstance == null || itemInstance2 == null) {
            return 0;
        }
        return itemInstance.getLocData() - itemInstance2.getLocData();
    }
}
