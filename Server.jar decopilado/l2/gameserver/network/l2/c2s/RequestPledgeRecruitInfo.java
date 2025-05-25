/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExPledgeRecruitInfo;
import l2.gameserver.tables.ClanTable;

public class RequestPledgeRecruitInfo
extends L2GameClientPacket {
    private int fY;

    @Override
    protected void readImpl() {
        this.fY = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || !Config.ENABLE_CLAN_ENTRY) {
            return;
        }
        Clan clan = ClanTable.getInstance().getClan(this.fY);
        if (clan == null) {
            return;
        }
        player.sendPacket((IStaticPacket)new ExPledgeRecruitInfo(clan));
    }
}
