/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class StopMove
extends L2GameServerPacket {
    private final int BX;
    private final int BY;
    private final int BZ;
    private final int Ca;
    private final int Cb;

    public StopMove(Creature creature) {
        this.BX = creature.getObjectId();
        this.BY = creature.getX();
        this.BZ = creature.getY();
        this.Ca = creature.getZ();
        this.Cb = creature.getHeading();
    }

    public StopMove(int n, Location location) {
        this.BX = n;
        this.BY = location.x;
        this.BZ = location.y;
        this.Ca = location.z;
        this.Cb = location.h;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(71);
        this.writeD(this.BX);
        this.writeD(this.BY);
        this.writeD(this.BZ);
        this.writeD(this.Ca);
        this.writeD(this.Cb);
    }
}
