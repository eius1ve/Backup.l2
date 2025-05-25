/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncAccuracyAdd
extends Func {
    static final StatFunctions.FuncAccuracyAdd func = new StatFunctions.FuncAccuracyAdd();

    private StatFunctions.FuncAccuracyAdd() {
        super(Stats.ACCURACY_COMBAT, 16, null);
    }

    @Override
    public void calc(Env env) {
        if (env.character.isPet()) {
            return;
        }
        env.value += Math.sqrt(env.character.getDEX()) * 6.0 + (double)env.character.getLevel();
        if (env.character.isSummon()) {
            env.value = env.value + (env.character.getLevel() < 60 ? 4.0 : 5.0);
        }
    }
}
