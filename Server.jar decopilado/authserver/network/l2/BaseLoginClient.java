/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2;

import java.nio.ByteBuffer;
import l2.authserver.crypt.CryptManager;
import l2.authserver.network.l2.L2LoginClient;
import l2.commons.net.nio.impl.IPacketHandler;
import l2.commons.net.nio.impl.MMOClient;
import l2.commons.net.nio.impl.MMOConnection;
import l2.commons.net.nio.impl.ReceivablePacket;

public class BaseLoginClient<T extends BaseLoginClient<T>>
extends MMOClient<MMOConnection<T>>
implements IPacketHandler<T> {
    private final CryptManager.LoginCryptProvider a = CryptManager.getInstance().getLogicCryptProvider();
    protected L2LoginClient _loginClient;

    public BaseLoginClient(MMOConnection<T> mMOConnection) {
        super(mMOConnection);
    }

    public void init() {
        ((MMOConnection)((Object)this.getConnection())).sendPacket(this.a.getStaticInitPacket());
    }

    public L2LoginClient getLoginClient() {
        return this._loginClient;
    }

    @Override
    public boolean decrypt(ByteBuffer byteBuffer, int n) {
        L2LoginClient l2LoginClient = this.getConnection();
        this._loginClient = new L2LoginClient((MMOConnection<L2LoginClient>)this.getConnection());
        ((MMOConnection)((Object)l2LoginClient)).setClient((L2LoginClient)this._loginClient);
        this._loginClient.setConnection(l2LoginClient);
        this._loginClient.setLoginCrypt(this.a.newLoginCrypt());
        this.setAuthed(true);
        this.setConnection(null);
        return this._loginClient.decrypt(byteBuffer, n);
    }

    @Override
    public boolean encrypt(ByteBuffer byteBuffer, int n) {
        byteBuffer.position(byteBuffer.position() + n);
        return true;
    }

    @Override
    protected void onDisconnection() {
        if (this._loginClient != null && this._loginClient != this) {
            this._loginClient.onDisconnection();
        }
        super.onDisconnection();
    }

    @Override
    public void closeNow(boolean bl) {
        if (this._loginClient != null && this._loginClient != this) {
            this._loginClient.closeNow(bl);
        }
        super.closeNow(bl);
    }

    @Override
    protected void onForcedDisconnection() {
        if (this._loginClient != null && this._loginClient != this) {
            this._loginClient.onForcedDisconnection();
        }
        super.onForcedDisconnection();
    }

    @Override
    public ReceivablePacket<T> handlePacket(ByteBuffer byteBuffer, T t) {
        throw new RuntimeException();
    }

    public String toString() {
        return "BaseLoginClient{_loginClient=" + this._loginClient + "}";
    }
}
