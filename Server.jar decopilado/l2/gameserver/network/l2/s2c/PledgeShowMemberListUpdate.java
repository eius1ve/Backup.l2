/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PledgeShowMemberListUpdate
extends L2GameServerPacket {
    private String _name;
    private int sb;
    private int ga;
    private int gg;
    private int yj;
    private int fW;
    private int if;
    private int AC;

    public PledgeShowMemberListUpdate(Player player) {
        UnitMember unitMember;
        this._name = player.getName();
        this.sb = player.getLevel();
        this.ga = player.getClassId().getId();
        this.gg = player.getSex();
        this.fW = player.getObjectId();
        this.yj = player.isOnline() ? 1 : 0;
        this.if = player.getPledgeType();
        SubUnit subUnit = player.getSubUnit();
        UnitMember unitMember2 = unitMember = subUnit == null ? null : subUnit.getUnitMember(this.fW);
        if (unitMember != null) {
            this.AC = unitMember.hasSponsor() ? 1 : 0;
        }
    }

    public PledgeShowMemberListUpdate(UnitMember unitMember) {
        this._name = unitMember.getName();
        this.sb = unitMember.getLevel();
        this.ga = unitMember.getClassId();
        this.gg = unitMember.getSex();
        this.fW = unitMember.getObjectId();
        this.yj = unitMember.isOnline() ? 1 : 0;
        this.if = unitMember.getPledgeType();
        this.AC = unitMember.hasSponsor() ? 1 : 0;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(91);
        this.writeS(this._name);
        this.writeD(this.sb);
        this.writeD(this.ga);
        this.writeD(this.gg);
        this.writeD(this.fW);
        this.writeD(this.yj);
        this.writeD(this.if);
        this.writeD(this.AC);
    }
}
