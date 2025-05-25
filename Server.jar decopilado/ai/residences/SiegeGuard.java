/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.World
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package ai.residences;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public abstract class SiegeGuard
extends DefaultAI {
    public SiegeGuard(NpcInstance npcInstance) {
        super(npcInstance);
        this.MAX_PURSUE_RANGE = 1000;
    }

    public int getMaxPathfindFails() {
        return Integer.MAX_VALUE;
    }

    public int getMaxAttackTimeout() {
        return 0;
    }

    protected boolean randomWalk() {
        return false;
    }

    protected boolean randomAnimation() {
        return false;
    }

    public boolean canSeeInSilentMove(Playable playable) {
        return !playable.isSilentMoving() || Rnd.chance((int)10);
    }

    protected boolean isAggressive() {
        return true;
    }

    protected boolean isGlobalAggro() {
        return true;
    }

    protected void onEvtAggression(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return;
        }
        if (creature == null || !npcInstance.isAutoAttackable(creature)) {
            return;
        }
        super.onEvtAggression(creature, n);
    }

    protected boolean thinkActive() {
        Object object;
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isActionsDisabled()) {
            return true;
        }
        if (this._def_think) {
            if (this.doTask()) {
                this.clearTasks();
            }
            return true;
        }
        long l = System.currentTimeMillis();
        if (l - this._checkAggroTimestamp > (long)Config.AGGRO_CHECK_INTERVAL) {
            Creature creature;
            this._checkAggroTimestamp = l;
            object = World.getAroundCharacters((GameObject)npcInstance);
            while (!object.isEmpty() && (creature = this.getNearestTarget((List)object)) != null) {
                if (this.checkAggression(creature)) {
                    npcInstance.getAggroList().addDamageHate(creature, 0, 2);
                    if (creature.isSummon()) {
                        npcInstance.getAggroList().addDamageHate((Creature)creature.getPlayer(), 0, 1);
                    }
                    this.startRunningTask(this.AI_TASK_ATTACK_DELAY);
                    this.setIntention(CtrlIntention.AI_INTENTION_ATTACK, creature);
                    return true;
                }
                object.remove(creature);
            }
        }
        if (!npcInstance.isInRange((Location)(object = npcInstance.getSpawnedLoc()), 250L)) {
            this.teleportHome();
            return true;
        }
        return false;
    }

    protected Creature prepareTarget() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return null;
        }
        List list = npcInstance.getAggroList().getHateList(this.MAX_PURSUE_RANGE);
        Creature creature = null;
        for (Creature creature2 : list) {
            if (!this.checkTarget(creature2, this.MAX_PURSUE_RANGE)) {
                npcInstance.getAggroList().remove(creature2, true);
                continue;
            }
            creature = creature2;
            break;
        }
        if (creature != null) {
            this.setAttackTarget(creature);
            return creature;
        }
        return null;
    }

    protected boolean canAttackCharacter(Creature creature) {
        return this.getActor().isAutoAttackable(creature);
    }
}
