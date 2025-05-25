/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPVPMatchUserDie
extends L2GameServerPacket {
    private int wd;
    private int we;

    public ExPVPMatchUserDie(int n, int n2) {
        this.wd = n;
        this.we = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(128);
        this.writeD(this.wd);
        this.writeD(this.we);
    }
}
