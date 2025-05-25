/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.gamecomm.as2gs;

import java.util.List;
import l2.authserver.network.gamecomm.SendablePacket;

public class NotifyWhitelistedIp
extends SendablePacket {
    private final List<String> P;
    private final List<String> Q;
    private final int dB;

    public NotifyWhitelistedIp(int n, List<String> list, List<String> list2) {
        this.P = list;
        this.Q = list2;
        this.dB = n;
    }

    @Override
    protected void writeImpl() {
        this.writeC(167);
        this.writeD(this.P.size());
        for (String string : this.P) {
            this.writeS(string);
        }
        this.writeD(this.Q.size());
        for (String string : this.Q) {
            this.writeS(string);
        }
        this.writeD(this.dB);
    }
}
