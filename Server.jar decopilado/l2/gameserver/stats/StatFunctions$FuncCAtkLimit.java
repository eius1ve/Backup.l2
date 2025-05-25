/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.Config;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncCAtkLimit
extends Func {
    static final Func func = new StatFunctions.FuncCAtkLimit();

    private StatFunctions.FuncCAtkLimit() {
        super(Stats.CRITICAL_DAMAGE, 256, null);
    }

    @Override
    public void calc(Env env) {
        env.value = Math.min((double)Config.LIM_CRIT_DAM / 2.0, env.value);
    }
}
