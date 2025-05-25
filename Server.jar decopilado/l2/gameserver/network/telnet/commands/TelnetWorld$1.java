/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import java.util.Iterator;
import java.util.regex.Pattern;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.telnet.TelnetCommand;

class TelnetWorld.1
extends TelnetCommand {
    TelnetWorld.1(String string) {
        super(string);
    }

    @Override
    public String getUsage() {
        return "find <name>";
    }

    @Override
    public String handle(String[] stringArray) {
        if (stringArray.length == 0) {
            return null;
        }
        Iterable<Player> iterable = GameObjectsStorage.getAllPlayersForIterate();
        Iterator<Player> iterator = iterable.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        Pattern pattern = Pattern.compile(stringArray[0] + "\\S+", 2);
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (!pattern.matcher(player.getName()).matches()) continue;
            ++n;
            stringBuilder.append(player).append("\r\n");
        }
        if (n == 0) {
            stringBuilder.append("Player not found.").append("\r\n");
        } else {
            stringBuilder.append("=================================================\n");
            stringBuilder.append("Found: ").append(n).append(" players.").append("\r\n");
        }
        return stringBuilder.toString();
    }
}
