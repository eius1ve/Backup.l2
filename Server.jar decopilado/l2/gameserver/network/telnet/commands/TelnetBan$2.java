/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.utils.AdminFunctions;

class TelnetBan.2
extends TelnetCommand {
    TelnetBan.2(String string) {
        super(string);
    }

    @Override
    public String getUsage() {
        return "chat_ban <name> <period>";
    }

    @Override
    public String handle(String[] stringArray) {
        if (stringArray.length == 0 || stringArray[0].isEmpty()) {
            return null;
        }
        int n = stringArray.length > 1 && !stringArray[1].isEmpty() ? Integer.parseInt(stringArray[1]) : -1;
        return AdminFunctions.banChat(null, "GMTelnet", stringArray[0], n, "telnet banned") + "\r\n";
    }
}
