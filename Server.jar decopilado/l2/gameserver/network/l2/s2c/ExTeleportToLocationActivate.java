/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.GameObject;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExTeleportToLocationActivate
extends L2GameServerPacket {
    private int _targetId;
    private Location _loc;

    public ExTeleportToLocationActivate(GameObject gameObject, Location location) {
        this._targetId = gameObject.getObjectId();
        this._loc = location;
    }

    public ExTeleportToLocationActivate(GameObject gameObject, int n, int n2, int n3) {
        this._targetId = gameObject.getObjectId();
        this._loc = new Location(n, n2, n3, gameObject.getHeading());
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(330);
        this.writeD(this._targetId);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z + Config.CLIENT_Z_SHIFT);
        this.writeD(0);
        this.writeD(this._loc.h);
        this.writeD(0);
    }
}
