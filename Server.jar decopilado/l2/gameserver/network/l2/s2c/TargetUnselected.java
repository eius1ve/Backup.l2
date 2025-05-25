/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.GameObject;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class TargetUnselected
extends L2GameServerPacket {
    private int _targetId;
    private Location _loc;

    public TargetUnselected(GameObject gameObject) {
        this._targetId = gameObject.getObjectId();
        this._loc = gameObject.getLoc();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(36);
        this.writeD(this._targetId);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeD(0);
    }
}
