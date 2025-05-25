/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import java.util.concurrent.ConcurrentLinkedQueue;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.items.ItemInstance;

public class ItemsAutoDestroy {
    private static ItemsAutoDestroy a;
    private ConcurrentLinkedQueue<ItemInstance> g = null;
    private ConcurrentLinkedQueue<ItemInstance> h = new ConcurrentLinkedQueue();

    private ItemsAutoDestroy() {
        if (Config.AUTODESTROY_ITEM_AFTER > 0) {
            this.g = new ConcurrentLinkedQueue();
            ThreadPoolManager.getInstance().scheduleAtFixedRate(new CheckItemsForDestroy(), 60000L, 60000L);
        }
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new CheckHerbsForDestroy(), 1000L, 1000L);
    }

    public static ItemsAutoDestroy getInstance() {
        if (a == null) {
            a = new ItemsAutoDestroy();
        }
        return a;
    }

    public void addItem(ItemInstance itemInstance) {
        itemInstance.setDropTime(System.currentTimeMillis());
        this.g.add(itemInstance);
    }

    public void addHerb(ItemInstance itemInstance) {
        itemInstance.setDropTime(System.currentTimeMillis());
        this.h.add(itemInstance);
    }

    public class CheckItemsForDestroy
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

    public class CheckHerbsForDestroy
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
}
