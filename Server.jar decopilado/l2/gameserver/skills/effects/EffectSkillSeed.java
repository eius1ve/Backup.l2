/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public final class EffectSkillSeed
extends Effect {
    private int De = 1;

    public EffectSkillSeed(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
    }

    public void incSeeds() {
        ++this.De;
    }

    public int getSeeds() {
        return this.De;
    }

    @Override
    public boolean onActionTime() {
        return false;
    }
}
