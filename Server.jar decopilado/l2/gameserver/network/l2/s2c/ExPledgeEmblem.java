/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPledgeEmblem
extends L2GameServerPacket {
    private static final int wv = 14336;
    private static final int ww = 65664;
    private final int wx;
    private final int wy;
    private final int crestId;
    private final int wz;
    private final int wA;
    private final byte[] u;

    public ExPledgeEmblem(int n, int n2, int n3, int n4, int n5, byte[] byArray) {
        this.wx = n;
        this.wy = n2;
        this.crestId = n3;
        this.wz = n4;
        this.wA = n5;
        this.u = byArray;
    }

    public static ExPledgeEmblem[] packets(int n, int n2, int n3, byte[] byArray) {
        ExPledgeEmblem[] exPledgeEmblemArray = new ExPledgeEmblem[5];
        for (int i = 0; i < 5; ++i) {
            int n4 = i * 14336;
            if (n4 < byArray.length) {
                int n5 = Math.min(byArray.length - n4, 14336);
                byte[] byArray2 = new byte[n5];
                System.arraycopy(byArray, n4, byArray2, 0, n5);
                exPledgeEmblemArray[i] = new ExPledgeEmblem(n, n2, n3, i, byArray.length, byArray2);
                continue;
            }
            exPledgeEmblemArray[i] = new ExPledgeEmblem(n, n2, n3, i, byArray.length, new byte[]{0});
        }
        return exPledgeEmblemArray;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(27);
        this.writeD(this.wx);
        this.writeD(this.wy);
        this.writeD(this.crestId);
        this.writeD(this.wz);
        this.writeD(this.wA);
        this.writeD(this.u.length);
        this.writeB(this.u);
    }
}
