/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import java.util.function.BiFunction;
import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;

public class EffectServitorShare.FuncShare
extends Func {
    public EffectServitorShare.FuncShare(Stats stats, int n, Object object, double d) {
        super(stats, n, object, d);
    }

    @Override
    public void calc(Env env) {
        BiFunction<Player, Env, Integer> biFunction = bH.get((Object)this.stat);
        if (biFunction != null) {
            env.value += (double)biFunction.apply(env.character.getPlayer(), env).intValue() * this.value;
        }
    }
}
