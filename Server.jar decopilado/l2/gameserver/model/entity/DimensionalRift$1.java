/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

import l2.commons.threading.RunnableImpl;

class DimensionalRift.1
extends RunnableImpl {
    DimensionalRift.1() {
    }

    @Override
    public void runImpl() throws Exception {
        if (DimensionalRift.this.jumps_current < DimensionalRift.this.getMaxJumps() && DimensionalRift.this.getPlayersInside(true) > 0) {
            ++DimensionalRift.this.jumps_current;
            DimensionalRift.this.teleportToNextRoom();
            DimensionalRift.this.createTeleporterTimer();
        } else {
            DimensionalRift.this.createNewKillRiftTimer();
        }
    }
}
