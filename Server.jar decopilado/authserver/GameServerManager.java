/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import l2.authserver.Config;
import l2.authserver.database.L2DatabaseFactory;
import l2.authserver.network.gamecomm.GameServer;
import l2.authserver.network.gamecomm.ProxyServer;
import l2.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameServerManager {
    private static final Logger L = LoggerFactory.getLogger(GameServerManager.class);
    private static final GameServerManager a = new GameServerManager();
    private final Map<Integer, GameServer> o = new TreeMap<Integer, GameServer>();
    private final Map<Integer, List<ProxyServer>> p = new TreeMap<Integer, List<ProxyServer>>();
    private final Map<Integer, ProxyServer> q = new TreeMap<Integer, ProxyServer>();
    private final ReadWriteLock b = new ReentrantReadWriteLock();
    private final Lock c = this.b.readLock();
    private final Lock d = this.b.writeLock();

    public static final GameServerManager getInstance() {
        return a;
    }

    public GameServerManager() {
        this.X();
        L.info("Loaded " + this.o.size() + " registered GameServer(s).");
        this.Y();
        L.info("Loaded " + this.q.size() + " proxy server(s).");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void X() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = L2DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `server_id` FROM `gameservers`");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("server_id");
                for (Config.ProxyServerConfig proxyServerConfig : Config.PROXY_SERVERS_CONFIGS) {
                    if (proxyServerConfig.getProxyId() != n) continue;
                    L.warn("Server with id " + n + " collides with proxy server.");
                }
                GameServer object = new GameServer(n);
                this.o.put(n, object);
            }
        }
        catch (Exception exception) {
            try {
                L.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    private void Y() {
        for (Config.ProxyServerConfig proxyServerConfig : Config.PROXY_SERVERS_CONFIGS) {
            LinkedList<ProxyServer> linkedList;
            if (this.o.containsKey(proxyServerConfig.getProxyId())) {
                L.warn("Won't load collided proxy with id " + proxyServerConfig.getProxyId() + ".");
                continue;
            }
            ProxyServer proxyServer = new ProxyServer(proxyServerConfig.getOrigServerId(), proxyServerConfig.getProxyId());
            try {
                linkedList = InetAddress.getByName(proxyServerConfig.getPorxyHost());
                proxyServer.setProxyAddr((InetAddress)((Object)linkedList));
            }
            catch (UnknownHostException unknownHostException) {
                L.error("Can't load proxy", (Throwable)unknownHostException);
                continue;
            }
            proxyServer.setProxyPort(proxyServerConfig.getProxyPort());
            proxyServer.setHideMain(proxyServerConfig.isHideMain());
            proxyServer.setMinAccessLevel(proxyServerConfig.getMinAccessLevel());
            proxyServer.setMaxAccessLevel(proxyServerConfig.getMaxAccessLevel());
            linkedList = this.p.get(proxyServer.getOrigServerId());
            if (linkedList == null) {
                linkedList = new LinkedList<ProxyServer>();
                this.p.put(proxyServer.getOrigServerId(), linkedList);
            }
            linkedList.add(proxyServer);
            this.q.put(proxyServer.getProxyServerId(), proxyServer);
        }
    }

    public List<ProxyServer> getProxyServersList(int n) {
        List<ProxyServer> list = this.p.get(n);
        return list != null ? list : Collections.emptyList();
    }

    public ProxyServer getProxyServerById(int n) {
        return this.q.get(n);
    }

    public GameServer[] getGameServers() {
        this.c.lock();
        try {
            GameServer[] gameServerArray = this.o.values().toArray(new GameServer[this.o.size()]);
            return gameServerArray;
        }
        finally {
            this.c.unlock();
        }
    }

    public GameServer getGameServerById(int n) {
        this.c.lock();
        try {
            GameServer gameServer = this.o.get(n);
            return gameServer;
        }
        finally {
            this.c.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean registerGameServer(GameServer gameServer) {
        if (!Config.ACCEPT_NEW_GAMESERVER) {
            return false;
        }
        this.d.lock();
        try {
            int n = 1;
            while (n++ < 127) {
                GameServer gameServer2 = this.o.get(n);
                if (!this.q.containsKey(n) && gameServer2 != null) continue;
                this.o.put(n, gameServer);
                gameServer.setId(n);
                boolean bl = true;
                return bl;
            }
        }
        finally {
            this.d.unlock();
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean registerGameServer(int n, GameServer gameServer) {
        this.d.lock();
        try {
            GameServer gameServer2 = this.o.get(n);
            if (!Config.ACCEPT_NEW_GAMESERVER && gameServer2 == null) {
                boolean bl = false;
                return bl;
            }
            if (gameServer2 == null || !gameServer2.isAuthed()) {
                this.o.put(n, gameServer);
                gameServer.setId(n);
                boolean bl = true;
                return bl;
            }
        }
        finally {
            this.d.unlock();
        }
        return false;
    }
}
