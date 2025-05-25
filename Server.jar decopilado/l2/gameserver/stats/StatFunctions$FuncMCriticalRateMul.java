/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.base.BaseStats;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMCriticalRateMul
extends Func {
    static final StatFunctions.FuncMCriticalRateMul func = new StatFunctions.FuncMCriticalRateMul();

    private StatFunctions.FuncMCriticalRateMul() {
        super(Stats.MCRITICAL_RATE, 16, null);
    }

    @Override
    public void calc(Env env) {
        env.value *= 0.1 * BaseStats.WIT.calcBonus(env.character);
    }
}
