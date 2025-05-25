/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class TargetSelected
extends L2GameServerPacket {
    private int fW;
    private int _targetId;
    private Location _loc;

    public TargetSelected(int n, int n2, Location location) {
        this.fW = n;
        this._targetId = n2;
        this._loc = location;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(35);
        this.writeD(this.fW);
        this.writeD(this._targetId);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeD(0);
    }
}
