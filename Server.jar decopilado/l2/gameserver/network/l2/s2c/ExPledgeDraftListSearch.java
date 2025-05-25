/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.model.pledge.entry.PledgeWaitingInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPledgeDraftListSearch
extends L2GameServerPacket {
    final List<PledgeWaitingInfo> _pledgeRecruitList;

    public ExPledgeDraftListSearch(List<PledgeWaitingInfo> list) {
        this._pledgeRecruitList = list;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(326);
        this.writeD(this._pledgeRecruitList.size());
        for (PledgeWaitingInfo pledgeWaitingInfo : this._pledgeRecruitList) {
            this.writeD(pledgeWaitingInfo.getPlayerId());
            this.writeS(pledgeWaitingInfo.getPlayerName());
            this.writeD(pledgeWaitingInfo.getKarma());
            this.writeD(pledgeWaitingInfo.getPlayerClassId());
            this.writeD(pledgeWaitingInfo.getPlayerLvl());
        }
    }
}
