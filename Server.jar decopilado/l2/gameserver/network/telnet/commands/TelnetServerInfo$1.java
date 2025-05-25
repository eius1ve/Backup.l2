/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.GameServer;
import l2.gameserver.network.telnet.TelnetCommand;

class TelnetServerInfo.1
extends TelnetCommand {
    TelnetServerInfo.1(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "version";
    }

    @Override
    public String handle(String[] stringArray) {
        return "Rev." + GameServer.getInstance().getVersion().getRevisionNumber() + " Builded : " + GameServer.getInstance().getVersion().getBuildDate() + "\r\n";
    }
}
