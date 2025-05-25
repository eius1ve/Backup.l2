/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExShowFortressMapInfo
extends L2GameServerPacket {
    private int rd;
    private boolean eT;
    private boolean[] b;

    @Override
    protected final void writeImpl() {
        this.writeEx(125);
        this.writeD(this.rd);
        this.writeD(this.eT);
        this.writeD(this.b.length);
        for (boolean bl : this.b) {
            this.writeD(bl);
        }
    }
}
