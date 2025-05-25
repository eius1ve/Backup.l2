/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.mutable.MutableLong
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import l2.commons.collections.LazyArrayList;
import l2.commons.dbutils.DbUtils;
import l2.commons.net.nio.impl.MMOClient;
import l2.commons.net.nio.impl.MMOConnection;
import l2.commons.net.nio.impl.SendablePacket;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.instancemanager.BypassManager;
import l2.gameserver.model.CharSelectInfoPackage;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.authcomm.AuthServerCommunication;
import l2.gameserver.network.authcomm.SessionKey;
import l2.gameserver.network.authcomm.gs2as.PlayerLogout;
import l2.gameserver.network.l2.CGModule;
import l2.gameserver.network.l2.GameCrypt;
import l2.gameserver.network.l2.SecondPasswordAuth;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.RequestNetPing;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.pfilter.PacketFilter;
import org.apache.commons.lang3.mutable.MutableLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class GameClient
extends MMOClient<MMOConnection<GameClient>> {
    private static final Logger cD = LoggerFactory.getLogger(GameClient.class);
    public static final String NO_IP = "?.?.?.?";
    public GameCrypt _crypt = null;
    public GameClientState _state;
    private String aJ;
    private Player c;
    private SessionKey a;
    private int pA = 0;
    private int serverId;
    private List<Integer> bM = new ArrayList<Integer>();
    private List<String> bN = null;
    private List<String> bO = null;
    private Map<Class<? extends L2GameClientPacket>, MutableLong> bq = new ConcurrentHashMap<Class<? extends L2GameClientPacket>, MutableLong>();
    private PacketFilter a;
    private final CGModule.CGContext a;
    private String aI;
    private SecondPasswordAuth a;
    private boolean dO = false;
    private int pB = 0;
    private int pC = 0;
    public static int DEFAULT_PAWN_CLIPPING_RANGE = 2048;
    private int pD = 0;
    private int pE = 0;
    private int pF = 0;
    private int pG = 0;
    private ScheduledFuture<?> af;

    public GameClient(MMOConnection<GameClient> mMOConnection) {
        super(mMOConnection);
        this._state = GameClientState.CONNECTED;
        this.a = CGModule.getInstance().initClient(this);
        this._crypt = CGModule.getInstance().initCrypt(this.a);
        this.a.setGameClient(this);
        this.a = PacketFilter.create(this);
    }

    @Override
    protected void onDisconnection() {
        if (this.af != null) {
            this.af.cancel(true);
            this.af = null;
        }
        this.setState(GameClientState.DISCONNECTED);
        Player player = this.getActiveChar();
        this.setActiveChar(null);
        if (player != null) {
            player.setNetConnection(null);
            player.scheduleDelete();
        }
        CGModule.CGContext cGContext = this.getCGMContext();
        CGModule.getInstance().doneClient(cGContext);
        cGContext.setGameClient(null);
        if (this.getSessionKey() != null) {
            if (this.isAuthed()) {
                AuthServerCommunication.getInstance().removeAuthedClient(this.getLogin());
                AuthServerCommunication.getInstance().sendPacket(new PlayerLogout(this.getLogin()));
            } else {
                AuthServerCommunication.getInstance().removeWaitingClient(this.getLogin());
            }
        }
    }

    @Override
    protected void onForcedDisconnection() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void markRestoredChar(int n) throws Exception {
        int n2 = this.getObjectIdForSlot(n);
        if (n2 < 0) {
            return;
        }
        if (this.c != null && this.c.getObjectId() == n2) {
            this.c.setDeleteTimer(0);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `deletetime`=0 WHERE `obj_id`=?");
            preparedStatement.setInt(1, n2);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cD.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void markToDeleteChar(int n) throws Exception {
        int n2 = this.getObjectIdForSlot(n);
        if (n2 < 0) {
            return;
        }
        if (this.c != null && this.c.getObjectId() == n2) {
            this.c.setDeleteTimer((int)(System.currentTimeMillis() / 1000L));
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `deletetime`=? WHERE `obj_id`=?");
            preparedStatement.setLong(1, (int)(System.currentTimeMillis() / 1000L));
            preparedStatement.setInt(2, n2);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cD.error("data error on update deletime char:", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public void deleteCharacterInSlot(int n) throws Exception {
        if (this.c != null) {
            return;
        }
        int n2 = this.getObjectIdForSlot(n);
        if (n2 == -1) {
            return;
        }
        this.deleteCharacterByCharacterObjId(n2);
    }

    public void deleteCharacterByCharacterObjId(int n) {
        CharacterDAO.getInstance().deleteCharacterDataByObjId(n);
    }

    public Player loadCharFromDisk(int n) {
        int n2 = this.getObjectIdForSlot(n);
        if (n2 == -1) {
            return null;
        }
        Player player = null;
        Player player2 = GameObjectsStorage.getPlayer(n2);
        if (player2 != null) {
            if (player2.isInOfflineMode() || player2.isLogoutStarted()) {
                player2.kick();
                return null;
            }
            player2.sendPacket((IStaticPacket)SystemMsg.ANOTHER_PERSON_HAS_LOGGED_IN_WITH_THE_SAME_ACCOUNT);
            GameClient gameClient = player2.getNetConnection();
            if (gameClient != null) {
                gameClient.setActiveChar(null);
                gameClient.closeNow(false);
            }
            player2.setNetConnection(this);
            player = player2;
        }
        if (player == null) {
            player = Player.restore(n2);
        }
        if (player != null) {
            this.setActiveChar(player);
        } else {
            cD.warn("could not restore obj_id: " + n2 + " in slot:" + n);
        }
        return player;
    }

    public int getObjectIdForSlot(int n) {
        if (n < 0 || n >= this.bM.size()) {
            cD.warn(this.getLogin() + " tried to modify Character in slot " + n + " but no characters exits at that slot.");
            return -1;
        }
        return this.bM.get(n);
    }

    public int getSlotForObjectId(int n) {
        List<Integer> list = this.bM;
        for (int i = 0; i < list.size(); ++i) {
            if (!Integer.valueOf(n).equals(list.get(i))) continue;
            return i;
        }
        return -1;
    }

    public SecondPasswordAuth getSecondPasswordAuth() {
        if (this.getLogin() == null || !Config.USE_SECOND_PASSWORD_AUTH) {
            return null;
        }
        if (this.a == null) {
            this.a = new SecondPasswordAuth(this.getLogin());
        }
        return this.a;
    }

    public boolean isSecondPasswordAuthed() {
        if (this.a == null) {
            return false;
        }
        return this.dO;
    }

    public void setSecondPasswordAuthed(boolean bl) {
        this.dO = bl;
    }

    public Player getActiveChar() {
        return this.c;
    }

    public SessionKey getSessionKey() {
        return this.a;
    }

    public String getLogin() {
        return this.aJ;
    }

    public void setLoginName(String string) {
        this.aJ = string;
    }

    public void setActiveChar(Player player) {
        this.c = player;
        if (player != null) {
            player.setNetConnection(this);
        }
    }

    public void setSessionId(SessionKey sessionKey) {
        this.a = sessionKey;
    }

    public void setCharSelection(CharSelectInfoPackage[] charSelectInfoPackageArray) {
        this.bM.clear();
        for (CharSelectInfoPackage charSelectInfoPackage : charSelectInfoPackageArray) {
            int n = charSelectInfoPackage.getObjectId();
            this.bM.add(n);
        }
    }

    public void setCharSelection(int n) {
        this.bM.clear();
        this.bM.add(n);
    }

    public int getRevision() {
        return this.pA;
    }

    public void setRevision(int n) {
        this.pA = n;
    }

    public int getServerId() {
        return this.serverId;
    }

    public void setServerId(int n) {
        this.serverId = n;
    }

    public String getHwid() {
        String string = CGModule.getInstance().getHWIDText(this.getCGMContext());
        if ((string == null || string.isEmpty()) && this.getSessionKey().hwid != null && this.getSessionKey().hwid.length > 0) {
            if (this.aI == null) {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.reset();
                    messageDigest.update(this.getSessionKey().hwid);
                    byte[] byArray = messageDigest.digest();
                    BigInteger bigInteger = new BigInteger(1, byArray);
                    Object object = bigInteger.toString(16);
                    while (((String)object).length() < 32) {
                        object = "0" + (String)object;
                    }
                    this.aI = object;
                    return this.aI;
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < this.getSessionKey().hwid.length; ++i) {
                        stringBuilder.append(String.format("%02x", this.getSessionKey().hwid[i]));
                    }
                    this.aI = stringBuilder.toString();
                }
            } else {
                return this.aI;
            }
        }
        return string;
    }

    private List<String> a(boolean bl) {
        if (bl) {
            if (this.bO == null) {
                this.bO = new LazyArrayList<String>();
            }
            return this.bO;
        }
        if (this.bN == null) {
            this.bN = new LazyArrayList<String>();
        }
        return this.bN;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void cleanBypasses(boolean bl) {
        List<String> list;
        List<String> list2 = list = this.a(bl);
        synchronized (list2) {
            list.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String encodeBypasses(String string, boolean bl) {
        List<String> list;
        List<String> list2 = list = this.a(bl);
        synchronized (list2) {
            return BypassManager.encode(string, list, bl);
        }
    }

    public BypassManager.DecodedBypass decodeBypass(String string) {
        BypassManager.BypassType bypassType = BypassManager.getBypassType(string, this.getActiveChar());
        boolean bl = bypassType == BypassManager.BypassType.ENCODED_BBS || bypassType == BypassManager.BypassType.SIMPLE_BBS;
        List<String> list = this.a(bl);
        if (bypassType == BypassManager.BypassType.ENCODED || bypassType == BypassManager.BypassType.ENCODED_BBS) {
            return BypassManager.decode(string, list, bl, this);
        }
        if (bypassType == BypassManager.BypassType.SIMPLE || bypassType == BypassManager.BypassType.SIMPLE_BBS) {
            return new BypassManager.DecodedBypass(string, bl, bypassType).trim();
        }
        cD.warn("Direct access to bypass: " + string + " / " + this.toString());
        return null;
    }

    public long getLastIncomePacketTimeStamp(Class<? extends L2GameClientPacket> clazz) {
        MutableLong mutableLong = this.bq.get(clazz);
        if (mutableLong == null) {
            mutableLong = new MutableLong(0L);
            this.bq.put(clazz, mutableLong);
        }
        return mutableLong.longValue();
    }

    public void setLastIncomePacketTimeStamp(Class<? extends L2GameClientPacket> clazz, long l) {
        MutableLong mutableLong = this.bq.get(clazz);
        if (mutableLong == null) {
            mutableLong = new MutableLong(0L);
            this.bq.put(clazz, mutableLong);
        }
        mutableLong.setValue(l);
    }

    public PacketFilter getPacketFilter() {
        return this.a;
    }

    @Override
    public boolean encrypt(ByteBuffer byteBuffer, int n) {
        this._crypt.encrypt(byteBuffer.array(), byteBuffer.position(), n);
        byteBuffer.position(byteBuffer.position() + n);
        return true;
    }

    @Override
    public boolean decrypt(ByteBuffer byteBuffer, int n) {
        boolean bl = this._crypt.decrypt(byteBuffer.array(), byteBuffer.position(), n);
        return bl;
    }

    CGModule.CGContext getCGMContext() {
        return this.a;
    }

    public void sendPacket(L2GameServerPacket l2GameServerPacket) {
        if (!this.isConnected()) {
            return;
        }
        if (l2GameServerPacket instanceof NpcHtmlMessage) {
            NpcHtmlMessage npcHtmlMessage = (NpcHtmlMessage)l2GameServerPacket;
            npcHtmlMessage.processHtml(this);
        }
        ((MMOConnection)((Object)this.getConnection())).sendPacket((SendablePacket<GameClient>)l2GameServerPacket);
    }

    public void sendPacket(L2GameServerPacket ... l2GameServerPacketArray) {
        if (!this.isConnected()) {
            return;
        }
        for (L2GameServerPacket l2GameServerPacket : l2GameServerPacketArray) {
            if (!(l2GameServerPacket instanceof NpcHtmlMessage)) continue;
            NpcHtmlMessage npcHtmlMessage = (NpcHtmlMessage)l2GameServerPacket;
            npcHtmlMessage.processHtml(this);
        }
        ((MMOConnection)this.getConnection()).sendPacket(l2GameServerPacketArray);
    }

    public void sendPackets(List<L2GameServerPacket> list) {
        if (!this.isConnected()) {
            return;
        }
        for (L2GameServerPacket l2GameServerPacket : list) {
            if (!(l2GameServerPacket instanceof NpcHtmlMessage)) continue;
            NpcHtmlMessage npcHtmlMessage = (NpcHtmlMessage)l2GameServerPacket;
            npcHtmlMessage.processHtml(this);
        }
        ((MMOConnection)this.getConnection()).sendPackets(list);
    }

    public void close(L2GameServerPacket l2GameServerPacket) {
        if (!this.isConnected()) {
            return;
        }
        if (l2GameServerPacket instanceof NpcHtmlMessage) {
            NpcHtmlMessage npcHtmlMessage = (NpcHtmlMessage)l2GameServerPacket;
            npcHtmlMessage.processHtml(this);
        }
        ((MMOConnection)((Object)this.getConnection())).close(l2GameServerPacket);
    }

    public Object enableCrypt() {
        return this._crypt.setKey(this._crypt.initKey(this.getCGMContext()));
    }

    public GameClientState getState() {
        return this._state;
    }

    public boolean isStateIs(GameClientState gameClientState) {
        return this.getState() == gameClientState;
    }

    public void setState(GameClientState gameClientState) {
        this._state = gameClientState;
        switch (gameClientState) {
            case AUTHED: {
                if (this.getConnection() == null) break;
                this.onPing(0, 0, DEFAULT_PAWN_CLIPPING_RANGE);
            }
        }
    }

    public void onPacketReadFail() {
        if (this.pB++ >= Config.FAIL_PACKET_AMOUNT_LIMIT) {
            cD.warn("Too many client packet fails, connection closed : " + this);
            this.closeNow(true);
        }
    }

    public void onUnknownPacket() {
        if (this.pC++ >= Config.UNKNOWN_PACKET_AMOUNT_LIMIT) {
            cD.warn("Too many client unknown packets, connection closed : " + this);
            this.closeNow(true);
        }
    }

    public String toString() {
        return this._state + " IP: " + this.getIpAddr() + (String)(this.aJ == null ? "" : " Account: " + this.aJ) + (String)(this.c == null ? "" : " Player : " + this.c);
    }

    public void onPing(int n, int n2, int n3) {
        if (this.pD == 0 || this.pD == n) {
            long l = System.currentTimeMillis();
            long l2 = GameServer.getInstance().getServerStartTime();
            this.pE = this.pD > 0 ? (int)(l - l2 - (long)n) : 0;
            this.pF = n2;
            this.pG = n3;
            if (this.af == null) {
                this.af = ThreadPoolManager.getInstance().schedule(new PingTask(this), 30000L);
            }
        }
    }

    private final void bS() {
        int n;
        long l = System.currentTimeMillis();
        long l2 = GameServer.getInstance().getServerStartTime();
        this.pD = n = (int)(l - l2);
        this.sendPacket((L2GameServerPacket)new RequestNetPing(n));
        this.af = null;
    }

    public int getPing() {
        return this.pE;
    }

    public int getFps() {
        return this.pF;
    }

    public int getPawnClippingRange() {
        return this.pG;
    }

    public void sendMessage(String string) {
        if (!this.isConnected()) {
            return;
        }
        this.sendPacket((L2GameServerPacket)new SystemMessage(SystemMsg.S1).addString(string));
    }

    public static final class GameClientState
    extends Enum<GameClientState> {
        public static final /* enum */ GameClientState CONNECTED = new GameClientState();
        public static final /* enum */ GameClientState AUTHED = new GameClientState();
        public static final /* enum */ GameClientState IN_GAME = new GameClientState();
        public static final /* enum */ GameClientState DISCONNECTED = new GameClientState();
        private static final /* synthetic */ GameClientState[] a;

        public static GameClientState[] values() {
            return (GameClientState[])a.clone();
        }

        public static GameClientState valueOf(String string) {
            return Enum.valueOf(GameClientState.class, string);
        }

        private static /* synthetic */ GameClientState[] a() {
            return new GameClientState[]{CONNECTED, AUTHED, IN_GAME, DISCONNECTED};
        }

        static {
            a = GameClientState.a();
        }
    }

    private static class PingTask
    extends RunnableImpl {
        private final GameClient c;

        private PingTask(GameClient gameClient) {
            this.c = gameClient;
        }

        @Override
        public void runImpl() throws Exception {
            if (this.c == null || !this.c.isConnected()) {
                return;
            }
            this.c.bS();
        }
    }
}
