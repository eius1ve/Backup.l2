/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

import java.util.Comparator;
import l2.gameserver.model.items.ItemInstance;

public static class Warehouse.ItemClassComparator
implements Comparator<ItemInstance> {
    private static final Comparator<ItemInstance> e = new Warehouse.ItemClassComparator();

    public static final Comparator<ItemInstance> getInstance() {
        return e;
    }

    @Override
    public int compare(ItemInstance itemInstance, ItemInstance itemInstance2) {
        if (itemInstance == null || itemInstance2 == null) {
            return 0;
        }
        int n = itemInstance.getItemClass().ordinal() - itemInstance2.getItemClass().ordinal();
        if (n == 0) {
            n = itemInstance.getCrystalType().ordinal() - itemInstance2.getCrystalType().ordinal();
        }
        if (n == 0) {
            n = itemInstance.getItemId() - itemInstance2.getItemId();
        }
        if (n == 0) {
            n = itemInstance.getEnchantLevel() - itemInstance2.getEnchantLevel();
        }
        return n;
    }
}
