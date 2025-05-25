/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.TpBookMark;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExGetBookMarkInfo
extends L2GameServerPacket {
    private final int vw;
    private final TpBookMark[] a;

    public ExGetBookMarkInfo(Player player) {
        this.vw = player.getTpBookmarkSize();
        this.a = player.getTpBookMarks().toArray(new TpBookMark[player.getTpBookMarks().size()]);
    }

    @Override
    protected void writeImpl() {
        this.writeEx(133);
        this.writeD(0);
        this.writeD(this.vw);
        this.writeD(this.a.length);
        for (int i = 0; i < this.a.length; ++i) {
            TpBookMark tpBookMark = this.a[i];
            this.writeD(i + 1);
            this.writeD(tpBookMark.x);
            this.writeD(tpBookMark.y);
            this.writeD(tpBookMark.z);
            this.writeS(tpBookMark.getName());
            this.writeD(tpBookMark.getIcon());
            this.writeS(tpBookMark.getAcronym());
        }
    }
}
