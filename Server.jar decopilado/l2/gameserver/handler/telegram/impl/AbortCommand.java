/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.Shutdown;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;

public class AbortCommand
implements ITelegramCommandHandler {
    private final String[] H = new String[]{"/abort"};

    @Override
    public void handle(String string, String string2) throws Exception {
        Shutdown.getInstance().cancel();
        TelegramApi.sendMessage(string, "Shutdown/Restart Aborted.");
    }

    @Override
    public String[] getCommands() {
        return this.H;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
