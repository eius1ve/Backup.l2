/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.entry.PledgeRecruitInfo;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class RequestPledgeSignInForOpenJoiningMethod
extends L2GameClientPacket {
    private int fY;
    private int qS;

    @Override
    protected void readImpl() {
        this.fY = this.readD();
        this.qS = this.readD();
    }

    @Override
    protected void runImpl() {
        Clan clan;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || player.getClan() != null || !Config.ENABLE_CLAN_ENTRY) {
            return;
        }
        PledgeRecruitInfo pledgeRecruitInfo = ClanEntryManager.getInstance().getClanById(this.fY);
        if (pledgeRecruitInfo != null && (clan = pledgeRecruitInfo.getClan()) != null && player.getClan() == null) {
            if (player.isOutOfControl() || player.getClan() != null) {
                player.sendActionFailed();
                return;
            }
            if (!player.canJoinClan()) {
                player.sendPacket((IStaticPacket)SystemMsg.AFTER_LEAVING_OR_HAVING_BEEN_DISMISSED_FROM_A_CLAN_YOU_MUST_WAIT_AT_LEAST_A_DAY_BEFORE_JOINING_ANOTHER_CLAN);
                return;
            }
            if (!player.getPlayerAccess().CanJoinClan) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_CANNOT_JOIN_THE_CLAN_BECAUSE_ONE_DAY_HAS_NOT_YET_PASSED_SINCE_THEY_LEFT_ANOTHER_CLAN).addName(player));
                return;
            }
            int n = pledgeRecruitInfo.getRecruitType();
            if (clan.getUnitMembersSize(n) >= clan.getSubPledgeLimit(n)) {
                if (n == 0) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_FULL_AND_CANNOT_ACCEPT_ADDITIONAL_CLAN_MEMBERS_AT_THIS_TIME).addString(clan.getName()));
                } else {
                    player.sendPacket((IStaticPacket)SystemMsg.THE_ACADEMYROYAL_GUARDORDER_OF_KNIGHTS_IS_FULL_AND_CANNOT_ACCEPT_NEW_MEMBERS_AT_THIS_TIME);
                }
                return;
            }
            if (n == -1 && (player.getLevel() > 40 || player.getClassId().getLevel() > 2)) {
                player.sendPacket((IStaticPacket)SystemMsg.TO_JOIN_A_CLAN_ACADEMY_CHARACTERS_MUST_BE_LEVEL_40_OR_BELOW_NOT_BELONG_ANOTHER_CLAN_AND_NOT_YET_COMPLETED_THEIR_2ND_CLASS_TRANSFER);
                return;
            }
            clan.addToClan(player, n);
            ClanEntryManager.getInstance().removePlayerApplication(clan.getClanId(), player.getObjectId());
        }
    }
}
