/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.utils.AutoBan;

public class CharBanCommand
implements ITelegramCommandHandler {
    private final String[] J = new String[]{"/char_ban"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendMessage(string, "Usage: /char_ban <name> <days>", "Enter player name and ban period in format: <name> <days>");
        } else {
            String[] stringArray = string2.split(" ", 2);
            if (stringArray.length < 2) {
                TelegramApi.sendMessage(string, "Usage: /char_ban <name> <days>");
                return;
            }
            String string3 = stringArray[0];
            int n = Integer.parseInt(stringArray[1]);
            if (n == 0) {
                if (!AutoBan.Banned(string3, 0, 0, "unban", "telegram " + string)) {
                    TelegramApi.sendMessage(string, "Can't unban \"" + string3 + "\".");
                    return;
                }
                TelegramApi.sendMessage(string, "\"" + string3 + "\" unbanned.");
            } else {
                if (!AutoBan.Banned(string3, -100, n, "unban", "telegram " + string)) {
                    TelegramApi.sendMessage(string, "Can't ban \"" + string3 + "\".");
                    return;
                }
                Player player = World.getPlayer(string3);
                if (player != null) {
                    player.kick();
                }
                TelegramApi.sendMessage(string, "\"" + string3 + "\" banned.");
            }
        }
    }

    @Override
    public String[] getCommands() {
        return this.J;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
