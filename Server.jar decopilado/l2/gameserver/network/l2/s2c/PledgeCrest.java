/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgeCrest
extends L2GameServerPacket {
    private int oU;
    private int An;
    private byte[] q;

    public PledgeCrest(int n, byte[] byArray) {
        this.oU = n;
        this.q = byArray;
        this.An = this.q.length;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(106);
        this.writeD(Config.REQUEST_ID);
        this.writeD(this.oU);
        this.writeD(this.An);
        this.writeB(this.q);
    }
}
