/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;

public static class GameObjectTasks.EndSitDownTask
extends RunnableImpl {
    private final HardReference<Player> C;

    public GameObjectTasks.EndSitDownTask(Player player) {
        this.C = player.getRef();
    }

    @Override
    public void runImpl() {
        Player player = this.C.get();
        if (player == null) {
            return;
        }
        player.sittingTaskLaunched = false;
        player.getAI().clearNextAction();
    }
}
