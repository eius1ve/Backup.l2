/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.base.BaseStats;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMoveSpeedMul
extends Func {
    static final StatFunctions.FuncMoveSpeedMul func = new StatFunctions.FuncMoveSpeedMul();

    private StatFunctions.FuncMoveSpeedMul() {
        super(Stats.RUN_SPEED, 32, null);
    }

    @Override
    public void calc(Env env) {
        env.value *= BaseStats.DEX.calcBonus(env.character);
    }
}
