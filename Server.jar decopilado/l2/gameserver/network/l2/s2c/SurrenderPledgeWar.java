/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class SurrenderPledgeWar
extends L2GameServerPacket {
    private String eB;
    private String fN;

    public SurrenderPledgeWar(String string, String string2) {
        this.eB = string;
        this.fN = string2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(103);
        this.writeS(this.eB);
        this.writeS(this.fN);
    }
}
