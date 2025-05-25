/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.Creature;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncSDefInit
extends Func {
    static final Func func = new StatFunctions.FuncSDefInit();

    private StatFunctions.FuncSDefInit() {
        super(Stats.SHIELD_RATE, 1, null);
    }

    @Override
    public void calc(Env env) {
        Creature creature = env.character;
        env.value = creature.getTemplate().baseShldRate;
    }
}
