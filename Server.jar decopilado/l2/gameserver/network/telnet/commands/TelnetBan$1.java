/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.utils.AdminFunctions;

class TelnetBan.1
extends TelnetCommand {
    TelnetBan.1(String string) {
        super(string);
    }

    @Override
    public String getUsage() {
        return "kick <name>";
    }

    @Override
    public String handle(String[] stringArray) {
        if (stringArray.length == 0 || stringArray[0].isEmpty()) {
            return null;
        }
        if (AdminFunctions.kick(stringArray[0], "telnet")) {
            return "Player kicked.\r\n";
        }
        return "Player not found.\r\n";
    }
}
