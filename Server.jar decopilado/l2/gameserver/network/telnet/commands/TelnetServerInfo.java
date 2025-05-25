/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.math.NumberUtils
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package l2.gameserver.network.telnet.commands;

import java.lang.management.ManagementFactory;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;
import l2.gameserver.GameServer;
import l2.gameserver.Shutdown;
import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.network.telnet.TelnetCommandHolder;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class TelnetServerInfo
implements TelnetCommandHolder {
    private Set<TelnetCommand> C = new LinkedHashSet<TelnetCommand>();

    public TelnetServerInfo() {
        this.C.add(new TelnetCommand("version", new String[]{"ver"}){

            @Override
            public String getUsage() {
                return "version";
            }

            @Override
            public String handle(String[] stringArray) {
                return "Rev." + GameServer.getInstance().getVersion().getRevisionNumber() + " Builded : " + GameServer.getInstance().getVersion().getBuildDate() + "\r\n";
            }
        });
        this.C.add(new TelnetCommand("uptime"){

            @Override
            public String getUsage() {
                return "uptime";
            }

            @Override
            public String handle(String[] stringArray) {
                return DurationFormatUtils.formatDurationHMS((long)ManagementFactory.getRuntimeMXBean().getUptime()) + "\r\n";
            }
        });
        this.C.add(new TelnetCommand("restart"){

            @Override
            public String getUsage() {
                return "restart <seconds>|now>";
            }

            @Override
            public String handle(String[] stringArray) {
                if (stringArray.length == 0) {
                    return null;
                }
                StringBuilder stringBuilder = new StringBuilder();
                if (NumberUtils.isNumber((String)stringArray[0])) {
                    int n = NumberUtils.toInt((String)stringArray[0]);
                    Shutdown.getInstance().schedule(n, 2);
                    stringBuilder.append("Server will restart in ").append(Shutdown.getInstance().getSeconds()).append(" seconds!\r\n");
                    stringBuilder.append("Type \"abort\" to abort restart!\r\n");
                } else if (stringArray[0].equalsIgnoreCase("now")) {
                    stringBuilder.append("Server will restart now!\r\n");
                    Shutdown.getInstance().schedule(0, 2);
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
                    Shutdown.getInstance().schedule(n, 2);
                    stringBuilder.append("Server will restart in ").append(Shutdown.getInstance().getSeconds()).append(" seconds!\r\n");
                    stringBuilder.append("Type \"abort\" to abort restart!\r\n");
                }
                return stringBuilder.toString();
            }
        });
        this.C.add(new TelnetCommand("shutdown"){

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
        });
        this.C.add(new TelnetCommand("abort"){

            @Override
            public String getUsage() {
                return "abort";
            }

            @Override
            public String handle(String[] stringArray) {
                Shutdown.getInstance().cancel();
                return "Aborted.\r\n";
            }
        });
    }

    @Override
    public Set<TelnetCommand> getCommands() {
        return this.C;
    }
}
