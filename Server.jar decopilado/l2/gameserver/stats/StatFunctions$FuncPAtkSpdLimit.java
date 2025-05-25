/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.Config;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncPAtkSpdLimit
extends Func {
    static final Func func = new StatFunctions.FuncPAtkSpdLimit();

    private StatFunctions.FuncPAtkSpdLimit() {
        super(Stats.POWER_ATTACK_SPEED, 256, null);
    }

    @Override
    public void calc(Env env) {
        env.value = Math.min((double)Config.LIM_PATK_SPD, env.value);
    }
}
