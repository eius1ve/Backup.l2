/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;

public static class GameObjectTasks.KickTask
extends RunnableImpl {
    private final HardReference<Player> H;

    public GameObjectTasks.KickTask(Player player) {
        this.H = player.getRef();
    }

    @Override
    public void runImpl() {
        Player player = this.H.get();
        if (player == null) {
            return;
        }
        player.setOfflineMode(false);
        player.kick();
    }
}
