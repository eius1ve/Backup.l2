/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ValidateLocation
extends L2GameServerPacket {
    private int yg;
    private Location _loc;

    public ValidateLocation(Creature creature) {
        this.yg = creature.getObjectId();
        this._loc = creature.getLoc();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(121);
        this.writeD(this.yg);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z + Config.CLIENT_Z_SHIFT);
        this.writeD(this._loc.h);
    }
}
