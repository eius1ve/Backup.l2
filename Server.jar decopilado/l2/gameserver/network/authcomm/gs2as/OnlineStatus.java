/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.authcomm.gs2as;

import l2.gameserver.network.authcomm.SendablePacket;

public class OnlineStatus
extends SendablePacket {
    private boolean aq;

    public OnlineStatus(boolean bl) {
        this.aq = bl;
    }

    @Override
    protected void writeImpl() {
        this.writeC(1);
        this.writeC(this.aq ? 1 : 0);
    }
}
