/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.model.items.ItemInstance;

public class ItemsAutoDestroy.CheckItemsForDestroy
extends RunnableImpl {
    @Override
    public void runImpl() throws Exception {
        long l = (long)Config.AUTODESTROY_ITEM_AFTER * 1000L;
        long l2 = System.currentTimeMillis();
        for (ItemInstance itemInstance : ItemsAutoDestroy.this.g) {
            if (itemInstance == null || itemInstance.getLastDropTime() == 0L || itemInstance.getLocation() != ItemInstance.ItemLocation.VOID) {
                ItemsAutoDestroy.this.g.remove(itemInstance);
                continue;
            }
            if (itemInstance.getLastDropTime() + l >= l2) continue;
            itemInstance.deleteMe();
            itemInstance.delete();
            ItemsAutoDestroy.this.g.remove(itemInstance);
        }
    }
}
