/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.utils.AutoBan;

public class NoSpamCommand
implements ITelegramCommandHandler {
    private final String[] ad = new String[]{"/nospam"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /nospam <name> <period>", "Enter player name and period in format: <name> <period>");
        } else {
            String[] stringArray = string2.split(" ", 2);
            if (stringArray.length < 2) {
                TelegramApi.sendMessage(string, "Usage: /nospam <name> <period>");
                return;
            }
            String string3 = stringArray[0];
            int n = Integer.parseInt(stringArray[1]);
            String string4 = AutoBan.noSpam(string3, n) + "\n";
            TelegramApi.sendMessage(string, string4);
        }
    }

    @Override
    public String[] getCommands() {
        return this.ad;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
