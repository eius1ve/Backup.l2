/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.UnitMember;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
private class PledgeShowMemberListAdd.PledgePacketMember {
    private String _name;
    private int _level;
    private int ga;
    private int gg;
    private int fZ;
    private int Av;
    private int if;

    public PledgeShowMemberListAdd.PledgePacketMember(UnitMember unitMember) {
        this._name = unitMember.getName();
        this._level = unitMember.getLevel();
        this.ga = unitMember.getClassId();
        this.gg = unitMember.getSex();
        this.fZ = 0;
        this.Av = unitMember.isOnline() ? unitMember.getObjectId() : 0;
        this.if = unitMember.getPledgeType();
    }
}
