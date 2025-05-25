/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.entry.PledgeRecruitInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPledgeRecruitBoardDetail
extends L2GameServerPacket {
    private final int wB;
    private final int wC;
    private final String fb;
    private final String fc;
    private final int wD;
    private final int wE;

    public ExPledgeRecruitBoardDetail(PledgeRecruitInfo pledgeRecruitInfo) {
        this.wB = pledgeRecruitInfo.getClanId();
        this.wC = pledgeRecruitInfo.getKarma();
        this.fb = pledgeRecruitInfo.getInformation();
        this.fc = pledgeRecruitInfo.getDetailedInformation();
        this.wD = pledgeRecruitInfo.getApplicationType();
        this.wE = pledgeRecruitInfo.getRecruitType();
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(322);
        this.writeD(this.wB);
        this.writeD(this.wC);
        this.writeS(this.fb);
        this.writeS(this.fc);
        this.writeD(this.wD);
        this.writeD(this.wE);
    }
}
