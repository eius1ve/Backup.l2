/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Experience;

public class SetLevelCommand
implements ITelegramCommandHandler {
    private final String[] am = new String[]{"/setlevel"};

    private static boolean b(String string) {
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /setlevel <name> <level>", "Enter player name and level to set");
        } else {
            String[] stringArray = string2.split(" ");
            if (stringArray.length != 2 || stringArray[0].isEmpty() || !SetLevelCommand.b(stringArray[1])) {
                TelegramApi.sendMessage(string, "Usage: /setlevel <name> <level>");
                return;
            }
            String string3 = stringArray[0];
            int n = Integer.parseInt(stringArray[1]);
            Player player = GameObjectsStorage.getPlayer(string3);
            if (player == null) {
                TelegramApi.sendMessage(string, "Player not found.");
                return;
            }
            if (this.a(player, n)) {
                TelegramApi.sendMessage(string, "Level of " + string3 + " set to " + n);
            } else {
                TelegramApi.sendMessage(string, "Failed to set level. Please check the level range.");
            }
        }
    }

    @Override
    public String[] getCommands() {
        return this.am;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }

    private boolean a(Player player, int n) {
        if (n < 1 || n > Experience.getMaxLevel()) {
            return false;
        }
        long l = Experience.LEVEL[n] - player.getExp();
        player.addExpAndSp(l, 0L);
        return true;
    }
}
