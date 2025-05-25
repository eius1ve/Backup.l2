/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.data.xml.holder.ClassLevelGainHolder;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMaxCpAdd
extends Func {
    static final StatFunctions.FuncMaxCpAdd func = new StatFunctions.FuncMaxCpAdd();

    private StatFunctions.FuncMaxCpAdd() {
        super(Stats.MAX_CP, 16, null);
    }

    @Override
    public void calc(Env env) {
        env.value = env.value + (env.character.isPlayer() ? ClassLevelGainHolder.getInstance().getCp(env.character.getPlayer().getClassId(), env.character.getLevel()) : env.character.getTemplate().baseCpMax);
    }
}
