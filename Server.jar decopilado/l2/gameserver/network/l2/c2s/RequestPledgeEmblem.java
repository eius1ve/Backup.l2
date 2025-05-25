/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.cache.CrestCache;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.ExPledgeEmblem;

public class RequestPledgeEmblem
extends L2GameClientPacket {
    private int oU;
    private int rK;

    @Override
    protected void readImpl() {
        this.oU = this.readD();
        this.rK = this.readD();
    }

    @Override
    protected void runImpl() {
        byte[] byArray;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.oU == 0 || this.rK == 0) {
            return;
        }
        int n = ((GameClient)this.getClient()).getServerId();
        int n2 = CrestCache.getInstance().getPledgeCrestLargeId(this.rK);
        if (n2 == this.oU && (byArray = CrestCache.getInstance().getPledgeCrestLarge(n2)) != null && byArray.length > 0) {
            this.sendPacket(ExPledgeEmblem.packets(n, this.rK, n2, byArray));
        }
    }
}
