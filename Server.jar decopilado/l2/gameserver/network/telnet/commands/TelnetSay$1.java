/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.Announcements;
import l2.gameserver.network.telnet.TelnetCommand;

class TelnetSay.1
extends TelnetCommand {
    TelnetSay.1(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "announce <text>";
    }

    @Override
    public String handle(String[] stringArray) {
        if (stringArray.length == 0) {
            return null;
        }
        Announcements.getInstance().announceToAll(stringArray[0]);
        return "Announcement sent.\r\n";
    }
}
