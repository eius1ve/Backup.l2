/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PledgeReceiveMemberInfo;
import l2.gameserver.network.l2.s2c.PledgeShowMemberListUpdate;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class RequestPledgeSetAcademyMaster
extends L2GameClientPacket {
    private int _mode;
    private String ev;
    private String ew;

    @Override
    protected void readImpl() {
        this._mode = this.readD();
        this.ev = this.readS(16);
        this.ew = this.readS(16);
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            return;
        }
        if ((player.getClanPrivileges() & 0x100) == 256) {
            UnitMember unitMember = player.getClan().getAnyMember(this.ev);
            UnitMember unitMember2 = player.getClan().getAnyMember(this.ew);
            if (unitMember != null && unitMember2 != null) {
                if (unitMember2.getPledgeType() != -1 || unitMember.getPledgeType() == -1) {
                    return;
                }
                if (this._mode == 1) {
                    if (unitMember.hasApprentice()) {
                        player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestOustAlly.MemberAlreadyHasApprentice", player, new Object[0]));
                        return;
                    }
                    if (unitMember2.hasSponsor()) {
                        player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestOustAlly.ApprenticeAlreadyHasSponsor", player, new Object[0]));
                        return;
                    }
                    unitMember.setApprentice(unitMember2.getObjectId());
                    clan.broadcastToOnlineMembers(new PledgeShowMemberListUpdate(unitMember2));
                    clan.broadcastToOnlineMembers(new L2GameServerPacket[]{((SystemMessage)new SystemMessage(SystemMsg.S2_HAS_BEEN_DESIGNATED_AS_THE_APPRENTICE_OF_CLAN_MEMBER_S1).addString(unitMember.getName())).addString(unitMember2.getName())});
                } else {
                    if (!unitMember.hasApprentice()) {
                        player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestOustAlly.MemberHasNoApprentice", player, new Object[0]));
                        return;
                    }
                    unitMember.setApprentice(0);
                    clan.broadcastToOnlineMembers(new PledgeShowMemberListUpdate(unitMember2));
                    clan.broadcastToOnlineMembers(new L2GameServerPacket[]{((SystemMessage)new SystemMessage(SystemMsg.S2_CLAN_MEMBER_C1S_APPRENTICE_HAS_BEEN_REMOVED).addString(unitMember.getName())).addString(unitMember2.getName())});
                }
                if (unitMember2.isOnline()) {
                    unitMember2.getPlayer().broadcastCharInfo();
                }
                player.sendPacket((IStaticPacket)new PledgeReceiveMemberInfo(unitMember));
            }
        } else {
            player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestOustAlly.NoMasterRights", player, new Object[0]));
        }
    }
}
