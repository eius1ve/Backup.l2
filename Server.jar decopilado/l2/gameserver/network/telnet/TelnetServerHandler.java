/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import l2.gameserver.Config;
import l2.gameserver.network.telnet.TelnetCommand;
import l2.gameserver.network.telnet.TelnetCommandHolder;
import l2.gameserver.network.telnet.commands.TelnetBan;
import l2.gameserver.network.telnet.commands.TelnetConfig;
import l2.gameserver.network.telnet.commands.TelnetDebug;
import l2.gameserver.network.telnet.commands.TelnetItems;
import l2.gameserver.network.telnet.commands.TelnetPerfomance;
import l2.gameserver.network.telnet.commands.TelnetSay;
import l2.gameserver.network.telnet.commands.TelnetServerInfo;
import l2.gameserver.network.telnet.commands.TelnetStatus;
import l2.gameserver.network.telnet.commands.TelnetWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelnetServerHandler
implements Runnable,
TelnetCommandHolder {
    private static final Logger dh = LoggerFactory.getLogger(TelnetServerHandler.class);
    private static final Pattern e = Pattern.compile("\"([^\"]*)\"|([^\\s]+)");
    private final Set<TelnetCommand> C = new LinkedHashSet<TelnetCommand>();
    private final Socket b;
    private boolean fx = false;

    public TelnetServerHandler(Socket socket) {
        this.b = socket;
        this.C.add(new TelnetCommand("help", new String[]{"h"}){

            @Override
            public String getUsage() {
                return "help [command]";
            }

            @Override
            public String handle(String[] stringArray) {
                if (stringArray.length == 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Available commands:\r\n");
                    for (TelnetCommand telnetCommand : TelnetServerHandler.this.C) {
                        stringBuilder.append(telnetCommand.getCommand()).append("\r\n");
                    }
                    return stringBuilder.toString();
                }
                TelnetCommand telnetCommand = TelnetServerHandler.this.a(stringArray[0]);
                if (telnetCommand == null) {
                    return "Unknown command.\r\n";
                }
                return "usage:\r\n" + telnetCommand.getUsage() + "\r\n";
            }
        });
        this.addHandler(new TelnetBan());
        this.addHandler(new TelnetConfig());
        this.addHandler(new TelnetDebug());
        this.addHandler(new TelnetPerfomance());
        this.addHandler(new TelnetSay());
        this.addHandler(new TelnetServerInfo());
        this.addHandler(new TelnetStatus());
        this.addHandler(new TelnetWorld());
        this.addHandler(new TelnetItems());
    }

    public void addHandler(TelnetCommandHolder telnetCommandHolder) {
        this.C.addAll(telnetCommandHolder.getCommands());
    }

    @Override
    public Set<TelnetCommand> getCommands() {
        return this.C;
    }

    private TelnetCommand a(String string) {
        for (TelnetCommand telnetCommand : this.C) {
            if (!telnetCommand.equals(string)) continue;
            return telnetCommand;
        }
        return null;
    }

    private String a(String string, String[] stringArray) {
        TelnetCommand telnetCommand = this.a(string);
        if (telnetCommand == null) {
            return "Unknown command.\r\n";
        }
        String string2 = telnetCommand.handle(stringArray);
        return string2 != null ? string2 : "usage:\r\n" + telnetCommand.getUsage() + "\r\n";
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.b.getInputStream()));
             PrintWriter printWriter = new PrintWriter(this.b.getOutputStream(), true);){
            String string;
            printWriter.println("Welcome to L2 GameServer telnet console.");
            printWriter.println("It is " + new Date() + " now.");
            if (!Config.TELNET_PASSWORD.isEmpty()) {
                printWriter.print("Password: ");
                printWriter.flush();
                string = bufferedReader.readLine();
                if (string == null || !string.equals(Config.TELNET_PASSWORD)) {
                    printWriter.println("Wrong password!");
                    this.b.close();
                    return;
                }
                this.fx = true;
                printWriter.println("Type 'help' to see all available commands.");
            } else {
                this.fx = true;
                printWriter.println("Type 'help' to see all available commands.");
            }
            while ((string = bufferedReader.readLine()) != null) {
                String string2 = null;
                boolean bl = false;
                if (this.fx) {
                    if (string.isEmpty()) {
                        string2 = "Type 'help' to see all available commands: ";
                    } else if (string.equalsIgnoreCase("exit")) {
                        string2 = "Have a good day!\n";
                        bl = true;
                    } else {
                        Matcher matcher = e.matcher(string);
                        matcher.find();
                        String string3 = matcher.group();
                        ArrayList<String> arrayList = new ArrayList<String>();
                        while (matcher.find()) {
                            String string4 = matcher.group(1);
                            if (string4 == null) {
                                string4 = matcher.group(0);
                            }
                            arrayList.add(string4);
                        }
                        string2 = this.a(string3, arrayList.toArray(new String[0]));
                    }
                }
                if (string2 != null) {
                    printWriter.println(string2);
                }
                if (!bl) continue;
                this.b.close();
                break;
            }
        }
        catch (IOException iOException) {
            if (iOException instanceof IOException) {
                try {
                    this.b.close();
                }
                catch (IOException iOException2) {
                    iOException2.printStackTrace();
                }
            }
            dh.error("Error occurred", (Throwable)iOException);
        }
    }
}
