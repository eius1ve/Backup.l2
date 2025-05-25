/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.base.BaseStats;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMaxHpMul
extends Func {
    static final StatFunctions.FuncMaxHpMul func = new StatFunctions.FuncMaxHpMul();

    private StatFunctions.FuncMaxHpMul() {
        super(Stats.MAX_HP, 32, null);
    }

    @Override
    public void calc(Env env) {
        env.value *= BaseStats.CON.calcBonus(env.character);
    }
}
