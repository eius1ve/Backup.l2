/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Creature
 *  l2.gameserver.utils.Location
 */
package events.TvTArena;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.utils.Location;

public class TvTTemplate.TeleportTask
extends RunnableImpl {
    Location loc;
    Creature target;

    public TvTTemplate.TeleportTask(Creature creature, Location location) {
        this.target = creature;
        this.loc = location;
        creature.block();
    }

    public void runImpl() throws Exception {
        this.target.unblock();
        this.target.teleToLocation(this.loc);
    }
}
