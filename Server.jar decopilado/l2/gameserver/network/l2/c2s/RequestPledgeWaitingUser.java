/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.entry.PledgeApplicantInfo;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExPledgeWaitingList;
import l2.gameserver.network.l2.s2c.ExPledgeWaitingUser;
import l2.gameserver.tables.ClanTable;

public class RequestPledgeWaitingUser
extends L2GameClientPacket {
    private int fY;
    private int ph;

    @Override
    protected void readImpl() throws Exception {
        this.fY = this.readD();
        this.ph = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        Clan clan = ClanTable.getInstance().getClan(this.fY);
        if (player == null || player.getClanId() != this.fY || clan == null || !Config.ENABLE_CLAN_ENTRY) {
            return;
        }
        PledgeApplicantInfo pledgeApplicantInfo = ClanEntryManager.getInstance().getPlayerApplication(clan.getClanId(), this.ph);
        if (pledgeApplicantInfo == null) {
            player.sendPacket((IStaticPacket)new ExPledgeWaitingList(clan.getClanId()));
        } else {
            player.sendPacket((IStaticPacket)new ExPledgeWaitingUser(pledgeApplicantInfo));
        }
    }
}
