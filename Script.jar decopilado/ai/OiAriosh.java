/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public class OiAriosh
extends Fighter {
    private static final int ab = 18556;
    private int ac = 0;
    private static final int[] g = new int[]{80, 60, 40, 30, 20, 10, 5, -5};

    public OiAriosh(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (!npcInstance.isDead() && npcInstance.getCurrentHpPercents() < (double)g[this.ac]) {
            this.a(creature);
            ++this.ac;
        }
        super.onEvtAttacked(creature, n);
    }

    private void a(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        try {
            SimpleSpawner simpleSpawner = new SimpleSpawner(NpcHolder.getInstance().getTemplate(18556));
            simpleSpawner.setLoc(Location.findPointToStay((GameObject)npcInstance, (int)100, (int)120));
            simpleSpawner.setReflection(npcInstance.getReflection());
            NpcInstance npcInstance2 = simpleSpawner.doSpawn(true);
            npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)100);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void onEvtDead(Creature creature) {
        this.ac = 0;
        super.onEvtDead(creature);
    }
}
