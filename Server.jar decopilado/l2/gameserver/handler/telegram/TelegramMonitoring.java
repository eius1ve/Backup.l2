/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.handler.telegram;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.List;
import l2.commons.lang.StatsUtils;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.handler.telegram.TelegramBotProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelegramMonitoring {
    private static final Logger bi = LoggerFactory.getLogger(TelegramMonitoring.class);
    private static final OperatingSystemMXBean a = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
    private static final MemoryMXBean c = ManagementFactory.getMemoryMXBean();

    private static double a() {
        return a.getCpuLoad() * 100.0;
    }

    public static void checkCpuUsageAndNotifyAdmin(String string) {
        double d = TelegramMonitoring.a();
        if (d >= TelegramBotProperties.TELEGRAM_BOT_CPU_PERCENT_ALERT) {
            try {
                String string2 = String.format("\u26a0\ufe0f Warning!\u26a0\ufe0f CPU load exceeds 90%%: Current total load - %.2f%%", d);
                TelegramApi.sendMessage(string, string2);
            }
            catch (Exception exception) {
                bi.error("\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u0440\u0438 \u043e\u0442\u043f\u0440\u0430\u0432\u043a\u0435 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043e \u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0435 \u043f\u0440\u043e\u0446\u0435\u0441\u0441\u043e\u0440\u0430 \u0430\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0442\u043e\u0440\u0443 \u0432 \u0447\u0430\u0442 ID: " + string, (Throwable)exception);
            }
        }
    }

    public static void scheduleCpuCheckTask(List<Long> list, long l) {
        ThreadPoolManager.getInstance().scheduleAtFixedRate(() -> {
            for (Long l : list) {
                TelegramMonitoring.checkCpuUsageAndNotifyAdmin(l.toString());
            }
        }, l, l);
    }

    public static void scheduleMemoryCheckTask(List<Long> list, long l) {
        ThreadPoolManager.getInstance().scheduleAtFixedRate(() -> {
            for (Long l : list) {
                TelegramMonitoring.a(l.toString());
            }
        }, l, l);
    }

    private static void a(String string) {
        double d = (double)c.getHeapMemoryUsage().getMax() / 1024.0;
        double d2 = (double)c.getHeapMemoryUsage().getUsed() / 1024.0;
        double d3 = d2 / d * 100.0;
        if (d3 >= TelegramBotProperties.TELEGRAM_BOT_MEMORY_PERCENT_ALERT) {
            try {
                String string2 = StatsUtils.getMemUsage().toString();
                TelegramApi.sendMessage(string, "\u26a0\ufe0f Warning! \u26a0\ufe0f More than 90% of available memory is in use:\n" + string2);
            }
            catch (Exception exception) {
                bi.error("Error sending message about memory usage to admin in chat ID: " + string, (Throwable)exception);
            }
        }
    }
}
