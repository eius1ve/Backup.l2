/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.entry.PledgeWaitingInfo;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class RequestPledgeDraftListApply
extends L2GameClientPacket {
    private int rG;
    private int gh;

    @Override
    protected void readImpl() {
        this.rG = this.readD();
        this.gh = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || !Config.ENABLE_CLAN_ENTRY) {
            return;
        }
        if (player.getClan() != null) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.ONLY_THE_CLAN_LEADER_OR_SOMEONE_WITH_RANK_MANAGEMENT_AUTHORITY_MAY_REGISTER_THE_CLAN));
            return;
        }
        switch (this.rG) {
            case 0: {
                if (!ClanEntryManager.getInstance().removeFromWaitingList(player.getObjectId(), false)) break;
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.ENTRY_APPLICATION_CANCELLED_YOU_MAY_APPLY_TO_A_NEW_CLAN_AFTER_5_MIN));
                break;
            }
            case 1: {
                PledgeWaitingInfo pledgeWaitingInfo = new PledgeWaitingInfo(player.getObjectId(), player.getLevel(), this.gh, player.getClassId().getId(), player.getName());
                if (ClanEntryManager.getInstance().addToWaitingList(player.getObjectId(), pledgeWaitingInfo)) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_JOINED_THE_WAITING_LIST_IF_YOU_DO_NOT_JOIN_ANY_CLAN_IN_30_D_YOUR_CHARACTER_WILL_BE_REMOVED_FROM_THE_LIST_IF_EXIT_WAITING_LIST_IS_USED_YOU_WILL_NOT_BE_ABLE_TO_JOIN_THE_WAITING_LIST_FOR_5_MIN));
                    break;
                }
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_MAY_APPLY_FOR_ENTRY_IN_S1_MIN_AFTER_CANCELLING_YOUR_APPLICATION).addNumber(ClanEntryManager.getInstance().getPlayerLockTime(player.getObjectId())));
                break;
            }
        }
    }
}
