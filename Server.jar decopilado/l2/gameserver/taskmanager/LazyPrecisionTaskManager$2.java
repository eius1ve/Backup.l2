/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.Player;

class LazyPrecisionTaskManager.2
extends RunnableImpl {
    final /* synthetic */ Player val$player;

    LazyPrecisionTaskManager.2(Player player) {
        this.val$player = player;
    }

    @Override
    public void runImpl() throws Exception {
        if (this.val$player.isInOfflineMode() || this.val$player.getLevel() < Config.ALT_PCBANG_POINTS_MIN_LVL || this.val$player.getPcBangPoints() >= Config.LIM_PC_BANG_POINTS) {
            return;
        }
        this.val$player.addPcBangPoints(Config.ALT_PCBANG_POINTS_BONUS, Config.ALT_PCBANG_POINTS_BONUS_DOUBLE_CHANCE > 0.0 && Rnd.chance(Config.ALT_PCBANG_POINTS_BONUS_DOUBLE_CHANCE));
    }
}
