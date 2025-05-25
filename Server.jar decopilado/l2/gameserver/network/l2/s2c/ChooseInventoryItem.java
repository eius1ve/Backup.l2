/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ChooseInventoryItem
extends L2GameServerPacket {
    private int tE;

    public ChooseInventoryItem(int n) {
        this.tE = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(124);
        this.writeD(this.tE);
    }
}
