/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class FriendAddRequest
extends L2GameServerPacket {
    private final String ft;

    public FriendAddRequest(String string) {
        this.ft = string;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(131);
        this.writeC(0);
        this.writeS(this.ft);
    }
}
