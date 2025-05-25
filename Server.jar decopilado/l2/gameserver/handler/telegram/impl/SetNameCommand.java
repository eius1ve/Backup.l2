/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.database.mysql;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.utils.Log;

public class SetNameCommand
implements ITelegramCommandHandler {
    private final String[] an = new String[]{"/setname"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /setname <player_name> <new_name>", "Enter time player name name and new name");
            return;
        }
        String[] stringArray = string2.split(" ");
        if (stringArray.length != 2) {
            TelegramApi.sendMessage(string, "Usage: /setname <player_name> <new_name>");
            return;
        }
        String string3 = stringArray[0];
        String string4 = stringArray[1];
        Player player = GameObjectsStorage.getPlayer(string3);
        if (player == null) {
            TelegramApi.sendMessage(string, "Player \"" + string3 + "\" not found.");
            return;
        }
        if (mysql.simple_get_int("count(*)", "characters", "`char_name` like '" + string4 + "'") > 0) {
            TelegramApi.sendMessage(string, "The name \"" + string4 + "\" is already in use. Please choose a different name.");
            return;
        }
        Log.add("Character " + player.getName() + " renamed to " + string4 + " via Telegram command", "renames");
        player.reName(string4);
        player.sendMessage("Your name has been changed to \"" + string4 + "\" via Telegram command.");
        TelegramApi.sendMessage(string, "Player \"" + string3 + "\" has been renamed to \"" + string4 + "\".");
    }

    @Override
    public String[] getCommands() {
        return this.an;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
