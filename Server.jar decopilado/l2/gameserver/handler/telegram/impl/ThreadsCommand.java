/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 */
package l2.gameserver.handler.telegram.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import l2.commons.lang.StatsUtils;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import org.apache.commons.io.FileUtils;

public class ThreadsCommand
implements ITelegramCommandHandler {
    private final String[] at = new String[]{"/threads"};

    @Override
    public void handle(String string, String string2) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        if (string2 == null || string2.trim().isEmpty()) {
            stringBuilder.append(StatsUtils.getThreadStats());
        } else if (string2.equals("dump") || string2.equals("d")) {
            try {
                new File("stats").mkdir();
                FileUtils.writeStringToFile((File)new File("stats/ThreadsDump-" + new SimpleDateFormat("MMddHHmmss").format(System.currentTimeMillis()) + ".txt"), (String)StatsUtils.getThreadStats(true, true, true).toString());
                stringBuilder.append("Threads stats saved.\n");
            }
            catch (IOException iOException) {
                stringBuilder.append("Exception: ").append(iOException.getMessage()).append("!\n");
            }
        } else {
            stringBuilder.append("Invalid command usage.");
        }
        TelegramApi.sendMessage(string, stringBuilder.toString());
    }

    @Override
    public String[] getCommands() {
        return this.at;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
