/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.gamecomm;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import l2.authserver.Config;
import l2.authserver.network.gamecomm.GameServerConnection;
import l2.authserver.network.gamecomm.SendablePacket;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GameServer {
    private int _id;
    private String aB;
    private String aC;
    private InetAddress a;
    private InetAddress b;
    private volatile int[] aj = new int[]{7777};
    private volatile int dp;
    private int dq;
    private int dr;
    private int ds;
    private boolean ae;
    private boolean af;
    private boolean ag;
    private boolean ah;
    private int dt;
    private GameServerConnection a;
    private boolean ai;
    private AtomicInteger g = new AtomicInteger(0);
    private volatile int du;

    public GameServer(GameServerConnection gameServerConnection) {
        this.a = gameServerConnection;
    }

    public GameServer(int n) {
        this._id = n;
    }

    public void setId(int n) {
        this._id = n;
    }

    public int getId() {
        return this._id;
    }

    public void setAuthed(boolean bl) {
        this.ai = bl;
    }

    public boolean isAuthed() {
        return this.ai;
    }

    public void setConnection(GameServerConnection gameServerConnection) {
        this.a = gameServerConnection;
    }

    public GameServerConnection getConnection() {
        return this.a;
    }

    public InetAddress getInternalHost() throws UnknownHostException {
        if (this.a != null) {
            return this.a;
        }
        this.a = InetAddress.getByName(this.aB);
        return this.a;
    }

    public void setInternalHost(String string) {
        if (string.equals("*")) {
            string = this.getConnection().getIpAddress();
        }
        this.aB = string;
        this.a = null;
    }

    public void setExternalHost(String string) {
        if (string.equals("*")) {
            string = this.getConnection().getIpAddress();
        }
        this.aC = string;
        this.b = null;
    }

    public InetAddress getExternalHost() throws UnknownHostException {
        if (this.b != null) {
            return this.b;
        }
        this.b = InetAddress.getByName(this.aC);
        return this.b;
    }

    public int getPort() {
        int[] nArray = this.aj;
        if (nArray.length == 0) {
            return -1;
        }
        return nArray[(this.g.incrementAndGet() & Integer.MAX_VALUE) % nArray.length];
    }

    public void setPorts(int[] nArray) {
        this.aj = nArray;
    }

    public int getHaProxyPort() {
        return this.dp;
    }

    public void setHaProxyPort(int n) {
        this.dp = n;
    }

    public void setMaxPlayers(int n) {
        this.dt = n;
    }

    public int getMaxPlayers() {
        return this.dt;
    }

    public int getOnline() {
        return this.du;
    }

    public void addAccount(String string) {
        ++this.du;
    }

    public void removeAccount(String string) {
        --this.du;
    }

    public void setDown() {
        this.setAuthed(false);
        this.setConnection(null);
        this.setOnline(false);
    }

    public String getName() {
        return Config.SERVER_NAMES.get(this.getId());
    }

    public void sendPacket(SendablePacket sendablePacket) {
        GameServerConnection gameServerConnection = this.getConnection();
        if (gameServerConnection != null) {
            gameServerConnection.sendPacket(sendablePacket);
        }
    }

    public int getServerType() {
        return this.dq;
    }

    public boolean isOnline() {
        return this.ae;
    }

    public void setOnline(boolean bl) {
        this.ae = bl;
    }

    public void setServerType(int n) {
        this.dq = n;
    }

    public boolean isPvp() {
        return this.af;
    }

    public void setPvp(boolean bl) {
        this.af = bl;
    }

    public boolean isShowingBrackets() {
        return this.ag;
    }

    public void setShowingBrackets(boolean bl) {
        this.ag = bl;
    }

    public boolean isGmOnly() {
        return this.ah;
    }

    public void setGmOnly(boolean bl) {
        this.ah = bl;
    }

    public int getAgeLimit() {
        return this.dr;
    }

    public void setAgeLimit(int n) {
        this.dr = n;
    }

    public void setProtocol(int n) {
        this.ds = n;
    }
}
