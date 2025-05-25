/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Formulas;
import l2.gameserver.stats.Stats;
import l2.gameserver.templates.StatsSet;

public class ManaDam
extends Skill {
    public ManaDam(StatsSet statsSet) {
        super(statsSet);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        int n = 0;
        if (this.isSSPossible()) {
            n = creature.getChargedSpiritShot();
        }
        for (Creature creature2 : list) {
            int n2;
            if (creature2 == null || creature2.isDead()) continue;
            double d = creature.getMAtk(creature2, this);
            if (n == 2) {
                d *= 4.0;
            } else if (n == 1) {
                d *= 2.0;
            }
            double d2 = creature2.getMDef(creature, this);
            double d3 = creature2.getMaxMp();
            double d4 = Math.sqrt(d) * this.getPower() * d3 / 108.0 / d2;
            if (Config.MDAM_CRIT_POSSIBLE && (n2 = Formulas.calcMCrit(creature, creature2, creature.getMagicCriticalRate(creature2, this))) != 0) {
                creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.MAGIC_CRITICAL_HIT));
                d4 *= creature.calcStat(Stats.MCRITICAL_DAMAGE, 4.0, creature2, this);
            }
            if (creature.isPlayer() && creature2.isPlayer() && d4 > 1.0) {
                d4 += d4 * (creature.calcStat(Stats.PVP_MAGIC_SKILL_DMG_BONUS, 1.0, null, null) - creature2.calcStat(Stats.PVP_MAGIC_SKILL_DEFENCE_BONUS, 1.0, null, null));
            }
            n2 = creature2.getLevel() - creature.getLevel();
            double d5 = creature2.calcStat(Stats.MAGIC_RESIST, creature, this) - creature.calcStat(Stats.MAGIC_POWER, creature2, this);
            double d6 = 4.0 * Math.max(1.0, (double)n2) * (1.0 + d5 / 100.0);
            if (Rnd.chance(d6)) {
                if (n2 > 9) {
                    d4 = 0.0;
                    var19_13 = new SystemMessage(SystemMsg.YOUR_ATTACK_HAS_FAILED);
                    var20_14 = (SystemMessage)new SystemMessage(SystemMsg.RESISTED_AGAINST_S1S_MAGIC).addName(creature2);
                    creature.sendPacket((IStaticPacket)var19_13);
                    creature2.sendPacket((IStaticPacket)var20_14);
                } else {
                    d4 /= 2.0;
                    var19_13 = new SystemMessage(SystemMsg.YOUR_ATTACK_HAS_FAILED);
                    var20_14 = (SystemMessage)new SystemMessage(SystemMsg.RESISTED_AGAINST_S1S_MAGIC).addName(creature2);
                    creature.sendPacket((IStaticPacket)var19_13);
                    creature2.sendPacket((IStaticPacket)var20_14);
                }
            }
            creature2.reduceCurrentMp(d4, creature, d4 > 0.0);
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }
}
