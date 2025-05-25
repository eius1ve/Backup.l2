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
import l2.commons.threading.RunnableStatsManager;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.network.telnet.TelnetCommand;
import org.apache.commons.io.FileUtils;

class TelnetPerfomance.1
extends TelnetCommand {
    TelnetPerfomance.1(String string, String ... stringArray) {
        super(string, stringArray);
    }

    @Override
    public String getUsage() {
        return "pool [dump]";
    }

    @Override
    public String handle(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        if (stringArray.length == 0 || stringArray[0].isEmpty()) {
            stringBuilder.append(ThreadPoolManager.getInstance().getStats());
        } else if (stringArray[0].equals("dump") || stringArray[0].equals("d")) {
            try {
                new File("stats").mkdir();
                FileUtils.writeStringToFile((File)new File("stats/RunnableStats-" + new SimpleDateFormat("MMddHHmmss").format(System.currentTimeMillis()) + ".txt"), (String)RunnableStatsManager.getInstance().getStats().toString());
                stringBuilder.append("Runnable stats saved.\n");
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
