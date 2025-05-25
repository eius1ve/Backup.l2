/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.model.Player;

public static class GameObjectTasks.PvPFlagTask
extends RunnableImpl {
    private final HardReference<Player> L;

    public GameObjectTasks.PvPFlagTask(Player player) {
        this.L = player.getRef();
    }

    @Override
    public void runImpl() {
        Player player = this.L.get();
        if (player == null) {
            return;
        }
        long l = Math.abs(System.currentTimeMillis() - player.getlastPvpAttack());
        if (l > (long)(Config.PVP_TIME + Config.PVP_BLINKING_UNFLAG_TIME)) {
            player.stopPvPFlag();
        } else if (l > (long)Config.PVP_TIME) {
            player.updatePvPFlag(2);
        } else {
            player.updatePvPFlag(1);
        }
    }
}
