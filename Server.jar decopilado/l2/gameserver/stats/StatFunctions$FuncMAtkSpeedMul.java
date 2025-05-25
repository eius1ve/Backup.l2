/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.base.BaseStats;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMAtkSpeedMul
extends Func {
    static final StatFunctions.FuncMAtkSpeedMul func = new StatFunctions.FuncMAtkSpeedMul();

    private StatFunctions.FuncMAtkSpeedMul() {
        super(Stats.MAGIC_ATTACK_SPEED, 32, null);
    }

    @Override
    public void calc(Env env) {
        env.value *= BaseStats.WIT.calcBonus(env.character);
    }
}
