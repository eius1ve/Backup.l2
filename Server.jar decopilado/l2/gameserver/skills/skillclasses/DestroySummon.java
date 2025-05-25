/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.templates.StatsSet;

public class DestroySummon
extends Skill {
    public DestroySummon(StatsSet statsSet) {
        super(statsSet);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        for (Creature creature2 : list) {
            if (creature2 == null || !creature2.isSummon() || !Rnd.chance(this.getActivateRate())) continue;
            if (Config.ALT_SAVE_SERVITOR_BUFF) {
                ((Summon)creature2).saveEffects();
            }
            ((Summon)creature2).unSummon();
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false, false);
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }
}
