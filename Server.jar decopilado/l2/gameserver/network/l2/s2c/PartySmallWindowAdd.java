/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PartySmallWindowAll;

public class PartySmallWindowAdd
extends L2GameServerPacket {
    private int fW;
    private int oO;
    private final PartySmallWindowAll.PartySmallWindowMemberInfo a;

    public PartySmallWindowAdd(Party party, Player player) {
        this.fW = party.getPartyLeader().getObjectId();
        this.oO = party.getLootDistribution();
        this.a = new PartySmallWindowAll.PartySmallWindowMemberInfo(player);
    }

    @Override
    protected final void writeImpl() {
        this.writeC(79);
        this.writeD(this.fW);
        this.writeD(this.oO);
        this.writeD(this.a._id);
        this.writeS(this.a._name);
        this.writeD(this.a.curCp);
        this.writeD(this.a.maxCp);
        this.writeD(this.a.curHp);
        this.writeD(this.a.maxHp);
        this.writeD(this.a.curMp);
        this.writeD(this.a.maxMp);
        this.writeD(0);
        this.writeC(this.a.level);
        this.writeD(this.a.class_id);
        this.writeC(0);
        this.writeH(this.a.race_id);
    }
}
