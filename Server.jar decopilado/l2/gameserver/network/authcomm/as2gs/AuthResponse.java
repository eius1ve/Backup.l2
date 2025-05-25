/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.authcomm.as2gs;

import l2.gameserver.network.authcomm.AuthServerCommunication;
import l2.gameserver.network.authcomm.ReceivablePacket;
import l2.gameserver.network.authcomm.gs2as.OnlineStatus;
import l2.gameserver.network.authcomm.gs2as.PlayerInGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthResponse
extends ReceivablePacket {
    private static final Logger cB = LoggerFactory.getLogger(AuthResponse.class);
    private int dW;
    private String dP;

    @Override
    protected void readImpl() {
        this.dW = this.readC();
        this.dP = this.readS();
    }

    @Override
    protected void runImpl() {
        String[] stringArray;
        cB.info("Registered on authserver as " + this.dW + " [" + this.dP + "]");
        this.sendPacket(new OnlineStatus(true));
        for (String string : stringArray = AuthServerCommunication.getInstance().getAccounts()) {
            this.sendPacket(new PlayerInGame(string));
        }
    }
}
