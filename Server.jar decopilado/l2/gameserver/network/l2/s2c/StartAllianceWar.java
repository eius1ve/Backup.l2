/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class StartAllianceWar
extends L2GameServerPacket {
    private String fG;
    private String fN;

    public StartAllianceWar(String string, String string2) {
        this.fG = string;
        this.fN = string2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(194);
        this.writeS(this.fN);
        this.writeS(this.fG);
    }
}
