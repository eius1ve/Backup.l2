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

private static class StatFunctions.FuncTradeLimit
extends Func {
    static final StatFunctions.FuncTradeLimit func = new StatFunctions.FuncTradeLimit();

    private StatFunctions.FuncTradeLimit() {
        super(Stats.TRADE_LIMIT, 1, null);
    }

    @Override
    public void calc(Env env) {
        Player player = (Player)env.character;
        if (player.getRace() == Race.dwarf) {
            env.value = player.getLevel() < 40 ? (double)Config.MAX_PVTSTORE_SLOTS_DWARF_FIRST_JOB : (double)Config.MAX_PVTSTORE_SLOTS_DWARF;
        } else {
            if (player.getLevel() < 40) {
                env.value = Config.MAX_PVTSTORE_SLOTS_OTHER_FIRST_JOB;
            }
            env.value = Config.MAX_PVTSTORE_SLOTS_OTHER;
        }
    }
}
