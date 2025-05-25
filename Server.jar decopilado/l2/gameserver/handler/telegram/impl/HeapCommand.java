/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import com.sun.management.HotSpotDiagnosticMXBean;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import javax.management.MBeanServer;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;

public class HeapCommand
implements ITelegramCommandHandler {
    private final String[] U = new String[]{"/heap"};

    @Override
    public void handle(String string, String string2) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /heap dump or /heap dump live", "Enter parameters in format: <name> <id> <count>");
            return;
        }
        if (string2.equals("dump") || string2.startsWith("dump ")) {
            boolean bl = string2.contains("live");
            try {
                new File("dumps").mkdir();
                String string3 = "dumps/HeapDump" + (bl ? "Live" : "") + "-" + new SimpleDateFormat("MMddHHmmss").format(System.currentTimeMillis()) + ".hprof";
                MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
                HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServer, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
                hotSpotDiagnosticMXBean.dumpHeap(string3, bl);
                stringBuilder.append("Heap dumped.\n");
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
        return this.U;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
