/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public class EffectRelax
extends Effect {
    private boolean fK;

    public EffectRelax(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
    }

    @Override
    public boolean checkCondition() {
        Player player = this._effected.getPlayer();
        if (player == null) {
            return false;
        }
        if (player.isMounted()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this._skill.getId(), this._skill.getLevel()));
            return false;
        }
        return super.checkCondition();
    }

    @Override
    public void onStart() {
        super.onStart();
        Player player = this._effected.getPlayer();
        if (player.isMoving()) {
            player.stopMove();
        }
        this.fK = player.isSitting();
        player.sitDown(null);
    }

    @Override
    public void onExit() {
        super.onExit();
        Skill skill = this.getSkill();
        if (skill != null) {
            for (Effect effect : this._effected.getEffectList().getEffectsBySkill(skill)) {
                if (effect == this) continue;
                effect.exit();
            }
        }
        if (!this.fK) {
            this._effected.getPlayer().standUp();
        }
    }

    @Override
    public boolean onActionTime() {
        Player player = this._effected.getPlayer();
        if (player.isAlikeDead() || player == null) {
            return false;
        }
        if (!player.isSitting()) {
            return false;
        }
        if (player.isCurrentHpFull() && this.getSkill().isToggle()) {
            this.getEffected().sendPacket((IStaticPacket)SystemMsg.THAT_SKILL_HAS_BEEN_DEACTIVATED_AS_HP_WAS_FULLY_RECOVERED);
            return false;
        }
        double d = this.calc();
        if (d > this._effected.getCurrentMp() && this.getSkill().isToggle()) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.NOT_ENOUGH_MP, new SystemMessage(SystemMsg.THE_EFFECT_OF_S1_HAS_BEEN_REMOVED).addSkillName(this.getSkill().getId(), this.getSkill().getDisplayLevel())});
            return false;
        }
        this._effected.reduceCurrentMp(d, null);
        return true;
    }
}
