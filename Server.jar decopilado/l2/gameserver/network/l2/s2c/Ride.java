/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class Ride
extends L2GameServerPacket {
    private final int AR;
    private final int AS;
    private final int AT;
    private final Location V;
    private final boolean fj;

    public Ride(Player player) {
        this.AS = player.getObjectId();
        this.AR = player.getMountType();
        this.fj = player.isMounted();
        this.AT = player.getMountNpcId() + 1000000;
        this.V = player.getLoc();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(140);
        this.writeD(this.AS);
        this.writeD(this.fj);
        this.writeD(this.AR);
        this.writeD(this.AT);
        this.writeD(this.V.x);
        this.writeD(this.V.y);
        this.writeD(this.V.z);
    }
}
