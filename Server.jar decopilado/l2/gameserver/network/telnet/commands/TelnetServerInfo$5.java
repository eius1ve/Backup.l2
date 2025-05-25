/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.Shutdown;
import l2.gameserver.network.telnet.TelnetCommand;

class TelnetServerInfo.5
extends TelnetCommand {
    TelnetServerInfo.5(String string) {
        super(string);
    }

    @Override
    public String getUsage() {
        return "abort";
    }

    @Override
    public String handle(String[] stringArray) {
        Shutdown.getInstance().cancel();
        return "Aborted.\r\n";
    }
}
