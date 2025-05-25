/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class Kama63Minion.DieScheduleTimerTask
extends RunnableImpl {
    NpcInstance _minion = null;
    NpcInstance _master = null;

    public Kama63Minion.DieScheduleTimerTask(NpcInstance npcInstance, NpcInstance npcInstance2) {
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
