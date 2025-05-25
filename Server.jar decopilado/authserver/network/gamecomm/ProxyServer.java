/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.gamecomm;

import java.net.InetAddress;
import l2.authserver.GameServerManager;
import l2.authserver.network.gamecomm.GameServer;

public class ProxyServer {
    private final int dw;
    private final int dx;
    private InetAddress c;
    private int dc;
    private boolean ak;
    private int dd;
    private int de;

    public ProxyServer(int n, int n2) {
        this.dw = n;
        this.dx = n2;
    }

    public int getOrigServerId() {
        return this.dw;
    }

    public int getProxyServerId() {
        return this.dx;
    }

    public InetAddress getProxyAddr() {
        return this.c;
    }

    public void setProxyAddr(InetAddress inetAddress) {
        this.c = inetAddress;
    }

    public int getProxyPort() {
        return this.dc;
    }

    public void setProxyPort(int n) {
        this.dc = n;
    }

    public GameServer getGameServer() {
        return GameServerManager.getInstance().getGameServerById(this.getOrigServerId());
    }

    public boolean isHideMain() {
        return this.ak;
    }

    public ProxyServer setHideMain(boolean bl) {
        this.ak = bl;
        return this;
    }

    public int getMinAccessLevel() {
        return this.dd;
    }

    public ProxyServer setMinAccessLevel(int n) {
        this.dd = n;
        return this;
    }

    public int getMaxAccessLevel() {
        return this.de;
    }

    public ProxyServer setMaxAccessLevel(int n) {
        this.de = n;
        return this;
    }
}
