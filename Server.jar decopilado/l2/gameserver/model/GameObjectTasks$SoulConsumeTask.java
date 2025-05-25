/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;

public static class GameObjectTasks.SoulConsumeTask
extends RunnableImpl {
    private final HardReference<Player> M;

    public GameObjectTasks.SoulConsumeTask(Player player) {
        this.M = player.getRef();
    }

    @Override
    public void runImpl() {
        Player player = this.M.get();
        if (player == null) {
            return;
        }
        player.setConsumedSouls(player.getConsumedSouls() + 1, null);
    }
}
