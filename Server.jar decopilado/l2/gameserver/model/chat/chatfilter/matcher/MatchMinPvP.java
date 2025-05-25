/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.chat.chatfilter.matcher;

import l2.gameserver.model.Player;
import l2.gameserver.model.chat.chatfilter.ChatFilterMatcher;
import l2.gameserver.network.l2.components.ChatType;

public class MatchMinPvP
implements ChatFilterMatcher {
    private final int kX;

    public MatchMinPvP(int n) {
        this.kX = n;
    }

    @Override
    public boolean isMatch(Player player, ChatType chatType, String string, Player player2) {
        return player.getPvpKills() < this.kX;
    }
}
