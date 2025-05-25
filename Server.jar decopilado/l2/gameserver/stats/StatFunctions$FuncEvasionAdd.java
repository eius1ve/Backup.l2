/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncEvasionAdd
extends Func {
    static final StatFunctions.FuncEvasionAdd func = new StatFunctions.FuncEvasionAdd();

    private StatFunctions.FuncEvasionAdd() {
        super(Stats.EVASION_RATE, 16, null);
    }

    @Override
    public void calc(Env env) {
        env.value += Math.sqrt(env.character.getDEX()) * 6.0 + (double)env.character.getLevel();
    }
}
