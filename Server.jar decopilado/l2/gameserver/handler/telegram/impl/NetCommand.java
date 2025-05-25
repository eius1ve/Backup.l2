/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.commons.net.nio.impl.SelectorThread;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;

public class NetCommand
implements ITelegramCommandHandler {
    private final String[] ac = new String[]{"/net"};

    @Override
    public void handle(String string, String string2) throws Exception {
        TelegramApi.sendMessage(string, String.valueOf(SelectorThread.getStats()));
    }

    @Override
    public String[] getCommands() {
        return this.ac;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
