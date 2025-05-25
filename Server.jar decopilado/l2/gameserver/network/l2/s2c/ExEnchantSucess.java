/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public final class ExEnchantSucess
extends L2GameServerPacket {
    private final int vd;

    public ExEnchantSucess(int n) {
        this.vd = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(368);
        this.writeD(this.vd);
    }
}
