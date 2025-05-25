/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class AskJoinAlliance
extends L2GameServerPacket {
    private String eJ;
    private String eK;
    private int sG;

    public AskJoinAlliance(int n, String string, String string2) {
        this.eJ = string;
        this.eK = string2;
        this.sG = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(187);
        this.writeD(this.sG);
        this.writeS(this.eJ);
        this.writeS("");
        this.writeS(this.eK);
    }
}
