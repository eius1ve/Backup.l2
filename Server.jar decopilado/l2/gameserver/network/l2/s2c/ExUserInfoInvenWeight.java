/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExUserInfoInvenWeight
extends L2GameServerPacket {
    private final int xS;
    private final int xT;
    private final int xU;

    public ExUserInfoInvenWeight(Player player) {
        this.xS = player.getObjectId();
        this.xT = player.getCurrentLoad();
        this.xU = player.getMaxLoad();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(358);
        this.writeD(this.xS);
        this.writeD(this.xT);
        this.writeD(this.xU);
    }
}
