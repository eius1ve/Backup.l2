/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.model.Player;

public static class GameObjectTasks.EndStandUpTask
extends RunnableImpl {
    private final HardReference<Player> D;

    public GameObjectTasks.EndStandUpTask(Player player) {
        this.D = player.getRef();
    }

    @Override
    public void runImpl() {
        Player player = this.D.get();
        if (player == null) {
            return;
        }
        player.sittingTaskLaunched = false;
        player.setSitting(false);
        if (!player.getAI().setNextIntention()) {
            player.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
        }
    }
}
