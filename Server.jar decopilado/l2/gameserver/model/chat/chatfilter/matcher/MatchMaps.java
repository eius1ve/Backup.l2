/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.chat.chatfilter.matcher;

import l2.gameserver.model.Player;
import l2.gameserver.model.chat.chatfilter.ChatFilterMatcher;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.utils.MapRegionUtils;

public class MatchMaps
implements ChatFilterMatcher {
    private final int[] aE;

    public MatchMaps(int[] nArray) {
        this.aE = nArray;
    }

    @Override
    public boolean isMatch(Player player, ChatType chatType, String string, Player player2) {
        int n = MapRegionUtils.regionX(player);
        int n2 = MapRegionUtils.regionY(player);
        for (int i = 0; i < this.aE.length; i += 2) {
            int n3 = this.aE[i];
            int n4 = this.aE[i + 1];
            if (n3 != n || n4 != n2) continue;
            return true;
        }
        return false;
    }
}
