/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet.commands;

import java.util.LinkedHashSet;
import java.util.Set;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.network.telnet.TelnetCommandHolder;
import l2.gameserver.utils.AdminFunctions;
import l2.gameserver.utils.AutoBan;

public class TelnetBan
implements TelnetCommandHolder {
    private Set<TelnetCommand> C = new LinkedHashSet<TelnetCommand>();

    public TelnetBan() {
        this.C.add(new TelnetCommand("kick"){

            @Override
            public String getUsage() {
                return "kick <name>";
            }

            @Override
            public String handle(String[] stringArray) {
                if (stringArray.length == 0 || stringArray[0].isEmpty()) {
                    return null;
                }
                if (AdminFunctions.kick(stringArray[0], "telnet")) {
                    return "Player kicked.\r\n";
                }
                return "Player not found.\r\n";
            }
        });
        this.C.add(new TelnetCommand("chat_ban"){

            @Override
            public String getUsage() {
                return "chat_ban <name> <period>";
            }

            @Override
            public String handle(String[] stringArray) {
                if (stringArray.length == 0 || stringArray[0].isEmpty()) {
                    return null;
                }
                int n = stringArray.length > 1 && !stringArray[1].isEmpty() ? Integer.parseInt(stringArray[1]) : -1;
                return AdminFunctions.banChat(null, "GMTelnet", stringArray[0], n, "telnet banned") + "\r\n";
            }
        });
        this.C.add(new TelnetCommand("char_ban"){

            @Override
            public String getUsage() {
                return "char_ban <name> <days>";
            }

            @Override
            public String handle(String[] stringArray) {
                int n;
                if (stringArray.length == 0 || stringArray[0].isEmpty()) {
                    return null;
                }
                String string = stringArray[0];
                int n2 = n = stringArray.length > 1 && !stringArray[1].isEmpty() ? Integer.parseInt(stringArray[1]) : -1;
                if (n == 0) {
                    if (!AutoBan.Banned(string, 0, 0, "unban", "telnet")) {
                        return "Can't unban \"" + string + "\".\r\n";
                    }
                    return "\"" + string + "\" unbanned.\r\n";
                }
                if (!AutoBan.Banned(string, -100, n, "unban", "telnet")) {
                    return "Can't ban \"" + string + "\".\r\n";
                }
                Player player = World.getPlayer(string);
                if (player != null) {
                    player.kick();
                }
                return "\"" + string + "\" banned.\r\n";
            }
        });
        this.C.add(new TelnetCommand("nospam"){

            @Override
            public String getUsage() {
                return "nospam <name> <period>";
            }

            @Override
            public String handle(String[] stringArray) {
                if (stringArray.length == 0 || stringArray[0].isEmpty()) {
                    return null;
                }
                int n = stringArray.length > 1 && !stringArray[1].isEmpty() ? Integer.parseInt(stringArray[1]) : -1;
                return AutoBan.noSpam(stringArray[0], n) + "\r\n";
            }
        });
    }

    @Override
    public Set<TelnetCommand> getCommands() {
        return this.C;
    }
}
