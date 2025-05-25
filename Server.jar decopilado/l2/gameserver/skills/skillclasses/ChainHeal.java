/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Stats;
import l2.gameserver.templates.StatsSet;

public class ChainHeal
extends Skill {
    private static final int[] bf = new int[]{30, 27, 24, 21, 18, 15, 12, 10, 10, 10, 10};

    public ChainHeal(StatsSet statsSet) {
        super(statsSet);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        int n = 0;
        for (Creature creature2 : list) {
            if (creature2 == null) continue;
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
            double d = (double)(bf[n] * creature2.getMaxHp()) / 100.0;
            double d2 = Math.max(0.0, Math.min(d, creature2.calcStat(Stats.HP_LIMIT, null, null) * (double)creature2.getMaxHp() / 100.0 - creature2.getCurrentHp()));
            if (d2 > 0.0) {
                creature2.setCurrentHp(d2 + creature2.getCurrentHp(), false);
            }
            if (creature2.isPlayer()) {
                if (creature != creature2) {
                    creature2.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.S2_HP_HAS_BEEN_RESTORED_BY_C1).addString(creature.getName())).addNumber(Math.round(d2)));
                } else {
                    creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HP_HAS_BEEN_RESTORED).addNumber(Math.round(d2)));
                }
            }
            ++n;
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        if (creature.isPlayer() && creature2 != null && creature2.isAutoAttackable(creature)) {
            creature.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return false;
        }
        return super.checkCondition(creature, creature2, bl, bl2, bl3);
    }

    @Override
    public SystemMsg checkTarget(Creature creature, Creature creature2, Creature creature3, boolean bl, boolean bl2) {
        SystemMsg systemMsg = super.checkTarget(creature, creature2, creature3, bl, bl2);
        if (systemMsg == null && creature.isPlayer() && creature2 != null && creature2.isAutoAttackable(creature)) {
            systemMsg = SystemMsg.INVALID_TARGET;
        }
        return systemMsg;
    }
}
