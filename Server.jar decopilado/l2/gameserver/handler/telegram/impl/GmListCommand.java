/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import java.util.List;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.Player;
import l2.gameserver.tables.GmListTable;

public class GmListCommand
implements ITelegramCommandHandler {
    private final String[] T = new String[]{"/gmlist", "/gms"};

    @Override
    public void handle(String string, String string2) throws Exception {
        List<Player> list = GmListTable.getAllGMs();
        int n = list.size();
        if (n == 0) {
            TelegramApi.sendMessage(string, "GMs not found.");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(list.get(i)).append("\n");
        }
        stringBuilder.append("Found: ").append(n).append(" GMs.\n");
        TelegramApi.sendMessage(string, stringBuilder.toString());
    }

    @Override
    public String[] getCommands() {
        return this.T;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
