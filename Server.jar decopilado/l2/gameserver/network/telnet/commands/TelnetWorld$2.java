/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.telnet.TelnetCommand;

class TelnetWorld.2
extends TelnetCommand {
    TelnetWorld.2(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "whois <name>";
    }

    @Override
    public String handle(String[] stringArray) {
        if (stringArray.length == 0) {
            return null;
        }
        Player player = GameObjectsStorage.getPlayer(stringArray[0]);
        if (player == null) {
            return "Player not found.\n";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name: .................... ").append(player.getName()).append("\r\n");
        stringBuilder.append("ID: ...................... ").append(player.getObjectId()).append("\r\n");
        stringBuilder.append("Account Name: ............ ").append(player.getAccountName()).append("\r\n");
        stringBuilder.append("IP: ...................... ").append(player.getIP()).append("\r\n");
        stringBuilder.append("Level: ................... ").append(player.getLevel()).append("\r\n");
        stringBuilder.append("Location: ................ ").append(player.getLoc()).append("\r\n");
        if (player.getClan() != null) {
            stringBuilder.append("Clan: .................... ").append(player.getClan().getName()).append("\r\n");
            if (player.getAlliance() != null) {
                stringBuilder.append("Ally: .................... ").append(player.getAlliance().getAllyName()).append("\r\n");
            }
        }
        stringBuilder.append("Offline: ................. ").append(player.isInOfflineMode()).append("\r\n");
        stringBuilder.append(player.toString()).append("\r\n");
        return stringBuilder.toString();
    }
}
