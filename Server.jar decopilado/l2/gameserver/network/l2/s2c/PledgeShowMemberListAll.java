/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PledgeShowMemberListAll
extends L2GameServerPacket {
    private int Aw;
    private int Ax;
    private int _level;
    private int pb;
    private int oX;
    private int Ay;
    private int Az;
    private int oS;
    private int AA;
    private int AB;
    private String fF;
    private String eL;
    private String fG;
    private int if;
    private int zb;
    private boolean fc;
    private List<PledgePacketMember> aS;

    public PledgeShowMemberListAll(Clan clan, SubUnit subUnit) {
        this.if = subUnit.getType();
        this.Aw = clan.getClanId();
        this.fF = subUnit.getName();
        this.eL = subUnit.getLeaderName();
        this.Ax = clan.getCrestId();
        this._level = clan.getLevel();
        this.oS = clan.getCastle();
        this.AA = clan.getHasHideout();
        this.pb = clan.getRank();
        this.oX = clan.getReputationScore();
        this.AB = clan.isAtWarOrUnderAttack();
        this.fc = clan.isPlacedForDisband();
        Alliance alliance = clan.getAlliance();
        if (alliance != null) {
            this.Ay = alliance.getAllyId();
            this.fG = alliance.getAllyName();
            this.Az = alliance.getAllyCrestId();
        }
        this.aS = new ArrayList<PledgePacketMember>(subUnit.size());
        for (UnitMember unitMember : subUnit.getUnitMembers()) {
            this.aS.add(new PledgePacketMember(unitMember));
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(90);
        this.writeD(this.if == 0);
        this.writeD(this.Aw);
        this.writeD(Config.REQUEST_ID);
        this.writeD(this.if);
        this.writeS(this.fF);
        this.writeS(this.eL);
        this.writeD(this.Ax);
        this.writeD(this._level);
        this.writeD(this.oS);
        this.writeD(0);
        this.writeD(this.AA);
        this.writeD(0);
        this.writeD(this.pb);
        this.writeD(this.oX);
        this.writeD(this.fc ? 3 : 0);
        this.writeD(0);
        this.writeD(this.Ay);
        this.writeS(this.fG);
        this.writeD(this.Az);
        this.writeD(this.AB);
        this.writeD(0);
        this.writeD(this.aS.size());
        for (PledgePacketMember pledgePacketMember : this.aS) {
            this.writeS(pledgePacketMember._name);
            this.writeD(pledgePacketMember._level);
            this.writeD(pledgePacketMember.ga);
            this.writeD(pledgePacketMember.gg);
            this.writeD(pledgePacketMember.fZ);
            this.writeD(pledgePacketMember.Av);
            this.writeD(pledgePacketMember.fd ? 1 : 0);
            this.writeC(0);
        }
    }

    private class PledgePacketMember {
        private String _name;
        private int _level;
        private int ga;
        private int gg;
        private int fZ;
        private int Av;
        private boolean fd;

        public PledgePacketMember(UnitMember unitMember) {
            this._name = unitMember.getName();
            this._level = unitMember.getLevel();
            this.ga = unitMember.getClassId();
            this.gg = unitMember.getSex();
            this.fZ = 0;
            this.Av = unitMember.isOnline() ? unitMember.getObjectId() : 0;
            this.fd = unitMember.getSponsor() != 0;
        }
    }
}
