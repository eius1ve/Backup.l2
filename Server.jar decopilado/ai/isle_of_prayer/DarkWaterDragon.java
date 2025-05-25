/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package ai.isle_of_prayer;

import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public class DarkWaterDragon
extends Fighter {
    private int aP = 0;
    private static final int aQ = 18482;
    private static final int aR = 22268;
    private static final int aS = 22269;
    private static final int[] MOBS = new int[]{22268, 22269};
    private static final int aT = 5;
    private static final int aU = 9596;

    public DarkWaterDragon(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (!npcInstance.isDead()) {
            switch (this.aP) {
                case 0: {
                    this.aP = 1;
                    this.d(creature);
                    break;
                }
                case 1: {
                    if (!(npcInstance.getCurrentHp() < (double)(npcInstance.getMaxHp() / 2))) break;
                    this.aP = 2;
                    this.d(creature);
                }
            }
        }
        super.onEvtAttacked(creature, n);
    }

    private void d(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        for (int i = 0; i < 5; ++i) {
            try {
                SimpleSpawner simpleSpawner = new SimpleSpawner(NpcHolder.getInstance().getTemplate(MOBS[Rnd.get((int)MOBS.length)]));
                simpleSpawner.setLoc(Location.findPointToStay((GameObject)npcInstance, (int)100, (int)120));
                NpcInstance npcInstance2 = simpleSpawner.doSpawn(true);
                npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)Rnd.get((int)1, (int)100));
                continue;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    protected void onEvtDead(Creature creature) {
        SimpleSpawner simpleSpawner;
        this.aP = 0;
        NpcInstance npcInstance = this.getActor();
        try {
            simpleSpawner = new SimpleSpawner(NpcHolder.getInstance().getTemplate(18482));
            simpleSpawner.setLoc(Location.findPointToStay((GameObject)npcInstance, (int)100, (int)120));
            simpleSpawner.doSpawn(true);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (creature != null && (simpleSpawner = creature.getPlayer()) != null && Rnd.chance((int)77)) {
            npcInstance.dropItem((Player)simpleSpawner, 9596, 1L);
        }
        super.onEvtDead(creature);
    }

    protected boolean randomWalk() {
        return this.aP == 0;
    }
}
