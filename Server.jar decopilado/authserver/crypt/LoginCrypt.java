/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.crypt;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.interfaces.RSAPrivateKey;
import l2.authserver.crypt.NewCrypt;

/*
 * Duplicate member names - consider using --renamedupmembers true
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class LoginCrypt {
    private final int do;
    private final NewCrypt a;
    private final RSAPrivateKey a;

    public LoginCrypt(int n, NewCrypt newCrypt, RSAPrivateKey rSAPrivateKey) {
        this.do = n;
        this.a = newCrypt;
        this.a = rSAPrivateKey;
    }

    public int getCookieId() {
        return this.do;
    }

    public RSAPrivateKey getRSAPrivateKey() {
        return this.a;
    }

    public boolean decrypt(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        this.a.decrypt(byteBuffer, n, n2);
        return NewCrypt.verifyChecksum(byteBuffer, n, n2);
    }

    public int encrypt(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        n2 += 4;
        n2 += 8 - n2 % 8;
        NewCrypt.appendChecksum(byteBuffer, n, n2);
        this.a.crypt(byteBuffer, n, n2);
        return n2;
    }
}
