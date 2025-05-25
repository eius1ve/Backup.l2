/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPledgeCount
extends L2GameServerPacket {
    private final int wu;

    public ExPledgeCount(Clan clan) {
        this.wu = clan.getOnlineMembers(0).size();
    }

    public ExPledgeCount(int n) {
        this.wu = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(317);
        this.writeD(this.wu);
    }
}
