/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.templates.InstantZone;
import l2.gameserver.templates.InstantZoneEntryType;

final class InstantZoneEntryType.1
extends InstantZoneEntryType {
    @Override
    public boolean canEnter(Player player, InstantZone instantZone) {
        if (player.isInParty()) {
            player.sendMessage(new CustomMessage("A_PARTY_CANNOT_BE_FORMED_IN_THIS_AREA", player, new Object[0]));
            return false;
        }
        CustomMessage customMessage = InstantZoneEntryType.a(player, instantZone);
        if (customMessage != null) {
            player.sendMessage(customMessage);
            return false;
        }
        return true;
    }

    @Override
    public boolean canReEnter(Player player, InstantZone instantZone) {
        if (player.isCursedWeaponEquipped() || player.isInFlyingTransform()) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_ENTER_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS", player, new Object[0]));
            return false;
        }
        return true;
    }
}
