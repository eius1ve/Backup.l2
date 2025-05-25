/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.nio.impl;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import l2.commons.net.nio.impl.MMOClient;
import l2.commons.net.nio.impl.MMOExecutableQueue;
import l2.commons.net.nio.impl.ReceivablePacket;
import l2.commons.net.nio.impl.SelectorThread;
import l2.commons.net.nio.impl.SendablePacket;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MMOConnection<T extends MMOClient> {
    public static final String NO_IP = "?.?.?.?";
    private final SelectorThread<T> a;
    private final SelectionKey b;
    private final Socket a;
    private final WritableByteChannel a;
    private final ReadableByteChannel a;
    private final Queue<SendablePacket<T>> a;
    private final Queue<ReceivablePacket<T>> b;
    private T _client;
    private ByteBuffer b;
    private ByteBuffer c;
    private ByteBuffer d;
    private boolean ay;
    private long ar;
    private boolean az;
    private long as;
    private AtomicBoolean c = new AtomicBoolean();
    private String aM;

    public MMOConnection(SelectorThread<T> selectorThread, Socket socket, SelectionKey selectionKey) {
        this.a = selectorThread;
        this.b = selectionKey;
        this.a = socket;
        this.a = socket.getChannel();
        this.a = socket.getChannel();
        this.a = new ArrayDeque();
        this.b = new MMOExecutableQueue<T>(selectorThread.getExecutor());
    }

    public void setClient(T t) {
        this._client = t;
    }

    public T getClient() {
        return this._client;
    }

    public void recvPacket(ReceivablePacket<T> receivablePacket) {
        if (receivablePacket == null) {
            return;
        }
        if (this.isClosed()) {
            return;
        }
        this.b.add(receivablePacket);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sendPacket(SendablePacket<T> sendablePacket) {
        if (sendablePacket == null) {
            return;
        }
        MMOConnection mMOConnection = this;
        synchronized (mMOConnection) {
            if (this.isClosed()) {
                return;
            }
            this.a.add(sendablePacket);
        }
        this.scheduleWriteInterest();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sendPacket(SendablePacket<T> ... sendablePacketArray) {
        if (sendablePacketArray == null || sendablePacketArray.length == 0) {
            return;
        }
        MMOConnection mMOConnection = this;
        synchronized (mMOConnection) {
            if (this.isClosed()) {
                return;
            }
            for (SendablePacket<T> sendablePacket : sendablePacketArray) {
                if (sendablePacket == null) continue;
                this.a.add(sendablePacket);
            }
        }
        this.scheduleWriteInterest();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sendPackets(List<? extends SendablePacket<T>> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        MMOConnection mMOConnection = this;
        synchronized (mMOConnection) {
            if (this.isClosed()) {
                return;
            }
            for (int i = 0; i < list.size(); ++i) {
                SendablePacket<T> sendablePacket = list.get(i);
                if (sendablePacket == null) continue;
                this.a.add(sendablePacket);
            }
        }
        this.scheduleWriteInterest();
    }

    protected SelectionKey getSelectionKey() {
        return this.b;
    }

    protected void disableReadInterest() {
        try {
            this.b.interestOps(this.b.interestOps() & 0xFFFFFFFE);
        }
        catch (CancelledKeyException cancelledKeyException) {
            // empty catch block
        }
    }

    protected void scheduleWriteInterest() {
        try {
            if (this.c.compareAndSet(false, true)) {
                this.as = System.currentTimeMillis();
            }
        }
        catch (CancelledKeyException cancelledKeyException) {
            // empty catch block
        }
    }

    protected void disableWriteInterest() {
        try {
            if (this.c.compareAndSet(true, false)) {
                this.b.interestOps(this.b.interestOps() & 0xFFFFFFFB);
            }
        }
        catch (CancelledKeyException cancelledKeyException) {
            // empty catch block
        }
    }

    protected void enableWriteInterest() {
        if (this.c.compareAndSet(true, false)) {
            this.b.interestOps(this.b.interestOps() | 4);
        }
    }

    protected boolean isPendingWrite() {
        return this.c.get();
    }

    public long getPendingWriteTime() {
        return this.as;
    }

    public Socket getSocket() {
        return this.a;
    }

    public WritableByteChannel getWritableChannel() {
        return this.a;
    }

    public ReadableByteChannel getReadableByteChannel() {
        return this.a;
    }

    protected Queue<SendablePacket<T>> getSendQueue() {
        return this.a;
    }

    protected Queue<ReceivablePacket<T>> getRecvQueue() {
        return this.b;
    }

    protected void createWriteBuffer(ByteBuffer byteBuffer) {
        if (this.c == null) {
            this.c = this.a.getPooledBuffer();
            this.c.put(byteBuffer);
        } else {
            ByteBuffer byteBuffer2 = this.a.getPooledBuffer();
            byteBuffer2.put(byteBuffer);
            int n = byteBuffer2.remaining();
            this.c.flip();
            int n2 = this.c.limit();
            if (n >= this.c.remaining()) {
                byteBuffer2.put(this.c);
                this.a.recycleBuffer(this.c);
                this.c = byteBuffer2;
            } else {
                this.c.limit(n);
                byteBuffer2.put(this.c);
                this.c.limit(n2);
                this.c.compact();
                this.d = this.c;
                this.c = byteBuffer2;
            }
        }
    }

    protected boolean hasPendingWriteBuffer() {
        return this.c != null;
    }

    protected void movePendingWriteBufferTo(ByteBuffer byteBuffer) {
        this.c.flip();
        byteBuffer.put(this.c);
        this.a.recycleBuffer(this.c);
        this.c = this.d;
        this.d = null;
    }

    protected void setReadBuffer(ByteBuffer byteBuffer) {
        this.b = byteBuffer;
    }

    public ByteBuffer getReadBuffer() {
        return this.b;
    }

    public boolean isClosed() {
        return this.ay || this.az;
    }

    public boolean isPengingClose() {
        return this.ay;
    }

    public long getPendingCloseTime() {
        return this.ar;
    }

    protected void close() throws IOException {
        this.az = true;
        this.a.close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void closeNow() {
        MMOConnection mMOConnection = this;
        synchronized (mMOConnection) {
            if (this.isClosed()) {
                return;
            }
            this.a.clear();
            this.ay = true;
            this.ar = System.currentTimeMillis();
        }
        this.disableReadInterest();
        this.disableWriteInterest();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void close(SendablePacket<T> sendablePacket) {
        MMOConnection mMOConnection = this;
        synchronized (mMOConnection) {
            if (this.isClosed()) {
                return;
            }
            this.a.clear();
            this.sendPacket(sendablePacket);
            this.ay = true;
            this.ar = System.currentTimeMillis();
        }
        this.disableReadInterest();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void closeLater() {
        MMOConnection mMOConnection = this;
        synchronized (mMOConnection) {
            if (this.isClosed()) {
                return;
            }
            this.ay = true;
            this.ar = System.currentTimeMillis();
        }
    }

    protected void releaseBuffers() {
        if (this.c != null) {
            this.a.recycleBuffer(this.c);
            this.c = null;
            if (this.d != null) {
                this.a.recycleBuffer(this.d);
                this.d = null;
            }
        }
        if (this.b != null) {
            this.a.recycleBuffer(this.b);
            this.b = null;
        }
    }

    protected void clearQueues() {
        this.a.clear();
        this.b.clear();
    }

    protected void onDisconnection() {
        ((MMOClient)this.getClient()).onDisconnection();
    }

    protected void onForcedDisconnection() {
        ((MMOClient)this.getClient()).onForcedDisconnection();
    }

    public String getIpAddr() {
        return this.aM;
    }

    protected void setIpAddr(String string) {
        this.aM = string;
    }

    public String toString() {
        return "MMOConnection: selector=" + this.a + "; client=" + this.getClient();
    }
}
