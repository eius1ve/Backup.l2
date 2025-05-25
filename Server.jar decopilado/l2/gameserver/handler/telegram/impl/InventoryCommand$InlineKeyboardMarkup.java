/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import java.util.List;
import l2.gameserver.handler.telegram.impl.InventoryCommand;

private static class InventoryCommand.InlineKeyboardMarkup {
    private final List<List<InventoryCommand.InlineKeyboardButton>> aq;

    public InventoryCommand.InlineKeyboardMarkup(List<List<InventoryCommand.InlineKeyboardButton>> list) {
        this.aq = list;
    }
}
