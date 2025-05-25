/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class L2FriendSay
extends L2GameServerPacket {
    private String cS;
    private String fz;
    private String dL;

    public L2FriendSay(String string, String string2, String string3) {
        this.cS = string;
        this.fz = string2;
        this.dL = string3;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(120);
        this.writeD(0);
        this.writeS(this.fz);
        this.writeS(this.cS);
        this.writeS(this.dL);
    }
}
