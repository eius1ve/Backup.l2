/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class RequestWithdrawAlly
extends L2GameClientPacket {
    @Override
    protected void readImpl() {
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
        if (!player.isClanLeader()) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_CLAN_LEADER_MAY_APPLY_FOR_WITHDRAWAL_FROM_THE_ALLIANCE);
            return;
        }
        if (clan.getAlliance() == null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_CURRENTLY_ALLIED_WITH_ANY_CLANS);
            return;
        }
        if (clan.equals(clan.getAlliance().getLeader())) {
            player.sendPacket((IStaticPacket)SystemMsg.ALLIANCE_LEADERS_CANNOT_WITHDRAW);
            return;
        }
        clan.broadcastToOnlineMembers(SystemMsg.YOU_HAVE_WITHDRAWN_FROM_THE_ALLIANCE, SystemMsg.A_CLAN_THAT_HAS_WITHDRAWN_OR_BEEN_EXPELLED_CANNOT_ENTER_INTO_AN_ALLIANCE_WITHIN_ONE_DAY_OF_WITHDRAWAL_OR_EXPULSION);
        Alliance alliance = clan.getAlliance();
        clan.setAllyId(0);
        clan.setLeavedAlly();
        alliance.broadcastAllyStatus();
        alliance.removeAllyMember(clan.getClanId());
    }
}
