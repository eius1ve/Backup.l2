/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public final class ExEnchantFail
extends L2GameServerPacket {
    public static final L2GameServerPacket STATIC = new ExEnchantFail(0, 0);
    private final int uY;
    private final int uZ;

    public ExEnchantFail(int n, int n2) {
        this.uY = n;
        this.uZ = n2;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(369);
        this.writeD(this.uY);
        this.writeD(this.uZ);
    }
}
