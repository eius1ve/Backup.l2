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
import l2.commons.threading.RunnableStatsManager;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import org.apache.commons.io.FileUtils;

public class PoolCommand
implements ITelegramCommandHandler {
    private final String[] ag = new String[]{"/pool"};

    @Override
    public void handle(String string, String string2) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        if (string2 == null || string2.trim().isEmpty()) {
            stringBuilder.append(ThreadPoolManager.getInstance().getStats());
        } else if (string2.equals("dump") || string2.equals("d")) {
            try {
                new File("stats").mkdir();
                FileUtils.writeStringToFile((File)new File("stats/RunnableStats-" + new SimpleDateFormat("MMddHHmmss").format(System.currentTimeMillis()) + ".txt"), (String)RunnableStatsManager.getInstance().getStats().toString());
                stringBuilder.append("Runnable stats saved.\n");
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
        return this.ag;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
