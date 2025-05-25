/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBR_GamePoint
extends L2GameServerPacket {
    private final int uF;
    private final long df;

    public ExBR_GamePoint(Player player) {
        this.uF = player.getObjectId();
        this.df = player.getPremiumPoints();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(214);
        this.writeD(this.uF);
        this.writeQ(this.df);
        this.writeD(2);
    }
}
