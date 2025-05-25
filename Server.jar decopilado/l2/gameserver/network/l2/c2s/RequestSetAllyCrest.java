/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.cache.CrestCache;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestSetAllyCrest
extends L2GameClientPacket {
    private int rY;
    private byte[] q;

    @Override
    protected void readImpl() {
        this.rY = this.readD();
        if (this.rY == 192 && this.rY == this._buf.remaining()) {
            this.q = new byte[this.rY];
            this.readB(this.q);
        }
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Alliance alliance = player.getAlliance();
        if (alliance != null && player.isAllyLeader()) {
            int n = 0;
            if (this.q != null && CrestCache.isValidCrestData(this.q)) {
                n = CrestCache.getInstance().saveAllyCrest(alliance.getAllyId(), this.q);
            } else if (alliance.hasAllyCrest()) {
                CrestCache.getInstance().removeAllyCrest(alliance.getAllyId());
            }
            alliance.setAllyCrestId(n);
            alliance.broadcastAllyStatus();
        }
    }
}
