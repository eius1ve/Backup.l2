/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExFishingEnd
extends L2GameServerPacket {
    private int fX;
    private boolean eD;

    public ExFishingEnd(Player player, boolean bl) {
        this.fX = player.getObjectId();
        this.eD = bl;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(31);
        this.writeD(this.fX);
        this.writeC(this.eD ? 1 : 0);
    }
}
