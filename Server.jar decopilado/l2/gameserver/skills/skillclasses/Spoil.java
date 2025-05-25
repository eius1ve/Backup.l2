/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.stats.Formulas;
import l2.gameserver.templates.StatsSet;

public class Spoil
extends Skill {
    public Spoil(StatsSet statsSet) {
        super(statsSet);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        int n;
        if (!creature.isPlayer()) {
            return;
        }
        int n2 = this.isSSPossible() ? (this.isMagic() ? creature.getChargedSpiritShot() : (creature.getChargedSoulShot() ? 2 : 0)) : (n = 0);
        if (n > 0 && this.getPower() > 0.0) {
            creature.unChargeShots(false);
        }
        for (Creature creature2 : list) {
            if (creature2 == null || creature2.isDead()) continue;
            if (creature2.isMonster()) {
                if (((MonsterInstance)creature2).isSpoiled()) {
                    creature.sendPacket((IStaticPacket)SystemMsg.IT_HAS_ALREADY_BEEN_SPOILED);
                } else {
                    MonsterInstance monsterInstance = (MonsterInstance)creature2;
                    int n3 = monsterInstance.getLevel();
                    int n4 = n3 - this.getMagicLevel();
                    double d = Math.max(this.getActivateRate(), 80);
                    if (n4 > 8) {
                        d -= d * (double)(n4 - 8) * 9.0 / 100.0;
                    }
                    d *= (double)this.getMagicLevel() / (double)n3;
                    boolean bl = Rnd.chance(d = Math.max(Config.MINIMUM_SPOIL_RATE, Math.min(d, 99.0)));
                    if (bl && monsterInstance.setSpoiled((Player)creature)) {
                        creature.sendPacket((IStaticPacket)SystemMsg.THE_SPOIL_CONDITION_HAS_BEEN_ACTIVATED);
                    }
                }
            }
            if (this.getPower() > 0.0) {
                double d;
                if (this.isMagic()) {
                    d = Formulas.calcMagicDam(creature, creature2, this, n);
                } else {
                    Formulas.AttackInfo attackInfo = Formulas.calcPhysDam(creature, creature2, this, false, false, n > 0, false);
                    d = attackInfo.damage;
                    if (attackInfo.lethal_dmg > 0.0) {
                        creature2.reduceCurrentHp(attackInfo.lethal_dmg, creature, this, true, true, false, false, false, false, false);
                    }
                }
                creature2.reduceCurrentHp(d, creature, this, true, true, false, true, false, false, true);
                creature2.doCounterAttack(this, creature, false);
            }
            this.getEffects(creature, creature2, false, false);
            creature2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, creature, Math.max(this._effectPoint, 1));
        }
    }
}
