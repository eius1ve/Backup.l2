/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;

class AdminDisconnect.1
extends RunnableImpl {
    final /* synthetic */ Player val$player;

    AdminDisconnect.1(Player player) {
        this.val$player = player;
    }

    @Override
    public void runImpl() throws Exception {
        this.val$player.kick();
    }
}
