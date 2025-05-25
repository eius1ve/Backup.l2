/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram;

import java.util.HashMap;
import java.util.Map;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.handler.telegram.TelegramBotProperties;

public class TelegramCommandHandler {
    private static final TelegramCommandHandler a = new TelegramCommandHandler();
    private final Map<String, ITelegramCommandHandler> af = new HashMap<String, ITelegramCommandHandler>();
    private final Map<String, String> ag = new HashMap<String, String>();

    private TelegramCommandHandler() {
    }

    public static TelegramCommandHandler getInstance() {
        return a;
    }

    public void registerCommandHandler(ITelegramCommandHandler iTelegramCommandHandler) {
        for (String string : iTelegramCommandHandler.getCommands()) {
            this.af.put(string, iTelegramCommandHandler);
        }
    }

    public void handleUpdate(String string, String string2) throws Exception {
        Object object;
        if (!TelegramBotProperties.TELEGRAM_CHAT_IDS.contains(Long.parseLong(string))) {
            TelegramApi.sendMessage(string, "Invalid User. You are not authorized to use this command.");
            return;
        }
        String string3 = this.ag.get(string);
        if (string3 != null && (object = this.af.get(string3)) != null) {
            object.handle(string, string2);
            this.ag.remove(string);
            return;
        }
        object = string2.split(" ", 2);
        String string4 = object[0];
        String string5 = ((String[])object).length > 1 ? object[1] : "";
        ITelegramCommandHandler iTelegramCommandHandler = this.af.get(string4);
        if (iTelegramCommandHandler != null) {
            if (string5.isEmpty() && iTelegramCommandHandler.requiresParams()) {
                this.ag.put(string, string4);
                iTelegramCommandHandler.handle(string, null);
            } else {
                iTelegramCommandHandler.handle(string, string5);
            }
        } else {
            TelegramApi.sendMessage(string, "Unknown command. Use /start to see available commands.");
        }
    }
}
