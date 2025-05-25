/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.authserver.network.gamecomm.gs2as;

import l2.authserver.accounts.Account;
import l2.authserver.network.gamecomm.GameServer;
import l2.authserver.network.gamecomm.ReceivablePacket;
import org.apache.commons.lang3.StringUtils;

public class PlayerInGame
extends ReceivablePacket {
    private String account;
    private String aI = "";

    @Override
    protected void readImpl() {
        this.account = this.readS();
        if (this.getAvaliableBytes() > 0) {
            this.aI = this.readS();
        }
    }

    @Override
    protected void runImpl() {
        GameServer gameServer = this.getGameServer();
        if (gameServer.isAuthed()) {
            gameServer.addAccount(this.account);
        }
        if (!StringUtils.isBlank((CharSequence)this.aI)) {
            Account account = new Account(this.account);
            account.restore();
            account.setLastHWID(this.aI);
            account.update();
        }
    }
}
