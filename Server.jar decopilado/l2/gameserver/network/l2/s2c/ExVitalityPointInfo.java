/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExVitalityPointInfo
extends L2GameServerPacket {
    private final int xZ;

    public ExVitalityPointInfo(int n) {
        this.xZ = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(161);
        this.writeD(this.xZ);
    }
}
