/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import l2.gameserver.Config;
import l2.gameserver.model.items.ItemInfo;

class ItemInfoCache.1
extends LinkedHashMap<Integer, ItemInfo> {
    ItemInfoCache.1(int n, float f, boolean bl) {
        super(n, f, bl);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, ItemInfo> entry) {
        return this.size() > Config.ITEM_LINK_CACHE_SIZE;
    }
}
