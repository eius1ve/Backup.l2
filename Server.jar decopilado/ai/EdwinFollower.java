/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.lang.reference.HardReferences
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.World
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.commons.util.Rnd;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.NpcInstance;

public class EdwinFollower
extends DefaultAI {
    private static final int G = 32072;
    private static final int H = 200;
    private long f = 0L;
    private HardReference<? extends Creature> c = HardReferences.emptyRef();

    public EdwinFollower(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public boolean isGlobalAI() {
        return true;
    }

    protected boolean randomAnimation() {
        return false;
    }

    protected boolean randomWalk() {
        return false;
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        Creature creature = (Creature)this.c.get();
        if (creature == null) {
            if (System.currentTimeMillis() > this.f) {
                this.f = System.currentTimeMillis() + 15000L;
                for (NpcInstance npcInstance2 : World.getAroundNpc((GameObject)npcInstance)) {
                    if (npcInstance2.getNpcId() != 32072) continue;
                    this.c = npcInstance2.getRef();
                    return true;
                }
            }
        } else if (!npcInstance.isMoving()) {
            int n = creature.getX() + Rnd.get((int)400) - 200;
            int n2 = creature.getY() + Rnd.get((int)400) - 200;
            int n3 = creature.getZ();
            npcInstance.setRunning();
            npcInstance.moveToLocation(n, n2, n3, 0, true);
            return true;
        }
        return false;
    }

    protected void onEvtAttacked(Creature creature, int n) {
    }

    protected void onEvtAggression(Creature creature, int n) {
    }
}
