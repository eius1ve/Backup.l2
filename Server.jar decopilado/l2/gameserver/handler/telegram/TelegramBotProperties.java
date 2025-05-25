/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import l2.commons.configuration.ExProperties;
import l2.gameserver.Config;

public class TelegramBotProperties {
    public static final String telegramBotFile = "config/telegram_bot.properties";
    public static boolean TELEGRAM_BOT_ENABLED;
    public static String TELEGRAM_BOT_API_KEY;
    public static String TELEGRAM_BOT_BASE_URL;
    public static List<Long> TELEGRAM_CHAT_IDS;
    public static boolean TELEGRAM_BOT_DEBUG;
    public static int TELEGRAM_BOT_TASK_DELAY;
    public static long TELEGRAM_BOT_MEMORY_CHECK_DELAY;
    public static double TELEGRAM_BOT_MEMORY_PERCENT_ALERT;
    public static long TELEGRAM_BOT_CPU_CHECK_DELAY;
    public static double TELEGRAM_BOT_CPU_PERCENT_ALERT;

    public static void loadTelegramBotProperties() {
        ExProperties exProperties = Config.load(telegramBotFile);
        TELEGRAM_BOT_ENABLED = exProperties.getProperty("TelegramBotEnabled", false);
        TELEGRAM_BOT_API_KEY = exProperties.getProperty("TelegramBotApiKey", "");
        TELEGRAM_BOT_BASE_URL = exProperties.getProperty("TelegramBotBaseURL", "https://api.telegram.org/bot");
        TELEGRAM_BOT_DEBUG = exProperties.getProperty("TelegramBotDebug", false);
        TELEGRAM_BOT_TASK_DELAY = exProperties.getProperty("TelegramBotTaskDelay", 1000);
        TELEGRAM_BOT_MEMORY_CHECK_DELAY = TimeUnit.MINUTES.toMillis(exProperties.getProperty("TelegramBotMemoryCheckDelay", 0));
        TELEGRAM_BOT_MEMORY_PERCENT_ALERT = exProperties.getProperty("TelegramBotMemoryReachingPercentageAlert", 90.0);
        TELEGRAM_BOT_CPU_CHECK_DELAY = TimeUnit.MINUTES.toMillis(exProperties.getProperty("TelegramBotCpuCheckDelay", 0));
        TELEGRAM_BOT_CPU_PERCENT_ALERT = exProperties.getProperty("TelegramBotCpuReachingPercentageAlert", 90.0);
        String string = exProperties.getProperty("TelegramChatIds", "");
        if (!string.isEmpty()) {
            String[] stringArray;
            for (String string2 : stringArray = string.split("\\s*,\\s*")) {
                TELEGRAM_CHAT_IDS.add(Long.parseLong(string2));
            }
        }
    }

    static {
        TELEGRAM_CHAT_IDS = new ArrayList<Long>();
    }
}
