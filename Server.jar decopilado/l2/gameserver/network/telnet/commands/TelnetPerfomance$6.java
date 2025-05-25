/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.commons.net.nio.impl.SelectorThread;
import l2.gameserver.network.telnet.TelnetCommand;

class TelnetPerfomance.6
extends TelnetCommand {
    TelnetPerfomance.6(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "net";
    }

    @Override
    public String handle(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SelectorThread.getStats());
        return stringBuilder.toString();
    }
}
