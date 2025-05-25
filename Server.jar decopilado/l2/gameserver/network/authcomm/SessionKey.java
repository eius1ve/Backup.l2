/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.authcomm;

import l2.authserver.Config;

public class SessionKey {
    public final int playOkID1;
    public final int playOkID2;
    public final int loginOkID1;
    public final int loginOkID2;
    public byte[] hwid;
    private final int pt;

    public SessionKey(int n, int n2, int n3, int n4) {
        this.playOkID1 = n3;
        this.playOkID2 = n4;
        this.loginOkID1 = n;
        this.loginOkID2 = n2;
        int n5 = n3;
        n5 *= 17;
        n5 += n4;
        n5 *= 37;
        n5 += n;
        n5 *= 51;
        this.pt = n5 += n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() == this.getClass()) {
            SessionKey sessionKey = (SessionKey)object;
            if (Config.REQUIRE_EULA) {
                return this.playOkID1 == sessionKey.playOkID1 && this.loginOkID1 == sessionKey.loginOkID1 && this.playOkID2 == sessionKey.playOkID2 && this.loginOkID2 == sessionKey.loginOkID2;
            }
            return this.playOkID1 == sessionKey.playOkID1 && this.playOkID2 == sessionKey.playOkID2;
        }
        return false;
    }

    public int hashCode() {
        return this.pt;
    }

    public String toString() {
        return "[playOkID1: " + this.playOkID1 + " playOkID2: " + this.playOkID2 + " loginOkID1: " + this.loginOkID1 + " loginOkID2: " + this.loginOkID2 + "]";
    }
}
