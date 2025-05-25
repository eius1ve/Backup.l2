/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPutEnchantScrollItemResult
extends L2GameServerPacket {
    public static final L2GameServerPacket FAIL = new ExPutEnchantScrollItemResult(0);
    private final int wU;

    public ExPutEnchantScrollItemResult(int n) {
        this.wU = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(338);
        this.writeD(this.wU);
    }
}
