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
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import l2.commons.net.nio.impl.HaProxyMMOConnection;
import l2.commons.net.nio.impl.IAcceptFilter;
import l2.commons.net.nio.impl.IClientFactory;
import l2.commons.net.nio.impl.IMMOExecutor;
import l2.commons.net.nio.impl.IPacketHandler;
import l2.commons.net.nio.impl.MMOClient;
import l2.commons.net.nio.impl.MMOConnection;
import l2.commons.net.nio.impl.SelectorConfig;
import l2.commons.net.nio.impl.SelectorThread;
import l2.gameserver.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HaProxySelectorThread<T extends MMOClient>
extends SelectorThread<T> {
    private static final Logger ae = LoggerFactory.getLogger(HaProxySelectorThread.class);
    private static final int eq = 16;
    private static final byte[] h = new byte[]{13, 10, 13, 10, 0, 13, 10, 81, 85, 73, 84, 10};
    private final byte[] i = new byte[12];
    private final byte[] j = new byte[12];
    private final byte[] k = new byte[36];

    public HaProxySelectorThread(SelectorConfig selectorConfig, IPacketHandler<T> iPacketHandler, IMMOExecutor<T> iMMOExecutor, IClientFactory<T> iClientFactory, IAcceptFilter iAcceptFilter) throws IOException {
        super(selectorConfig, iPacketHandler, iMMOExecutor, iClientFactory, iAcceptFilter);
    }

    @Override
    public void openServerSocket(InetAddress inetAddress, int n) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(inetAddress == null ? new InetSocketAddress(n) : new InetSocketAddress(inetAddress, n));
        serverSocketChannel.register(this.getSelector(), serverSocketChannel.validOps());
        this.setName("HaProxySelectorThread:" + serverSocketChannel.socket().getLocalPort());
    }

    @Override
    protected void acceptConnection(SelectionKey selectionKey) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
        try {
            SocketChannel socketChannel;
            while ((socketChannel = serverSocketChannel.accept()) != null) {
                socketChannel.configureBlocking(false);
                SelectionKey selectionKey2 = socketChannel.register(this.getSelector(), 1);
                Socket socket = socketChannel.socket();
                HaProxyMMOConnection haProxyMMOConnection = new HaProxyMMOConnection(this, socket, selectionKey2);
                haProxyMMOConnection.setIpAddr("?.?.?.?");
                selectionKey2.attach(haProxyMMOConnection);
                this._connections.add(haProxyMMOConnection);
            }
        }
        catch (IOException iOException) {
            ae.error("Error in " + this.getName(), (Throwable)iOException);
        }
    }

    @Override
    protected void finishConnection(SelectionKey selectionKey) {
        try {
            ((SocketChannel)selectionKey.channel()).finishConnect();
        }
        catch (IOException iOException) {
            HaProxyMMOConnection haProxyMMOConnection = (HaProxyMMOConnection)selectionKey.attachment();
            if (haProxyMMOConnection.isHaProxyInitiated()) {
                Object t = haProxyMMOConnection.getClient();
                ((MMOConnection)((MMOClient)t).getConnection()).onForcedDisconnection();
            } else {
                haProxyMMOConnection.onForcedDisconnection();
            }
            this.closeConnectionImpl(haProxyMMOConnection);
        }
    }

    @Override
    protected void readPacket(SelectionKey selectionKey) {
        HaProxyMMOConnection haProxyMMOConnection = (HaProxyMMOConnection)selectionKey.attachment();
        if (haProxyMMOConnection.isClosed()) {
            return;
        }
        int n = -2;
        ByteBuffer byteBuffer = haProxyMMOConnection.getReadBuffer();
        if (byteBuffer == null) {
            byteBuffer = this.READ_BUFFER;
        }
        if (byteBuffer.position() == byteBuffer.limit()) {
            Log.network("Read buffer exhausted for client : " + haProxyMMOConnection.getClient() + ", try to adjust buffer size, current : " + byteBuffer.capacity() + ", primary : " + (byteBuffer == this.READ_BUFFER) + ". Closing connection.");
            this.closeConnectionImpl(haProxyMMOConnection);
        } else {
            try {
                n = haProxyMMOConnection.getReadableByteChannel().read(byteBuffer);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (n > 0) {
                byteBuffer.flip();
                stats.increaseIncomingBytes(n);
                if (haProxyMMOConnection.isHaProxyInitiated()) {
                    while (this.tryReadPacket2(selectionKey, haProxyMMOConnection, byteBuffer)) {
                    }
                } else if (this.tryInitHaProxyHeader(selectionKey, haProxyMMOConnection, byteBuffer) && haProxyMMOConnection.isHaProxyInitiated()) {
                    while (this.tryReadPacket2(selectionKey, haProxyMMOConnection, byteBuffer)) {
                    }
                }
            } else if (n == 0) {
                this.closeConnectionImpl(haProxyMMOConnection);
            } else if (n == -1) {
                this.closeConnectionImpl(haProxyMMOConnection);
            } else {
                haProxyMMOConnection.onForcedDisconnection();
                this.closeConnectionImpl(haProxyMMOConnection);
            }
        }
        if (byteBuffer == this.READ_BUFFER) {
            byteBuffer.clear();
        }
    }

    private boolean a(byte[] byArray) {
        return Arrays.equals(byArray, h);
    }

    private String a(int n, byte[] byArray) {
        if (n == 17) {
            return String.format("%d.%d.%d.%d", 0xFF & byArray[0], 0xFF & byArray[1], 0xFF & byArray[2], 0xFF & byArray[3]);
        }
        if (n == 33) {
            return String.format("%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x:%02x%02x", 0xFF & byArray[0], 0xFF & byArray[1], 0xFF & byArray[2], 0xFF & byArray[3], 0xFF & byArray[4], 0xFF & byArray[5], 0xFF & byArray[6], 0xFF & byArray[7], 0xFF & byArray[8], 0xFF & byArray[9], 0xFF & byArray[10], 0xFF & byArray[11], 0xFF & byArray[12], 0xFF & byArray[13], 0xFF & byArray[14], 0xFF & byArray[15]);
        }
        return null;
    }

    public static final String formatHex(byte[] byArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < byArray.length; i += 16) {
            int n;
            int n2;
            stringBuilder.append(String.format("%04x: ", i));
            for (n2 = 0; n2 < 16; ++n2) {
                n = i + n2;
                if (n < byArray.length) {
                    stringBuilder.append(String.format("%02x ", byArray[n]));
                    continue;
                }
                stringBuilder.append("   ");
            }
            stringBuilder.append(": ");
            for (n2 = 0; n2 < 16; ++n2) {
                n = i + n2;
                if (n < byArray.length) {
                    byte by = byArray[n];
                    if (by > 31 && by < 128) {
                        stringBuilder.append(String.format("%c", by));
                        continue;
                    }
                    stringBuilder.append('.');
                    continue;
                }
                stringBuilder.append(' ');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    protected boolean tryInitHaProxyHeader(SelectionKey selectionKey, HaProxyMMOConnection<T> haProxyMMOConnection, ByteBuffer byteBuffer) {
        if (haProxyMMOConnection.isClosed()) {
            return false;
        }
        int n = byteBuffer.position();
        if (byteBuffer.remaining() >= 16) {
            byteBuffer.get(this.i);
            if (!this.a(this.i)) {
                Log.network("Incorrect HAProxy sign. Closing connection.");
                this.closeConnectionImpl(haProxyMMOConnection);
                return false;
            }
            int n2 = byteBuffer.get() & 0xFF;
            int n3 = 0xF & n2 >> 4;
            if (n3 != 2) {
                Log.network("Incorrect HAProxy version. Expected v2, got " + n3 + ". Closing connection.");
                this.closeConnectionImpl(haProxyMMOConnection);
                return false;
            }
            int n4 = 0xF & n2;
            if (n4 == 0) {
                this.closeConnectionImpl(haProxyMMOConnection);
                return false;
            }
            if (n4 != 1) {
                Log.network("Unknown HAProxy command: " + n4 + ". Closing connection.");
                this.closeConnectionImpl(haProxyMMOConnection);
                return false;
            }
            int n5 = byteBuffer.get() & 0xFF;
            byte[] byArray = null;
            if (n5 == 17) {
                byArray = this.j;
            } else if (n5 == 33) {
                byArray = this.k;
            }
            if (byArray == null) {
                Log.network("Incorrect HAProxy protocol family: " + n5 + "! Closing connection.");
                this.closeConnectionImpl(haProxyMMOConnection);
                return false;
            }
            int n6 = (byteBuffer.get() & 0xFF) << 8;
            if ((n6 |= byteBuffer.get() & 0xFF) <= 0 || n6 != byArray.length) {
                Log.network("Incorrect HAProxy packet size : " + n6 + "! Closing connection.");
                this.closeConnectionImpl(haProxyMMOConnection);
                return false;
            }
            if (n6 <= byteBuffer.remaining()) {
                byteBuffer.get(byArray);
                String string = this.a(n5, byArray);
                if (string == null) {
                    Log.network("Incorrect HAProxy source addr! Closing connection.");
                    this.closeConnectionImpl(haProxyMMOConnection);
                    return false;
                }
                byteBuffer.position(n + 16 + n6);
                haProxyMMOConnection.setIpAddr(string);
                haProxyMMOConnection.setHaProxyInitiated(true);
                HaProxyMMOConnection<T> haProxyMMOConnection2 = this.getClientFactory().create(haProxyMMOConnection);
                ((MMOClient)((Object)haProxyMMOConnection2)).setConnection(haProxyMMOConnection);
                haProxyMMOConnection.setClient(haProxyMMOConnection2);
                if (!byteBuffer.hasRemaining()) {
                    this.freeBuffer(byteBuffer, haProxyMMOConnection);
                    return false;
                }
                return true;
            }
            byteBuffer.position(n);
        }
        if (n == byteBuffer.capacity()) {
            Log.network("Read buffer exhausted for HAProxy client. Try to adjust buffer size, current : " + byteBuffer.capacity() + ", primary : " + (byteBuffer == this.READ_BUFFER) + ".");
        }
        if (byteBuffer == this.READ_BUFFER) {
            this.allocateReadBuffer(haProxyMMOConnection);
        } else {
            byteBuffer.compact();
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
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
                Object object = mMOConnection.getClient();
                if (object != null) {
                    ((MMOClient)object).setConnection(null);
                }
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
            Object object = mMOConnection.getClient();
            if (object != null) {
                ((MMOClient)object).setConnection(null);
            }
            mMOConnection.getSelectionKey().attach(null);
            mMOConnection.getSelectionKey().cancel();
            this._connections.remove(mMOConnection);
            stats.decreseOpenedConnections();
        }
    }
}
