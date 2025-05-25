/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.crypt;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import l2.authserver.Config;
import l2.authserver.crypt.LoginCrypt;
import l2.authserver.crypt.NewCrypt;
import l2.authserver.crypt.ScrambledKeyPair;
import l2.authserver.network.l2.s2c.StaticInit;
import l2.commons.util.Rnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CryptManager {
    private static final Logger Q = LoggerFactory.getLogger(CryptManager.class);
    private static final CryptManager a = new CryptManager();
    private final Map<Integer, LoginCryptProvider> t = new HashMap<Integer, LoginCryptProvider>();
    private LoginCryptProvider[] a;
    private AtomicInteger f = new AtomicInteger(0);

    public static CryptManager getInstance() {
        return a;
    }

    private CryptManager() {
    }

    private ScrambledKeyPair[] a(int n) throws Exception {
        ScrambledKeyPair[] scrambledKeyPairArray = new ScrambledKeyPair[n];
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        RSAKeyGenParameterSpec rSAKeyGenParameterSpec = new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4);
        keyPairGenerator.initialize(rSAKeyGenParameterSpec);
        for (int i = 0; i < scrambledKeyPairArray.length; ++i) {
            scrambledKeyPairArray[i] = new ScrambledKeyPair(keyPairGenerator.generateKeyPair());
        }
        Q.info("Generated " + scrambledKeyPairArray.length + " RSA key(s).");
        return scrambledKeyPairArray;
    }

    private byte[][] a(int n) {
        byte[][] byArrayArray = new byte[n][];
        for (int i = 0; i < byArrayArray.length; ++i) {
            byte[] byArray = new byte[16];
            for (int j = 0; j < byArray.length; ++j) {
                byArray[j] = (byte)(Rnd.nextInt() & 0xFF);
            }
            byArrayArray[i] = byArray;
        }
        return byArrayArray;
    }

    public void init() throws Exception {
        this.t.clear();
        int n = 0;
        int n2 = Integer.MAX_VALUE / (Config.LOGIN_BLOWFISH_KEYS * Config.LOGIN_RSA_KEYPAIRS) - 1;
        for (ScrambledKeyPair scrambledKeyPair : this.a(Config.LOGIN_RSA_KEYPAIRS)) {
            for (byte[] byArray : this.a(Config.LOGIN_BLOWFISH_KEYS)) {
                this.t.put(n += n2, new LoginCryptProvider(n, scrambledKeyPair, byArray));
            }
        }
        this.a = this.t.values().toArray(new LoginCryptProvider[this.t.size()]);
    }

    public LoginCryptProvider getLogicCryptProvider() {
        return this.a[this.f.incrementAndGet() % this.a.length];
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class LoginCryptProvider {
        private final int dn;
        private final ScrambledKeyPair a;
        private final byte[] b;
        private final StaticInit a;

        private LoginCryptProvider(int n, ScrambledKeyPair scrambledKeyPair, byte[] byArray) {
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
}
