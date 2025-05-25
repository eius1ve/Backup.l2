/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.network.gamecomm.gs2as;

import java.util.ArrayList;
import java.util.List;
import l2.authserver.accounts.Account;
import l2.authserver.network.gamecomm.ReceivablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeAccessLevelMulti
extends ReceivablePacket {
    public static final Logger _log = LoggerFactory.getLogger(ChangeAccessLevelMulti.class);
    private List<String> R;
    private int level;
    private int dg;

    @Override
    protected void readImpl() {
        int n = Math.min(this.readD(), 127);
        this.R = new ArrayList<String>(n);
        for (int i = 0; i < n; ++i) {
            this.R.add(this.readS());
        }
        this.level = this.readD();
        this.dg = this.readD();
    }

    @Override
    protected void runImpl() {
        if (this.R == null || this.R.isEmpty()) {
            return;
        }
        for (String string : this.R) {
            Account account = new Account(string);
            account.restore();
            account.setAccessLevel(this.level);
            account.setBanExpire(this.dg);
            account.update();
        }
    }
}
