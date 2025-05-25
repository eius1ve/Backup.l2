/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.cache.CrestCache;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PledgeCrest;

public class RequestPledgeCrest
extends L2GameClientPacket {
    private int oU;

    @Override
    protected void readImpl() {
        this.oU = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.oU == 0) {
            return;
        }
        byte[] byArray = CrestCache.getInstance().getPledgeCrest(this.oU);
        if (byArray != null) {
            PledgeCrest pledgeCrest = new PledgeCrest(this.oU, byArray);
            this.sendPacket((L2GameServerPacket)pledgeCrest);
        }
    }
}
