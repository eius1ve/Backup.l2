/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import java.util.List;
import l2.gameserver.handler.telegram.impl.ListClansCommand;

private static class ListClansCommand.InlineKeyboardMarkup {
    private final List<List<ListClansCommand.InlineKeyboardButton>> ar;

    public ListClansCommand.InlineKeyboardMarkup(List<List<ListClansCommand.InlineKeyboardButton>> list) {
        this.ar = list;
    }
}
