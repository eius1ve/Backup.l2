/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.ClanEntryStatus;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExPledgeRecruitApplyInfo;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class RequestPledgeRecruitApplyInfo
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
        if (!Config.ENABLE_CLAN_ENTRY) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.NOT_WORKING_PLEASE_TRY_AGAIN_LATER));
            return;
        }
        ClanEntryStatus clanEntryStatus = player.getClan() != null && player.isClanLeader() && ClanEntryManager.getInstance().isClanRegistered(player.getClanId()) ? ClanEntryStatus.ORDERED : (player.getClan() == null && ClanEntryManager.getInstance().isPlayerRegistered(player.getObjectId()) ? ClanEntryStatus.WAITING : ClanEntryStatus.DEFAULT);
        player.sendPacket((IStaticPacket)new ExPledgeRecruitApplyInfo(clanEntryStatus));
    }
}
