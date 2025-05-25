/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import com.sun.management.HotSpotDiagnosticMXBean;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import javax.management.MBeanServer;
import l2.gameserver.network.telnet.TelnetCommand;

class TelnetPerfomance.3
extends TelnetCommand {
    TelnetPerfomance.3(String string) {
        super(string);
    }

    @Override
    public String getUsage() {
        return "heap [dump] <live>";
    }

    @Override
    public String handle(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        if (stringArray.length == 0 || stringArray[0].isEmpty()) {
            return null;
        }
        if (stringArray[0].equals("dump") || stringArray[0].equals("d")) {
            try {
                boolean bl = stringArray.length == 2 && !stringArray[1].isEmpty() && (stringArray[1].equals("live") || stringArray[1].equals("l"));
                new File("dumps").mkdir();
                String string = "dumps/HeapDump" + (bl ? "Live" : "") + "-" + new SimpleDateFormat("MMddHHmmss").format(System.currentTimeMillis()) + ".hprof";
                MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
                HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServer, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
                hotSpotDiagnosticMXBean.dumpHeap(string, bl);
                stringBuilder.append("Heap dumped.\r\n");
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
