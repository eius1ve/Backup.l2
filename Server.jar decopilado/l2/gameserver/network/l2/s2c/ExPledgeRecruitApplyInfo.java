/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.components.ClanEntryStatus;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPledgeRecruitApplyInfo
extends L2GameServerPacket {
    private final ClanEntryStatus a;

    public ExPledgeRecruitApplyInfo(ClanEntryStatus clanEntryStatus) {
        this.a = clanEntryStatus;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(320);
        this.writeD(this.a.ordinal());
    }
}
