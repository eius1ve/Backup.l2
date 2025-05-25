/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.chat.chatfilter.matcher;

import java.util.Deque;
import java.util.Iterator;
import l2.gameserver.model.Player;
import l2.gameserver.model.chat.chatfilter.ChatFilterMatcher;
import l2.gameserver.model.chat.chatfilter.ChatMsg;
import l2.gameserver.network.l2.components.ChatType;

public class MatchFloodLimit
implements ChatFilterMatcher {
    private final int kS;
    private final int kT;
    private final int kU;

    public MatchFloodLimit(int n, int n2, int n3) {
        this.kS = n;
        this.kT = n2;
        this.kU = n3;
    }

    @Override
    public boolean isMatch(Player player, ChatType chatType, String string, Player player2) {
        int n;
        int n2 = n = (int)(System.currentTimeMillis() / 1000L);
        int n3 = 0;
        int n4 = string.hashCode();
        Deque<ChatMsg> deque = player.getMessageBucket();
        Iterator<ChatMsg> iterator = deque.descendingIterator();
        while (iterator.hasNext()) {
            ChatMsg chatMsg = iterator.next();
            if (chatMsg.chatType != chatType || chatMsg.msgHashcode != n4) continue;
            n2 = chatMsg.time;
            if (this.kU != ++n3) continue;
            break;
        }
        return this.kU <= (n3 -= (n - n2) / this.kT * this.kS);
    }
}
