/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.handler.telegram;

import java.util.List;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.handler.telegram.TelegramBotProperties;
import l2.gameserver.handler.telegram.TelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramCommandRegistry;
import l2.gameserver.handler.telegram.TelegramMonitoring;
import l2.gameserver.handler.telegram.model.CallbackQuery;
import l2.gameserver.handler.telegram.model.GetUpdateResult;
import l2.gameserver.handler.telegram.model.Message;
import l2.gameserver.handler.telegram.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelegramBot {
    private static final Logger bh = LoggerFactory.getLogger(TelegramBot.class);
    private long aN = 0L;
    private static final TelegramBot a = new TelegramBot();

    public static TelegramBot getInstance() {
        return a;
    }

    private static long a(GetUpdateResult getUpdateResult, long l) throws Exception {
        if (getUpdateResult == null || getUpdateResult.result == null) {
            return l;
        }
        for (Update update : getUpdateResult.result) {
            if (update == null) continue;
            long l2 = update.update_id;
            if (update.message != null) {
                TelegramBot.a(update.message);
            } else if (update.callback_query != null) {
                TelegramBot.a(update.callback_query);
            }
            if (l2 < l) continue;
            l = l2 + 1L;
        }
        return l;
    }

    private static void a(Message message) throws Exception {
        if (message.chat == null || message.text == null) {
            return;
        }
        String string = message.chat.id.toString();
        String string2 = message.text;
        TelegramCommandHandler.getInstance().handleUpdate(string, string2);
    }

    private static void a(CallbackQuery callbackQuery) throws Exception {
        String string = callbackQuery.message.chat.id.toString();
        String string2 = callbackQuery.data;
        TelegramCommandHandler.getInstance().handleUpdate(string, string2);
    }

    private void d(int n) {
        ThreadPoolManager.getInstance().scheduleAtFixedRate(() -> {
            try {
                GetUpdateResult getUpdateResult = TelegramApi.getUpdates(this.aN);
                this.aN = TelegramBot.a(getUpdateResult, this.aN);
            }
            catch (Exception exception) {
                if (TelegramBotProperties.TELEGRAM_BOT_DEBUG) {
                    exception.printStackTrace();
                }
                bh.error("Telegram Bot: Schedule Update Task interrupt");
            }
        }, n, n);
    }

    public void load() {
        TelegramBotProperties.loadTelegramBotProperties();
        if (TelegramBotProperties.TELEGRAM_BOT_ENABLED) {
            Object object;
            try {
                TelegramApi.setBotCommands();
            }
            catch (Exception exception) {
                if (TelegramBotProperties.TELEGRAM_BOT_DEBUG) {
                    throw new RuntimeException(exception);
                }
                bh.error("Telegram Bot: Error setting up Telegram Bot Commands");
            }
            TelegramCommandRegistry.registerCommands();
            try {
                object = TelegramApi.getUpdates(0L);
                if (object != null && ((GetUpdateResult)object).result.length > 0) {
                    this.aN = ((GetUpdateResult)object).result[((GetUpdateResult)object).result.length - 1].update_id + 1L;
                    bh.info("Telegram Bot: Starting with Last update ID " + this.aN);
                } else {
                    this.aN = 0L;
                    bh.info("Telegram Bot: Starting");
                }
            }
            catch (Exception exception) {
                bh.error("Error retrieving initial update ID from Telegram", (Throwable)exception);
                this.aN = 0L;
            }
            this.d(TelegramBotProperties.TELEGRAM_BOT_TASK_DELAY);
            object = TelegramBotProperties.TELEGRAM_CHAT_IDS;
            if (object != null && !object.isEmpty()) {
                if (TelegramBotProperties.TELEGRAM_BOT_MEMORY_CHECK_DELAY > 0L) {
                    TelegramMonitoring.scheduleMemoryCheckTask((List<Long>)object, TelegramBotProperties.TELEGRAM_BOT_MEMORY_CHECK_DELAY);
                }
                if (TelegramBotProperties.TELEGRAM_BOT_CPU_CHECK_DELAY > 0L) {
                    TelegramMonitoring.scheduleCpuCheckTask((List<Long>)object, TelegramBotProperties.TELEGRAM_BOT_CPU_CHECK_DELAY);
                }
            }
        }
    }
}
