/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.geodata.GeoEngine
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public class Elpy
extends Fighter {
    public Elpy(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature != null && Rnd.chance((int)50)) {
            Location location = Location.findPointToStay((GameObject)npcInstance, (int)150, (int)200);
            if (GeoEngine.canMoveToCoord((int)npcInstance.getX(), (int)npcInstance.getY(), (int)npcInstance.getZ(), (int)location.x, (int)location.y, (int)location.z, (int)npcInstance.getGeoIndex())) {
                npcInstance.setRunning();
                this.addTaskMove(location, false);
            }
        }
    }

    public boolean checkAggression(Creature creature) {
        return false;
    }

    protected void onEvtAggression(Creature creature, int n) {
    }
}
