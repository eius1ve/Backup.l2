/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import java.util.List;
import l2.gameserver.handler.telegram.impl.ListOnlinePlayersCommand;

private static class ListOnlinePlayersCommand.InlineKeyboardMarkup {
    private final List<List<ListOnlinePlayersCommand.InlineKeyboardButton>> as;

    public ListOnlinePlayersCommand.InlineKeyboardMarkup(List<List<ListOnlinePlayersCommand.InlineKeyboardButton>> list) {
        this.as = list;
    }
}
