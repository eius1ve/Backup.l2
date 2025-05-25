/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.BaseStats;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.templates.item.WeaponTemplate;

private static class StatFunctions.FuncSDefPlayers
extends Func {
    static final StatFunctions.FuncSDefPlayers func = new StatFunctions.FuncSDefPlayers();

    private StatFunctions.FuncSDefPlayers() {
        super(Stats.SHIELD_RATE, 32, null);
    }

    @Override
    public void calc(Env env) {
        if (env.value == 0.0) {
            return;
        }
        Creature creature = env.character;
        ItemInstance itemInstance = ((Player)creature).getInventory().getPaperdollItem(7);
        if (itemInstance == null || itemInstance.getItemType() != WeaponTemplate.WeaponType.NONE) {
            return;
        }
        env.value *= BaseStats.DEX.calcBonus(env.character);
    }
}
