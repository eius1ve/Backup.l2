/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Mystic;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public class SeerFlouros
extends Mystic {
    private int ac = 0;
    private static final int aj = 18560;
    private static final int ak = 2;
    private static final int[] i = new int[]{80, 60, 40, 30, 20, 10, 5, -5};

    public SeerFlouros(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (!npcInstance.isDead() && npcInstance.getCurrentHpPercents() < (double)i[this.ac]) {
            this.b(creature);
            ++this.ac;
        }
        super.onEvtAttacked(creature, n);
    }

    private void b(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        for (int i = 0; i < 2; ++i) {
            try {
                SimpleSpawner simpleSpawner = new SimpleSpawner(NpcHolder.getInstance().getTemplate(18560));
                simpleSpawner.setLoc(Location.findPointToStay((GameObject)npcInstance, (int)100, (int)120));
                simpleSpawner.setReflection(npcInstance.getReflection());
                NpcInstance npcInstance2 = simpleSpawner.doSpawn(true);
                npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)100);
                continue;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    protected void onEvtDead(Creature creature) {
        this.ac = 0;
        super.onEvtDead(creature);
    }
}
