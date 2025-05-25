/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.entry.PledgeRecruitInfo;

private static class ExPledgeRecruitBoardSearch.ExPledgeRecruitBoardSearchRecord {
    protected int crestId;
    protected int allyCrestId;
    protected String clanName;
    protected String clanLeaderName;
    protected int clanLevel;
    protected int clanSize;
    protected int karma;
    protected String information;
    protected int applicationType;
    protected int recruitType;
    int clanId;
    int allyId;

    public ExPledgeRecruitBoardSearch.ExPledgeRecruitBoardSearchRecord(PledgeRecruitInfo pledgeRecruitInfo) {
        this.karma = pledgeRecruitInfo.getKarma();
        this.information = pledgeRecruitInfo.getInformation();
        this.applicationType = pledgeRecruitInfo.getApplicationType();
        this.recruitType = pledgeRecruitInfo.getRecruitType();
        Clan clan = pledgeRecruitInfo.getClan();
        this.clanId = clan.getClanId();
        this.allyId = clan.getAllyId();
        this.crestId = clan.getCrestId();
        this.allyCrestId = clan.getAlliance() != null ? clan.getAlliance().getAllyCrestId() : 0;
        this.clanName = clan.getName();
        this.clanLeaderName = clan.getLeaderName();
        this.clanLevel = clan.getLevel();
        this.clanSize = clan.getAllSize();
    }
}
