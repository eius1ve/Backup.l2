/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;
import npc.model.OrfenInstance;

public class Orfen
extends Fighter {
    private final String[] h = new String[]{"1000028", "1000029", "1000030", "1000031"};
    public final Skill[] _paralyze = this.getActor().getTemplate().getDebuffSkills();

    public Orfen(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean thinkActive() {
        if (super.thinkActive()) {
            return true;
        }
        OrfenInstance orfenInstance = this.getActor();
        if (orfenInstance.isTeleported() && orfenInstance.getCurrentHpPercents() > 95.0) {
            orfenInstance.setTeleported(false);
            return true;
        }
        return false;
    }

    protected boolean createNewTask() {
        return this.defaultNewTask();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        Skill skill;
        super.onEvtAttacked(creature, n);
        OrfenInstance orfenInstance = this.getActor();
        if (orfenInstance.isCastingNow()) {
            return;
        }
        double d = orfenInstance.getDistance((GameObject)creature);
        if (Config.ORFEN_USE_TELEPORT && d > 300.0 && d < 1000.0 && this._damSkills.length > 0 && Rnd.chance((int)10)) {
            Functions.npcSayCustomMessage((NpcInstance)orfenInstance, (String)this.h[Rnd.get((int)(this.h.length - 1))], (Object[])new Object[]{creature.getName()});
            this.a(creature, Location.findFrontPosition((GameObject)orfenInstance, (GameObject)creature, (int)0, (int)50));
            Skill skill2 = this._damSkills[Rnd.get((int)this._damSkills.length)];
            if (this.canUseSkill(skill2, creature, -1.0)) {
                this.addTaskAttack(creature, skill2, 1000000);
            }
        } else if (this._paralyze.length > 0 && Rnd.chance((int)20) && this.canUseSkill(skill = this._paralyze[Rnd.get((int)this._paralyze.length)], creature, -1.0)) {
            this.addTaskAttack(creature, skill, 1000000);
        }
    }

    protected void onEvtSeeSpell(Skill skill, Creature creature) {
        super.onEvtSeeSpell(skill, creature);
        OrfenInstance orfenInstance = this.getActor();
        if (orfenInstance.isCastingNow()) {
            return;
        }
        double d = orfenInstance.getDistance((GameObject)creature);
        if (Config.ORFEN_USE_TELEPORT && this._damSkills.length > 0 && skill.getEffectPoint() > 0 && d < 1000.0 && Rnd.chance((int)20)) {
            Functions.npcSayCustomMessage((NpcInstance)orfenInstance, (String)this.h[Rnd.get((int)this.h.length)], (Object[])new Object[]{creature.getName()});
            this.a(creature, Location.findFrontPosition((GameObject)orfenInstance, (GameObject)creature, (int)0, (int)50));
            Skill skill2 = this._damSkills[Rnd.get((int)this._damSkills.length)];
            if (this.canUseSkill(skill2, creature, -1.0)) {
                this.addTaskAttack(creature, skill2, 1000000);
            }
        }
    }

    public OrfenInstance getActor() {
        return (OrfenInstance)super.getActor();
    }

    private void a(Creature creature, Location location) {
        creature.teleToLocation(location);
    }
}
