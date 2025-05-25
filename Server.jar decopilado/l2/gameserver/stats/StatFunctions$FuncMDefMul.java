/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.base.BaseStats;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMDefMul
extends Func {
    static final StatFunctions.FuncMDefMul func = new StatFunctions.FuncMDefMul();

    private StatFunctions.FuncMDefMul() {
        super(Stats.MAGIC_DEFENCE, 32, null);
    }

    @Override
    public void calc(Env env) {
        env.value *= BaseStats.MEN.calcBonus(env.character) * env.character.getLevelMod();
    }
}
