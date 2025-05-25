/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.geodata.GeoEngine
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.Mystic;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class PaganGuard
extends Mystic {
    public PaganGuard(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.startImmobilized();
    }

    protected boolean canSeeInSilentMove(Playable playable) {
        if (playable.isSilentMoving()) {
            return this.getActor().getParameter("canSeeInSilentMove", true);
        }
        return true;
    }

    public boolean checkAggression(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return false;
        }
        if (this.getIntention() != CtrlIntention.AI_INTENTION_ACTIVE || !this.isGlobalAggro()) {
            return false;
        }
        if (creature.isAlikeDead() || !creature.isPlayable()) {
            return false;
        }
        if (!creature.isInRangeZ(npcInstance.getSpawnedLoc(), (long)npcInstance.getAggroRange())) {
            return false;
        }
        if (creature.isPlayable() && !this.canSeeInSilentMove((Playable)creature)) {
            return false;
        }
        if (npcInstance.getNpcId() == 18343 && (Functions.getItemCount((Playable)((Playable)creature), (int)8067) != 0L || Functions.getItemCount((Playable)((Playable)creature), (int)8064) != 0L)) {
            return false;
        }
        if (!GeoEngine.canSeeTarget((GameObject)npcInstance, (GameObject)creature, (boolean)false)) {
            return false;
        }
        if (this.getIntention() != CtrlIntention.AI_INTENTION_ATTACK) {
            npcInstance.getAggroList().addDamageHate(creature, 0, 1);
            this.setIntention(CtrlIntention.AI_INTENTION_ATTACK, creature);
        }
        return true;
    }

    protected boolean randomWalk() {
        return false;
    }
}
