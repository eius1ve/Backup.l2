/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.data.xml.holder.ClassLevelGainHolder;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMaxHpAdd
extends Func {
    static final StatFunctions.FuncMaxHpAdd func = new StatFunctions.FuncMaxHpAdd();

    private StatFunctions.FuncMaxHpAdd() {
        super(Stats.MAX_HP, 16, null);
    }

    @Override
    public void calc(Env env) {
        env.value = env.value + (env.character.isPlayer() ? ClassLevelGainHolder.getInstance().getHp(env.character.getPlayer().getClassId(), env.character.getLevel()) : env.character.getTemplate().baseHpMax);
    }
}
