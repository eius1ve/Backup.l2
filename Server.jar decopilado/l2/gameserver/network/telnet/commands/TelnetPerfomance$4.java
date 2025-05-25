/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 */
package l2.gameserver.network.telnet.commands;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import l2.commons.lang.StatsUtils;
import l2.gameserver.network.telnet.TelnetCommand;
import org.apache.commons.io.FileUtils;

class TelnetPerfomance.4
extends TelnetCommand {
    TelnetPerfomance.4(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "threads [dump]";
    }

    @Override
    public String handle(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        if (stringArray.length == 0 || stringArray[0].isEmpty()) {
            stringBuilder.append(StatsUtils.getThreadStats());
        } else if (stringArray[0].equals("dump") || stringArray[0].equals("d")) {
            try {
                new File("stats").mkdir();
                FileUtils.writeStringToFile((File)new File("stats/ThreadsDump-" + new SimpleDateFormat("MMddHHmmss").format(System.currentTimeMillis()) + ".txt"), (String)StatsUtils.getThreadStats(true, true, true).toString());
                stringBuilder.append("Threads stats saved.\r\n");
            }
            catch (IOException iOException) {
                stringBuilder.append("Exception: " + iOException.getMessage() + "!\r\n");
            }
        } else {
            return null;
        }
        return stringBuilder.toString();
    }
}
