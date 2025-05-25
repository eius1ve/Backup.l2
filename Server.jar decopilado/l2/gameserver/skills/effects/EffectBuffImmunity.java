/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public final class EffectBuffImmunity
extends Effect {
    private final boolean fy;
    private final boolean fz;
    private final boolean fA;

    public EffectBuffImmunity(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
        this.fy = effectTemplate.getParam().getBool("ignoreSelfBuff", true);
        this.fz = effectTemplate.getParam().getBool("ignorePartyBuff", true);
        this.fA = effectTemplate.getParam().getBool("ignoreClanBuff", true);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.getEffected().startBuffImmunity();
    }

    @Override
    public void onExit() {
        this.getEffected().stopBuffImmunity();
        super.onExit();
    }

    @Override
    public boolean onActionTime() {
        if (this._effected.isDead()) {
            return false;
        }
        return this.getSkill().isToggle();
    }

    public boolean isIgnoreSelfBuff() {
        return this.fy;
    }

    public boolean isIgnorePartyBuff() {
        return this.fz;
    }

    public boolean isIgnoreClanBuff() {
        return this.fA;
    }
}
