/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.Config;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncRunSpdLimit
extends Func {
    static final Func func = new StatFunctions.FuncRunSpdLimit();

    private StatFunctions.FuncRunSpdLimit() {
        super(Stats.RUN_SPEED, 256, null);
    }

    @Override
    public void calc(Env env) {
        env.value = env.character.getPlayer() != null && env.character.getPlayer().isGM() ? Math.min((double)Config.LIM_MOVE_GM, env.value) : Math.min((double)Config.LIM_MOVE, env.value);
    }
}
