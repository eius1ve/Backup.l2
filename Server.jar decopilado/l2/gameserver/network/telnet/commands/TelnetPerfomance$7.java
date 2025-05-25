/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.geodata.PathFindBuffers;
import l2.gameserver.network.telnet.TelnetCommand;

class TelnetPerfomance.7
extends TelnetCommand {
    TelnetPerfomance.7(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "pathfind";
    }

    @Override
    public String handle(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PathFindBuffers.getStats());
        return stringBuilder.toString();
    }
}
