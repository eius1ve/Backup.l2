/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;

public class WhoisCharCommand
implements ITelegramCommandHandler {
    private final String[] ax = new String[]{"/whois", "/who"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /whois <name>", "Enter player name to show details: <name>");
            return;
        }
        Player player = GameObjectsStorage.getPlayer(string2);
        if (player == null) {
            TelegramApi.sendMessage(string, "Player not found.");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name: .................... ").append(player.getName()).append("\n");
        stringBuilder.append("ID: ...................... ").append(player.getObjectId()).append("\n");
        stringBuilder.append("Account Name: ............ ").append(player.getAccountName()).append("\n");
        stringBuilder.append("IP: ...................... ").append(player.getIP()).append("\n");
        stringBuilder.append("Level: ................... ").append(player.getLevel()).append("\n");
        stringBuilder.append("Location: ................ ").append(player.getLoc()).append("\n");
        if (player.getClan() != null) {
            stringBuilder.append("Clan: .................... ").append(player.getClan().getName()).append("\n");
            if (player.getAlliance() != null) {
                stringBuilder.append("Ally: .................... ").append(player.getAlliance().getAllyName()).append("\n");
            }
        }
        stringBuilder.append("Offline: ................. ").append(player.isInOfflineMode()).append("\n");
        stringBuilder.append(player.toString()).append("\n");
        TelegramApi.sendMessage(string, stringBuilder.toString());
    }

    @Override
    public String[] getCommands() {
        return this.ax;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
