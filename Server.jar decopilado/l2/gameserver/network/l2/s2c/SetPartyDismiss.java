/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class SetPartyDismiss
extends L2GameServerPacket {
    public static final SetPartyDismiss STATIC = new SetPartyDismiss(1);
    private final int Bn;

    private SetPartyDismiss(int n) {
        this.Bn = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(63);
        this.writeD(this.Bn);
    }
}
