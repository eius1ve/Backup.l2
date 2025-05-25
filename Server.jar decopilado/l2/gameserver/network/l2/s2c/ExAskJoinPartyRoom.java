/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExAskJoinPartyRoom
extends L2GameServerPacket {
    private String eM;
    private String eN;

    public ExAskJoinPartyRoom(String string, String string2) {
        this.eM = string;
        this.eN = string2;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(53);
        this.writeS(this.eM);
        this.writeS(this.eN);
    }
}
