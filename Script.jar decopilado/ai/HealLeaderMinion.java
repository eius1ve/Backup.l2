/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.ai.DefaultAI$Teleport
 *  l2.gameserver.ai.Priest
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.MinionInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.ai.Priest;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.MinionInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.utils.Location;

public class HealLeaderMinion
extends Priest {
    public HealLeaderMinion(NpcInstance npcInstance) {
        super(npcInstance);
        this.MAX_PURSUE_RANGE = 10000;
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return true;
        }
        if (this._def_think) {
            if (this.doTask()) {
                this.clearTasks();
            }
            return true;
        }
        Creature creature = this.a();
        if (creature == null) {
            return false;
        }
        if (npcInstance.getDistance((GameObject)creature) - creature.getColRadius() - npcInstance.getColRadius() > 200.0) {
            this.a(Location.findFrontPosition((GameObject)creature, (GameObject)npcInstance, (int)100, (int)150));
            return false;
        }
        if (!creature.isCurrentHpFull() && this.doTask()) {
            return this.createNewTask();
        }
        return false;
    }

    private void a(Location location) {
        NpcInstance npcInstance = this.getActor();
        npcInstance.setRunning();
        if (npcInstance.moveToLocation(location, 0, true)) {
            return;
        }
        this.clientStopMoving();
        this._pathfindFails = 0;
        npcInstance.broadcastPacketToOthers(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 2036, 1, 500, 600000L)});
        ThreadPoolManager.getInstance().schedule((Runnable)new DefaultAI.Teleport((DefaultAI)this, location), 500L);
    }

    protected boolean createNewTask() {
        this.clearTasks();
        NpcInstance npcInstance = this.getActor();
        Creature creature = this.a();
        if (npcInstance.isDead() || creature == null) {
            return false;
        }
        if (!creature.isCurrentHpFull()) {
            Skill skill = this._healSkills[Rnd.get((int)this._healSkills.length)];
            if ((double)skill.getAOECastRange() < npcInstance.getDistance((GameObject)creature)) {
                this.a(Location.findFrontPosition((GameObject)creature, (GameObject)npcInstance, (int)(skill.getAOECastRange() - 30), (int)(skill.getAOECastRange() - 10)));
            }
            this.addTaskBuff(creature, skill);
            return true;
        }
        return false;
    }

    private Creature a() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isMinion()) {
            return ((MinionInstance)npcInstance).getLeader();
        }
        return null;
    }

    protected void onIntentionAttack(Creature creature) {
    }
}
