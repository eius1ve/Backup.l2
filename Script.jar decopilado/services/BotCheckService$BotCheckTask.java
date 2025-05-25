/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 */
package services;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import services.BotCheckService;

private static class BotCheckService.BotCheckTask
extends RunnableImpl {
    private final HardReference<Player> ae;

    private BotCheckService.BotCheckTask(Player player) {
        this.ae = player.getRef();
    }

    public void runImpl() {
        Player player = (Player)this.ae.get();
        if (player == null) {
            return;
        }
        BotCheckService.ao(player);
    }
}
