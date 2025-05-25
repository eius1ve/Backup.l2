/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.authcomm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.network.authcomm.PacketHandler;
import l2.gameserver.network.authcomm.ReceivablePacket;
import l2.gameserver.network.authcomm.SendablePacket;
import l2.gameserver.network.authcomm.gs2as.AuthRequest;
import l2.gameserver.network.l2.GameClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AuthServerCommunication
extends Thread {
    private static final Logger cx = LoggerFactory.getLogger(AuthServerCommunication.class);
    private static final AuthServerCommunication a = new AuthServerCommunication();
    private final Map<String, GameClient> bo = new HashMap<String, GameClient>();
    private final Map<String, GameClient> bp = new HashMap<String, GameClient>();
    private final ReadWriteLock g;
    private final Lock B;
    private final Lock C;
    private final ByteBuffer f;
    private final ByteBuffer g = new ReentrantReadWriteLock();
    private final Queue<SendablePacket> f;
    private final Lock D;
    private final AtomicBoolean k;
    private SelectionKey a;
    private Selector a;
    private boolean aj;
    private boolean dN;

    public static final AuthServerCommunication getInstance() {
        return a;
    }

    private AuthServerCommunication() {
        this.B = this.g.readLock();
        this.C = this.g.writeLock();
        this.f = ByteBuffer.allocate(65536).order(ByteOrder.LITTLE_ENDIAN);
        this.g = ByteBuffer.allocate(65536).order(ByteOrder.LITTLE_ENDIAN);
        this.f = new ArrayDeque();
        this.D = new ReentrantLock();
        this.k = new AtomicBoolean();
        try {
            this.a = Selector.open();
        }
        catch (IOException iOException) {
            cx.error("", (Throwable)iOException);
        }
    }

    private void bR() throws IOException {
        cx.info("Connecting to authserver on " + Config.GAME_SERVER_LOGIN_HOST + ":" + Config.GAME_SERVER_LOGIN_PORT);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        this.a = socketChannel.register(this.a, 8);
        socketChannel.connect(new InetSocketAddress(Config.GAME_SERVER_LOGIN_HOST, Config.GAME_SERVER_LOGIN_PORT));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sendPacket(SendablePacket sendablePacket) {
        boolean bl;
        if (this.isShutdown()) {
            return;
        }
        this.D.lock();
        try {
            this.f.add(sendablePacket);
            bl = this.enableWriteInterest();
        }
        catch (CancelledKeyException cancelledKeyException) {
            return;
        }
        finally {
            this.D.unlock();
        }
        if (bl) {
            this.a.wakeup();
        }
    }

    private boolean disableWriteInterest() throws CancelledKeyException {
        if (this.k.compareAndSet(true, false)) {
            this.a.interestOps(this.a.interestOps() & 0xFFFFFFFB);
            return true;
        }
        return false;
    }

    private boolean enableWriteInterest() throws CancelledKeyException {
        if (!this.k.getAndSet(true)) {
            this.a.interestOps(this.a.interestOps() | 4);
            return true;
        }
        return false;
    }

    protected ByteBuffer getReadBuffer() {
        return this.f;
    }

    protected ByteBuffer getWriteBuffer() {
        return this.g;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        while (true) {
            block27: {
                int n;
                Iterator<SelectionKey> iterator;
                Set<SelectionKey> set;
                SelectionKey selectionKey2;
                if (this.aj) {
                    return;
                }
                this.dN = false;
                block17: while (true) {
                    block26: {
                        try {
                            while (!this.isShutdown()) {
                                this.bR();
                                long l = System.currentTimeMillis();
                                int n2 = this.a.select(5000L);
                                l = System.currentTimeMillis() - l;
                                if (n2 == 0 && l < 5000L) {
                                    for (SelectionKey selectionKey2 : this.a.keys()) {
                                        SocketChannel socketChannel;
                                        if (!selectionKey2.isValid() || (socketChannel = (SocketChannel)selectionKey2.channel()) == null || (selectionKey2.interestOps() & 8) == 0) continue;
                                        this.a(selectionKey2);
                                        break block17;
                                    }
                                    continue;
                                }
                                set = this.a.selectedKeys();
                                if (set.isEmpty()) {
                                    throw new IOException("Connection timeout.");
                                }
                                iterator = set.iterator();
                                try {}
                                catch (CancelledKeyException cancelledKeyException) {
                                    break block17;
                                }
                                break block26;
                            }
                            break;
                        }
                        catch (IOException iOException) {
                            cx.error("AuthServer I/O error: " + iOException.getMessage());
                            break block27;
                        }
                    }
                    block20: while (true) {
                        if (!iterator.hasNext()) continue block17;
                        selectionKey2 = iterator.next();
                        iterator.remove();
                        n = selectionKey2.readyOps();
                        switch (n) {
                            case 8: {
                                this.a(selectionKey2);
                                break block17;
                            }
                            default: {
                                continue block20;
                            }
                        }
                        break;
                    }
                    break;
                }
                while (!this.isShutdown()) {
                    this.a.select();
                    set = this.a.selectedKeys();
                    iterator = set.iterator();
                    try {
                        block22: while (iterator.hasNext()) {
                            selectionKey2 = iterator.next();
                            iterator.remove();
                            n = selectionKey2.readyOps();
                            switch (n) {
                                case 4: {
                                    this.write(selectionKey2);
                                    break;
                                }
                                case 1: {
                                    this.read(selectionKey2);
                                    break;
                                }
                                case 5: {
                                    this.write(selectionKey2);
                                    this.read(selectionKey2);
                                    continue block22;
                                }
                            }
                        }
                        continue;
                    }
                    catch (CancelledKeyException cancelledKeyException) {}
                    break;
                }
            }
            this.close();
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException interruptedException) {
            }
        }
    }

    private void read(SelectionKey selectionKey) throws IOException {
        ByteBuffer byteBuffer;
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        int n = socketChannel.read(byteBuffer = this.getReadBuffer());
        if (n == -1) {
            throw new IOException("End of stream.");
        }
        if (n == 0) {
            return;
        }
        byteBuffer.flip();
        while (this.a(selectionKey, byteBuffer)) {
        }
    }

    private boolean a(SelectionKey selectionKey, ByteBuffer byteBuffer) throws IOException {
        int n = byteBuffer.position();
        if (byteBuffer.remaining() > 2) {
            int n2 = byteBuffer.getShort() & 0xFFFF;
            if (n2 <= 2) {
                throw new IOException("Incorrect packet size: <= 2");
            }
            if ((n2 -= 2) <= byteBuffer.remaining()) {
                int n3 = byteBuffer.limit();
                byteBuffer.limit(n + n2 + 2);
                ReceivablePacket receivablePacket = PacketHandler.handlePacket(byteBuffer);
                if (receivablePacket != null && receivablePacket.read()) {
                    ThreadPoolManager.getInstance().execute(receivablePacket);
                }
                byteBuffer.limit(n3);
                byteBuffer.position(n + n2 + 2);
                if (!byteBuffer.hasRemaining()) {
                    byteBuffer.clear();
                    return false;
                }
                return true;
            }
            byteBuffer.position(n);
        }
        byteBuffer.compact();
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void write(SelectionKey selectionKey) throws IOException {
        boolean bl;
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        ByteBuffer byteBuffer = this.getWriteBuffer();
        this.D.lock();
        try {
            SendablePacket sendablePacket;
            int n = 0;
            while (n++ < 64 && (sendablePacket = (SendablePacket)this.f.poll()) != null) {
                int n2 = byteBuffer.position();
                byteBuffer.position(n2 + 2);
                sendablePacket.write();
                int n3 = byteBuffer.position() - n2 - 2;
                if (n3 == 0) {
                    byteBuffer.position(n2);
                    continue;
                }
                byteBuffer.position(n2);
                byteBuffer.putShort((short)(n3 + 2));
                byteBuffer.position(n2 + n3 + 2);
            }
            bl = this.f.isEmpty();
            if (bl) {
                this.disableWriteInterest();
            }
        }
        finally {
            this.D.unlock();
        }
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        if (byteBuffer.remaining() > 0) {
            byteBuffer.compact();
            bl = false;
        } else {
            byteBuffer.clear();
        }
        if (!bl && this.enableWriteInterest()) {
            this.a.wakeup();
        }
    }

    private void a(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        socketChannel.finishConnect();
        selectionKey.interestOps(selectionKey.interestOps() & 0xFFFFFFF7);
        selectionKey.interestOps(selectionKey.interestOps() | 1);
        this.sendPacket(new AuthRequest());
    }

    private void close() {
        this.dN = !this.aj;
        this.D.lock();
        try {
            this.f.clear();
        }
        finally {
            this.D.unlock();
        }
        this.f.clear();
        this.g.clear();
        this.k.set(false);
        try {
            if (this.a != null) {
                this.a.channel().close();
                this.a.cancel();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.C.lock();
        try {
            this.bo.clear();
        }
        finally {
            this.C.unlock();
        }
    }

    public void shutdown() {
        this.aj = true;
        this.a.wakeup();
    }

    public boolean isShutdown() {
        return this.aj || this.dN;
    }

    public void restart() {
        this.dN = true;
        this.a.wakeup();
    }

    public GameClient addWaitingClient(GameClient gameClient) {
        this.C.lock();
        try {
            GameClient gameClient2 = this.bo.put(gameClient.getLogin(), gameClient);
            return gameClient2;
        }
        finally {
            this.C.unlock();
        }
    }

    public GameClient removeWaitingClient(String string) {
        this.C.lock();
        try {
            GameClient gameClient = this.bo.remove(string);
            return gameClient;
        }
        finally {
            this.C.unlock();
        }
    }

    public GameClient addAuthedClient(GameClient gameClient) {
        this.C.lock();
        try {
            GameClient gameClient2 = this.bp.put(gameClient.getLogin(), gameClient);
            return gameClient2;
        }
        finally {
            this.C.unlock();
        }
    }

    public GameClient removeAuthedClient(String string) {
        this.C.lock();
        try {
            GameClient gameClient = this.bp.remove(string);
            return gameClient;
        }
        finally {
            this.C.unlock();
        }
    }

    public GameClient getAuthedClient(String string) {
        this.B.lock();
        try {
            GameClient gameClient = this.bp.get(string);
            return gameClient;
        }
        finally {
            this.B.unlock();
        }
    }

    public GameClient removeClient(GameClient gameClient) {
        this.C.lock();
        try {
            if (gameClient.isAuthed()) {
                GameClient gameClient2 = this.bp.remove(gameClient.getLogin());
                return gameClient2;
            }
            GameClient gameClient3 = this.bo.remove(gameClient.getSessionKey());
            return gameClient3;
        }
        finally {
            this.C.unlock();
        }
    }

    public String[] getAccounts() {
        this.B.lock();
        try {
            String[] stringArray = this.bp.keySet().toArray(new String[this.bp.size()]);
            return stringArray;
        }
        finally {
            this.B.unlock();
        }
    }
}
