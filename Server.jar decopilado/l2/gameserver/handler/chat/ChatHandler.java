/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.chat;

import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.handler.chat.IChatHandler;
import l2.gameserver.network.l2.components.ChatType;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ChatHandler
extends AbstractHolder {
    private static final ChatHandler a = new ChatHandler();
    private IChatHandler[] a = new IChatHandler[ChatType.VALUES.length];

    public static ChatHandler getInstance() {
        return a;
    }

    private ChatHandler() {
    }

    public void register(IChatHandler iChatHandler) {
        this.a[iChatHandler.getType().ordinal()] = iChatHandler;
    }

    public IChatHandler getHandler(ChatType chatType) {
        return this.a[chatType.ordinal()];
    }

    @Override
    public int size() {
        return this.a.length;
    }

    @Override
    public void clear() {
    }
}
