/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.GameServer;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;

public class VersionCommand
implements ITelegramCommandHandler {
    private final String[] aw = new String[]{"/version", "/ver"};

    @Override
    public void handle(String string, String string2) throws Exception {
        String string3 = "Rev. " + GameServer.getInstance().getVersion().getRevisionNumber() + " Built on: " + GameServer.getInstance().getVersion().getBuildDate();
        TelegramApi.sendMessage(string, string3);
    }

    @Override
    public String[] getCommands() {
        return this.aw;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
