/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Stats;
import l2.gameserver.templates.StatsSet;

public class ManaHeal
extends Skill {
    private final boolean fW;

    public ManaHeal(StatsSet statsSet) {
        super(statsSet);
        this.fW = statsSet.getBool("ignoreMpEff", false);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        int n;
        double d = this._power;
        int n2 = n = this.isSSPossible() ? creature.getChargedSpiritShot() : 0;
        if (n > 0 && Config.MANAHEAL_SPS_BONUS) {
            d *= n == 2 ? 1.5 : 1.3;
        }
        for (Creature creature2 : list) {
            int n3;
            double d2;
            if (creature2.isHealBlocked()) continue;
            double d3 = creature == creature2 ? d : (d2 = Math.min(d * 1.7, d * (!this.fW ? creature2.calcStat(Stats.MANAHEAL_EFFECTIVNESS, 100.0, creature, this) : 100.0) / 100.0));
            if (this.getMagicLevel() > 0 && creature != creature2 && (n3 = creature2.getLevel() - this.getMagicLevel()) > 5) {
                d2 = n3 < 20 ? d2 / 100.0 * (double)(100 - n3 * 5) : 0.0;
            }
            if (d2 == 0.0) {
                creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_FAILED).addSkillName(this._id, this.getDisplayLevel()));
                this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
                continue;
            }
            double d4 = Math.max(0.0, Math.min(d2, creature2.calcStat(Stats.MP_LIMIT, null, null) * (double)creature2.getMaxMp() / 100.0 - creature2.getCurrentMp()));
            if (d4 > 0.0) {
                creature2.setCurrentMp(d4 + creature2.getCurrentMp());
            }
            if (creature2.isPlayer()) {
                if (creature != creature2) {
                    creature2.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.S2_MP_HAS_BEEN_RESTORED_BY_C1).addString(creature.getName())).addNumber(Math.round(d4)));
                } else {
                    creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_MP_HAS_BEEN_RESTORED).addNumber(Math.round(d4)));
                }
            }
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }
}
