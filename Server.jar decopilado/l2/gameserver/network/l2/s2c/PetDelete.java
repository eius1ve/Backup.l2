/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PetDelete
extends L2GameServerPacket {
    private int zU;
    private int zV;

    public PetDelete(int n, int n2) {
        this.zU = n;
        this.zV = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(183);
        this.writeD(this.zU);
        this.writeD(this.zV);
    }
}
