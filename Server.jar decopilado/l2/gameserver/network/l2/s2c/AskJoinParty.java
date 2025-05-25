/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class AskJoinParty
extends L2GameServerPacket {
    private String eJ;
    private int hs;

    public AskJoinParty(String string, int n) {
        this.eJ = string;
        this.hs = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(57);
        this.writeS(this.eJ);
        this.writeD(this.hs);
    }
}
