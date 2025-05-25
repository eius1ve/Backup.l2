/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  org.apache.commons.lang3.StringUtils
 */
package zones;

import l2.gameserver.model.Player;
import org.apache.commons.lang3.StringUtils;
import zones.KillRewardZone;

final class KillRewardZone.PlayerCheck.2
extends KillRewardZone.PlayerCheck {
    @Override
    boolean check(Player player, Player player2) {
        if (!player.isConnected() || !player2.isConnected()) {
            return false;
        }
        return !StringUtils.equals((CharSequence)player.getIP(), (CharSequence)player2.getIP());
    }
}
