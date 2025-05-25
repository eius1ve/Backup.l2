/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.telnet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.network.telnet.TelnetServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TelnetServer
extends RunnableImpl {
    private static final Logger dg = LoggerFactory.getLogger(TelnetServer.class);
    private static final TelnetServer a = new TelnetServer();
    private final ExecutorService a;
    private ServerSocket a = Executors.newFixedThreadPool(Config.TELNET_SIMULTANEOUS_CONNECTIONS + 1, new ThreadFactory(){
        private final AtomicInteger s = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "TelnetServer thread-" + this.s.getAndIncrement());
        }
    });

    private TelnetServer() {
    }

    public static TelnetServer getInstance() {
        return a;
    }

    public void start() {
        this.a.execute(this);
    }

    @Override
    public void runImpl() throws Exception {
        this.a = new ServerSocket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(Config.TELNET_HOSTNAME.equals("*") ? null : Config.TELNET_HOSTNAME, Config.TELNET_PORT);
        this.a.bind(inetSocketAddress);
        dg.info("[Telnet Server] Started on port: " + Config.TELNET_PORT);
        this.bT();
    }

    private void bT() {
        while (!this.a.isClosed()) {
            try {
                Socket socket = this.a.accept();
                try {
                    String string = socket.getInetAddress().getHostAddress();
                    if (this.c(string)) {
                        dg.info("[Telnet Server] Accepted connection from: " + string);
                        this.a.execute(new TelnetServerHandler(socket));
                        continue;
                    }
                    dg.warn("[Telnet Server] Rejected connection from unauthorized IP: " + string);
                    socket.close();
                }
                finally {
                    if (socket == null) continue;
                    socket.close();
                }
            }
            catch (IOException iOException) {
                dg.error("[Telnet Server] Failed to accept connection: " + iOException.getMessage());
            }
        }
    }

    private boolean c(String string) {
        if (Config.TELNET_ALLOWED_IPS.contains("*")) {
            return true;
        }
        if (Config.TELNET_ALLOWED_IPS.contains(string)) {
            return true;
        }
        for (String string2 : Config.TELNET_ALLOWED_IPS) {
            if (!this.a(string, string2)) continue;
            return true;
        }
        return false;
    }

    public void stop() {
        try {
            if (this.a != null && !this.a.isClosed()) {
                this.a.close();
                dg.info("[Telnet Server] stopped.");
            }
        }
        catch (IOException iOException) {
            dg.error("Error stopping [Telnet Server]: " + iOException.getMessage());
        }
    }

    private boolean a(String string, String string2) {
        try {
            String[] stringArray = string2.split("/");
            if (stringArray.length != 2) {
                return false;
            }
            String string3 = stringArray[0];
            int n = Integer.parseInt(stringArray[1]);
            InetAddress inetAddress = InetAddress.getByName(string);
            InetAddress inetAddress2 = InetAddress.getByName(string3);
            byte[] byArray = inetAddress.getAddress();
            byte[] byArray2 = inetAddress2.getAddress();
            int n2 = -1 << 32 - n;
            for (int i = 0; i < byArray.length; ++i) {
                int n3 = byArray2[i] & 0xFF;
                int n4 = n2 >> 8 * (3 - i) & 0xFF;
                int n5 = byArray[i] & 0xFF;
                if ((n3 & n4) == (n5 & n4)) continue;
                return false;
            }
            return true;
        }
        catch (NumberFormatException | UnknownHostException exception) {
            dg.error("Error parsing subnet: " + string2, (Throwable)exception);
            return false;
        }
    }
}
