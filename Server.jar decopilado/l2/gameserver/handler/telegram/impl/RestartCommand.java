/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.math.NumberUtils
 */
package l2.gameserver.handler.telegram.impl;

import java.util.Calendar;
import l2.gameserver.Shutdown;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import org.apache.commons.lang3.math.NumberUtils;

public class RestartCommand
implements ITelegramCommandHandler {
    private final String[] aj = new String[]{"/restart"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /restart <seconds>|now|<hh:mm>", "Enter time for restart in format: <seconds>|now|<hh:mm>");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            if (NumberUtils.isNumber((String)string2)) {
                int n = NumberUtils.toInt((String)string2);
                Shutdown.getInstance().schedule(n, 2);
                stringBuilder.append("Server will restart in ").append(Shutdown.getInstance().getSeconds()).append(" seconds!\n");
                stringBuilder.append("Type \"/abort\" to abort restart!\n");
            } else if (string2.equalsIgnoreCase("now")) {
                stringBuilder.append("Server will restart now!\n");
                Shutdown.getInstance().schedule(0, 2);
            } else {
                String[] stringArray = string2.split(":");
                Calendar calendar = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                calendar.set(11, Integer.parseInt(stringArray[0]));
                calendar.set(12, stringArray.length > 1 ? Integer.parseInt(stringArray[1]) : 0);
                calendar.set(13, 0);
                calendar.set(14, 0);
                if (calendar.before(calendar2)) {
                    calendar.roll(5, true);
                }
                int n = (int)(calendar.getTimeInMillis() / 1000L - calendar2.getTimeInMillis() / 1000L);
                Shutdown.getInstance().schedule(n, 2);
                stringBuilder.append("Server will restart in ").append(Shutdown.getInstance().getSeconds()).append(" seconds!\n");
                stringBuilder.append("Type \"/abort\" to abort restart!\n");
            }
            TelegramApi.sendMessage(string, stringBuilder.toString());
        }
    }

    @Override
    public String[] getCommands() {
        return this.aj;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
