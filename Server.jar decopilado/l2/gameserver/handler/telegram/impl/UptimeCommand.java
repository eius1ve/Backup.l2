/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package l2.gameserver.handler.telegram.impl;

import java.lang.management.ManagementFactory;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class UptimeCommand
implements ITelegramCommandHandler {
    private final String[] av = new String[]{"/uptime"};

    @Override
    public void handle(String string, String string2) throws Exception {
        String string3 = DurationFormatUtils.formatDurationHMS((long)ManagementFactory.getRuntimeMXBean().getUptime());
        TelegramApi.sendMessage(string, string3);
    }

    @Override
    public String[] getCommands() {
        return this.av;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
