/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.conditions.ConditionPlayerState;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMultRegenResting
extends Func {
    static final StatFunctions.FuncMultRegenResting[] func = new StatFunctions.FuncMultRegenResting[Stats.NUM_STATS];

    static Func getFunc(Stats stats) {
        int n = stats.ordinal();
        if (func[n] == null) {
            StatFunctions.FuncMultRegenResting.func[n] = new StatFunctions.FuncMultRegenResting(stats);
        }
        return func[n];
    }

    private StatFunctions.FuncMultRegenResting(Stats stats) {
        super(stats, 48, null);
        this.setCondition(new ConditionPlayerState(ConditionPlayerState.CheckPlayerState.RESTING, true));
    }

    @Override
    public void calc(Env env) {
        env.value = env.character.isPlayer() && env.character.getLevel() <= 40 && ((Player)env.character).getClassId().getLevel() < 3 && this.stat == Stats.REGENERATE_HP_RATE ? (env.value *= 6.0) : (env.value *= 1.5);
    }
}
