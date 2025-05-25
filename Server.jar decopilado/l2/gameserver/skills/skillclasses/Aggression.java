/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.StatsSet;

public class Aggression
extends Skill {
    private final boolean fO;
    private final boolean fP;

    public Aggression(StatsSet statsSet) {
        super(statsSet);
        this.fO = statsSet.getBool("unaggroing", false);
        this.fP = statsSet.getBool("silent", false);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        int n = this._effectPoint;
        if (this.isSSPossible() && (creature.getChargedSoulShot() || creature.getChargedSpiritShot() > 0)) {
            n *= 2;
        }
        for (Creature creature2 : list) {
            if (creature2 == null || !creature2.isAutoAttackable(creature)) continue;
            if (creature2.isNpc()) {
                if (this.fO) {
                    if (creature2.isNpc() && creature.isPlayable()) {
                        ((NpcInstance)creature2).getAggroList().addDamageHate(creature, 0, -n);
                    }
                } else {
                    creature2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, creature, n);
                    if (!this.fP) {
                        creature2.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, creature, 0);
                    }
                }
            } else if (creature2.isPlayable() && !creature2.isDebuffImmune()) {
                creature2.setTarget(creature);
            }
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }
}
