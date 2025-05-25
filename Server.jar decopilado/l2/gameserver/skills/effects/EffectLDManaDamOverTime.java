/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public class EffectLDManaDamOverTime
extends Effect {
    public EffectLDManaDamOverTime(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
    }

    @Override
    public boolean onActionTime() {
        if (this._effected.isDead()) {
            return false;
        }
        double d = this.calc();
        if ((d *= (double)this._effected.getLevel() / 2.4) > this._effected.getCurrentMp() && this.getSkill().isToggle()) {
            this._effected.sendPacket((IStaticPacket)SystemMsg.NOT_ENOUGH_MP);
            this._effected.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_EFFECT_OF_S1_HAS_BEEN_REMOVED).addSkillName(this.getSkill().getId(), this.getSkill().getDisplayLevel()));
            return false;
        }
        this._effected.reduceCurrentMp(d, null);
        return true;
    }
}
