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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginServerFail
extends ReceivablePacket {
    private static final Logger cC = LoggerFactory.getLogger(LoginServerFail.class);
    private static final String[] aM = new String[]{"none", "IP banned", "IP reserved", "wrong hexid", "ID reserved", "no free ID", "not authed", "already logged in"};
    private int ef;

    public String getReason() {
        return aM[this.ef];
    }

    @Override
    protected void readImpl() {
        this.ef = this.readC();
    }

    @Override
    protected void runImpl() {
        cC.warn("Authserver registration failed! Reason: " + this.getReason());
        AuthServerCommunication.getInstance().restart();
    }
}
