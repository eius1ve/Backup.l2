/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.geodata.PathFindBuffers;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;

public class PathfindCommand
implements ITelegramCommandHandler {
    private final String[] af = new String[]{"/pathfind"};

    @Override
    public void handle(String string, String string2) throws Exception {
        TelegramApi.sendMessage(string, String.valueOf(PathFindBuffers.getStats()));
    }

    @Override
    public String[] getCommands() {
        return this.af;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
