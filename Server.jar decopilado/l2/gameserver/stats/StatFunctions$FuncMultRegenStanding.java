/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.conditions.ConditionPlayerState;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMultRegenStanding
extends Func {
    static final StatFunctions.FuncMultRegenStanding[] func = new StatFunctions.FuncMultRegenStanding[Stats.NUM_STATS];

    static Func getFunc(Stats stats) {
        int n = stats.ordinal();
        if (func[n] == null) {
            StatFunctions.FuncMultRegenStanding.func[n] = new StatFunctions.FuncMultRegenStanding(stats);
        }
        return func[n];
    }

    private StatFunctions.FuncMultRegenStanding(Stats stats) {
        super(stats, 48, null);
        this.setCondition(new ConditionPlayerState(ConditionPlayerState.CheckPlayerState.STANDING, true));
    }

    @Override
    public void calc(Env env) {
        env.value *= 1.1;
    }
}
