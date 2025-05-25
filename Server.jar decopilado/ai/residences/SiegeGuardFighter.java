/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.residences;

import ai.residences.SiegeGuard;
import l2.gameserver.model.instances.NpcInstance;

public class SiegeGuardFighter
extends SiegeGuard {
    public SiegeGuardFighter(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean createNewTask() {
        return this.defaultFightTask();
    }

    public int getRatePHYS() {
        return 25;
    }

    public int getRateDOT() {
        return 50;
    }

    public int getRateDEBUFF() {
        return 50;
    }

    public int getRateDAM() {
        return 75;
    }

    public int getRateSTUN() {
        return 50;
    }

    public int getRateBUFF() {
        return 10;
    }

    public int getRateHEAL() {
        return 50;
    }
}
