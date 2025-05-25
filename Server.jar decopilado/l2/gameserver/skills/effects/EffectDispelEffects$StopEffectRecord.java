/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

private static final class EffectDispelEffects.StopEffectRecord {
    private final EffectTemplate a;
    private final int Db;
    private final long dE;
    private final long dF;

    public EffectDispelEffects.StopEffectRecord(EffectTemplate effectTemplate, int n, long l, long l2) {
        this.a = effectTemplate;
        this.Db = n;
        this.dE = l;
        this.dF = l2;
    }

    public EffectDispelEffects.StopEffectRecord(Effect effect) {
        this(effect.getTemplate(), effect.getCount(), effect.getTime(), effect.getPeriod());
    }

    public void apply(Skill skill, Player player) {
        Env env = new Env(player, player, skill);
        Effect effect = this.a.getEffect(env);
        if (effect == null || effect.isOneTime()) {
            return;
        }
        effect.setCount(this.Db);
        effect.setPeriod(this.Db == 1 ? this.dF - this.dE : this.dF);
        player.getEffectList().addEffect(effect);
    }
}
