/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgeShowMemberListDelete
extends L2GameServerPacket {
    private String fH;

    public PledgeShowMemberListDelete(String string) {
        this.fH = string;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(93);
        this.writeS(this.fH);
    }
}
