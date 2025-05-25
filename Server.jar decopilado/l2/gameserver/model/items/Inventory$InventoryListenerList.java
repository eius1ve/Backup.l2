/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

import l2.commons.listener.ListenerList;
import l2.gameserver.listener.inventory.OnEquipListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.items.ItemInstance;

public class Inventory.InventoryListenerList
extends ListenerList<Playable> {
    public void onEquip(int n, ItemInstance itemInstance) {
        this.forEachListener(OnEquipListener.class, onEquipListener -> onEquipListener.onEquip(n, itemInstance, Inventory.this.getActor()));
    }

    public void onUnequip(int n, ItemInstance itemInstance) {
        this.forEachListener(OnEquipListener.class, onEquipListener -> onEquipListener.onUnequip(n, itemInstance, Inventory.this.getActor()));
    }
}
