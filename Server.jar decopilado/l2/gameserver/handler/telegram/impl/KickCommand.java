/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.utils.AdminFunctions;

public class KickCommand
implements ITelegramCommandHandler {
    private final String[] Y = new String[]{"/kick"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /kick <name>", "Enter player name to kick");
        } else if (AdminFunctions.kick(string2, "telegram bot user " + string)) {
            TelegramApi.sendMessage(string, "Player kicked.");
        } else {
            TelegramApi.sendMessage(string, "Player not found.");
        }
    }

    @Override
    public String[] getCommands() {
        return this.Y;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
