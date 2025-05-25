/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.LinkedList;
import java.util.List;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.entry.PledgeRecruitInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPledgeRecruitBoardSearch
extends L2GameServerPacket {
    static final int CLAN_PER_PAGE = 12;
    private final List<ExPledgeRecruitBoardSearchRecord> cn;
    private final int wF;
    private final int wG;
    private final int wH;

    public static ExPledgeRecruitBoardSearch paged(List<PledgeRecruitInfo> list, int n) {
        int n2 = (n - 1) * 12;
        int n3 = Math.min(n2 + 12, list.size());
        LinkedList<ExPledgeRecruitBoardSearchRecord> linkedList = new LinkedList<ExPledgeRecruitBoardSearchRecord>();
        for (int i = n2; i < n3; ++i) {
            linkedList.add(new ExPledgeRecruitBoardSearchRecord(list.get(i)));
        }
        return new ExPledgeRecruitBoardSearch(linkedList, n, (int)Math.ceil((double)list.size() / 12.0), n3 - n2);
    }

    private ExPledgeRecruitBoardSearch(List<ExPledgeRecruitBoardSearchRecord> list, int n, int n2, int n3) {
        this.cn = list;
        this.wF = n;
        this.wG = n2;
        this.wH = n3;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(321);
        this.writeD(this.wF);
        this.writeD(this.wG);
        this.writeD(this.wH);
        for (ExPledgeRecruitBoardSearchRecord exPledgeRecruitBoardSearchRecord : this.cn) {
            this.writeD(exPledgeRecruitBoardSearchRecord.clanId);
            this.writeD(exPledgeRecruitBoardSearchRecord.allyId);
        }
        for (ExPledgeRecruitBoardSearchRecord exPledgeRecruitBoardSearchRecord : this.cn) {
            this.writeD(exPledgeRecruitBoardSearchRecord.crestId);
            this.writeD(exPledgeRecruitBoardSearchRecord.allyCrestId);
            this.writeS(exPledgeRecruitBoardSearchRecord.clanName);
            this.writeS(exPledgeRecruitBoardSearchRecord.clanLeaderName);
            this.writeD(exPledgeRecruitBoardSearchRecord.clanLevel);
            this.writeD(exPledgeRecruitBoardSearchRecord.clanSize);
            this.writeD(exPledgeRecruitBoardSearchRecord.karma);
            this.writeS(exPledgeRecruitBoardSearchRecord.information);
            this.writeD(exPledgeRecruitBoardSearchRecord.applicationType);
            this.writeD(exPledgeRecruitBoardSearchRecord.recruitType);
        }
    }

    private static class ExPledgeRecruitBoardSearchRecord {
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

        public ExPledgeRecruitBoardSearchRecord(PledgeRecruitInfo pledgeRecruitInfo) {
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
}
