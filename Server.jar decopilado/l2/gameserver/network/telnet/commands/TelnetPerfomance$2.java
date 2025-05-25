/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.network.telnet.commands.TelnetPerfomance;

class TelnetPerfomance.2
extends TelnetCommand {
    TelnetPerfomance.2(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "mem";
    }

    @Override
    public String handle(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TelnetPerfomance.getMemUsage());
        return stringBuilder.toString();
    }
}
