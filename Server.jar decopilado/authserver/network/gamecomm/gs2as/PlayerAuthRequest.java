/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.gamecomm.gs2as;

import l2.authserver.accounts.SessionManager;
import l2.authserver.network.gamecomm.ReceivablePacket;
import l2.authserver.network.gamecomm.as2gs.PlayerAuthResponse;
import l2.authserver.network.l2.SessionKey;

public class PlayerAuthRequest
extends ReceivablePacket {
    private String account;
    private SessionKey a;

    @Override
    protected void readImpl() {
        this.account = this.readS();
        this.a = SessionKey.of(this.readD(), this.readD(), this.readD(), this.readD());
    }

    @Override
    protected void runImpl() {
        if (this.a == null) {
            return;
        }
        SessionManager.Session session = SessionManager.getInstance().closeSession(this.a);
        if (session == null || !session.getAccount().getLogin().equals(this.account)) {
            this.sendPacket(new PlayerAuthResponse(this.account));
            return;
        }
        this.sendPacket(new PlayerAuthResponse(session, session.getSessionKey().equals(this.a), session.getAccount().getLastServer()));
    }
}
