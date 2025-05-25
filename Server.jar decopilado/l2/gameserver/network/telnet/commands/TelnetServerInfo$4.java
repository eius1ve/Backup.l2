/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.math.NumberUtils
 */
package l2.gameserver.network.telnet.commands;

import java.util.Calendar;
import l2.gameserver.Shutdown;
import l2.gameserver.network.telnet.TelnetCommand;
import org.apache.commons.lang3.math.NumberUtils;

class TelnetServerInfo.4
extends TelnetCommand {
    TelnetServerInfo.4(String string) {
        super(string);
    }

    @Override
    public String getUsage() {
        return "shutdown <seconds>|now|<hh:mm>";
    }

    @Override
    public String handle(String[] stringArray) {
        if (stringArray.length == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (NumberUtils.isNumber((String)stringArray[0])) {
            int n = NumberUtils.toInt((String)stringArray[0]);
            Shutdown.getInstance().schedule(n, 0);
            stringBuilder.append("Server will shutdown in ").append(Shutdown.getInstance().getSeconds()).append(" seconds!\r\n");
            stringBuilder.append("Type \"abort\" to abort shutdown!\r\n");
        } else if (stringArray[0].equalsIgnoreCase("now")) {
            stringBuilder.append("Server will shutdown now!\r\n");
            Shutdown.getInstance().schedule(0, 0);
        } else {
            String[] stringArray2 = stringArray[0].split(":");
            Calendar calendar = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar.set(11, Integer.parseInt(stringArray2[0]));
            calendar.set(12, stringArray2.length > 1 ? Integer.parseInt(stringArray2[1]) : 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            if (calendar.before(calendar2)) {
                calendar.roll(5, true);
            }
            int n = (int)(calendar.getTimeInMillis() / 1000L - calendar2.getTimeInMillis() / 1000L);
            Shutdown.getInstance().schedule(n, 0);
            stringBuilder.append("Server will shutdown in ").append(Shutdown.getInstance().getSeconds()).append(" seconds!\r\n");
            stringBuilder.append("Type \"abort\" to abort shutdown!\r\n");
        }
        return stringBuilder.toString();
    }
}
