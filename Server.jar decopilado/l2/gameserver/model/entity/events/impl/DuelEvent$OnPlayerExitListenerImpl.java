/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.impl;

import l2.gameserver.listener.actor.player.OnPlayerExitListener;
import l2.gameserver.model.Player;

private class DuelEvent.OnPlayerExitListenerImpl
implements OnPlayerExitListener {
    private DuelEvent.OnPlayerExitListenerImpl() {
    }

    @Override
    public void onPlayerExit(Player player) {
        DuelEvent.this.playerExit(player);
    }
}
