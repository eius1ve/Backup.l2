/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.PledgeInfo;
import l2.gameserver.tables.ClanTable;

public class RequestPledgeInfo
extends L2GameClientPacket {
    private int fY;

    @Override
    protected void readImpl() {
        this.fY = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.fY < 10000000) {
            player.sendActionFailed();
            return;
        }
        Clan clan = ClanTable.getInstance().getClan(this.fY);
        if (clan == null) {
            player.sendActionFailed();
            return;
        }
        player.sendPacket((IStaticPacket)new PledgeInfo(clan));
    }
}
