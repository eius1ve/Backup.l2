/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.base.BaseStats;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncPAtkMul
extends Func {
    static final StatFunctions.FuncPAtkMul func = new StatFunctions.FuncPAtkMul();

    private StatFunctions.FuncPAtkMul() {
        super(Stats.POWER_ATTACK, 32, null);
    }

    @Override
    public void calc(Env env) {
        env.value *= BaseStats.STR.calcBonus(env.character) * env.character.getLevelMod();
    }
}
