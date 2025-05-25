/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.model.Player;

class AutoSaveManager.2
extends RunnableImpl {
    final /* synthetic */ Player val$player;

    AutoSaveManager.2(Player player) {
        this.val$player = player;
    }

    @Override
    public void runImpl() throws Exception {
        if (!this.val$player.isOnline()) {
            return;
        }
        this.val$player.store(true);
        if (Config.AUTOSAVE_ITEMS) {
            this.val$player.getInventory().store();
        }
    }
}
