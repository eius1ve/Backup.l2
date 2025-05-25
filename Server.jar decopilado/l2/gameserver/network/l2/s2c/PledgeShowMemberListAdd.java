/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgeShowMemberListAdd
extends L2GameServerPacket {
    private PledgePacketMember a;

    public PledgeShowMemberListAdd(UnitMember unitMember) {
        this.a = new PledgePacketMember(unitMember);
    }

    @Override
    protected final void writeImpl() {
        this.writeC(92);
        this.writeS(this.a._name);
        this.writeD(this.a._level);
        this.writeD(this.a.ga);
        this.writeD(this.a.gg);
        this.writeD(this.a.fZ);
        this.writeD(this.a.Av);
        this.writeD(this.a.if);
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    private class PledgePacketMember {
        private String _name;
        private int _level;
        private int ga;
        private int gg;
        private int fZ;
        private int Av;
        private int if;

        public PledgePacketMember(UnitMember unitMember) {
            this._name = unitMember.getName();
            this._level = unitMember.getLevel();
            this.ga = unitMember.getClassId();
            this.gg = unitMember.getSex();
            this.fZ = 0;
            this.Av = unitMember.isOnline() ? unitMember.getObjectId() : 0;
            this.if = unitMember.getPledgeType();
        }
    }
}
