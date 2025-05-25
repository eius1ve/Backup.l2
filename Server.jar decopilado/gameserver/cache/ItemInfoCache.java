/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.cache;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;

public class ItemInfoCache {
    private static final ItemInfoCache a = new ItemInfoCache();
    private final Map<Integer, ItemInfo> z = Collections.synchronizedMap(new LinkedHashMap<Integer, ItemInfo>(Config.ITEM_LINK_CACHE_SIZE, 0.75f, true){

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, ItemInfo> entry) {
            return this.size() > Config.ITEM_LINK_CACHE_SIZE;
        }
    });

    private ItemInfoCache() {
    }

    public static ItemInfoCache getInstance() {
        return a;
    }

    public void put(ItemInstance itemInstance) {
        this.z.put(itemInstance.getObjectId(), new ItemInfo(itemInstance));
    }

    public ItemInfo get(int n2) {
        return this.z.computeIfPresent(n2, (n, itemInfo) -> {
            Player player = World.getPlayer(itemInfo.getOwnerId());
            if (player != null) {
                ItemInstance itemInstance = player.getInventory().getItemByObjectId((int)n);
                if (itemInstance != null && itemInstance.getItemId() == itemInfo.getItemId()) {
                    return new ItemInfo(itemInstance);
                }
                return null;
            }
            return itemInfo;
        });
    }
}
