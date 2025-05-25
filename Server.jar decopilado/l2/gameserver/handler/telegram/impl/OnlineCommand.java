/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.utils.Util;

public class OnlineCommand
implements ITelegramCommandHandler {
    private final String[] ae = new String[]{"/online"};

    @Override
    public void handle(String string, String string2) throws Exception {
        String string3 = this.c();
        TelegramApi.sendMessage(string, "Current online players: " + string3);
    }

    @Override
    public String[] getCommands() {
        return this.ae;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }

    private String c() {
        return Util.formatAdena(GameObjectsStorage.getAllPlayersCount());
    }
}
