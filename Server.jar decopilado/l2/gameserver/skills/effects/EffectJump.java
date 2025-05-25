/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.network.l2.s2c.ExPlayAnimation;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public class EffectJump
extends Effect {
    public EffectJump(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
    }

    @Override
    public boolean onActionTime() {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.getEffected() != null && this.getEffected().isPlayer() && !this.getEffected().isDead() && !this.getEffected().getPlayer().isSitting()) {
            this.getEffected().broadcastPacket(ExPlayAnimation.jump(this.getEffected().getPlayer()));
        }
    }
}
