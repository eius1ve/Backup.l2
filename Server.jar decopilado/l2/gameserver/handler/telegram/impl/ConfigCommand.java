/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.Config;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;

public class ConfigCommand
implements ITelegramCommandHandler {
    private final String[] L = new String[]{"/config"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /config parameter[=value]", "Enter configuration parameter in format: parameter[=value]");
        } else {
            String string3;
            String[] stringArray = string2.split("=");
            String string4 = stringArray.length == 1 ? ((string3 = Config.getField(string2)) == null ? "Not found." : string3) : (Config.setField(stringArray[0], stringArray[1]) ? "Done." : "Error!");
            TelegramApi.sendMessage(string, string4);
        }
    }

    @Override
    public String[] getCommands() {
        return this.L;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
