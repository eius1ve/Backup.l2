/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.templates.StatsSet;

public class VitalityHeal
extends Skill {
    public VitalityHeal(StatsSet statsSet) {
        super(statsSet);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        int n = Config.ALT_VITALITY_LEVELS[4];
        double d = this._power;
        for (Creature creature2 : list) {
            if (creature2.isPlayer()) {
                Player player = creature2.getPlayer();
                double d2 = (double)(n / 100) * d;
                player.addVitality(d2);
            }
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
        }
        creature.getPlayer().sendUserInfo(true, UserInfoType.VITA_FAME);
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }
}
