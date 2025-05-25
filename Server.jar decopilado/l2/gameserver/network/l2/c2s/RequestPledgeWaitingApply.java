/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.entry.PledgeApplicantInfo;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.ClanEntryStatus;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExPledgeRecruitApplyInfo;
import l2.gameserver.network.l2.s2c.ExPledgeWaitingListAlarm;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.utils.HtmlUtils;

public class RequestPledgeWaitingApply
extends L2GameClientPacket {
    private int gh;
    private int fY;
    private String dL;

    @Override
    protected void readImpl() throws Exception {
        this.gh = this.readD();
        this.fY = this.readD();
        this.dL = this.readS();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || player.getClan() != null || !Config.ENABLE_CLAN_ENTRY) {
            return;
        }
        this.dL = HtmlUtils.sanitizeHtml(this.dL);
        Clan clan = ClanTable.getInstance().getClan(this.fY);
        if (clan == null) {
            return;
        }
        PledgeApplicantInfo pledgeApplicantInfo = new PledgeApplicantInfo(player.getObjectId(), player.getName(), player.getLevel(), this.gh, clan.getClanId(), this.dL);
        if (ClanEntryManager.getInstance().addPlayerApplicationToClan(clan.getClanId(), pledgeApplicantInfo)) {
            player.sendPacket((IStaticPacket)new ExPledgeRecruitApplyInfo(ClanEntryStatus.WAITING));
            Player player2 = GameObjectsStorage.getPlayer(clan.getLeaderId());
            if (player2 != null) {
                player2.sendPacket((IStaticPacket)ExPledgeWaitingListAlarm.STATIC_PACKET);
            }
        } else {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_MAY_APPLY_FOR_ENTRY_IN_S1_MIN_AFTER_CANCELLING_YOUR_APPLICATION).addNumber(ClanEntryManager.getInstance().getPlayerLockTime(player.getObjectId())));
        }
    }
}
