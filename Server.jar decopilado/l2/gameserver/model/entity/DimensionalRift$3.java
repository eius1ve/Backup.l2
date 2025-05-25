/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.instancemanager.DimensionalRiftManager;
import l2.gameserver.model.Player;

class DimensionalRift.3
extends RunnableImpl {
    DimensionalRift.3() {
    }

    @Override
    public void runImpl() throws Exception {
        if (DimensionalRift.this.isCollapseStarted()) {
            return;
        }
        for (Player player : DimensionalRift.this.getParty().getPartyMembers()) {
            if (player == null || player.getReflection() != DimensionalRift.this) continue;
            DimensionalRiftManager.getInstance().teleportToWaitingRoom(player);
        }
        DimensionalRift.this.collapse();
    }
}
