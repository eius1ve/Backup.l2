/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.tables.ClanTable;

public class RequestOustAlly
extends L2GameClientPacket {
    private String eq;

    @Override
    protected void readImpl() {
        this.eq = this.readS(32);
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendActionFailed();
            return;
        }
        Alliance alliance = clan.getAlliance();
        if (alliance == null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_CURRENTLY_ALLIED_WITH_ANY_CLANS);
            return;
        }
        if (!player.isAllyLeader()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_FEATURE_IS_ONLY_AVAILABLE_TO_ALLIANCE_LEADERS);
            return;
        }
        if (this.eq == null) {
            return;
        }
        Clan clan2 = ClanTable.getInstance().getClanByName(this.eq);
        if (clan2 != null) {
            if (!alliance.isMember(clan2.getClanId())) {
                player.sendActionFailed();
                return;
            }
            if (alliance.getLeader().equals(clan2)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_FAILED_TO_WITHDRAW_FROM_THE_ALLIANCE);
                return;
            }
            clan2.broadcastToOnlineMembers(SystemMsg.A_CLAN_THAT_HAS_WITHDRAWN_OR_BEEN_EXPELLED_CANNOT_ENTER_INTO_AN_ALLIANCE_WITHIN_ONE_DAY_OF_WITHDRAWAL_OR_EXPULSION);
            clan2.setAllyId(0);
            clan2.setLeavedAlly();
            alliance.broadcastAllyStatus();
            alliance.removeAllyMember(clan2.getClanId());
            alliance.setExpelledMember();
            player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestOustAlly.ClanDismissed", player, new Object[0]).addString(clan2.getName()).addString(alliance.getAllyName()));
        }
    }
}
