/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.SummonInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.stats.Formulas;
import l2.gameserver.templates.StatsSet;

public class MDam
extends Skill {
    public MDam(StatsSet statsSet) {
        super(statsSet);
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        if (!(this._targetType != Skill.SkillTargetType.TARGET_AREA_AIM_CORPSE || creature2 != null && creature2.isDead() && (creature2.isNpc() || creature2.isSummon()))) {
            creature.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return false;
        }
        return super.checkCondition(creature, creature2, bl, bl2, bl3);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        int n = this.isSSPossible() ? (this.isMagic() ? creature.getChargedSpiritShot() : (creature.getChargedSoulShot() ? 2 : 0)) : 0;
        Object object = list.iterator();
        while (object.hasNext()) {
            Creature creature2 = object.next();
            if (creature2 == null || creature2.isDead()) continue;
            boolean bl = creature2.checkReflectSkill(creature, this);
            Creature creature3 = bl ? creature : creature2;
            double d = Formulas.calcMagicDam(creature, creature3, this, n);
            if (d >= 1.0) {
                creature3.reduceCurrentHp(d, creature, this, true, true, false, true, false, false, true);
            }
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false, bl);
        }
        if (this.isSuicideAttack()) {
            creature.doDie(null);
        } else if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
        if (this._targetType == Skill.SkillTargetType.TARGET_AREA_AIM_CORPSE && list.size() > 0 && (object = list.get(0)) != null && ((Creature)object).isDead()) {
            if (((GameObject)object).isNpc()) {
                ((NpcInstance)object).endDecayTask();
            } else if (((GameObject)object).isSummon()) {
                ((SummonInstance)object).endDecayTask();
            }
            creature.getAI().setAttackTarget(null);
        }
    }
}
