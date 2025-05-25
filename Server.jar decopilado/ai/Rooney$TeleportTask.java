/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.utils.Location;

private final class Rooney.TeleportTask
extends RunnableImpl {
    private final Location _loc;

    public Rooney.TeleportTask(Location location) {
        this._loc = location;
    }

    public final void runImpl() {
        Rooney.this.getActor().teleToLocation(this._loc);
        Rooney.this.r = false;
    }
}
