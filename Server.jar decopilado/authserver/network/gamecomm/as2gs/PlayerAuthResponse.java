/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.gamecomm.as2gs;

import l2.authserver.accounts.Account;
import l2.authserver.accounts.SessionManager;
import l2.authserver.network.gamecomm.SendablePacket;
import l2.authserver.network.l2.SessionKey;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PlayerAuthResponse
extends SendablePacket {
    private static final byte[] d = new byte[0];
    private String av;
    private boolean al;
    private int dC;
    private int dD;
    private int dE;
    private int dF;
    private double d;
    private int dG;
    private int dH;
    private byte[] hwid = d;

    public PlayerAuthResponse(SessionManager.Session session, boolean bl, int n) {
        Account account = session.getAccount();
        this.av = account.getLogin();
        this.al = bl;
        if (bl) {
            SessionKey sessionKey = session.getSessionKey();
            this.dC = sessionKey.playOkID1;
            this.dD = sessionKey.playOkID2;
            this.dE = sessionKey.loginOkID1;
            this.dF = sessionKey.loginOkID2;
            this.dH = n;
            if (sessionKey.hwid != null) {
                this.hwid = sessionKey.hwid;
            }
        }
    }

    public PlayerAuthResponse(String string) {
        this.av = string;
        this.al = false;
    }

    @Override
    protected void writeImpl() {
        this.writeC(2);
        this.writeS(this.av);
        this.writeC(this.al ? 1 : 0);
        if (this.al) {
            this.writeD(this.dC);
            this.writeD(this.dD);
            this.writeD(this.dE);
            this.writeD(this.dF);
            this.writeD(this.dH);
            this.writeC(this.hwid.length);
            this.writeB(this.hwid);
        }
    }
}
