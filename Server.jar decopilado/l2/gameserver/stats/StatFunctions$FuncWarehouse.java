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

private static class StatFunctions.FuncWarehouse
extends Func {
    static final StatFunctions.FuncWarehouse func = new StatFunctions.FuncWarehouse();

    private StatFunctions.FuncWarehouse() {
        super(Stats.STORAGE_LIMIT, 1, null);
    }

    @Override
    public void calc(Env env) {
        Player player = (Player)env.character;
        env.value = player.getTemplate().race == Race.dwarf ? (double)Config.WAREHOUSE_SLOTS_DWARF : (double)Config.WAREHOUSE_SLOTS_NO_DWARF;
        env.value += (double)player.getExpandWarehouse();
    }
}
