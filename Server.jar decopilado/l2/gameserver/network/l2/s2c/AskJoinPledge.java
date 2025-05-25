/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class AskJoinPledge
extends L2GameServerPacket {
    private int sG;
    private String eB;

    public AskJoinPledge(int n, String string) {
        this.sG = n;
        this.eB = string;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(44);
        this.writeD(this.sG);
        this.writeS(this.eB);
    }
}
