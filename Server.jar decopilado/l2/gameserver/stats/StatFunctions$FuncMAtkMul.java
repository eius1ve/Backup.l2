/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.base.BaseStats;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMAtkMul
extends Func {
    static final StatFunctions.FuncMAtkMul func = new StatFunctions.FuncMAtkMul();

    private StatFunctions.FuncMAtkMul() {
        super(Stats.MAGIC_ATTACK, 32, null);
    }

    @Override
    public void calc(Env env) {
        double d = BaseStats.INT.calcBonus(env.character);
        double d2 = env.character.getLevelMod();
        env.value *= d2 * d2 * d * d;
    }
}
