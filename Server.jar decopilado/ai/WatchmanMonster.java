/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.lang.reference.HardReferences
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class WatchmanMonster
extends Fighter {
    private long D = 0L;
    private boolean H = false;
    private HardReference<? extends Creature> e = HardReferences.emptyRef();
    static final String[] flood = new String[]{"1000026", "1000012"};
    static final String[] flood2 = new String[]{"1010484", "1010630"};

    public WatchmanMonster(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature != null && !npcInstance.getFaction().isNone() && npcInstance.getCurrentHpPercents() < 50.0 && this.D < System.currentTimeMillis() - 15000L) {
            this.D = System.currentTimeMillis();
            this.e = creature.getRef();
            if (this.b()) {
                return;
            }
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1010484", (Object[])new Object[0]);
        }
        super.onEvtAttacked(creature, n);
    }

    private boolean b() {
        this.H = false;
        NpcInstance npcInstance = this.getActor();
        Creature creature = (Creature)this.e.get();
        if (creature == null) {
            return false;
        }
        for (NpcInstance npcInstance2 : npcInstance.getAroundNpc(1000, 150)) {
            if (npcInstance.isDead() || !npcInstance2.isInFaction(npcInstance) || npcInstance2.isInCombat()) continue;
            this.clearTasks();
            this.H = true;
            this.addTaskMove(npcInstance2.getLoc(), true);
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)flood[Rnd.get((int)flood.length)], (Object[])new Object[0]);
            return true;
        }
        return false;
    }

    protected void onEvtDead(Creature creature) {
        this.D = 0L;
        this.e = HardReferences.emptyRef();
        this.H = false;
        super.onEvtDead(creature);
    }

    protected void onEvtArrived() {
        NpcInstance npcInstance = this.getActor();
        if (this.H) {
            Creature creature = (Creature)this.e.get();
            if (creature != null) {
                Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)flood2[Rnd.get((int)flood2.length)], (Object[])new Object[0]);
                this.notifyFriends(creature, 100);
            }
            this.H = false;
            this.notifyEvent(CtrlEvent.EVT_AGGRESSION, creature, 100);
        } else {
            super.onEvtArrived();
        }
    }

    protected void onEvtAggression(Creature creature, int n) {
        if (!this.H) {
            super.onEvtAggression(creature, n);
        }
    }
}
