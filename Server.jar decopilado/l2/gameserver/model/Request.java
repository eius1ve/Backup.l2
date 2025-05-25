/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.collections.MultiValueSet;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Request
extends MultiValueSet<String> {
    private static final long bI = 1L;
    private static final Logger bG = LoggerFactory.getLogger(Request.class);
    private static final AtomicInteger q = new AtomicInteger();
    private final int jo = q.incrementAndGet();
    private L2RequestType a;
    private HardReference<Player> Q;
    private HardReference<Player> R;
    private volatile boolean cr;
    private volatile boolean cs;
    private volatile boolean ct;
    private volatile boolean cu;
    private long ap;
    private Future<?> a;

    public Request(L2RequestType l2RequestType, Player player, Player player2) {
        this.Q = player.getRef();
        this.R = player2.getRef();
        this.a = l2RequestType;
        player.setRequest(this);
        player2.setRequest(this);
    }

    public Request setTimeout(long l) {
        this.ap = l > 0L ? System.currentTimeMillis() + l : 0L;
        this.a = ThreadPoolManager.getInstance().schedule(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                Request.this.timeout();
            }
        }, l);
        return this;
    }

    public int getId() {
        return this.jo;
    }

    public void cancel() {
        this.ct = true;
        if (this.a != null) {
            this.a.cancel(false);
        }
        this.a = null;
        Player player = this.getRequestor();
        if (player != null && player.getRequest() == this) {
            player.setRequest(null);
        }
        if ((player = this.getReciever()) != null && player.getRequest() == this) {
            player.setRequest(null);
        }
    }

    public void done() {
        this.cu = true;
        if (this.a != null) {
            this.a.cancel(false);
        }
        this.a = null;
        Player player = this.getRequestor();
        if (player != null && player.getRequest() == this) {
            player.setRequest(null);
        }
        if ((player = this.getReciever()) != null && player.getRequest() == this) {
            player.setRequest(null);
        }
    }

    public void timeout() {
        Player player = this.getReciever();
        if (player != null && player.getRequest() == this) {
            player.sendPacket((IStaticPacket)SystemMsg.TIME_EXPIRED);
        }
        this.cancel();
    }

    public Player getOtherPlayer(Player player) {
        if (player == this.getRequestor()) {
            return this.getReciever();
        }
        if (player == this.getReciever()) {
            return this.getRequestor();
        }
        return null;
    }

    public Player getRequestor() {
        return this.Q.get();
    }

    public Player getReciever() {
        return this.R.get();
    }

    public boolean isInProgress() {
        if (this.ct) {
            return false;
        }
        if (this.cu) {
            return false;
        }
        if (this.ap == 0L) {
            return true;
        }
        return this.ap > System.currentTimeMillis();
    }

    public boolean isTypeOf(L2RequestType l2RequestType) {
        return this.a == l2RequestType;
    }

    public void confirm(Player player) {
        if (player == this.getRequestor()) {
            this.cr = true;
        } else if (player == this.getReciever()) {
            this.cs = true;
        }
    }

    public boolean isConfirmed(Player player) {
        if (player == this.getRequestor()) {
            return this.cr;
        }
        if (player == this.getReciever()) {
            return this.cs;
        }
        return false;
    }

    public static final class L2RequestType
    extends Enum<L2RequestType> {
        public static final /* enum */ L2RequestType CUSTOM = new L2RequestType();
        public static final /* enum */ L2RequestType PARTY = new L2RequestType();
        public static final /* enum */ L2RequestType PARTY_ROOM = new L2RequestType();
        public static final /* enum */ L2RequestType CLAN = new L2RequestType();
        public static final /* enum */ L2RequestType ALLY = new L2RequestType();
        public static final /* enum */ L2RequestType TRADE = new L2RequestType();
        public static final /* enum */ L2RequestType TRADE_REQUEST = new L2RequestType();
        public static final /* enum */ L2RequestType FRIEND = new L2RequestType();
        public static final /* enum */ L2RequestType CHANNEL = new L2RequestType();
        public static final /* enum */ L2RequestType DUEL = new L2RequestType();
        private static final /* synthetic */ L2RequestType[] a;

        public static L2RequestType[] values() {
            return (L2RequestType[])a.clone();
        }

        public static L2RequestType valueOf(String string) {
            return Enum.valueOf(L2RequestType.class, string);
        }

        private static /* synthetic */ L2RequestType[] a() {
            return new L2RequestType[]{CUSTOM, PARTY, PARTY_ROOM, CLAN, ALLY, TRADE, TRADE_REQUEST, FRIEND, CHANNEL, DUEL};
        }

        static {
            a = L2RequestType.a();
        }
    }
}
