/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExPledgeWaitingListApplied;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class RequestPledgeWaitingApplied
extends L2GameClientPacket {
    @Override
    protected void readImpl() throws Exception {
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || player.getClan() != null) {
            return;
        }
        if (!Config.ENABLE_CLAN_ENTRY) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.NOT_WORKING_PLEASE_TRY_AGAIN_LATER));
            return;
        }
        int n = ClanEntryManager.getInstance().getClanIdForPlayerApplication(player.getObjectId());
        if (n > 0) {
            player.sendPacket((IStaticPacket)new ExPledgeWaitingListApplied(n, player.getObjectId()));
        }
    }
}
