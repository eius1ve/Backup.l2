/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.authcomm.gs2as;

import l2.gameserver.network.authcomm.SendablePacket;

public class BonusRequest
extends SendablePacket {
    private String account;
    private double d;
    private int dG;

    public BonusRequest(String string, double d, int n) {
        this.account = string;
        this.d = d;
        this.dG = n;
    }

    @Override
    protected void writeImpl() {
        this.writeC(16);
        this.writeS(this.account);
        this.writeF(this.d);
        this.writeD(this.dG);
    }
}
