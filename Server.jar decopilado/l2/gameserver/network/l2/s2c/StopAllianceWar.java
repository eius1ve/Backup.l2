/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class StopAllianceWar
extends L2GameServerPacket {
    private String fG;
    private String fN;

    public StopAllianceWar(String string, String string2) {
        this.fG = string;
        this.fN = string2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(196);
        this.writeS(this.fG);
        this.writeS(this.fN);
    }
}
