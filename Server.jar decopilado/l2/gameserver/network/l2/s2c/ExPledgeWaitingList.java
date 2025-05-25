/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.pledge.entry.PledgeApplicantInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPledgeWaitingList
extends L2GameServerPacket {
    private final List<ExPledgeWaitingListRecord> cp;

    public ExPledgeWaitingList(int n) {
        ArrayList<ExPledgeWaitingListRecord> arrayList = new ArrayList<ExPledgeWaitingListRecord>();
        for (PledgeApplicantInfo pledgeApplicantInfo : ClanEntryManager.getInstance().getApplicantListForClan(n).values()) {
            arrayList.add(new ExPledgeWaitingListRecord(pledgeApplicantInfo));
        }
        this.cp = arrayList;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(324);
        this.writeD(this.cp.size());
        for (ExPledgeWaitingListRecord exPledgeWaitingListRecord : this.cp) {
            this.writeD(exPledgeWaitingListRecord.wK);
            this.writeS(exPledgeWaitingListRecord.ff);
            this.writeD(exPledgeWaitingListRecord.wL);
            this.writeD(exPledgeWaitingListRecord.wM);
        }
    }

    private static class ExPledgeWaitingListRecord {
        private final int wK;
        private final String ff;
        private final int wL;
        private final int wM;

        public ExPledgeWaitingListRecord(PledgeApplicantInfo pledgeApplicantInfo) {
            this.wK = pledgeApplicantInfo.getPlayerId();
            this.ff = pledgeApplicantInfo.getPlayerName();
            this.wL = pledgeApplicantInfo.getClassId();
            this.wM = pledgeApplicantInfo.getPlayerLvl();
        }
    }
}
