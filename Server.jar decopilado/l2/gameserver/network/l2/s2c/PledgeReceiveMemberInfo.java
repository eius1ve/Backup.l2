/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgeReceiveMemberInfo
extends L2GameServerPacket {
    private UnitMember b;

    public PledgeReceiveMemberInfo(UnitMember unitMember) {
        this.b = unitMember;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(63);
        this.writeD(this.b.getPledgeType());
        this.writeS(this.b.getName());
        this.writeS(this.b.getTitle());
        this.writeD(this.b.getPowerGrade());
        this.writeS(this.b.getSubUnit().getName());
        this.writeS(this.b.getRelatedName());
    }
}
