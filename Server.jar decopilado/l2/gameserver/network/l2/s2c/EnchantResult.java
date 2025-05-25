/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class EnchantResult
extends L2GameServerPacket {
    private final int tS;
    private final int tT;
    private final long dd;
    private final int tU;
    public static final EnchantResult SUCCESS = new EnchantResult(1, 0, 0L);
    public static final EnchantResult CANCEL = new EnchantResult(2, 0, 0L);
    public static final EnchantResult BLESSED_FAILED = new EnchantResult(3, 0, 0L);
    public static final EnchantResult FAILED_NO_CRYSTALS = new EnchantResult(4, 0, 0L);
    public static final EnchantResult ANCIENT_FAILED = new EnchantResult(5, 0, 0L);

    public EnchantResult(int n, int n2, long l) {
        this.tS = n;
        this.tT = n2;
        this.dd = l;
        this.tU = 0;
    }

    public EnchantResult(int n) {
        this.tS = 0;
        this.tT = 0;
        this.dd = 0L;
        this.tU = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(135);
        this.writeD(this.tS);
        this.writeD(this.tT);
        this.writeQ(this.dd);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(this.tU);
    }
}
