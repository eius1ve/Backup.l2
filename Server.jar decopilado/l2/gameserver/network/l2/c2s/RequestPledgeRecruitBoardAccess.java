/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.model.pledge.entry.PledgeRecruitInfo;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.HtmlUtils;

public class RequestPledgeRecruitBoardAccess
extends L2GameClientPacket {
    private int rG;
    private int gh;
    private String dM;
    private String eu;
    private int pl;
    private int rM;

    @Override
    protected void readImpl() {
        this.rG = this.readD();
        this.gh = this.readD();
        this.dM = this.readS();
        this.eu = this.readS();
        this.pl = this.readD();
        this.rM = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || !Config.ENABLE_CLAN_ENTRY) {
            return;
        }
        this.eu = HtmlUtils.sanitizeHtml(this.eu);
        this.dM = HtmlUtils.sanitizeHtml(this.dM);
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.ONLY_THE_CLAN_LEADER_OR_SOMEONE_WITH_RANK_MANAGEMENT_AUTHORITY_MAY_REGISTER_THE_CLAN));
            return;
        }
        if ((player.getClanPrivileges() & 0x10) != 16) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.ONLY_THE_CLAN_LEADER_OR_SOMEONE_WITH_RANK_MANAGEMENT_AUTHORITY_MAY_CHANGE_CLAN_INFORMATION));
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
        PledgeRecruitInfo pledgeRecruitInfo = new PledgeRecruitInfo(clan.getClanId(), this.gh, this.dM, this.eu, this.pl, n);
        switch (this.rG) {
            case 0: {
                ClanEntryManager.getInstance().removeFromClanList(clan.getClanId());
                break;
            }
            case 1: {
                if (ClanEntryManager.getInstance().addToClanList(clan.getClanId(), pledgeRecruitInfo)) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.ENTRY_APPLICATION_COMPLETE_USE_MY_APPLICATION_TO_CHECK_OR_CANCEL_YOUR_APPLICATION_APPLICATION_IS_AUTOMATICALLY_CANCELLED_AFTER_30_D_IF_YOU_CANCEL_APPLICATION_YOU_CANNOT_APPLY_AGAIN_FOR_5_MIN));
                    break;
                }
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_MAY_APPLY_FOR_ENTRY_IN_S1_MIN_AFTER_CANCELLING_YOUR_APPLICATION).addNumber(ClanEntryManager.getInstance().getClanLockTime(clan.getClanId())));
                break;
            }
            case 2: {
                if (ClanEntryManager.getInstance().updateClanList(clan.getClanId(), pledgeRecruitInfo)) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.ENTRY_APPLICATION_COMPLETE_USE_MY_APPLICATION_TO_CHECK_OR_CANCEL_YOUR_APPLICATION_APPLICATION_IS_AUTOMATICALLY_CANCELLED_AFTER_30_D_IF_YOU_CANCEL_APPLICATION_YOU_CANNOT_APPLY_AGAIN_FOR_5_MIN));
                    break;
                }
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_MAY_APPLY_FOR_ENTRY_IN_S1_MIN_AFTER_CANCELLING_YOUR_APPLICATION).addNumber(ClanEntryManager.getInstance().getClanLockTime(clan.getClanId())));
            }
        }
    }
}
