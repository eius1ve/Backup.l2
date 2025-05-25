/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.commons.lang.StatsUtils;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;

public class MemCommand
implements ITelegramCommandHandler {
    private final String[] ab = new String[]{"/mem"};

    @Override
    public void handle(String string, String string2) throws Exception {
        String string3 = this.b();
        TelegramApi.sendMessage(string, string3);
    }

    @Override
    public String[] getCommands() {
        return this.ab;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }

    private String b() {
        return StatsUtils.getMemUsage().toString();
    }
}
