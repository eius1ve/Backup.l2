/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.FinishRotating;
import l2.gameserver.network.l2.s2c.StartRotating;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Formulas;
import l2.gameserver.templates.StatsSet;

public class PDam
extends Skill {
    private final boolean gb;
    private final boolean gc;
    private final boolean gd;
    private final boolean ge;
    private final boolean gf;

    public PDam(StatsSet statsSet) {
        super(statsSet);
        this.gb = statsSet.getBool("onCrit", false);
        this.gc = statsSet.getBool("directHp", false);
        this.gd = statsSet.getBool("turner", false);
        this.ge = statsSet.getBool("blow", false);
        this.gf = statsSet.getBool("staticDamage", false);
    }

    @Override
    public boolean isBlowSkill() {
        return this.ge;
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        boolean bl = creature.getChargedSoulShot() && this.isSSPossible();
        double d = this.getPower();
        for (Creature creature2 : list) {
            boolean bl2;
            Creature creature3;
            if (creature2 == null || creature2.isDead()) continue;
            if (this.gd && !creature2.isInvul()) {
                creature2.broadcastPacket(new StartRotating(creature2, creature2.getHeading(), 1, 65535));
                creature2.broadcastPacket(new FinishRotating(creature2, creature.getHeading(), 65535));
                creature2.setHeading(creature.getHeading());
                creature2.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1S_EFFECT_CAN_BE_FELT).addSkillName(this._displayId, this._displayLevel));
            }
            Creature creature4 = creature3 = (bl2 = creature2.checkReflectSkill(creature, this)) ? creature : creature2;
            if (this.gf) {
                creature3.reduceCurrentHp(d, creature, this, true, true, this.gc, true, false, false, false);
                return;
            }
            Formulas.AttackInfo attackInfo = Formulas.calcPhysDam(creature, creature3, this, false, this.ge, bl, this.gb);
            if (attackInfo.lethal_dmg > 0.0) {
                creature3.reduceCurrentHp(attackInfo.lethal_dmg, creature, this, true, true, false, false, false, false, false);
            }
            if (!attackInfo.miss || attackInfo.damage >= 1.0) {
                creature3.reduceCurrentHp(attackInfo.damage, creature, this, true, true, attackInfo.lethal ? false : this.gc, true, false, false, this.getPower() != 0.0);
            }
            if (!bl2) {
                creature3.doCounterAttack(this, creature, this.ge);
            }
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false, bl2);
        }
        if (this.isSuicideAttack()) {
            creature.doDie(null);
        } else if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }
}
