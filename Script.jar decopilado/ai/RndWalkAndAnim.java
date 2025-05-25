/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.geodata.GeoEngine
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public class RndWalkAndAnim
extends DefaultAI {
    protected static final int PET_WALK_RANGE = 100;

    public RndWalkAndAnim(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isMoving()) {
            return false;
        }
        int n = Rnd.get((int)100);
        if (n < 10) {
            this.randomWalk();
        } else if (n < 20) {
            npcInstance.onRandomAnimation();
        }
        return false;
    }

    protected boolean randomWalk() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null) {
            return false;
        }
        Location location = npcInstance.getSpawnedLoc();
        int n = location.x + Rnd.get((int)200) - 100;
        int n2 = location.y + Rnd.get((int)200) - 100;
        int n3 = GeoEngine.getHeight((int)n, (int)n2, (int)location.z, (int)npcInstance.getGeoIndex());
        npcInstance.setRunning();
        npcInstance.moveToLocation(n, n2, n3, 0, true);
        return true;
    }

    protected void onEvtAttacked(Creature creature, int n) {
    }

    protected void onEvtAggression(Creature creature, int n) {
    }
}
