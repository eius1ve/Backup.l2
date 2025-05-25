/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.model.Player;

class LazyPrecisionTaskManager.3
extends RunnableImpl {
    final /* synthetic */ Player val$player;

    LazyPrecisionTaskManager.3(Player player) {
        this.val$player = player;
    }

    @Override
    public void runImpl() throws Exception {
        if (this.val$player.isInOfflineMode() || !this.val$player.isInPeaceZone()) {
            return;
        }
        this.val$player.setVitality(this.val$player.getVitality() + (double)Config.ALT_VITALITY_POINTS_PER_MINUTE);
    }
}
