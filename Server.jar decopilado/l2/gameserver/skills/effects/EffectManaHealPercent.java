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
import l2.gameserver.stats.Stats;

public class EffectManaHealPercent
extends Effect {
    private final boolean fJ;

    public EffectManaHealPercent(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
        this.fJ = effectTemplate.getParam().getBool("ignoreMpEff", true);
    }

    @Override
    public boolean checkCondition() {
        if (this._effected.isHealBlocked()) {
            return false;
        }
        return super.checkCondition();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this._effected.isHealBlocked()) {
            return;
        }
        double d = this.calc() * (double)this._effected.getMaxMp() / 100.0;
        double d2 = d * (!this.fJ ? this._effected.calcStat(Stats.MANAHEAL_EFFECTIVNESS, 100.0, this._effector, this.getSkill()) : 100.0) / 100.0;
        double d3 = Math.max(0.0, Math.min(d2, this._effected.calcStat(Stats.MP_LIMIT, null, null) * (double)this._effected.getMaxMp() / 100.0 - this._effected.getCurrentMp()));
        this._effected.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_MP_HAS_BEEN_RESTORED).addNumber(Math.round(d3)));
        if (d3 > 0.0) {
            this._effected.setCurrentMp(d3 + this._effected.getCurrentMp());
        }
    }

    @Override
    public boolean onActionTime() {
        return false;
    }
}
