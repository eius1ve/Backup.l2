/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.tables.ClanTable;

public class RequestStopPledgeWar
extends L2GameClientPacket {
    private String eB;

    @Override
    protected void readImpl() {
        this.eB = this.readS(32);
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            return;
        }
        if ((player.getClanPrivileges() & 0x20) != 32) {
            player.sendPacket(SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT, ActionFail.STATIC);
            return;
        }
        Clan clan2 = ClanTable.getInstance().getClanByName(this.eB);
        if (clan2 == null) {
            player.sendPacket(SystemMsg.CLAN_NAME_IS_INVALID, ActionFail.STATIC);
            return;
        }
        if (!clan.isAtWarWith(clan2.getClanId())) {
            player.sendPacket(SystemMsg.YOU_HAVE_NOT_DECLARED_A_CLAN_WAR_AGAINST_THE_CLAN_S1, ActionFail.STATIC);
            return;
        }
        for (UnitMember unitMember : clan) {
            if (!unitMember.isOnline() || !unitMember.getPlayer().isInCombat()) continue;
            player.sendPacket(SystemMsg.A_CEASEFIRE_DURING_A_CLAN_WAR_CAN_NOT_BE_CALLED_WHILE_MEMBERS_OF_YOUR_CLAN_ARE_ENGAGED_IN_BATTLE, ActionFail.STATIC);
            return;
        }
        ClanTable.getInstance().stopClanWar(clan, clan2);
    }
}
