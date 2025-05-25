/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.conditions.ConditionPlayerState;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMultRegenRunning
extends Func {
    static final StatFunctions.FuncMultRegenRunning[] func = new StatFunctions.FuncMultRegenRunning[Stats.NUM_STATS];

    static Func getFunc(Stats stats) {
        int n = stats.ordinal();
        if (func[n] == null) {
            StatFunctions.FuncMultRegenRunning.func[n] = new StatFunctions.FuncMultRegenRunning(stats);
        }
        return func[n];
    }

    private StatFunctions.FuncMultRegenRunning(Stats stats) {
        super(stats, 48, null);
        this.setCondition(new ConditionPlayerState(ConditionPlayerState.CheckPlayerState.RUNNING, true));
    }

    @Override
    public void calc(Env env) {
        env.value *= 0.7;
    }
}
