/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.entry.PledgeApplicantInfo;
import l2.gameserver.model.pledge.entry.PledgeRecruitInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPledgeWaitingListApplied
extends L2GameServerPacket {
    private final int wN;
    private final String fg;
    private final String fh;
    private final int wO;
    private final int wP;
    private final int wQ;
    private final String fi;
    private final String fj;

    public ExPledgeWaitingListApplied(int n, int n2) {
        PledgeRecruitInfo pledgeRecruitInfo = ClanEntryManager.getInstance().getClanById(n);
        Clan clan = pledgeRecruitInfo.getClan();
        PledgeApplicantInfo pledgeApplicantInfo = ClanEntryManager.getInstance().getPlayerApplication(n, n2);
        this.wN = clan.getClanId();
        this.fg = clan.getName();
        this.fh = clan.getLeaderName();
        this.wO = clan.getLevel();
        this.wP = clan.getAllSize();
        this.wQ = pledgeRecruitInfo.getKarma();
        this.fi = pledgeRecruitInfo.getInformation();
        this.fj = pledgeApplicantInfo != null ? pledgeApplicantInfo.getMessage() : "";
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(323);
        this.writeD(this.wN);
        this.writeS(this.fg);
        this.writeS(this.fh);
        this.writeD(this.wO);
        this.writeD(this.wP);
        this.writeD(this.wQ);
        this.writeS(this.fi);
        this.writeS(this.fj);
    }
}
