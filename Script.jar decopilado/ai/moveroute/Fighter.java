/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.moveroute;

import ai.moveroute.MoveRouteDefaultAI;
import l2.gameserver.model.instances.NpcInstance;

public class Fighter
extends MoveRouteDefaultAI {
    public Fighter(NpcInstance npcInstance) {
        super(npcInstance);
    }

    @Override
    protected boolean thinkActive() {
        return super.thinkActive() || this.defaultThinkBuff(2);
    }

    protected boolean createNewTask() {
        return this.defaultFightTask();
    }

    public int getRatePHYS() {
        return 30;
    }

    public int getRateDOT() {
        return 20;
    }

    public int getRateDEBUFF() {
        return 20;
    }

    public int getRateDAM() {
        return 15;
    }

    public int getRateSTUN() {
        return 30;
    }

    public int getRateBUFF() {
        return 10;
    }

    public int getRateHEAL() {
        return 20;
    }
}
