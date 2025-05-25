/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.InstantZone;
import l2.gameserver.templates.InstantZoneEntryType;

final class InstantZoneEntryType.2
extends InstantZoneEntryType {
    @Override
    public boolean canEnter(Player player, InstantZone instantZone) {
        Party party = player.getParty();
        if (party == null) {
            player.sendMessage(new CustomMessage("YOU_ARE_NOT_CURRENTLY_IN_A_PARTY_SO_YOU_CANNOT_ENTER", player, new Object[0]));
            return false;
        }
        if (!party.isLeader(player)) {
            player.sendMessage(new CustomMessage("ONLY_A_PARTY_LEADER_CAN_MAKE_THE_REQUEST_TO_ENTER", player, new Object[0]));
            return false;
        }
        if (party.getMemberCount() < instantZone.getMinParty()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_MUST_HAVE_A_MINIMUM_OF_S1_PEOPLE_TO_ENTER_THIS_INSTANT_ZONE).addNumber(instantZone.getMinParty()));
            return false;
        }
        if (party.getMemberCount() > instantZone.getMaxParty()) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_ENTER_DUE_TO_THE_PARTY_HAVING_EXCEEDED_THE_LIMIT", player, new Object[0]));
            return false;
        }
        for (Player player2 : party.getPartyMembers()) {
            if (!player.isInRange(player2, 500L)) {
                for (Player player3 : player2) {
                    player3.sendMessage(new CustomMessage("C1_IS_IN_A_LOCATION_WHICH_CANNOT_BE_ENTERED_THEREFORE_IT_CANNOT_BE_PROCESSED", player3, player2));
                }
                return false;
            }
            CustomMessage customMessage = InstantZoneEntryType.a(player2, instantZone);
            if (customMessage == null) continue;
            player.sendMessage(customMessage);
            return false;
        }
        return true;
    }

    @Override
    public boolean canReEnter(Player player, InstantZone instantZone) {
        Party party = player.getParty();
        if (party == null) {
            player.sendMessage(new CustomMessage("YOU_ARE_NOT_CURRENTLY_IN_A_PARTY_SO_YOU_CANNOT_ENTER", player, new Object[0]));
            return false;
        }
        if (party.getMemberCount() > instantZone.getMaxParty()) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_ENTER_DUE_TO_THE_PARTY_HAVING_EXCEEDED_THE_LIMIT", player, new Object[0]));
            return false;
        }
        if (player.isCursedWeaponEquipped() || player.isInFlyingTransform()) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_ENTER_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS", player, new Object[0]));
            return false;
        }
        return true;
    }
}
