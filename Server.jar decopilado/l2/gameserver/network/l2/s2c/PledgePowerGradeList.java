/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.RankPrivs;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgePowerGradeList
extends L2GameServerPacket {
    private RankPrivs[] a;

    public PledgePowerGradeList(RankPrivs[] rankPrivsArray) {
        this.a = rankPrivsArray;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(61);
        this.writeD(this.a.length);
        for (RankPrivs rankPrivs : this.a) {
            this.writeD(rankPrivs.getRank());
            this.writeD(rankPrivs.getParty());
        }
    }
}
