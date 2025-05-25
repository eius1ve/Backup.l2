/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.residences;

import ai.residences.SiegeGuard;
import l2.gameserver.model.instances.NpcInstance;

public class SiegeGuardPriest
extends SiegeGuard {
    public SiegeGuardPriest(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean createNewTask() {
        return this.defaultFightTask();
    }

    public int getRatePHYS() {
        return this._damSkills.length == 0 ? 25 : 0;
    }

    public int getRateDOT() {
        return 35;
    }

    public int getRateDEBUFF() {
        return 50;
    }

    public int getRateDAM() {
        return 60;
    }

    public int getRateSTUN() {
        return 10;
    }

    public int getRateBUFF() {
        return 25;
    }

    public int getRateHEAL() {
        return 90;
    }
}
