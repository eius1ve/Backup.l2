/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RestartResponse
extends L2GameServerPacket {
    public static final RestartResponse OK = new RestartResponse(1);
    public static final RestartResponse FAIL = new RestartResponse(0);
    private String dL = "bye";
    private int ld;

    public RestartResponse(int n) {
        this.ld = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(113);
        this.writeD(this.ld);
        this.writeS(this.dL);
    }
}
