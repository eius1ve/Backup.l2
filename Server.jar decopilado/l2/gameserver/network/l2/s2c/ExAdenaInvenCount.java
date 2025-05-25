/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExAdenaInvenCount
extends L2GameServerPacket {
    private long de;
    private int uq;

    public ExAdenaInvenCount(Player player) {
        this.de = player.getInventory().getAdena();
        this.uq = player.getInventory().getSize();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(318);
        this.writeQ(this.de);
        this.writeH(this.uq);
    }
}
