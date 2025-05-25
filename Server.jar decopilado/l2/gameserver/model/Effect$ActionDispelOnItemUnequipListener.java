/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.listener.inventory.OnEquipListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.items.ItemInstance;

private class Effect.ActionDispelOnItemUnequipListener
implements OnEquipListener {
    private Effect.ActionDispelOnItemUnequipListener() {
    }

    @Override
    public void onEquip(int n, ItemInstance itemInstance, Playable playable) {
    }

    @Override
    public void onUnequip(int n, ItemInstance itemInstance, Playable playable) {
        if (itemInstance.isWeapon() && (Effect.this.getTemplate()._cancelOnItem & 1) != 0 || itemInstance.isArmor() && (Effect.this.getTemplate()._cancelOnItem & 2) != 0) {
            Effect.this.exit();
        }
    }
}
