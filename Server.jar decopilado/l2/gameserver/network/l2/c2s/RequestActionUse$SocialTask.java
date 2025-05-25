/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;

static class RequestActionUse.SocialTask
extends RunnableImpl {
    Player _player;

    RequestActionUse.SocialTask(Player player) {
        this._player = player;
    }

    @Override
    public void runImpl() throws Exception {
        this._player.stopParalyzed();
    }
}
