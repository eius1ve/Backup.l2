/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet;

import l2.gameserver.network.telnet.TelnetCommand;

class TelnetServerHandler.1
extends TelnetCommand {
    TelnetServerHandler.1(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "help [command]";
    }

    @Override
    public String handle(String[] stringArray) {
        if (stringArray.length == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Available commands:\r\n");
            for (TelnetCommand telnetCommand : TelnetServerHandler.this.C) {
                stringBuilder.append(telnetCommand.getCommand()).append("\r\n");
            }
            return stringBuilder.toString();
        }
        TelnetCommand telnetCommand = TelnetServerHandler.this.a(stringArray[0]);
        if (telnetCommand == null) {
            return "Unknown command.\r\n";
        }
        return "usage:\r\n" + telnetCommand.getUsage() + "\r\n";
    }
}
