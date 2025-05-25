/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.network.gamecomm.gs2as;

import l2.authserver.accounts.Account;
import l2.authserver.network.gamecomm.ReceivablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeAccessLevel
extends ReceivablePacket {
    public static final Logger _log = LoggerFactory.getLogger(ChangeAccessLevel.class);
    private String account;
    private int level;
    private int dg;

    @Override
    protected void readImpl() {
        this.account = this.readS();
        this.level = this.readD();
        this.dg = this.readD();
    }

    @Override
    protected void runImpl() {
        Account account = new Account(this.account);
        account.restore();
        account.setAccessLevel(this.level);
        account.setBanExpire(this.dg);
        account.update();
    }
}
