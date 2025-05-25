/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.utils.Location
 */
package events.TvT2;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.utils.Location;

private class PvPEvent.TeleportTask
extends RunnableImpl {
    private Player _player;
    private Location _loc;
    private Reflection a;

    public PvPEvent.TeleportTask(Player player, Location location, Reflection reflection) {
        this._player = player;
        this._loc = location;
        this.a = reflection;
    }

    public void runImpl() throws Exception {
        this._player.teleToLocation(this._loc, this.a);
    }
}
