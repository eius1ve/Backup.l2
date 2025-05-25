/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPutEnchantTargetItemResult
extends L2GameServerPacket {
    public static final L2GameServerPacket FAIL = new ExPutEnchantTargetItemResult(0);
    public static final L2GameServerPacket SUCCESS = new ExPutEnchantTargetItemResult(1);
    private int dA;

    public ExPutEnchantTargetItemResult(int n) {
        this.dA = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(130);
        this.writeD(this.dA);
    }
}
