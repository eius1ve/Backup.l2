/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class RequestPledgeWaitingUserAccept
extends L2GameClientPacket {
    private boolean ea;
    private int ph;
    private int rM;

    @Override
    protected void readImpl() throws Exception {
        this.ea = this.readD() == 1;
        this.ph = this.readD();
        this.rM = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || player.getClan() == null || !Config.ENABLE_CLAN_ENTRY) {
            return;
        }
        Clan clan = player.getClan();
        Player player2 = GameObjectsStorage.getPlayer(this.ph);
        if (this.ea) {
            if (player2 != null && player2.getClan() == null && clan != null) {
                if (!clan.canInvite()) {
                    player.sendPacket((IStaticPacket)SystemMsg.AFTER_A_CLAN_MEMBER_IS_DISMISSED_FROM_A_CLAN_THE_CLAN_MUST_WAIT_AT_LEAST_A_DAY_BEFORE_ACCEPTING_A_NEW_MEMBER);
                    return;
                }
                if (this.ph == player.getObjectId()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_ASK_YOURSELF_TO_APPLY_TO_A_CLAN);
                    return;
                }
                if ((player.getClanPrivileges() & 2) != 2) {
                    player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_LEADER_CAN_GIVE_OUT_INVITATIONS);
                    return;
                }
                int n = -128;
                block0 : switch (this.rM) {
                    case -1: {
                        n = -1;
                        break;
                    }
                    case 0: {
                        n = 0;
                        break;
                    }
                    case 100: {
                        n = 100;
                        break;
                    }
                    case 200: {
                        n = 200;
                        break;
                    }
                    case 1001: {
                        n = 1001;
                        break;
                    }
                    case 1002: {
                        n = 1002;
                        break;
                    }
                    case 2001: {
                        n = 2001;
                        break;
                    }
                    case 2002: {
                        n = 2002;
                        break;
                    }
                    default: {
                        for (SubUnit subUnit : clan.getAllSubUnits()) {
                            if (subUnit == null || subUnit.getLeaderObjectId() != this.rM) continue;
                            n = subUnit.getType();
                            break block0;
                        }
                    }
                }
                if (clan.getUnitMembersSize(n) >= clan.getSubPledgeLimit(n)) {
                    if (n == 0) {
                        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_FULL_AND_CANNOT_ACCEPT_ADDITIONAL_CLAN_MEMBERS_AT_THIS_TIME).addString(clan.getName()));
                    } else {
                        player.sendPacket((IStaticPacket)SystemMsg.THE_ACADEMYROYAL_GUARDORDER_OF_KNIGHTS_IS_FULL_AND_CANNOT_ACCEPT_NEW_MEMBERS_AT_THIS_TIME);
                    }
                    return;
                }
                Player player3 = GameObjectsStorage.getPlayer(this.ph);
                if (player3 == null) {
                    player.sendActionFailed();
                    return;
                }
                if (player3.getClan() == player.getClan()) {
                    player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
                    return;
                }
                if (player3.getClan() != null) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_ALREADY_A_MEMBER_OF_ANOTHER_CLAN).addName(player3));
                    return;
                }
                if (!player3.getPlayerAccess().CanJoinClan || !player3.canJoinClan()) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_CANNOT_JOIN_THE_CLAN_BECAUSE_ONE_DAY_HAS_NOT_YET_PASSED_SINCE_THEY_LEFT_ANOTHER_CLAN).addName(player));
                    return;
                }
                if (n == -1 && (player3.getLevel() > 40 || player3.getClassId().getLevel() > 2)) {
                    player.sendPacket((IStaticPacket)SystemMsg.TO_JOIN_A_CLAN_ACADEMY_CHARACTERS_MUST_BE_LEVEL_40_OR_BELOW_NOT_BELONG_ANOTHER_CLAN_AND_NOT_YET_COMPLETED_THEIR_2ND_CLASS_TRANSFER);
                    return;
                }
                ClanEntryManager.getInstance().removePlayerApplication(clan.getClanId(), player2.getObjectId());
                clan.addToClan(player2, n);
            }
        } else {
            ClanEntryManager.getInstance().removePlayerApplication(clan.getClanId(), player2.getObjectId());
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_REJECTED_CLAN_ENTRY_APPLICATION).addName(player2));
        }
    }
}
