/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncHennaDEX
extends Func {
    static final StatFunctions.FuncHennaDEX func = new StatFunctions.FuncHennaDEX();

    private StatFunctions.FuncHennaDEX() {
        super(Stats.STAT_DEX, 16, null);
    }

    @Override
    public void calc(Env env) {
        Player player = (Player)env.character;
        if (player != null) {
            env.value = Math.max(1.0, env.value + (double)player.getHennaStatDEX());
        }
    }
}
