/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lucera2.dbmsstruct.model.DBMSStructureSynchronizer
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver;

import com.lucera2.dbmsstruct.model.DBMSStructureSynchronizer;
import java.io.File;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import l2.authserver.Config;
import l2.authserver.GameServerManager;
import l2.authserver.Shutdown;
import l2.authserver.ThreadPoolManager;
import l2.authserver.database.L2DatabaseFactory;
import l2.authserver.network.gamecomm.GameServerCommunication;
import l2.authserver.network.l2.BaseLoginClient;
import l2.authserver.network.l2.SelectorHelper;
import l2.commons.net.nio.impl.HaProxySelectorThread;
import l2.commons.net.nio.impl.MMOConnection;
import l2.commons.net.nio.impl.SelectorConfig;
import l2.commons.net.nio.impl.SelectorThread;
import l2.commons.threading.RunnableImpl;
import l2.commons.time.cron.SchedulingPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AuthServer {
    private static final Logger I = LoggerFactory.getLogger(AuthServer.class);
    public static final int SHUTDOWN = 0;
    public static final int RESTART = 2;
    public static final int NONE = -1;
    private static AuthServer a;
    private GameServerCommunication a;
    private final List<SelectorThread<BaseLoginClient>> N;

    public static AuthServer getInstance() {
        return a;
    }

    public AuthServer() throws Throwable {
        SelectorThread<BaseLoginClient> selectorThread;
        Config.initCrypt();
        GameServerManager.getInstance();
        final SelectorHelper selectorHelper = new SelectorHelper();
        SelectorConfig selectorConfig = new SelectorConfig(){

            @Override
            public void onIncorrectPacketSize(MMOConnection mMOConnection, int n) {
                selectorHelper.onIncorrectPacketSize(mMOConnection, n);
            }
        };
        selectorConfig.PACKET_SIZE = 1024;
        this.a = GameServerCommunication.getInstance();
        this.a.openServerSocket(Config.GAME_SERVER_LOGIN_HOST.equals("*") ? null : InetAddress.getByName(Config.GAME_SERVER_LOGIN_HOST), Config.GAME_SERVER_LOGIN_PORT);
        this.a.start();
        I.info("Listening for gameservers on " + Config.GAME_SERVER_LOGIN_HOST + ":" + Config.GAME_SERVER_LOGIN_PORT);
        ArrayList<SelectorThread<BaseLoginClient>> arrayList = new ArrayList<SelectorThread<BaseLoginClient>>();
        if (Config.PORT_LOGIN > 0) {
            selectorThread = new SelectorThread<BaseLoginClient>(selectorConfig, selectorHelper, selectorHelper, selectorHelper, selectorHelper);
            selectorThread.openServerSocket(Config.LOGIN_HOST.equals("*") ? null : InetAddress.getByName(Config.LOGIN_HOST), Config.PORT_LOGIN);
            selectorThread.start();
            arrayList.add(selectorThread);
            I.info("Listening for clients on " + Config.LOGIN_HOST + ":" + Config.PORT_LOGIN);
        }
        if (Config.HAPROXY_PORT_LOGIN > 0) {
            selectorThread = new HaProxySelectorThread<BaseLoginClient>(selectorConfig, selectorHelper, selectorHelper, selectorHelper, selectorHelper);
            ((HaProxySelectorThread)selectorThread).openServerSocket(Config.HAPROXY_LOGIN_HOST.equals("*") ? null : InetAddress.getByName(Config.HAPROXY_LOGIN_HOST), Config.HAPROXY_PORT_LOGIN);
            selectorThread.start();
            arrayList.add(selectorThread);
            I.info("Listening for HAProxy clients on " + Config.HAPROXY_LOGIN_HOST + ":" + Config.HAPROXY_PORT_LOGIN);
        }
        this.N = Collections.unmodifiableList(arrayList);
    }

    public List<SelectorThread<BaseLoginClient>> getSelectorThreads() {
        return this.N;
    }

    public GameServerCommunication getGameServerListener() {
        return this.a;
    }

    public static void checkFreePorts() throws Throwable {
        ServerSocket serverSocket = null;
        try {
            if (Config.PORT_LOGIN > 0) {
                serverSocket = Config.LOGIN_HOST.equalsIgnoreCase("*") ? new ServerSocket(Config.PORT_LOGIN) : new ServerSocket(Config.PORT_LOGIN, 50, InetAddress.getByName(Config.LOGIN_HOST));
            }
            if (Config.HAPROXY_PORT_LOGIN > 0) {
                serverSocket = Config.HAPROXY_LOGIN_HOST.equalsIgnoreCase("*") ? new ServerSocket(Config.HAPROXY_PORT_LOGIN) : new ServerSocket(Config.HAPROXY_PORT_LOGIN, 50, InetAddress.getByName(Config.HAPROXY_LOGIN_HOST));
            }
        }
        finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                }
                catch (Exception exception) {}
            }
        }
    }

    protected void scheduleRestartByCron(String string) {
        SchedulingPattern schedulingPattern;
        try {
            schedulingPattern = new SchedulingPattern(string);
        }
        catch (SchedulingPattern.InvalidPatternException invalidPatternException) {
            return;
        }
        long l = System.currentTimeMillis();
        long l2 = schedulingPattern.next(l) - l;
        ThreadPoolManager.getInstance().schedule(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                Runtime.getRuntime().exit(2);
            }
        }, l2);
    }

    private static final void W() throws Exception {
        if (!Config.DATABASE_EX_STRUCTURE_MANAGER) {
            return;
        }
        try (Connection connection = L2DatabaseFactory.getInstance().getConnection();){
            DBMSStructureSynchronizer dBMSStructureSynchronizer = new DBMSStructureSynchronizer(connection, Config.DATABASE_NAME, AuthServer.class.getResourceAsStream("/authd.json"));
            Map map = dBMSStructureSynchronizer.getDBVariables();
            I.info("DBMS: " + map.getOrDefault("version_comment", "") + " " + map.getOrDefault("version", "") + " on " + map.getOrDefault("version_compile_os", "") + " " + map.getOrDefault("version_compile_machine", ""));
            I.info("Charset: " + map.getOrDefault("character_set_connection", "") + " " + map.getOrDefault("collation_connection", ""));
            dBMSStructureSynchronizer.synchronize(new String[0]);
        }
    }

    public static void main(String[] stringArray) throws Throwable {
        new File("./log/").mkdir();
        Config.load();
        AuthServer.checkFreePorts();
        L2DatabaseFactory.getInstance().getConnection().close();
        AuthServer.W();
        a = new AuthServer();
        if (!Config.RESTART_AT_TIME.isEmpty()) {
            a.scheduleRestartByCron(Config.RESTART_AT_TIME);
        }
        Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());
    }
}
