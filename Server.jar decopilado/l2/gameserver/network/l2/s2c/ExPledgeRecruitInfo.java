/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPledgeRecruitInfo
extends L2GameServerPacket {
    private final String fd;
    private final String fe;
    private final int wI;
    private final int wJ;
    private final List<SubUnit> co = new ArrayList<SubUnit>();

    public ExPledgeRecruitInfo(Clan clan) {
        this.fd = clan.getName();
        this.fe = clan.getLeader().getName();
        this.wI = clan.getLevel();
        this.wJ = clan.getAllSize();
        for (SubUnit subUnit : clan.getAllSubUnits()) {
            if (subUnit.getType() == 0) continue;
            this.co.add(subUnit);
        }
    }

    @Override
    protected void writeImpl() {
        this.writeEx(319);
        this.writeS(this.fd);
        this.writeS(this.fe);
        this.writeD(this.wI);
        this.writeD(this.wJ);
        this.writeD(this.co.size());
        for (SubUnit subUnit : this.co) {
            this.writeD(subUnit.getType());
            this.writeS(subUnit.getName());
        }
    }
}
