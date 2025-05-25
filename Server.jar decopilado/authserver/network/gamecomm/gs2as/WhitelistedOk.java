/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.gamecomm.gs2as;

import l2.authserver.ClientManager;
import l2.authserver.Config;
import l2.authserver.network.gamecomm.ReceivablePacket;
import l2.authserver.network.l2.L2LoginClient;
import l2.authserver.network.l2.s2c.LoginOk;
import l2.authserver.network.l2.s2c.ServerList;

public class WhitelistedOk
extends ReceivablePacket {
    private int dB;

    @Override
    protected void readImpl() {
        this.dB = this.readD();
    }

    @Override
    protected void runImpl() {
        L2LoginClient l2LoginClient = ClientManager.getInstance().removeClientByCookieId(this.dB);
        if (l2LoginClient == null || !l2LoginClient.isConnected()) {
            return;
        }
        if (!Config.REQUIRE_EULA) {
            l2LoginClient.sendPacket(new ServerList(l2LoginClient.getAccount()));
            return;
        }
        l2LoginClient.sendPacket(new LoginOk(l2LoginClient.getSessionKey()));
    }
}
