/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.commons.lang.StatsUtils;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;

public class GCCommand
implements ITelegramCommandHandler {
    private final String[] O = new String[]{"/gc"};

    @Override
    public void handle(String string, String string2) throws Exception {
        TelegramApi.sendMessage(string, String.valueOf(StatsUtils.getGCStats()));
    }

    @Override
    public String[] getCommands() {
        return this.O;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
