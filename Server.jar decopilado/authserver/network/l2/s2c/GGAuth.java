/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.network.l2.s2c;

import l2.authserver.network.l2.s2c.L2LoginServerPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GGAuth
extends L2LoginServerPacket {
    static Logger _log = LoggerFactory.getLogger(GGAuth.class);
    public static int SKIP_GG_AUTH_REQUEST = 11;
    private int dY;

    public GGAuth(int n) {
        this.dY = n;
    }

    @Override
    protected void writeImpl() {
        this.writeC(11);
        this.writeD(this.dY);
        this.writeD(1732584193);
        this.writeD(-271733879);
        this.writeD(-1732584194);
        this.writeD(271733878);
    }
}
