/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.inventory.OnDisplayListener
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.items.ItemInstance
 */
package services;

import java.util.Map;
import l2.gameserver.listener.inventory.OnDisplayListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.items.ItemInstance;

private static class ItemFakeAppearance.ItemFakeAppearanceDisplayListener
implements OnDisplayListener {
    private final Map<Integer, Integer> cq;

    private ItemFakeAppearance.ItemFakeAppearanceDisplayListener(Map<Integer, Integer> map) {
        this.cq = map;
    }

    public Integer onDisplay(int n, ItemInstance itemInstance, Playable playable) {
        return this.cq.get(n);
    }
}
