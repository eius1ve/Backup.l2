/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.jacksum.JacksumAPI
 *  net.jacksum.algorithms.AbstractChecksum
 *  net.jacksum.formats.Encoding
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.crypt;

import net.jacksum.JacksumAPI;
import net.jacksum.algorithms.AbstractChecksum;
import net.jacksum.formats.Encoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordHash {
    private static final Logger R = LoggerFactory.getLogger(PasswordHash.class);
    private final String name;

    public PasswordHash(String string) {
        this.name = string;
    }

    public boolean compare(String string, String string2) {
        try {
            return this.encrypt(string).equalsIgnoreCase(string2);
        }
        catch (Exception exception) {
            R.error(this.name + ": encryption error!", (Throwable)exception);
            return false;
        }
    }

    public String encrypt(String string) throws Exception {
        AbstractChecksum abstractChecksum = JacksumAPI.getChecksumInstance((String)this.name);
        abstractChecksum.setEncoding(Encoding.BASE64);
        abstractChecksum.update(string.getBytes());
        return abstractChecksum.format("#CHECKSUM");
    }
}
