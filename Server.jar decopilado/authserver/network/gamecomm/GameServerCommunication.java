/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.network.gamecomm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import l2.authserver.ThreadPoolManager;
import l2.authserver.network.gamecomm.GameServer;
import l2.authserver.network.gamecomm.GameServerConnection;
import l2.authserver.network.gamecomm.PacketHandler;
import l2.authserver.network.gamecomm.ReceivablePacket;
import l2.authserver.network.gamecomm.SendablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GameServerCommunication
extends Thread {
    private static final Logger S = LoggerFactory.getLogger(GameServerCommunication.class);
    private static final GameServerCommunication b = new GameServerCommunication();
    private final ByteBuffer a;
    private Selector a = ByteBuffer.allocate(65536).order(ByteOrder.LITTLE_ENDIAN);
    private boolean aj;

    public static GameServerCommunication getInstance() {
        return b;
    }

    private GameServerCommunication() {
    }

    public void openServerSocket(InetAddress inetAddress, int n) throws IOException {
        this.a = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(inetAddress == null ? new InetSocketAddress(n) : new InetSocketAddress(inetAddress, n));
        serverSocketChannel.register(this.a, serverSocketChannel.validOps());
    }

    @Override
    public void run() {
        SelectionKey selectionKey = null;
        while (!this.isShutdown()) {
            try {
                this.a.select();
                Set<SelectionKey> set = this.a.selectedKeys();
                Iterator<SelectionKey> iterator = set.iterator();
                while (iterator.hasNext()) {
                    selectionKey = iterator.next();
                    iterator.remove();
                    if (!selectionKey.isValid()) {
                        this.close(selectionKey);
                        continue;
                    }
                    int n = selectionKey.readyOps();
                    switch (n) {
                        case 8: {
                            this.close(selectionKey);
                            break;
                        }
                        case 16: {
                            this.accept(selectionKey);
                            break;
                        }
                        case 4: {
                            this.write(selectionKey);
                            break;
                        }
                        case 1: {
                            this.read(selectionKey);
                            break;
                        }
                        case 5: {
                            this.write(selectionKey);
                            this.read(selectionKey);
                        }
                    }
                }
            }
            catch (ClosedSelectorException closedSelectorException) {
                S.error("Selector " + this.a + " closed!");
                return;
            }
            catch (IOException iOException) {
                S.error("Gameserver I/O error: " + iOException.getMessage());
                this.close(selectionKey);
            }
            catch (Exception exception) {
                S.error("", (Throwable)exception);
            }
        }
    }

    public void accept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        SelectionKey selectionKey2 = socketChannel.register(this.a, 1);
        GameServerConnection gameServerConnection = new GameServerConnection(selectionKey2);
        selectionKey2.attach(gameServerConnection);
        gameServerConnection.setGameServer(new GameServer(gameServerConnection));
    }

    public void read(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        GameServerConnection gameServerConnection = (GameServerConnection)selectionKey.attachment();
        GameServer gameServer = gameServerConnection.getGameServer();
        ByteBuffer byteBuffer = gameServerConnection.getReadBuffer();
        int n = socketChannel.read(byteBuffer);
        if (n == -1) {
            this.close(selectionKey);
            return;
        }
        if (n == 0) {
            return;
        }
        byteBuffer.flip();
        while (this.tryReadPacket(selectionKey, gameServer, byteBuffer)) {
        }
    }

    protected boolean tryReadPacket(SelectionKey selectionKey, GameServer gameServer, ByteBuffer byteBuffer) throws IOException {
        int n = byteBuffer.position();
        if (byteBuffer.remaining() > 2) {
            int n2 = byteBuffer.getShort() & 0xFFFF;
            if (n2 <= 2) {
                throw new IOException("Incorrect packet size: <= 2");
            }
            if ((n2 -= 2) <= byteBuffer.remaining()) {
                int n3 = byteBuffer.limit();
                byteBuffer.limit(n + n2 + 2);
                ReceivablePacket receivablePacket = PacketHandler.handlePacket(gameServer, byteBuffer);
                if (receivablePacket != null) {
                    receivablePacket.setByteBuffer(byteBuffer);
                    receivablePacket.setClient(gameServer);
                    if (receivablePacket.read()) {
                        ThreadPoolManager.getInstance().execute(receivablePacket);
                    }
                    receivablePacket.setByteBuffer(null);
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
    public void write(SelectionKey selectionKey) throws IOException {
        boolean bl;
        GameServerConnection gameServerConnection = (GameServerConnection)selectionKey.attachment();
        GameServer gameServer = gameServerConnection.getGameServer();
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        ByteBuffer byteBuffer = this.getWriteBuffer();
        gameServerConnection.disableWriteInterest();
        Queue<SendablePacket> queue = gameServerConnection.sendQueue;
        Lock lock = gameServerConnection.sendLock;
        lock.lock();
        try {
            SendablePacket sendablePacket;
            int n = 0;
            while (n++ < 64 && (sendablePacket = queue.poll()) != null) {
                int n2 = byteBuffer.position();
                byteBuffer.position(n2 + 2);
                sendablePacket.setByteBuffer(byteBuffer);
                sendablePacket.setClient(gameServer);
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
            bl = queue.isEmpty();
            if (bl) {
                gameServerConnection.disableWriteInterest();
            }
        }
        finally {
            lock.unlock();
        }
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        if (byteBuffer.remaining() > 0) {
            byteBuffer.compact();
            bl = false;
        } else {
            byteBuffer.clear();
        }
        if (!bl && gameServerConnection.enableWriteInterest()) {
            this.a.wakeup();
        }
    }

    private ByteBuffer getWriteBuffer() {
        return this.a;
    }

    public void close(SelectionKey selectionKey) {
        if (selectionKey == null) {
            return;
        }
        try {
            try {
                GameServerConnection gameServerConnection = (GameServerConnection)selectionKey.attachment();
                if (gameServerConnection != null) {
                    gameServerConnection.onDisconnection();
                }
            }
            finally {
                selectionKey.channel().close();
                selectionKey.cancel();
            }
        }
        catch (IOException iOException) {
            S.error("", (Throwable)iOException);
        }
    }

    public boolean isShutdown() {
        return this.aj;
    }

    public void setShutdown(boolean bl) {
        this.aj = bl;
    }
}
