/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.chat.chatfilter.matcher;

import l2.gameserver.model.Player;
import l2.gameserver.model.chat.chatfilter.ChatFilterMatcher;
import l2.gameserver.network.l2.components.ChatType;

public class MatchLogicalOr
implements ChatFilterMatcher {
    private final ChatFilterMatcher[] b;

    public MatchLogicalOr(ChatFilterMatcher[] chatFilterMatcherArray) {
        this.b = chatFilterMatcherArray;
    }

    @Override
    public boolean isMatch(Player player, ChatType chatType, String string, Player player2) {
        for (ChatFilterMatcher chatFilterMatcher : this.b) {
            if (!chatFilterMatcher.isMatch(player, chatType, string, player2)) continue;
            return true;
        }
        return false;
    }
}
