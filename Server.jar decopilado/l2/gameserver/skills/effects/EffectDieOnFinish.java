/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public class EffectDieOnFinish
extends Effect {
    public EffectDieOnFinish(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
    }

    @Override
    public boolean onActionTime() {
        if (this._effected.isDead()) {
            return false;
        }
        if (this.getTimeLeft() < 1) {
            this._effected.doDie(this._effected);
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onExit() {
        super.onExit();
    }
}
