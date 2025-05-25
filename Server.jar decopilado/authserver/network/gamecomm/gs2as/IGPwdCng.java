/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.gamecomm.gs2as;

import l2.authserver.Config;
import l2.authserver.accounts.Account;
import l2.authserver.crypt.PasswordHash;
import l2.authserver.network.gamecomm.ReceivablePacket;
import l2.authserver.network.gamecomm.as2gs.NotifyPwdCngResult;

public class IGPwdCng
extends ReceivablePacket {
    private int dz;
    private String aF;
    private String aG;
    private String aH;

    @Override
    protected void readImpl() {
        this.dz = this.readD();
        this.aF = this.readS();
        this.aG = this.readS();
        this.aH = this.readS();
    }

    @Override
    protected void runImpl() {
        try {
            Account account = new Account(this.aF);
            account.restore();
            if (account.getPasswordHash() == null) {
                this.sendPacket(new NotifyPwdCngResult(this.dz, 4));
                return;
            }
            if (!this.aH.matches(Config.APASSWD_TEMPLATE)) {
                this.sendPacket(new NotifyPwdCngResult(this.dz, 3));
                return;
            }
            boolean bl = Config.DEFAULT_CRYPT.compare(this.aG, account.getPasswordHash());
            if (!bl) {
                for (PasswordHash passwordHash : Config.LEGACY_CRYPT) {
                    if (!passwordHash.compare(this.aG, account.getPasswordHash())) continue;
                    bl = true;
                    break;
                }
            }
            if (!bl) {
                this.sendPacket(new NotifyPwdCngResult(this.dz, 2));
                return;
            }
            account.setPasswordHash(Config.DEFAULT_CRYPT.encrypt(this.aH));
            account.update();
            this.sendPacket(new NotifyPwdCngResult(this.dz, 1));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
