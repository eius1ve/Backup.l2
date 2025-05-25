/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class FriendAddRequestResult
extends L2GameServerPacket {
    private final int yh;
    private final int yi;
    private final String fu;
    private final int yj;
    private final int yk;
    private final int yl;
    private final int ym;

    public FriendAddRequestResult(Player player, int n) {
        this.yh = n;
        this.yi = player.getObjectId();
        this.fu = player.getName();
        this.yj = player.isOnline() ? 1 : 0;
        this.yk = player.getObjectId();
        this.yl = player.getLevel();
        this.ym = player.getActiveClassId();
    }

    @Override
    protected void writeImpl() {
        this.writeC(85);
        this.writeD(this.yh);
        this.writeD(this.yi);
        this.writeS(this.fu);
        this.writeD(this.yj);
        this.writeD(this.yk);
        this.writeD(this.yl);
        this.writeD(this.ym);
        this.writeH(0);
    }
}
