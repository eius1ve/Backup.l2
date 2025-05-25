/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.stats.Env
 *  l2.gameserver.stats.Stats
 *  l2.gameserver.stats.funcs.Func
 */
package services;

import java.util.List;
import java.util.Map;
import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import services.StatModifier;

class StatModifier.4
extends Func {
    final /* synthetic */ Map val$lookup2;

    StatModifier.4(Stats stats, int n, Object object, Map map) {
        this.val$lookup2 = map;
        super(stats, n, object);
    }

    public void calc(Env env) {
        if (env.character == null || !env.character.isPlayer()) {
            return;
        }
        Player player = (Player)env.character;
        List list = (List)this.val$lookup2.get(player.getActiveClassId());
        if (list == null || list.isEmpty()) {
            return;
        }
        double d = 1.0;
        double d2 = 0.0;
        for (StatModifier.StatMod statMod : list) {
            if (!statMod.test(player, env.target)) continue;
            d += statMod.getMul() - 1.0;
            d2 += statMod.getAdd();
        }
        env.value *= d;
        env.value += d2;
    }
}
