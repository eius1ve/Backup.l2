/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import l2.gameserver.Config;
import l2.gameserver.network.telnet.TelnetCommand;

class TelnetConfig.1
extends TelnetCommand {
    TelnetConfig.1(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "config parameter[=value]";
    }

    @Override
    public String handle(String[] stringArray) {
        if (stringArray.length == 0 || stringArray[0].isEmpty()) {
            return null;
        }
        String[] stringArray2 = stringArray[0].split("=");
        if (stringArray2.length == 1) {
            String string = Config.getField(stringArray[0]);
            return string == null ? "Not found.\n" : string + "\r\n";
        }
        if (Config.setField(stringArray2[0], stringArray2[1])) {
            return "Done.\n";
        }
        return "Error!\n";
    }
}
