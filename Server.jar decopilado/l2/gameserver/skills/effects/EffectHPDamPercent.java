/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public class EffectHPDamPercent
extends Effect {
    public EffectHPDamPercent(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this._effected.isDead()) {
            return;
        }
        double d = (100.0 - this.calc()) * (double)this._effected.getMaxHp() / 100.0;
        d = Math.min(this._effected.getCurrentHp(), Math.max(0.0, d));
        this._effected.setCurrentHp(d, false);
    }

    @Override
    public boolean onActionTime() {
        return false;
    }
}
