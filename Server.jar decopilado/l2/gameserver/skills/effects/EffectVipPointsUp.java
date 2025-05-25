/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public class EffectVipPointsUp
extends Effect {
    private final int Dg;

    public EffectVipPointsUp(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
        this.Dg = effectTemplate.getParam().getInteger("vipPoints", 0);
    }

    @Override
    public void onStart() {
        if (!this._effected.isPlayer()) {
            return;
        }
        this._effected.getPlayer().updateVipPoints(this.Dg);
        super.onStart();
    }

    @Override
    public boolean onActionTime() {
        return false;
    }
}
