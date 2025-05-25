/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import java.util.Iterator;
import java.util.regex.Pattern;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;

public class FindCharCommand
implements ITelegramCommandHandler {
    private final String[] N = new String[]{"/find"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /find <name>", "Enter approximate player name to find in list: <name>");
            return;
        }
        Iterable<Player> iterable = GameObjectsStorage.getAllPlayersForIterate();
        Iterator<Player> iterator = iterable.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        Pattern pattern = Pattern.compile(string2 + "\\S*", 2);
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (!pattern.matcher(player.getName()).matches()) continue;
            ++n;
            stringBuilder.append(player).append("\n");
        }
        if (n == 0) {
            stringBuilder.append("Player not found.\n");
        } else {
            stringBuilder.append("=================================================\n");
            stringBuilder.append("Found: ").append(n).append(" players.\n");
        }
        TelegramApi.sendMessage(string, stringBuilder.toString());
    }

    @Override
    public String[] getCommands() {
        return this.N;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
