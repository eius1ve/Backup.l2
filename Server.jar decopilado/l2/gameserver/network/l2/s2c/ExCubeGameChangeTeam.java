/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExCubeGameChangeTeam
extends L2GameServerPacket {
    private int fW;
    private boolean ez;

    public ExCubeGameChangeTeam(Player player, boolean bl) {
        this.fW = player.getObjectId();
        this.ez = bl;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(151);
        this.writeD(5);
        this.writeD(this.fW);
        this.writeD(this.ez ? 1 : 0);
        this.writeD(this.ez ? 0 : 1);
    }
}
