/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.UnitMember;

private class PledgeShowMemberListAll.PledgePacketMember {
    private String _name;
    private int _level;
    private int ga;
    private int gg;
    private int fZ;
    private int Av;
    private boolean fd;

    public PledgeShowMemberListAll.PledgePacketMember(UnitMember unitMember) {
        this._name = unitMember.getName();
        this._level = unitMember.getLevel();
        this.ga = unitMember.getClassId();
        this.gg = unitMember.getSex();
        this.fZ = 0;
        this.Av = unitMember.isOnline() ? unitMember.getObjectId() : 0;
        this.fd = unitMember.getSponsor() != 0;
    }
}
