/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Mystic;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

public class WizardSaint
extends Mystic {
    private boolean m = false;
    private final int aN;
    private final NpcTemplate b;

    public WizardSaint(NpcInstance npcInstance) {
        super(npcInstance);
        this.b = NpcHolder.getInstance().getTemplate(npcInstance.getParameter("CreateOnePrivateOnAttack", 0));
        this.aN = npcInstance.getParameter("CreateOnePrivateOnAttackChance", 0);
    }

    protected void onEvtSpawn() {
        this.m = false;
        super.onEvtSpawn();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (Rnd.get((int)100) < this.aN && !this.m) {
            this.m = true;
            SimpleSpawner simpleSpawner = new SimpleSpawner(this.b);
            simpleSpawner.setLoc(Location.findPointToStay((GameObject)npcInstance, (int)100, (int)100));
            NpcInstance npcInstance2 = simpleSpawner.doSpawn(true);
            if (creature.isPet() || creature.isSummon()) {
                npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)Rnd.get((int)2, (int)100));
            }
            npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature.getPlayer(), (Object)Rnd.get((int)1, (int)100));
            simpleSpawner.stopRespawn();
        }
        super.onEvtAttacked(creature, n);
    }
}
