/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class AllianceCrest
extends L2GameServerPacket {
    private int oU;
    private byte[] q;

    public AllianceCrest(int n, byte[] byArray) {
        this.oU = n;
        this.q = byArray;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(175);
        this.writeD(Config.REQUEST_ID);
        this.writeD(this.oU);
        this.writeD(this.q.length);
        this.writeB(this.q);
    }
}
