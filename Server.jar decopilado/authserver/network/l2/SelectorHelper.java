/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.network.l2;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import l2.authserver.Config;
import l2.authserver.IpBanManager;
import l2.authserver.ThreadPoolManager;
import l2.authserver.network.l2.BaseLoginClient;
import l2.commons.net.nio.impl.IAcceptFilter;
import l2.commons.net.nio.impl.IClientFactory;
import l2.commons.net.nio.impl.IMMOExecutor;
import l2.commons.net.nio.impl.IPacketHandler;
import l2.commons.net.nio.impl.MMOConnection;
import l2.commons.net.nio.impl.ReceivablePacket;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectorHelper
implements IAcceptFilter,
IClientFactory<BaseLoginClient>,
IMMOExecutor<BaseLoginClient>,
IPacketHandler<BaseLoginClient> {
    private static final Logger Z = LoggerFactory.getLogger(SelectorHelper.class);
    private final ClientCloseTask a = new ClientCloseTask();

    public SelectorHelper() {
        ThreadPoolManager.getInstance().scheduleAtFixedRate(this.a, 30000L, 30000L);
    }

    @Override
    public void execute(Runnable runnable) {
        ThreadPoolManager.getInstance().execute(runnable);
    }

    public void onIncorrectPacketSize(MMOConnection mMOConnection, int n) {
        if (Config.BLACKLIST_CLEAN_INTERVAL > 0L) {
            IpBanManager.getInstance().addBlackListIp(mMOConnection.getIpAddr());
        }
        Log.network("Incorrect packet size : " + n + "! Client : " + mMOConnection.getClient() + ". Closing connection.");
    }

    @Override
    public BaseLoginClient create(MMOConnection<BaseLoginClient> mMOConnection) {
        BaseLoginClient<BaseLoginClient> baseLoginClient = new BaseLoginClient<BaseLoginClient>(mMOConnection);
        this.a.addClient(baseLoginClient);
        baseLoginClient.init();
        return baseLoginClient;
    }

    @Override
    public boolean accept(SocketChannel socketChannel) {
        return !IpBanManager.getInstance().isIpBanned(socketChannel.socket().getInetAddress().getHostAddress());
    }

    @Override
    public ReceivablePacket<BaseLoginClient> handlePacket(ByteBuffer byteBuffer, BaseLoginClient baseLoginClient) {
        return baseLoginClient.handlePacket(byteBuffer, baseLoginClient);
    }

    private static class ClientCloseTask
    extends RunnableImpl {
        private ConcurrentLinkedQueue<BaseLoginClient> a = new ConcurrentLinkedQueue();
        private ConcurrentLinkedQueue<BaseLoginClient> b = new ConcurrentLinkedQueue();

        public void addClient(BaseLoginClient baseLoginClient) {
            this.a.add(baseLoginClient);
        }

        @Override
        public void runImpl() throws Exception {
            BaseLoginClient baseLoginClient = this.b.poll();
            while (baseLoginClient != null) {
                try {
                    baseLoginClient.closeNow(false);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                baseLoginClient = this.b.poll();
            }
            baseLoginClient = this.a.poll();
            while (baseLoginClient != null) {
                if (baseLoginClient.isAuthed() || baseLoginClient.isConnected()) {
                    this.b.add(baseLoginClient);
                }
                baseLoginClient = this.a.poll();
            }
        }
    }
}
