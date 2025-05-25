/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;

public class CharMoveToLocation
extends L2GameServerPacket {
    private int fW;
    private int tB;
    private Location R;
    private Location _destination;

    public CharMoveToLocation(Creature creature) {
        this(creature, creature.getLoc(), creature.getDestination());
    }

    public CharMoveToLocation(Creature creature, Location location, Location location2) {
        this.fW = creature.getObjectId();
        this.R = location;
        this._destination = location2;
        if (!creature.isFlying()) {
            this.tB = Config.CLIENT_Z_SHIFT;
        }
        if (creature.isInWater()) {
            this.tB += Config.CLIENT_Z_SHIFT;
        }
        if (this._destination == null) {
            Log.debug("CharMoveToLocation: desc is null, but moving. L2Character: " + creature.getObjectId() + ":" + creature.getName() + "; Loc: " + this.R);
            this._destination = this.R;
        }
    }

    public CharMoveToLocation(int n, Location location, Location location2) {
        this.fW = n;
        this.R = location;
        this._destination = location2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(47);
        this.writeD(this.fW);
        this.writeD(this._destination.x);
        this.writeD(this._destination.y);
        this.writeD(this._destination.z + this.tB);
        this.writeD(this.R.x);
        this.writeD(this.R.y);
        this.writeD(this.R.z + this.tB);
    }
}
