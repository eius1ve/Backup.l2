/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class GMViewPledgeInfo
extends L2GameServerPacket {
    private String _playerName;
    private String clan_name;
    private String leader_name;
    private String ally_name;
    private int clan_id;
    private int clan_crest_id;
    private int yV;
    private int rank;
    private int yW;
    private int ally_id;
    private int ally_crest_id;
    private int yX;
    private int yY;
    private int yZ;
    private int za;
    private int zb;
    private int if;
    private List<PledgeMemberInfo> aS = Collections.emptyList();

    public GMViewPledgeInfo(String string, Clan clan, SubUnit subUnit) {
        this.aS = new ArrayList<PledgeMemberInfo>(subUnit.getUnitMembers().size());
        this.if = subUnit.getType();
        this._playerName = string;
        this.clan_id = clan.getClanId();
        this.clan_name = clan.getName();
        this.leader_name = clan.getLeaderName();
        this.clan_crest_id = clan.getCrestId();
        this.yV = clan.getLevel();
        this.yX = clan.getCastle();
        this.yY = clan.getHasHideout();
        this.rank = clan.getRank();
        this.yW = clan.getReputationScore();
        Alliance alliance = clan.getAlliance();
        this.ally_id = alliance == null ? 0 : alliance.getAllyId();
        this.ally_name = alliance == null ? "" : alliance.getAllyName();
        this.ally_crest_id = alliance == null ? 0 : alliance.getAllyCrestId();
        this.za = clan.isAtWar();
        for (UnitMember unitMember : subUnit.getUnitMembers()) {
            this.aS.add(new PledgeMemberInfo(unitMember.getName(), unitMember.getLevel(), unitMember.getClassId(), unitMember.isOnline() ? unitMember.getObjectId() : 0, unitMember.getSex(), 1, unitMember.getSponsor() != 0 ? 1 : 0));
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(150);
        this.writeD(this.if == 0);
        this.writeS(this._playerName);
        this.writeD(this.clan_id);
        this.writeD(this.if);
        this.writeS(this.clan_name);
        this.writeS(this.leader_name);
        this.writeD(this.clan_crest_id);
        this.writeD(this.yV);
        this.writeD(this.yX);
        this.writeD(this.yY);
        this.writeD(0);
        this.writeD(this.rank);
        this.writeD(this.yW);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(this.ally_id);
        this.writeS(this.ally_name);
        this.writeD(this.ally_crest_id);
        this.writeD(this.za);
        this.writeD(0);
        this.writeD(this.aS.size());
        for (PledgeMemberInfo pledgeMemberInfo : this.aS) {
            this.writeS(pledgeMemberInfo._name);
            this.writeD(pledgeMemberInfo.level);
            this.writeD(pledgeMemberInfo.class_id);
            this.writeD(pledgeMemberInfo.sex);
            this.writeD(pledgeMemberInfo.race);
            this.writeD(pledgeMemberInfo.online);
            this.writeD(pledgeMemberInfo.sponsor);
            this.writeC(0);
        }
    }

    static class PledgeMemberInfo {
        public String _name;
        public int level;
        public int class_id;
        public int online;
        public int sex;
        public int race;
        public int sponsor;

        public PledgeMemberInfo(String string, int n, int n2, int n3, int n4, int n5, int n6) {
            this._name = string;
            this.level = n;
            this.class_id = n2;
            this.online = n3;
            this.sex = n4;
            this.race = n5;
            this.sponsor = n6;
        }
    }
}
