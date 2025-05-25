/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.Config;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMaxCpLimit
extends Func {
    static final Func func = new StatFunctions.FuncMaxCpLimit();

    private StatFunctions.FuncMaxCpLimit() {
        super(Stats.MAX_CP, 256, null);
    }

    @Override
    public void calc(Env env) {
        env.value = Math.min((double)Config.LIM_MAX_CP, env.value);
    }
}
