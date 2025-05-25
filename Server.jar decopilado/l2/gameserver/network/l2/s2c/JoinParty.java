/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class JoinParty
extends L2GameServerPacket {
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    private final int zm;
    private final int zn;

    public JoinParty(int n, int n2) {
        this.zm = n;
        this.zn = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(58);
        this.writeD(this.zm);
        this.writeD(this.zn);
    }
}
