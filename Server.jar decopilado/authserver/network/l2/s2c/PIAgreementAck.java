/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.s2c;

import l2.authserver.network.l2.s2c.L2LoginServerPacket;

public class PIAgreementAck
extends L2LoginServerPacket {
    private final int ed;
    private final int ee;

    public PIAgreementAck(int n, int n2) {
        this.ed = n;
        this.ee = n2;
    }

    @Override
    protected void writeImpl() {
        this.writeC(18);
        this.writeD(this.ed);
        this.writeC(this.ee);
    }
}
