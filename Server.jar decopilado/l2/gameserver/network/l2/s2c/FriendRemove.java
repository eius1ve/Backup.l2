/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class FriendRemove
extends L2GameServerPacket {
    private final int yn;
    private final String fv;

    public FriendRemove(String string, int n) {
        this.yn = n;
        this.fv = string;
    }

    @Override
    protected void writeImpl() {
        this.writeC(87);
        this.writeD(this.yn);
        this.writeS(this.fv);
    }
}
