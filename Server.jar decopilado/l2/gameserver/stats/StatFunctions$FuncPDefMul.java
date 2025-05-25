/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncPDefMul
extends Func {
    static final StatFunctions.FuncPDefMul func = new StatFunctions.FuncPDefMul();

    private StatFunctions.FuncPDefMul() {
        super(Stats.POWER_DEFENCE, 32, null);
    }

    @Override
    public void calc(Env env) {
        env.value *= env.character.getLevelMod();
    }
}
