/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.commons.net.nio.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import l2.commons.net.nio.impl.IAcceptFilter;
import l2.commons.net.nio.impl.IClientFactory;
import l2.commons.net.nio.impl.IMMOExecutor;
import l2.commons.net.nio.impl.IPacketHandler;
import l2.commons.net.nio.impl.MMOClient;
import l2.commons.net.nio.impl.MMOConnection;
import l2.commons.net.nio.impl.ReceivablePacket;
import l2.commons.net.nio.impl.SelectorConfig;
import l2.commons.net.nio.impl.SelectorStats;
import l2.commons.net.nio.impl.SendablePacket;
import l2.gameserver.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SelectorThread<T extends MMOClient>
extends Thread {
    private static final Logger af = LoggerFactory.getLogger(SelectorThread.class);
    private final Selector c = Selector.open();
    private final IPacketHandler<T> a;
    private final IMMOExecutor<T> b;
    private final IClientFactory<T> a;
    private IAcceptFilter a;
    private boolean aA;
    protected final SelectorConfig _sc;
    private final int eu;
    private ByteBuffer e;
    protected final ByteBuffer WRITE_BUFFER;
    protected final ByteBuffer READ_BUFFER;
    private T a;
    protected final List<MMOConnection<T>> _connections;
    private final Queue<ByteBuffer> d;
    private static final List<SelectorThread> U = new ArrayList<SelectorThread>();
    protected static SelectorStats stats = new SelectorStats();
    public static long MAX_CONNECTIONS = Long.MAX_VALUE;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SelectorThread(SelectorConfig selectorConfig, IPacketHandler<T> iPacketHandler, IMMOExecutor<T> iMMOExecutor, IClientFactory<T> iClientFactory, IAcceptFilter iAcceptFilter) throws IOException {
        List<SelectorThread> list = U;
        synchronized (list) {
            U.add(this);
        }
        this._sc = selectorConfig;
        this.a = iAcceptFilter;
        this.a = iPacketHandler;
        this.a = iClientFactory;
        this.b = iMMOExecutor;
        this.d = new ArrayDeque<ByteBuffer>(this._sc.HELPER_BUFFER_COUNT);
        this._connections = new CopyOnWriteArrayList<MMOConnection<T>>();
        this.e = ByteBuffer.wrap(new byte[this._sc.WRITE_BUFFER_SIZE]).order(this._sc.BYTE_ORDER);
        this.WRITE_BUFFER = ByteBuffer.wrap(new byte[this._sc.WRITE_BUFFER_SIZE]).order(this._sc.BYTE_ORDER);
        this.READ_BUFFER = ByteBuffer.wrap(new byte[this._sc.READ_BUFFER_SIZE]).order(this._sc.BYTE_ORDER);
        this.eu = Math.max(this._sc.READ_BUFFER_SIZE, this._sc.WRITE_BUFFER_SIZE);
        for (int i = 0; i < this._sc.HELPER_BUFFER_COUNT; ++i) {
            this.d.add(ByteBuffer.wrap(new byte[this.eu]).order(this._sc.BYTE_ORDER));
        }
    }

    public void openServerSocket(InetAddress inetAddress, int n) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(inetAddress == null ? new InetSocketAddress(n) : new InetSocketAddress(inetAddress, n));
        serverSocketChannel.register(this.getSelector(), serverSocketChannel.validOps());
        this.setName("SelectorThread:" + serverSocketChannel.socket().getLocalPort());
    }

    protected ByteBuffer getPooledBuffer() {
        if (this.d.isEmpty()) {
            return ByteBuffer.wrap(new byte[this.eu]).order(this._sc.BYTE_ORDER);
        }
        return this.d.poll();
    }

    protected void recycleBuffer(ByteBuffer byteBuffer) {
        if (this.d.size() < this._sc.HELPER_BUFFER_COUNT) {
            byteBuffer.clear();
            this.d.add(byteBuffer);
        }
    }

    protected void freeBuffer(ByteBuffer byteBuffer, MMOConnection<T> mMOConnection) {
        if (byteBuffer == this.READ_BUFFER) {
            this.READ_BUFFER.clear();
        } else {
            mMOConnection.setReadBuffer(null);
            this.recycleBuffer(byteBuffer);
        }
    }

    @Override
    public void run() {
        int n = 0;
        Set<SelectionKey> set = null;
        Iterator<SelectionKey> iterator = null;
        Iterator<MMOConnection<T>> iterator2 = null;
        SelectionKey selectionKey = null;
        MMOConnection<T> mMOConnection2 = null;
        long l = 0L;
        block8: while (true) {
            try {
                while (true) {
                    if (this.isShuttingDown()) {
                        this.closeSelectorThread();
                        break block8;
                    }
                    l = System.currentTimeMillis();
                    for (MMOConnection<T> mMOConnection2 : this._connections) {
                        if (mMOConnection2.isPengingClose() && (!mMOConnection2.isPendingWrite() || l - mMOConnection2.getPendingCloseTime() >= 10000L)) {
                            this.closeConnectionImpl(mMOConnection2);
                            continue;
                        }
                        if (!mMOConnection2.isPendingWrite() || l - mMOConnection2.getPendingWriteTime() < this._sc.INTEREST_DELAY) continue;
                        mMOConnection2.enableWriteInterest();
                    }
                    n = this.getSelector().selectNow();
                    if (n > 0) {
                        set = this.getSelector().selectedKeys();
                        iterator = set.iterator();
                        while (iterator.hasNext()) {
                            selectionKey = iterator.next();
                            iterator.remove();
                            if (!selectionKey.isValid()) continue;
                            try {
                                if (selectionKey.isAcceptable()) {
                                    this.acceptConnection(selectionKey);
                                    continue;
                                }
                                if (selectionKey.isConnectable()) {
                                    this.finishConnection(selectionKey);
                                    continue;
                                }
                                if (selectionKey.isReadable()) {
                                    this.readPacket(selectionKey);
                                }
                                if (!selectionKey.isValid() || !selectionKey.isWritable()) continue;
                                this.writePacket(selectionKey);
                            }
                            catch (CancelledKeyException cancelledKeyException) {}
                        }
                    }
                    try {
                        Thread.sleep(this._sc.SLEEP_TIME);
                        continue block8;
                    }
                    catch (InterruptedException interruptedException) {
                        continue;
                    }
                    break;
                }
            }
            catch (IOException iOException) {
                af.error("Error in " + this.getName(), (Throwable)iOException);
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {}
                continue;
            }
            break;
        }
    }

    protected void finishConnection(SelectionKey selectionKey) {
        try {
            ((SocketChannel)selectionKey.channel()).finishConnect();
        }
        catch (IOException iOException) {
            MMOConnection mMOConnection = (MMOConnection)selectionKey.attachment();
            Object t = mMOConnection.getClient();
            ((MMOConnection)((MMOClient)t).getConnection()).onForcedDisconnection();
            this.closeConnectionImpl((MMOConnection<T>)((MMOClient)t).getConnection());
        }
    }

    protected void acceptConnection(SelectionKey selectionKey) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
        try {
            SocketChannel socketChannel;
            while ((socketChannel = serverSocketChannel.accept()) != null) {
                if (MAX_CONNECTIONS > stats.getCurrentConnections() && (this.getAcceptFilter() == null || this.getAcceptFilter().accept(socketChannel))) {
                    socketChannel.configureBlocking(false);
                    SelectionKey selectionKey2 = socketChannel.register(this.getSelector(), 1);
                    Socket socket = socketChannel.socket();
                    MMOConnection mMOConnection = new MMOConnection(this, socket, selectionKey2);
                    mMOConnection.setIpAddr(socket.getInetAddress().getHostAddress());
                    MMOConnection mMOConnection2 = this.getClientFactory().create(mMOConnection);
                    ((MMOClient)((Object)mMOConnection2)).setConnection(mMOConnection);
                    mMOConnection.setClient(mMOConnection2);
                    selectionKey2.attach(mMOConnection);
                    this._connections.add(mMOConnection);
                    stats.increaseOpenedConnections();
                    continue;
                }
                socketChannel.close();
            }
        }
        catch (IOException iOException) {
            af.error("Error in " + this.getName(), (Throwable)iOException);
        }
    }

    protected void readPacket(SelectionKey selectionKey) {
        MMOConnection mMOConnection = (MMOConnection)selectionKey.attachment();
        if (mMOConnection.isClosed()) {
            return;
        }
        int n = -2;
        ByteBuffer byteBuffer = mMOConnection.getReadBuffer();
        if (byteBuffer == null) {
            byteBuffer = this.READ_BUFFER;
        }
        if (byteBuffer.position() == byteBuffer.limit()) {
            Log.network("Read buffer exhausted for client : " + mMOConnection.getClient() + ", try to adjust buffer size, current : " + byteBuffer.capacity() + ", primary : " + (byteBuffer == this.READ_BUFFER) + ". Closing connection.");
            this.closeConnectionImpl(mMOConnection);
        } else {
            try {
                n = mMOConnection.getReadableByteChannel().read(byteBuffer);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (n > 0) {
                byteBuffer.flip();
                stats.increaseIncomingBytes(n);
                int n2 = 0;
                while (this.tryReadPacket2(selectionKey, mMOConnection, byteBuffer)) {
                    ++n2;
                }
            } else if (n == 0) {
                this.closeConnectionImpl(mMOConnection);
            } else if (n == -1) {
                this.closeConnectionImpl(mMOConnection);
            } else {
                mMOConnection.onForcedDisconnection();
                this.closeConnectionImpl(mMOConnection);
            }
        }
        if (byteBuffer == this.READ_BUFFER) {
            byteBuffer.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean tryReadPacket2(SelectionKey selectionKey, MMOConnection<T> mMOConnection, ByteBuffer byteBuffer) {
        if (mMOConnection.isClosed()) {
            return false;
        }
        int n = byteBuffer.position();
        if (byteBuffer.remaining() > this._sc.HEADER_SIZE) {
            int n2 = byteBuffer.getChar() & 0xFFFF;
            if (n2 <= this._sc.HEADER_SIZE || n2 > this._sc.PACKET_SIZE) {
                try {
                    this._sc.onIncorrectPacketSize(mMOConnection, n2);
                }
                finally {
                    this.closeConnectionImpl(mMOConnection);
                }
                return false;
            }
            if ((n2 -= this._sc.HEADER_SIZE) <= byteBuffer.remaining()) {
                stats.increaseIncomingPacketsCount();
                this.parseClientPacket(this.getPacketHandler(), byteBuffer, n2, mMOConnection);
                byteBuffer.position(n + n2 + this._sc.HEADER_SIZE);
                if (!byteBuffer.hasRemaining()) {
                    this.freeBuffer(byteBuffer, mMOConnection);
                    return false;
                }
                return true;
            }
            byteBuffer.position(n);
        }
        if (n == byteBuffer.capacity()) {
            Log.network("Read buffer exhausted for client : " + mMOConnection.getClient() + ", try to adjust buffer size, current : " + byteBuffer.capacity() + ", primary : " + (byteBuffer == this.READ_BUFFER) + ".");
        }
        if (byteBuffer == this.READ_BUFFER) {
            this.allocateReadBuffer(mMOConnection);
        } else {
            byteBuffer.compact();
        }
        return false;
    }

    protected void allocateReadBuffer(MMOConnection<T> mMOConnection) {
        mMOConnection.setReadBuffer(this.getPooledBuffer().put(this.READ_BUFFER));
        this.READ_BUFFER.clear();
    }

    protected boolean parseClientPacket(IPacketHandler<T> iPacketHandler, ByteBuffer byteBuffer, int n, MMOConnection<T> mMOConnection) {
        int n2 = byteBuffer.position();
        ((MMOClient)mMOConnection.getClient()).decrypt(byteBuffer, n);
        byteBuffer.position(n2);
        if (byteBuffer.hasRemaining()) {
            int n3 = byteBuffer.limit();
            byteBuffer.limit(n2 + n);
            ReceivablePacket<T> receivablePacket = iPacketHandler.handlePacket(byteBuffer, mMOConnection.getClient());
            if (receivablePacket != null) {
                receivablePacket.setByteBuffer(byteBuffer);
                receivablePacket.setClient(mMOConnection.getClient());
                if (receivablePacket.read()) {
                    mMOConnection.recvPacket(receivablePacket);
                }
                receivablePacket.setByteBuffer(null);
            }
            byteBuffer.limit(n3);
        }
        return true;
    }

    protected void writePacket(SelectionKey selectionKey) {
        MMOConnection mMOConnection = (MMOConnection)selectionKey.attachment();
        this.prepareWriteBuffer(mMOConnection);
        this.e.flip();
        int n = this.e.remaining();
        int n2 = -1;
        try {
            n2 = mMOConnection.getWritableChannel().write(this.e);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (n2 >= 0) {
            stats.increaseOutgoingBytes(n2);
            if (n2 != n) {
                mMOConnection.createWriteBuffer(this.e);
            }
            if (!mMOConnection.getSendQueue().isEmpty() || mMOConnection.hasPendingWriteBuffer()) {
                mMOConnection.scheduleWriteInterest();
            }
        } else {
            mMOConnection.onForcedDisconnection();
            this.closeConnectionImpl(mMOConnection);
        }
    }

    protected T getWriteClient() {
        return (T)this.a;
    }

    protected ByteBuffer getWriteBuffer() {
        return this.WRITE_BUFFER;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void prepareWriteBuffer(MMOConnection<T> mMOConnection) {
        this.a = mMOConnection.getClient();
        this.e.clear();
        if (mMOConnection.hasPendingWriteBuffer()) {
            mMOConnection.movePendingWriteBufferTo(this.e);
        }
        if (this.e.hasRemaining() && !mMOConnection.hasPendingWriteBuffer()) {
            Queue<SendablePacket<T>> queue = mMOConnection.getSendQueue();
            for (int i = 0; i < this._sc.MAX_SEND_PER_PASS; ++i) {
                SendablePacket<T> sendablePacket;
                MMOConnection<T> mMOConnection2 = mMOConnection;
                synchronized (mMOConnection2) {
                    sendablePacket = queue.poll();
                    if (sendablePacket == null) {
                        break;
                    }
                }
                try {
                    stats.increaseOutgoingPacketsCount();
                    this.putPacketIntoWriteBuffer(sendablePacket, true);
                    this.WRITE_BUFFER.flip();
                    if (this.e.remaining() >= this.WRITE_BUFFER.limit()) {
                        this.e.put(this.WRITE_BUFFER);
                        continue;
                    }
                    mMOConnection.createWriteBuffer(this.WRITE_BUFFER);
                }
                catch (Exception exception) {
                    af.error("Error in " + this.getName(), (Throwable)exception);
                }
                break;
            }
        }
        this.WRITE_BUFFER.clear();
        this.a = null;
    }

    protected final void putPacketIntoWriteBuffer(SendablePacket<T> sendablePacket, boolean bl) {
        this.WRITE_BUFFER.clear();
        int n = this.WRITE_BUFFER.position();
        this.WRITE_BUFFER.position(n + this._sc.HEADER_SIZE);
        sendablePacket.write();
        int n2 = this.WRITE_BUFFER.position() - n - this._sc.HEADER_SIZE;
        if (n2 == 0) {
            this.WRITE_BUFFER.position(n);
            return;
        }
        this.WRITE_BUFFER.position(n + this._sc.HEADER_SIZE);
        if (bl) {
            ((MMOClient)((Object)this.a)).encrypt(this.WRITE_BUFFER, n2);
            n2 = this.WRITE_BUFFER.position() - n - this._sc.HEADER_SIZE;
        }
        this.WRITE_BUFFER.putChar(n, (char)(this._sc.HEADER_SIZE + n2));
        this.WRITE_BUFFER.position(n + this._sc.HEADER_SIZE + n2);
    }

    protected SelectorConfig getConfig() {
        return this._sc;
    }

    protected Selector getSelector() {
        return this.c;
    }

    protected IMMOExecutor<T> getExecutor() {
        return this.b;
    }

    protected IPacketHandler<T> getPacketHandler() {
        return this.a;
    }

    protected IClientFactory<T> getClientFactory() {
        return this.a;
    }

    public void setAcceptFilter(IAcceptFilter iAcceptFilter) {
        this.a = iAcceptFilter;
    }

    public IAcceptFilter getAcceptFilter() {
        return this.a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void closeConnectionImpl(MMOConnection<T> mMOConnection) {
        try {
            mMOConnection.onDisconnection();
        }
        catch (Throwable throwable) {
            try {
                mMOConnection.close();
            }
            catch (IOException iOException) {
            }
            finally {
                mMOConnection.releaseBuffers();
                mMOConnection.clearQueues();
                ((MMOClient)mMOConnection.getClient()).setConnection(null);
                mMOConnection.getSelectionKey().attach(null);
                mMOConnection.getSelectionKey().cancel();
                this._connections.remove(mMOConnection);
                stats.decreseOpenedConnections();
            }
            throw throwable;
        }
        try {
            mMOConnection.close();
        }
        catch (IOException iOException) {
        }
        finally {
            mMOConnection.releaseBuffers();
            mMOConnection.clearQueues();
            ((MMOClient)mMOConnection.getClient()).setConnection(null);
            mMOConnection.getSelectionKey().attach(null);
            mMOConnection.getSelectionKey().cancel();
            this._connections.remove(mMOConnection);
            stats.decreseOpenedConnections();
        }
    }

    public void shutdown() {
        this.aA = true;
    }

    public boolean isShuttingDown() {
        return this.aA;
    }

    protected void closeAllChannels() {
        Set<SelectionKey> set = this.getSelector().keys();
        for (SelectionKey selectionKey : set) {
            try {
                selectionKey.channel().close();
            }
            catch (IOException iOException) {}
        }
    }

    protected void closeSelectorThread() {
        this.closeAllChannels();
        try {
            this.getSelector().close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static CharSequence getStats() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("selectorThreadCount: .... ").append(U.size()).append("\n");
        stringBuilder.append("=================================================\n");
        stringBuilder.append("getTotalConnections: .... ").append(stats.getTotalConnections()).append("\n");
        stringBuilder.append("getCurrentConnections: .. ").append(stats.getCurrentConnections()).append("\n");
        stringBuilder.append("getMaximumConnections: .. ").append(stats.getMaximumConnections()).append("\n");
        stringBuilder.append("getIncomingBytesTotal: .. ").append(stats.getIncomingBytesTotal()).append("\n");
        stringBuilder.append("getOutgoingBytesTotal: .. ").append(stats.getOutgoingBytesTotal()).append("\n");
        stringBuilder.append("getIncomingPacketsTotal:  ").append(stats.getIncomingPacketsTotal()).append("\n");
        stringBuilder.append("getOutgoingPacketsTotal:  ").append(stats.getOutgoingPacketsTotal()).append("\n");
        stringBuilder.append("getMaxBytesPerRead: ..... ").append(stats.getMaxBytesPerRead()).append("\n");
        stringBuilder.append("getMaxBytesPerWrite: .... ").append(stats.getMaxBytesPerWrite()).append("\n");
        stringBuilder.append("=================================================\n");
        return stringBuilder;
    }
}
