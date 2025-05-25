/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExAutoSoulShot
extends L2GameServerPacket {
    private final int uv;
    private final boolean eo;
    private final int uw;

    public ExAutoSoulShot(int n, boolean bl, int n2) {
        this.uv = n;
        this.eo = bl;
        this.uw = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(12);
        this.writeD(this.uv);
        this.writeD(this.eo);
        this.writeD(this.uw);
    }
}
