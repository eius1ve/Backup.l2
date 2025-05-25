/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Request;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.AskJoinPledge;
import l2.gameserver.network.l2.s2c.SystemMessage;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class RequestJoinPledge
extends L2GameClientPacket {
    public static final String PLEDGE_TYPE_VAR_NAME = "pledgeType";
    private int fW;
    private int if;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
        this.if = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || player.getClan() == null) {
            return;
        }
        if (player.isOutOfControl()) {
            player.sendActionFailed();
            return;
        }
        if (player.isProcessingRequest()) {
            player.sendPacket((IStaticPacket)SystemMsg.WAITING_FOR_ANOTHER_REPLY);
            return;
        }
        Clan clan = player.getClan();
        if (clan.isPlacedForDisband()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_ALREADY_REQUESTED_THE_DISSOLUTION_OF_YOUR_CLAN);
            return;
        }
        if (!clan.canInvite()) {
            player.sendPacket((IStaticPacket)SystemMsg.AFTER_A_CLAN_MEMBER_IS_DISMISSED_FROM_A_CLAN_THE_CLAN_MUST_WAIT_AT_LEAST_A_DAY_BEFORE_ACCEPTING_A_NEW_MEMBER);
            return;
        }
        if (this.fW == player.getObjectId()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_ASK_YOURSELF_TO_APPLY_TO_A_CLAN);
            return;
        }
        if ((player.getClanPrivileges() & 2) != 2) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_LEADER_CAN_GIVE_OUT_INVITATIONS);
            return;
        }
        GameObject gameObject = player.getVisibleObject(this.fW);
        if (gameObject == null || !gameObject.isPlayer()) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return;
        }
        Player player2 = (Player)gameObject;
        if (player2.getClan() == player.getClan()) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return;
        }
        if (!player2.getPlayerAccess().CanJoinClan) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_CANNOT_JOIN_THE_CLAN_BECAUSE_ONE_DAY_HAS_NOT_YET_PASSED_SINCE_THEY_LEFT_ANOTHER_CLAN).addName(player2));
            return;
        }
        if (player2.getClan() != null) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_ALREADY_A_MEMBER_OF_ANOTHER_CLAN).addName(player2));
            return;
        }
        if (player2.isBusy()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_IS_ON_ANOTHER_TASK).addName(player2));
            return;
        }
        if (this.if == -1 && (player2.getLevel() > 40 || player2.getClassId().getLevel() > 2)) {
            player.sendPacket((IStaticPacket)SystemMsg.TO_JOIN_A_CLAN_ACADEMY_CHARACTERS_MUST_BE_LEVEL_40_OR_BELOW_NOT_BELONG_ANOTHER_CLAN_AND_NOT_YET_COMPLETED_THEIR_2ND_CLASS_TRANSFER);
            return;
        }
        if (clan.getUnitMembersSize(this.if) >= clan.getSubPledgeLimit(this.if)) {
            if (this.if == 0) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_FULL_AND_CANNOT_ACCEPT_ADDITIONAL_CLAN_MEMBERS_AT_THIS_TIME).addString(clan.getName()));
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.THE_ACADEMYROYAL_GUARDORDER_OF_KNIGHTS_IS_FULL_AND_CANNOT_ACCEPT_NEW_MEMBERS_AT_THIS_TIME);
            }
            return;
        }
        Request request = new Request(Request.L2RequestType.CLAN, player, player2).setTimeout(10000L);
        request.set(PLEDGE_TYPE_VAR_NAME, this.if);
        player2.sendPacket((IStaticPacket)new AskJoinPledge(player.getObjectId(), player.getClan().getName()));
    }
}
