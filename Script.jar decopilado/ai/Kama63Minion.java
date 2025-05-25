/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.World
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import java.util.concurrent.ScheduledFuture;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class Kama63Minion
extends Fighter {
    private static final int Y = 18571;
    private static final int Z = 25000;
    private long f = 0L;
    private NpcInstance c;
    private boolean q = false;
    ScheduledFuture<?> _dieTask = null;

    public Kama63Minion(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtSpawn() {
        this.c = this.a(18571);
        super.onEvtSpawn();
    }

    protected boolean thinkActive() {
        if (this.c == null) {
            this.c = this.a(18571);
        } else if (!this.q) {
            this.q = true;
            Functions.npcSayCustomMessage((NpcInstance)this.c, (String)"Kama63Boss", (Object[])new Object[0]);
            NpcInstance npcInstance = this.getActor();
            npcInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)this.c.getAggroList().getRandomHated(), (Object)Rnd.get((int)1, (int)100));
            this._dieTask = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new DieScheduleTimerTask(npcInstance, this.c)), 25000L);
        }
        return super.thinkActive();
    }

    private NpcInstance a(int n) {
        if (System.currentTimeMillis() < this.f) {
            return null;
        }
        this.f = System.currentTimeMillis() + 15000L;
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null) {
            return null;
        }
        for (NpcInstance npcInstance2 : World.getAroundNpc((GameObject)npcInstance)) {
            if (npcInstance2.getNpcId() != n) continue;
            return npcInstance2;
        }
        return null;
    }

    protected void onEvtDead(Creature creature) {
        this.q = false;
        if (this._dieTask != null) {
            this._dieTask.cancel(false);
            this._dieTask = null;
        }
        super.onEvtDead(creature);
    }

    public class DieScheduleTimerTask
    extends RunnableImpl {
        NpcInstance _minion = null;
        NpcInstance _master = null;

        public DieScheduleTimerTask(NpcInstance npcInstance, NpcInstance npcInstance2) {
            this._minion = npcInstance;
            this._master = npcInstance2;
        }

        public void runImpl() {
            if (this._master != null && this._minion != null && !this._master.isDead() && !this._minion.isDead()) {
                this._master.setCurrentHp(this._master.getCurrentHp() + this._minion.getCurrentHp() * 5.0, false);
            }
            Functions.npcSayCustomMessage((NpcInstance)this._minion, (String)"Kama63Minion", (Object[])new Object[0]);
            this._minion.doDie((Creature)this._minion);
        }
    }
}
