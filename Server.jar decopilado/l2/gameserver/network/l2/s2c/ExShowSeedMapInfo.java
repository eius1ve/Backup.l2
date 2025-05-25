/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExShowSeedMapInfo
extends L2GameServerPacket {
    private static final Location[] i = new Location[]{new Location(-246857, 251960, 4331, 1), new Location(-213770, 210760, 4400, 2)};

    @Override
    protected void writeImpl() {
        this.writeEx(162);
        this.writeD(i.length);
        for (Location location : i) {
            this.writeD(location.x);
            this.writeD(location.y);
            this.writeD(location.z);
            this.writeD(location.h);
        }
    }
}
