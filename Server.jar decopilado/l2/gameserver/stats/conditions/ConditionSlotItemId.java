/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.ConditionInventory;

public final class ConditionSlotItemId
extends ConditionInventory {
    private final int DU;
    private final int DV;

    public ConditionSlotItemId(int n, int n2, int n3) {
        super(n);
        this.DU = n2;
        this.DV = n3;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (!env.character.isPlayer()) {
            return false;
        }
        PcInventory pcInventory = ((Player)env.character).getInventory();
        ItemInstance itemInstance = pcInventory.getPaperdollItem(this._slot);
        if (itemInstance == null) {
            return this.DU == 0;
        }
        return itemInstance.getItemId() == this.DU && itemInstance.getEnchantLevel() >= this.DV;
    }
}
