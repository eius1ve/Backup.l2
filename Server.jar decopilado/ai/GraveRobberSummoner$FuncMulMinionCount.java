/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.stats.Env
 *  l2.gameserver.stats.Stats
 *  l2.gameserver.stats.funcs.Func
 */
package ai;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private class GraveRobberSummoner.FuncMulMinionCount
extends Func {
    public GraveRobberSummoner.FuncMulMinionCount(Stats stats, int n, Object object) {
        super(stats, n, object);
    }

    public void calc(Env env) {
        env.value *= (double)GraveRobberSummoner.this.R;
    }
}
