/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.network.telnet.commands.TelnetPerfomance;

class TelnetPerfomance.5
extends TelnetCommand {
    TelnetPerfomance.5(String string) {
        super(string);
    }

    @Override
    public String getUsage() {
        return "gc";
    }

    @Override
    public String handle(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TelnetPerfomance.getGCStats());
        return stringBuilder.toString();
    }
}
