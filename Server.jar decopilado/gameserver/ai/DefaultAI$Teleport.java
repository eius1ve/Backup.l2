/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

protected class DefaultAI.Teleport
extends RunnableImpl {
    Location _destination;
    boolean _updateSpawnLocation;

    public DefaultAI.Teleport(Location location) {
        this._destination = location;
        this._updateSpawnLocation = false;
    }

    public DefaultAI.Teleport(Location location, boolean bl) {
        this._destination = location;
        this._updateSpawnLocation = bl;
    }

    @Override
    public void runImpl() throws Exception {
        NpcInstance npcInstance = DefaultAI.this.getActor();
        if (npcInstance != null) {
            npcInstance.teleToLocation(this._destination);
            if (this._updateSpawnLocation) {
                npcInstance.setSpawnedLoc(this._destination);
            }
        }
    }
}
