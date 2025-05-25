/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.crypt;

import java.security.interfaces.RSAPrivateKey;
import l2.authserver.crypt.LoginCrypt;
import l2.authserver.crypt.NewCrypt;
import l2.authserver.crypt.ScrambledKeyPair;
import l2.authserver.network.l2.s2c.StaticInit;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public static class CryptManager.LoginCryptProvider {
    private final int dn;
    private final ScrambledKeyPair a;
    private final byte[] b;
    private final StaticInit a;

    private CryptManager.LoginCryptProvider(int n, ScrambledKeyPair scrambledKeyPair, byte[] byArray) {
        this.dn = n;
        this.a = scrambledKeyPair;
        this.b = byArray;
        this.a = new StaticInit(scrambledKeyPair.getScrambledModulus(), byArray, n);
    }

    public LoginCrypt newLoginCrypt() {
        return new LoginCrypt(this.dn, new NewCrypt(this.b), (RSAPrivateKey)this.a.getKeyPair().getPrivate());
    }

    public StaticInit getStaticInitPacket() {
        return this.a;
    }
}
