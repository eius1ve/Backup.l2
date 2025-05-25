/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;

private class Zone.SkillTimer
extends Zone.ZoneTimer {
    public Zone.SkillTimer(Creature creature) {
        super(Zone.this, creature);
    }

    @Override
    public void runImpl() throws Exception {
        if (!Zone.this.isActive()) {
            return;
        }
        if (!Zone.this.checkTarget(this.cha)) {
            return;
        }
        Skill skill = Zone.this.getZoneSkill();
        if (skill == null) {
            return;
        }
        if (Rnd.chance(Zone.this.getTemplate().getSkillProb()) && !this.cha.isDead()) {
            skill.getEffects(this.cha, this.cha, false, false);
        }
        this.next();
    }
}
