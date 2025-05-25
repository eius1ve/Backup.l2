/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.objects;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Player;

class DuelSnapshotObject.1
extends RunnableImpl {
    DuelSnapshotObject.1() {
    }

    @Override
    public void runImpl() throws Exception {
        Player player = DuelSnapshotObject.this.getPlayer();
        if (player == null) {
            return;
        }
        player.teleToLocation(DuelSnapshotObject.this.K, ReflectionManager.DEFAULT);
    }
}
