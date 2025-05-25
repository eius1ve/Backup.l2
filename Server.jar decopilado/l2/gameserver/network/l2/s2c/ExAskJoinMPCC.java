/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExAskJoinMPCC
extends L2GameServerPacket {
    private String eJ;

    public ExAskJoinMPCC(String string) {
        this.eJ = string;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(26);
        this.writeS(this.eJ);
        this.writeD(0);
    }
}
