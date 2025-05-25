/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.taskmanager.DelayedItemsManager;
import l2.gameserver.templates.item.ItemTemplate;

public class GiveItemCommand
implements ITelegramCommandHandler {
    private final String[] S = new String[]{"/give_item"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /give_item <name> <id> <count>", "Enter parameters in format: <name> <id> <count>");
        } else {
            long l;
            int n;
            String[] stringArray = string2.split(" ", 3);
            if (stringArray.length < 3 || stringArray[0].isEmpty() || stringArray[1].isEmpty() || stringArray[2].isEmpty()) {
                TelegramApi.sendMessage(string, "Usage: /give_item <name> <id> <count>");
                return;
            }
            String string3 = stringArray[0];
            try {
                n = Integer.parseInt(stringArray[1]);
                l = Long.parseLong(stringArray[2]);
            }
            catch (NumberFormatException numberFormatException) {
                TelegramApi.sendMessage(string, "Invalid item ID or count.");
                return;
            }
            int n2 = CharacterDAO.getInstance().getObjectIdByName(string3);
            if (n2 <= 0) {
                TelegramApi.sendMessage(string, "Player not found: " + string3);
                return;
            }
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
            if (itemTemplate != null) {
                DelayedItemsManager.getInstance().addDelayed(n2, n, Math.toIntExact(l), 0, 0, 0, "Telegram Bot Reward Item " + n + "(" + l + ") " + System.currentTimeMillis());
                TelegramApi.sendMessage(string, "Issued " + l + " " + itemTemplate.getName() + " to " + string3 + ".");
            } else {
                TelegramApi.sendMessage(string, "Invalid item " + n + " or count.");
            }
        }
    }

    @Override
    public String[] getCommands() {
        return this.S;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
