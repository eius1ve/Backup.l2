/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgeStatusChanged
extends L2GameServerPacket {
    private int vh;
    private int clan_id;
    private int level;

    public PledgeStatusChanged(Clan clan) {
        this.vh = clan.getLeaderId();
        this.clan_id = clan.getClanId();
        this.level = clan.getLevel();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(205);
        this.writeD(this.vh);
        this.writeD(this.clan_id);
        this.writeD(0);
        this.writeD(this.level);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
    }
}
