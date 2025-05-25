/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.voicecommands.impl;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.utils.Location;

static class Wedding.EscapeFinalizer
extends RunnableImpl {
    private Player c;
    private Location _loc;

    Wedding.EscapeFinalizer(Player player, Location location) {
        this.c = player;
        this._loc = location;
    }

    @Override
    public void runImpl() throws Exception {
        this.c.stopParalyzed();
        if (this.c.isDead()) {
            return;
        }
        this.c.teleToLocation(this._loc);
    }
}
