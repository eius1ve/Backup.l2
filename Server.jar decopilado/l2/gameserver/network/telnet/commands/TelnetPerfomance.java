/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 */
package l2.gameserver.network.telnet.commands;

import com.sun.management.HotSpotDiagnosticMXBean;
import java.io.File;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.management.MBeanServer;
import l2.commons.lang.StatsUtils;
import l2.commons.net.nio.impl.SelectorThread;
import l2.commons.threading.RunnableStatsManager;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.geodata.PathFindBuffers;
import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.network.telnet.TelnetCommandHolder;
import l2.gameserver.taskmanager.AiTaskManager;
import l2.gameserver.taskmanager.EffectTaskManager;
import org.apache.commons.io.FileUtils;

public class TelnetPerfomance
implements TelnetCommandHolder {
    private static final MemoryMXBean d = ManagementFactory.getMemoryMXBean();
    private Set<TelnetCommand> C = new LinkedHashSet<TelnetCommand>();

    public TelnetPerfomance() {
        this.C.add(new TelnetCommand("pool", new String[]{"p"}){

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
        });
        this.C.add(new TelnetCommand("mem", new String[]{"m"}){

            @Override
            public String getUsage() {
                return "mem";
            }

            @Override
            public String handle(String[] stringArray) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(TelnetPerfomance.getMemUsage());
                return stringBuilder.toString();
            }
        });
        this.C.add(new TelnetCommand("heap"){

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
        });
        this.C.add(new TelnetCommand("threads", new String[]{"t"}){

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
        });
        this.C.add(new TelnetCommand("gc"){

            @Override
            public String getUsage() {
                return "gc";
            }

            @Override
            public String handle(String[] stringArray) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(TelnetPerfomance.getGCStats());
                return stringBuilder.toString();
            }
        });
        this.C.add(new TelnetCommand("net", new String[]{"ns"}){

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
        });
        this.C.add(new TelnetCommand("pathfind", new String[]{"pfs"}){

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
        });
        this.C.add(new TelnetCommand("aistats", new String[]{"as"}){

            @Override
            public String getUsage() {
                return "aistats";
            }

            @Override
            public String handle(String[] stringArray) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < Config.AI_TASK_MANAGER_COUNT; ++i) {
                    stringBuilder.append("AiTaskManager #").append(i + 1).append("\r\n");
                    stringBuilder.append("=================================================\r\n");
                    stringBuilder.append(AiTaskManager.getInstance().getStats(i));
                    stringBuilder.append("=================================================\r\n");
                }
                return stringBuilder.toString();
            }
        });
        this.C.add(new TelnetCommand("effectstats", new String[]{"es"}){

            @Override
            public String getUsage() {
                return "effectstats";
            }

            @Override
            public String handle(String[] stringArray) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < Config.EFFECT_TASK_MANAGER_COUNT; ++i) {
                    stringBuilder.append("EffectTaskManager #").append(i + 1).append("\r\n");
                    stringBuilder.append("=================================================\r\n");
                    stringBuilder.append(EffectTaskManager.getInstance().getStats(i));
                    stringBuilder.append("=================================================\r\n");
                }
                return stringBuilder.toString();
            }
        });
    }

    public static CharSequence getGCStats() {
        StringBuilder stringBuilder = new StringBuilder();
        for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            stringBuilder.append("GarbageCollector (").append(garbageCollectorMXBean.getName()).append(")\r\n");
            stringBuilder.append("=================================================\r\n");
            stringBuilder.append("getCollectionCount: ..... ").append(garbageCollectorMXBean.getCollectionCount()).append("\r\n");
            stringBuilder.append("getCollectionTime: ...... ").append(garbageCollectorMXBean.getCollectionTime()).append(" ms").append("\r\n");
            stringBuilder.append("=================================================\r\n");
        }
        return stringBuilder;
    }

    public static CharSequence getMemUsage() {
        double d = (double)TelnetPerfomance.d.getHeapMemoryUsage().getMax() / 1024.0;
        double d2 = (double)TelnetPerfomance.d.getHeapMemoryUsage().getCommitted() / 1024.0;
        double d3 = (double)TelnetPerfomance.d.getHeapMemoryUsage().getUsed() / 1024.0;
        double d4 = d - d2;
        double d5 = d2 - d3;
        double d6 = d - d3;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AllowedMemory: ........... ").append((int)d).append(" KB").append("\r\n");
        stringBuilder.append("     Allocated: .......... ").append((int)d2).append(" KB (").append((double)Math.round(d2 / d * 1000000.0) / 10000.0).append("%)").append("\r\n");
        stringBuilder.append("     Non-Allocated: ...... ").append((int)d4).append(" KB (").append((double)Math.round(d4 / d * 1000000.0) / 10000.0).append("%)").append("\r\n");
        stringBuilder.append("AllocatedMemory: ......... ").append((int)d2).append(" KB").append("\r\n");
        stringBuilder.append("     Used: ............... ").append((int)d3).append(" KB (").append((double)Math.round(d3 / d * 1000000.0) / 10000.0).append("%)").append("\r\n");
        stringBuilder.append("     Unused (cached): .... ").append((int)d5).append(" KB (").append((double)Math.round(d5 / d * 1000000.0) / 10000.0).append("%)").append("\r\n");
        stringBuilder.append("UseableMemory: ........... ").append((int)d6).append(" KB (").append((double)Math.round(d6 / d * 1000000.0) / 10000.0).append("%)").append("\r\n");
        return stringBuilder;
    }

    @Override
    public Set<TelnetCommand> getCommands() {
        return this.C;
    }
}
