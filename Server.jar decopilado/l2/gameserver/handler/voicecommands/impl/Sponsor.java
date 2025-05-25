/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.handler.voicecommands.impl;

import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PledgeReceiveMemberInfo;
import l2.gameserver.network.l2.s2c.PledgeShowMemberListUpdate;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import org.apache.commons.lang3.StringUtils;

public class Sponsor
extends Functions
implements IVoicedCommandHandler {
    private static final String cb = "setsponsor";
    private static final String cc = "unsetsponsor";
    private static final String[] aJ = new String[]{"setsponsor", "unsetsponsor"};

    @Override
    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (player == null) {
            return false;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            return false;
        }
        if (StringUtils.isBlank((CharSequence)(string2 = StringUtils.trimToEmpty((String)string2)))) {
            return false;
        }
        Player player2 = World.getPlayer(string2);
        if (player2 == null) {
            return false;
        }
        if ((player.getClanPrivileges() & 0x100) != 256) {
            player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestOustAlly.NoMasterRights", player, new Object[0]));
            return false;
        }
        UnitMember unitMember = player.getClan().getAnyMember(player.getName());
        UnitMember unitMember2 = player.getClan().getAnyMember(player2.getName());
        if (unitMember == null || unitMember2 == null) {
            return false;
        }
        if (unitMember2.getPledgeType() != -1 || unitMember.getPledgeType() == -1) {
            return false;
        }
        if (string.startsWith(cb)) {
            if (unitMember.hasApprentice()) {
                player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestOustAlly.MemberAlreadyHasApprentice", player, new Object[0]));
                return false;
            }
            if (unitMember2.hasSponsor()) {
                player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestOustAlly.ApprenticeAlreadyHasSponsor", player, new Object[0]));
                return false;
            }
            unitMember.setApprentice(unitMember2.getObjectId());
            clan.broadcastToOnlineMembers(new PledgeShowMemberListUpdate(unitMember2));
            clan.broadcastToOnlineMembers(new L2GameServerPacket[]{((SystemMessage)new SystemMessage(SystemMsg.S2_HAS_BEEN_DESIGNATED_AS_THE_APPRENTICE_OF_CLAN_MEMBER_S1).addString(unitMember.getName())).addString(player2.getName())});
        } else if (string.startsWith(cc)) {
            if (!unitMember.hasApprentice()) {
                player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestOustAlly.MemberHasNoApprentice", player, new Object[0]));
                return false;
            }
            unitMember.setApprentice(0);
            clan.broadcastToOnlineMembers(new PledgeShowMemberListUpdate(unitMember2));
            clan.broadcastToOnlineMembers(new L2GameServerPacket[]{((SystemMessage)new SystemMessage(SystemMsg.S2_CLAN_MEMBER_C1S_APPRENTICE_HAS_BEEN_REMOVED).addString(unitMember.getName())).addString(player2.getName())});
        }
        if (unitMember2.isOnline()) {
            unitMember2.getPlayer().broadcastCharInfo();
        }
        player.sendPacket((IStaticPacket)new PledgeReceiveMemberInfo(unitMember));
        return false;
    }

    @Override
    public String[] getVoicedCommandList() {
        return aJ;
    }
}
