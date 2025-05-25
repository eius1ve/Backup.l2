/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.Request;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class RequestAnswerJoinPledge
extends L2GameClientPacket {
    private int dY;

    @Override
    protected void readImpl() {
        this.dY = this._buf.hasRemaining() ? this.readD() : 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        Integer n;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Request request = player.getRequest();
        if (request == null || !request.isTypeOf(Request.L2RequestType.CLAN)) {
            return;
        }
        if (!request.isInProgress()) {
            request.cancel();
            player.sendActionFailed();
            return;
        }
        if (player.isOutOfControl()) {
            request.cancel();
            player.sendActionFailed();
            return;
        }
        Player player2 = request.getRequestor();
        if (player2 == null) {
            request.cancel();
            player.sendPacket((IStaticPacket)SystemMsg.THAT_PLAYER_IS_NOT_ONLINE);
            player.sendActionFailed();
            return;
        }
        if (player2.getRequest() != request) {
            request.cancel();
            player.sendActionFailed();
            return;
        }
        Clan clan = player2.getClan();
        if (clan == null) {
            request.cancel();
            player.sendActionFailed();
            return;
        }
        if (this.dY == 0 || player.isOlyParticipant() || player.getTeam() != TeamType.NONE) {
            request.cancel();
            player2.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_DECLINED_YOUR_CLAN_INVITATION).addName(player));
            return;
        }
        if (!player.canJoinClan()) {
            request.cancel();
            player.sendPacket((IStaticPacket)SystemMsg.AFTER_LEAVING_OR_HAVING_BEEN_DISMISSED_FROM_A_CLAN_YOU_MUST_WAIT_AT_LEAST_A_DAY_BEFORE_JOINING_ANOTHER_CLAN);
            return;
        }
        try {
            n = request.getInteger("pledgeType");
        }
        catch (Exception exception) {
            request.done();
            return;
        }
        if (n == null || clan.getUnitMembersSize(n) >= clan.getSubPledgeLimit(n)) {
            request.cancel();
            if (n == 0) {
                player2.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_FULL_AND_CANNOT_ACCEPT_ADDITIONAL_CLAN_MEMBERS_AT_THIS_TIME).addString(clan.getName()));
            } else {
                player2.sendPacket((IStaticPacket)SystemMsg.THE_ACADEMYROYAL_GUARDORDER_OF_KNIGHTS_IS_FULL_AND_CANNOT_ACCEPT_NEW_MEMBERS_AT_THIS_TIME);
            }
            return;
        }
        try {
            clan.addToClan(player, n);
        }
        finally {
            request.done();
        }
    }
}
