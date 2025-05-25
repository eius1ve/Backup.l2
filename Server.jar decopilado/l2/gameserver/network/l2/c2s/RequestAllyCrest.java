/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.cache.CrestCache;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.AllianceCrest;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RequestAllyCrest
extends L2GameClientPacket {
    private int oU;

    @Override
    protected void readImpl() {
        this.oU = this.readD();
    }

    @Override
    protected void runImpl() {
        if (this.oU == 0) {
            return;
        }
        byte[] byArray = CrestCache.getInstance().getAllyCrest(this.oU);
        if (byArray != null) {
            AllianceCrest allianceCrest = new AllianceCrest(this.oU, byArray);
            this.sendPacket((L2GameServerPacket)allianceCrest);
        }
    }
}
