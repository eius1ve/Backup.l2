/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.Player;
import l2.gameserver.model.base.BaseStats;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncMaxCpMul
extends Func {
    static final StatFunctions.FuncMaxCpMul func = new StatFunctions.FuncMaxCpMul();

    private StatFunctions.FuncMaxCpMul() {
        super(Stats.MAX_CP, 32, null);
    }

    @Override
    public void calc(Env env) {
        double d = 1.0;
        int n = SevenSigns.getInstance().getSealOwner(3);
        int n2 = SevenSigns.getInstance().getPlayerCabal((Player)env.character);
        if (n != 0) {
            d = n2 == n ? 1.1 : 0.9;
        }
        env.value *= BaseStats.CON.calcBonus(env.character) * d;
    }
}
