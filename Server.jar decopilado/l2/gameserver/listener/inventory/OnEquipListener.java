/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.listener.inventory;

import l2.commons.listener.Listener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.items.ItemInstance;

public interface OnEquipListener
extends Listener<Playable> {
    public void onEquip(int var1, ItemInstance var2, Playable var3);

    public void onUnequip(int var1, ItemInstance var2, Playable var3);
}
