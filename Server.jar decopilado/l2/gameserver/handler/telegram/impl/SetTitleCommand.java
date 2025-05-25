/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;

public class SetTitleCommand
implements ITelegramCommandHandler {
    private final String[] aq = new String[]{"/settitle"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /settitle <player_name> <title>", "Enter player name and title to set");
            return;
        }
        String[] stringArray = string2.split(" ");
        if (stringArray.length < 2) {
            TelegramApi.sendMessage(string, "Usage: /settitle <player_name> <title>");
            return;
        }
        String string3 = stringArray[0];
        String string4 = string2.substring(string3.length()).trim();
        Player player = GameObjectsStorage.getPlayer(string3);
        if (player == null) {
            TelegramApi.sendMessage(string, "Player not found.");
            return;
        }
        player.setTitle(string4);
        player.sendMessage("Your title has been changed via Telegram.");
        player.sendChanges();
        TelegramApi.sendMessage(string, "Title of " + string3 + " set to: " + string4);
    }

    @Override
    public String[] getCommands() {
        return this.aq;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
