/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public class EffectChangeHairColor
extends Effect {
    private final int CV;

    public EffectChangeHairColor(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
        this.CV = effectTemplate.getParam().getInteger("hair_color", 0);
    }

    @Override
    public void onStart() {
        if (!this._effected.isPlayer()) {
            return;
        }
        this._effected.getPlayer().setHairColor(this.CV);
        this._effected.getPlayer().broadcastUserInfo(false, new UserInfoType[0]);
        super.onStart();
    }

    @Override
    public boolean onActionTime() {
        return false;
    }
}
