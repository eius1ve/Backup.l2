/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.network.gamecomm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import l2.authserver.Config;
import l2.authserver.ThreadPoolManager;
import l2.authserver.network.gamecomm.GameServer;
import l2.authserver.network.gamecomm.SendablePacket;
import l2.authserver.network.gamecomm.as2gs.PingRequest;
import l2.commons.threading.RunnableImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GameServerConnection {
    private static final Logger T = LoggerFactory.getLogger(GameServerConnection.class);
    final ByteBuffer readBuffer = ByteBuffer.allocate(65536).order(ByteOrder.LITTLE_ENDIAN);
    final Queue<SendablePacket> sendQueue = new ArrayDeque<SendablePacket>();
    final Lock sendLock = new ReentrantLock();
    final AtomicBoolean isPengingWrite = new AtomicBoolean();
    private final Selector b;
    private final SelectionKey a;
    private GameServer a;
    private Future<?> d;
    private int dv;

    public GameServerConnection(SelectionKey selectionKey) {
        this.a = selectionKey;
        this.b = selectionKey.selector();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sendPacket(SendablePacket sendablePacket) {
        boolean bl;
        this.sendLock.lock();
        try {
            this.sendQueue.add(sendablePacket);
            bl = this.enableWriteInterest();
        }
        catch (CancelledKeyException cancelledKeyException) {
            return;
        }
        finally {
            this.sendLock.unlock();
        }
        if (bl) {
            this.b.wakeup();
        }
    }

    protected boolean disableWriteInterest() throws CancelledKeyException {
        if (this.isPengingWrite.compareAndSet(true, false)) {
            this.a.interestOps(this.a.interestOps() & 0xFFFFFFFB);
            return true;
        }
        return false;
    }

    protected boolean enableWriteInterest() throws CancelledKeyException {
        if (!this.isPengingWrite.getAndSet(true)) {
            this.a.interestOps(this.a.interestOps() | 4);
            return true;
        }
        return false;
    }

    public void closeNow() {
        this.a.interestOps(8);
        this.b.wakeup();
    }

    public void onDisconnection() {
        try {
            this.stopPingTask();
            this.readBuffer.clear();
            this.sendLock.lock();
            try {
                this.sendQueue.clear();
            }
            finally {
                this.sendLock.unlock();
            }
            this.isPengingWrite.set(false);
            if (this.a != null && this.a.isAuthed()) {
                T.info("Connection with gameserver " + this.a.getId() + " [" + this.a.getName() + "] lost.");
                T.info("Setting gameserver down. All proxies will be down as well.");
                this.a.setDown();
            }
            this.a = null;
        }
        catch (Exception exception) {
            T.error("", (Throwable)exception);
        }
    }

    ByteBuffer getReadBuffer() {
        return this.readBuffer;
    }

    GameServer getGameServer() {
        return this.a;
    }

    void setGameServer(GameServer gameServer) {
        this.a = gameServer;
    }

    public String getIpAddress() {
        return ((SocketChannel)this.a.channel()).socket().getInetAddress().getHostAddress();
    }

    public void onPingResponse() {
        this.dv = 0;
    }

    public int getPingRetry() {
        return this.dv;
    }

    public void startPingTask() {
        if (Config.GAME_SERVER_PING_DELAY == 0L) {
            return;
        }
        this.d = ThreadPoolManager.getInstance().scheduleAtFixedRate(new PingTask(), Config.GAME_SERVER_PING_DELAY, Config.GAME_SERVER_PING_DELAY);
    }

    public void stopPingTask() {
        if (this.d != null) {
            this.d.cancel(false);
            this.d = null;
        }
    }

    private class PingTask
    extends RunnableImpl {
        private PingTask() {
        }

        @Override
        public void runImpl() {
            if (Config.GAME_SERVER_PING_RETRY > 0 && GameServerConnection.this.dv > Config.GAME_SERVER_PING_RETRY) {
                _log.warn("Gameserver " + GameServerConnection.this.a.getId() + " [" + GameServerConnection.this.a.getName() + "] : ping timeout!");
                GameServerConnection.this.closeNow();
                return;
            }
            ++GameServerConnection.this.dv;
            GameServerConnection.this.sendPacket(new PingRequest());
        }
    }
}
