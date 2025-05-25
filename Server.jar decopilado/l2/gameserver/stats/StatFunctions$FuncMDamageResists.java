/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.Config;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Formulas;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMDamageResists
extends Func {
    static final StatFunctions.FuncMDamageResists func = new StatFunctions.FuncMDamageResists();

    private StatFunctions.FuncMDamageResists() {
        super(Stats.MAGIC_DAMAGE, 48, null);
    }

    @Override
    public void calc(Env env) {
        if (env.target.isRaid() && Math.abs(env.character.getLevel() - env.target.getLevel()) > Config.RAID_MAX_LEVEL_DIFF) {
            env.value = 1.0;
            return;
        }
        env.value = Formulas.calcDamageResists(env.skill, env.character, env.target, env.value);
    }
}
