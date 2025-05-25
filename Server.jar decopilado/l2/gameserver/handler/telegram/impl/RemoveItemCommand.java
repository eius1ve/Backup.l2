/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.utils.ItemFunctions;

public class RemoveItemCommand
implements ITelegramCommandHandler {
    private final String[] ai = new String[]{"/remove_item"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /remove_item <name> <id> <count>", "Enter private message in format: <name> <message>");
            return;
        }
        String[] stringArray = string2.split(" ");
        if (stringArray.length != 3 || stringArray[0].isEmpty() || stringArray[1].isEmpty() || stringArray[2].isEmpty()) {
            TelegramApi.sendMessage(string, "Usage: /remove_item <name> <id> <count>");
            return;
        }
        try {
            int n = Integer.parseInt(stringArray[1]);
            long l = Long.parseLong(stringArray[2]);
            String string3 = stringArray[0];
            Player player = GameObjectsStorage.getPlayer(string3);
            if (player == null) {
                TelegramApi.sendMessage(string, "Player not found.");
                return;
            }
            ItemInstance itemInstance = player.getInventory().getItemByItemId(n);
            if (itemInstance == null) {
                TelegramApi.sendMessage(string, "Item not found in " + string3 + " player's inventory.");
                return;
            }
            if (itemInstance.getCount() < l) {
                TelegramApi.sendMessage(string, "Player " + string3 + " does not have enough items to remove.");
                return;
            }
            TelegramApi.sendMessage(string, "Item removed. " + itemInstance.getName() + " (" + l + ")");
            ItemFunctions.removeItem(player, n, l, true);
        }
        catch (Exception exception) {
            TelegramApi.sendMessage(string, "Error: " + exception.getMessage());
        }
    }

    @Override
    public String[] getCommands() {
        return this.ai;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
