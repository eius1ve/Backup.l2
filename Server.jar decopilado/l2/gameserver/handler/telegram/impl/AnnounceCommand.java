/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.Announcements;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;

public class AnnounceCommand
implements ITelegramCommandHandler {
    private final String[] I = new String[]{"/announce"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /announce <message>", "Enter your announcement message");
        } else {
            TelegramApi.sendMessage(string, "Announce send: " + string2);
            Announcements.getInstance().announceToAll(string2);
        }
    }

    @Override
    public String[] getCommands() {
        return this.I;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
