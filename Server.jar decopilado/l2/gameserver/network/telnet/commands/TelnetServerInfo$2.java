/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package l2.gameserver.network.telnet.commands;

import java.lang.management.ManagementFactory;
import l2.gameserver.network.telnet.TelnetCommand;
import org.apache.commons.lang3.time.DurationFormatUtils;

class TelnetServerInfo.2
extends TelnetCommand {
    TelnetServerInfo.2(String string) {
        super(string);
    }

    @Override
    public String getUsage() {
        return "uptime";
    }

    @Override
    public String handle(String[] stringArray) {
        return DurationFormatUtils.formatDurationHMS((long)ManagementFactory.getRuntimeMXBean().getUptime()) + "\r\n";
    }
}
