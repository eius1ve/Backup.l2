/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncInventory
extends Func {
    static final StatFunctions.FuncInventory func = new StatFunctions.FuncInventory();

    private StatFunctions.FuncInventory() {
        super(Stats.INVENTORY_LIMIT, 1, null);
    }

    @Override
    public void calc(Env env) {
        Player player = (Player)env.character;
        env.value = player.isGM() ? (double)Config.INVENTORY_MAXIMUM_GM : (player.getTemplate().race == Race.dwarf ? (double)Config.INVENTORY_MAXIMUM_DWARF : (double)Config.INVENTORY_MAXIMUM_NO_DWARF);
        env.value += (double)player.getExpandInventory();
        env.value = Math.min(env.value, (double)Config.INVENTORY_MAXIMUM_PERMISSIBLE_LIMIT);
    }
}
