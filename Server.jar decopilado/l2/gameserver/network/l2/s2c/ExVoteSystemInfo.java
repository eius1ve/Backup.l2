/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExVoteSystemInfo
extends L2GameServerPacket {
    private final int ya;
    private final int yb;
    private final int yc;
    private final int yd;
    private final int ye;

    public ExVoteSystemInfo(Player player) {
        this.ya = player.getGivableRec();
        this.yb = player.getReceivedRec();
        this.yc = 0;
        this.yd = 0;
        this.ye = 0;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(202);
        this.writeD(this.ya);
        this.writeD(this.yb);
        this.writeD(this.yc);
        this.writeD(this.yd);
        this.writeD(this.ye);
    }
}
