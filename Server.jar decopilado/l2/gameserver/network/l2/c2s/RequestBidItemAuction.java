/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public final class RequestBidItemAuction
extends L2GameClientPacket {
    private int ql;
    private long cc;

    @Override
    protected final void readImpl() {
        this.ql = this.readD();
        this.cc = this.readQ();
    }

    @Override
    protected final void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
    }
}
