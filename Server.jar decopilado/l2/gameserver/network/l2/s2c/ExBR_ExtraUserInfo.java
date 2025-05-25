/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

@Deprecated
public class ExBR_ExtraUserInfo
extends L2GameServerPacket {
    private int fW;
    private int uD;
    private int uE;

    @Override
    protected void writeImpl() {
        this.writeEx(218);
        this.writeD(this.fW);
        this.writeD(this.uD);
        this.writeC(this.uE);
    }
}
