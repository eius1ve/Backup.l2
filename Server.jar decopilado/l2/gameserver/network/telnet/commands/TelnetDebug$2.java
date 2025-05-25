/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.network.authcomm.AuthServerCommunication;
import l2.gameserver.network.telnet.TelnetCommand;

class TelnetDebug.2
extends TelnetCommand {
    TelnetDebug.2(String string) {
        super(string);
    }

    @Override
    public String getUsage() {
        return "asrestart";
    }

    @Override
    public String handle(String[] stringArray) {
        AuthServerCommunication.getInstance().restart();
        return "Restarted.\n";
    }
}
