/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.items.ItemInstance;

public class ItemsAutoDestroy.CheckHerbsForDestroy
extends RunnableImpl {
    static final long _sleep = 60000L;

    @Override
    public void runImpl() throws Exception {
        long l = System.currentTimeMillis();
        for (ItemInstance itemInstance : ItemsAutoDestroy.this.h) {
            if (itemInstance == null || itemInstance.getLastDropTime() == 0L || itemInstance.getLocation() != ItemInstance.ItemLocation.VOID) {
                ItemsAutoDestroy.this.h.remove(itemInstance);
                continue;
            }
            if (itemInstance.getLastDropTime() + 60000L >= l) continue;
            itemInstance.deleteMe();
            ItemsAutoDestroy.this.h.remove(itemInstance);
        }
    }
}
