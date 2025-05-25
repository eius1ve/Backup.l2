/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.Config;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncEvasionLimit
extends Func {
    static final Func func = new StatFunctions.FuncEvasionLimit();

    private StatFunctions.FuncEvasionLimit() {
        super(Stats.EVASION_RATE, 256, null);
    }

    @Override
    public void calc(Env env) {
        env.value = Math.min((double)Config.LIM_EVASION, env.value);
    }
}
