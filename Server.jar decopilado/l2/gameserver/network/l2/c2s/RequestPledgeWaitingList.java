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
import l2.gameserver.network.l2.s2c.ExPledgeWaitingList;
import l2.gameserver.tables.ClanTable;

public class RequestPledgeWaitingList
extends L2GameClientPacket {
    private int fY;

    @Override
    protected void readImpl() throws Exception {
        this.fY = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        Clan clan = ClanTable.getInstance().getClan(this.fY);
        if (player == null || player.getClanId() != this.fY || clan == null || !Config.ENABLE_CLAN_ENTRY) {
            return;
        }
        player.sendPacket((IStaticPacket)new ExPledgeWaitingList(clan.getClanId()));
    }
}
