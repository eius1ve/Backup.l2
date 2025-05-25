/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExResponseShowContents
extends L2GameServerPacket {
    private final String fn;

    public ExResponseShowContents(String string) {
        this.fn = string;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(176);
        this.writeS(this.fn);
    }
}
