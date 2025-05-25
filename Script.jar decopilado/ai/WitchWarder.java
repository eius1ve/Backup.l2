/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import l2.gameserver.ai.Fighter;
import l2.gameserver.model.instances.NpcInstance;

public class WitchWarder
extends Fighter {
    private long f = 0L;
    private boolean I = false;
    private static final int aM = 180000;

    public WitchWarder(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return true;
        }
        if (this._def_think) {
            this.doTask();
            this.I = false;
            return true;
        }
        if (!this.I) {
            this.I = true;
            this.f = System.currentTimeMillis() + 180000L;
        }
        if (this.f != 0L && this.I && this.f < System.currentTimeMillis()) {
            npcInstance.deleteMe();
        }
        return super.thinkActive();
    }

    protected boolean randomWalk() {
        return false;
    }
}
