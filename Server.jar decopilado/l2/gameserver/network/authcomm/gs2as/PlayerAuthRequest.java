/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.authcomm.gs2as;

import l2.gameserver.network.authcomm.SendablePacket;
import l2.gameserver.network.l2.GameClient;

public class PlayerAuthRequest
extends SendablePacket {
    private String account;
    private int dC;
    private int dD;
    private int dE;
    private int dF;

    public PlayerAuthRequest(GameClient gameClient) {
        this.account = gameClient.getLogin();
        this.dC = gameClient.getSessionKey().playOkID1;
        this.dD = gameClient.getSessionKey().playOkID2;
        this.dE = gameClient.getSessionKey().loginOkID1;
        this.dF = gameClient.getSessionKey().loginOkID2;
    }

    @Override
    protected void writeImpl() {
        this.writeC(2);
        this.writeS(this.account);
        this.writeD(this.dC);
        this.writeD(this.dD);
        this.writeD(this.dE);
        this.writeD(this.dF);
    }
}
