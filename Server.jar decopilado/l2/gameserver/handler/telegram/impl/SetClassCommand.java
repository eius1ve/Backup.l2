/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;

public class SetClassCommand
implements ITelegramCommandHandler {
    private final String[] ak = new String[]{"/setclass"};

    @Override
    public void handle(String string, String string2) throws Exception {
        int n;
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /setclass <player_name> <class_id>", "Enter player name and class ID");
            return;
        }
        String[] stringArray = string2.trim().split(" ");
        if (stringArray.length != 2) {
            TelegramApi.sendMessage(string, "Invalid parameters. Usage: /setclass <player_name> <class_id>");
            return;
        }
        String string3 = stringArray[0];
        try {
            n = Integer.parseInt(stringArray[1]);
        }
        catch (NumberFormatException numberFormatException) {
            TelegramApi.sendMessage(string, "Invalid class ID. It must be a number.");
            return;
        }
        if (n > 118) {
            TelegramApi.sendMessage(string, "There are no classes over 118 ID.");
            return;
        }
        Player player = GameObjectsStorage.getPlayer(string3);
        if (player == null) {
            TelegramApi.sendMessage(string, "Player \"" + string3 + "\" not found in game.");
            return;
        }
        player.setClassId(n, false, false);
        player.sendMessage("Your class has been changed via Telegram command.");
        TelegramApi.sendMessage(string, "Class ID " + n + " set for player " + string3);
        player.broadcastCharInfo();
        player.sendSkillList();
    }

    @Override
    public String[] getCommands() {
        return this.ak;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
