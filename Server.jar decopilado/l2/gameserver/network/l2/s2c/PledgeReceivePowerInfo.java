/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.RankPrivs;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgeReceivePowerInfo
extends L2GameServerPacket {
    private int Ao;
    private int zA;
    private String fE;

    public PledgeReceivePowerInfo(UnitMember unitMember) {
        RankPrivs rankPrivs;
        this.Ao = unitMember.getPowerGrade();
        this.fE = unitMember.getName();
        this.zA = unitMember.isClanLeader() ? 0xFFFFFE : ((rankPrivs = unitMember.getClan().getRankPrivs(unitMember.getPowerGrade())) != null ? rankPrivs.getPrivs() : 0);
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(62);
        this.writeD(this.Ao);
        this.writeS(this.fE);
        this.writeD(this.zA);
    }
}
