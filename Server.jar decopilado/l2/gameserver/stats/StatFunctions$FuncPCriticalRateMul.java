/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.Summon;
import l2.gameserver.model.base.BaseStats;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncPCriticalRateMul
extends Func {
    static final StatFunctions.FuncPCriticalRateMul func = new StatFunctions.FuncPCriticalRateMul();

    private StatFunctions.FuncPCriticalRateMul() {
        super(Stats.CRITICAL_BASE, 16, null);
    }

    @Override
    public void calc(Env env) {
        if (!(env.character instanceof Summon)) {
            env.value *= BaseStats.DEX.calcBonus(env.character);
        }
        env.value *= 0.01 * env.character.calcStat(Stats.CRITICAL_RATE, env.target, env.skill);
    }
}
