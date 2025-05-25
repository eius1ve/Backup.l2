/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPutEnchantSupportItemResult
extends L2GameServerPacket {
    public static final L2GameServerPacket FAIL = new ExPutEnchantSupportItemResult(0);
    public static final L2GameServerPacket SUCCESS = new ExPutEnchantSupportItemResult(1);
    private int dA;

    private ExPutEnchantSupportItemResult(int n) {
        this.dA = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(131);
        this.writeD(this.dA);
    }
}
