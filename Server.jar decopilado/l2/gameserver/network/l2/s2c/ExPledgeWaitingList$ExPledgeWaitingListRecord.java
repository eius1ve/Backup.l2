/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.entry.PledgeApplicantInfo;

private static class ExPledgeWaitingList.ExPledgeWaitingListRecord {
    private final int wK;
    private final String ff;
    private final int wL;
    private final int wM;

    public ExPledgeWaitingList.ExPledgeWaitingListRecord(PledgeApplicantInfo pledgeApplicantInfo) {
        this.wK = pledgeApplicantInfo.getPlayerId();
        this.ff = pledgeApplicantInfo.getPlayerName();
        this.wL = pledgeApplicantInfo.getClassId();
        this.wM = pledgeApplicantInfo.getPlayerLvl();
    }
}
