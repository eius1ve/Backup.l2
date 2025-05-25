/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncHennaSTR
extends Func {
    static final StatFunctions.FuncHennaSTR func = new StatFunctions.FuncHennaSTR();

    private StatFunctions.FuncHennaSTR() {
        super(Stats.STAT_STR, 16, null);
    }

    @Override
    public void calc(Env env) {
        Player player = (Player)env.character;
        if (player != null) {
            env.value = Math.max(1.0, env.value + (double)player.getHennaStatSTR());
        }
    }
}
