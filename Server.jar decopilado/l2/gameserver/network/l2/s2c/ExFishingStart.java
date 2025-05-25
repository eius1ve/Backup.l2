/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExFishingStart
extends L2GameServerPacket {
    private int pM;
    private Location _loc;
    private int vp;
    private boolean eE;

    public ExFishingStart(Creature creature, int n, Location location, boolean bl) {
        this.pM = creature.getObjectId();
        this.vp = n;
        this._loc = location;
        this.eE = bl;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(30);
        this.writeD(this.pM);
        this.writeD(this.vp);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeC(this.eE ? 1 : 0);
        this.writeC(1);
    }
}
