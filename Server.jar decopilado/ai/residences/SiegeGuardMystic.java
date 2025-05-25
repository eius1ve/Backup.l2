/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.residences;

import ai.residences.SiegeGuard;
import l2.gameserver.model.instances.NpcInstance;

public class SiegeGuardMystic
extends SiegeGuard {
    public SiegeGuardMystic(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean createNewTask() {
        return this.defaultFightTask();
    }

    public int getRatePHYS() {
        return this._damSkills.length == 0 ? 25 : 0;
    }

    public int getRateDOT() {
        return 25;
    }

    public int getRateDEBUFF() {
        return 25;
    }

    public int getRateDAM() {
        return 100;
    }

    public int getRateSTUN() {
        return 10;
    }

    public int getRateBUFF() {
        return 10;
    }

    public int getRateHEAL() {
        return 20;
    }
}
