/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.utils.AutoBan;

class TelnetBan.3
extends TelnetCommand {
    TelnetBan.3(String string) {
        super(string);
    }

    @Override
    public String getUsage() {
        return "char_ban <name> <days>";
    }

    @Override
    public String handle(String[] stringArray) {
        int n;
        if (stringArray.length == 0 || stringArray[0].isEmpty()) {
            return null;
        }
        String string = stringArray[0];
        int n2 = n = stringArray.length > 1 && !stringArray[1].isEmpty() ? Integer.parseInt(stringArray[1]) : -1;
        if (n == 0) {
            if (!AutoBan.Banned(string, 0, 0, "unban", "telnet")) {
                return "Can't unban \"" + string + "\".\r\n";
            }
            return "\"" + string + "\" unbanned.\r\n";
        }
        if (!AutoBan.Banned(string, -100, n, "unban", "telnet")) {
            return "Can't ban \"" + string + "\".\r\n";
        }
        Player player = World.getPlayer(string);
        if (player != null) {
            player.kick();
        }
        return "\"" + string + "\" banned.\r\n";
    }
}
