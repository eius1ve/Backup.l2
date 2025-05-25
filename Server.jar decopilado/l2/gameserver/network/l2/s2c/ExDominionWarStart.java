/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExDominionWarStart
extends L2GameServerPacket {
    private int fW;
    private int uU;
    private boolean eA;

    @Override
    protected void writeImpl() {
        this.writeEx(163);
        this.writeD(this.fW);
        this.writeD(1);
        this.writeD(this.uU);
        this.writeD(this.eA ? 1 : 0);
        this.writeD(this.eA ? this.uU : 0);
    }
}
