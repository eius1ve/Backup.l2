/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgeInfo
extends L2GameServerPacket {
    private int clan_id;
    private String clan_name;
    private String ally_name;

    public PledgeInfo(Clan clan) {
        this.clan_id = clan.getClanId();
        this.clan_name = clan.getName();
        this.ally_name = clan.getAlliance() == null ? "" : clan.getAlliance().getAllyName();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(137);
        this.writeD(Config.REQUEST_ID);
        this.writeD(this.clan_id);
        this.writeS(this.clan_name);
        this.writeS(this.ally_name);
    }
}
