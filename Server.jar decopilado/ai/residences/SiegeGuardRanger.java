/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.residences;

import ai.residences.SiegeGuard;
import l2.gameserver.model.instances.NpcInstance;

public class SiegeGuardRanger
extends SiegeGuard {
    public SiegeGuardRanger(NpcInstance npcInstance) {
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
        return 25;
    }

    public int getRateDAM() {
        return 75;
    }

    public int getRateSTUN() {
        return 75;
    }

    public int getRateBUFF() {
        return 5;
    }

    public int getRateHEAL() {
        return 50;
    }
}
