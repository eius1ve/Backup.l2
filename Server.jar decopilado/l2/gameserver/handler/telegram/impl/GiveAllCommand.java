/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.ItemTemplate;

public class GiveAllCommand
implements ITelegramCommandHandler {
    private final String[] R = new String[]{"/give_all"};

    @Override
    public void handle(String string, String string2) throws Exception {
        long l;
        int n;
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /give_all <id> <count>", "Enter parameters in format: <id> <count>");
            return;
        }
        String[] stringArray = string2.split(" ", 2);
        if (stringArray.length < 2 || stringArray[0].isEmpty() || stringArray[1].isEmpty()) {
            TelegramApi.sendMessage(string, "Usage: /give_all <id> <count>");
            return;
        }
        try {
            n = Integer.parseInt(stringArray[0]);
            l = Long.parseLong(stringArray[1]);
        }
        catch (NumberFormatException numberFormatException) {
            TelegramApi.sendMessage(string, "Invalid item ID or count.");
            return;
        }
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
        if (itemTemplate == null) {
            TelegramApi.sendMessage(string, "Invalid item ID: " + n);
            return;
        }
        int n2 = 0;
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player == null || player.isInOfflineMode() || player.isLogoutStarted() || !player.getInventory().validateWeight(itemTemplate, l) || !player.getInventory().validateCapacity(itemTemplate, l)) continue;
            Functions.addItem(player, n, l);
            player.sendMessage(player.isLangRus() ? "\u0412\u044b \u0431\u044b\u043b\u0438 \u0432\u043e\u0437\u043d\u0430\u0433\u0440\u0430\u0436\u0434\u0435\u043d\u044b! " + itemTemplate.getName() + " " + l : "You have been rewarded! " + itemTemplate.getName() + " " + l);
            ++n2;
        }
        TelegramApi.sendMessage(string, "Rewarded " + n2 + " players with " + l + " " + itemTemplate.getName());
    }

    @Override
    public String[] getCommands() {
        return this.R;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
