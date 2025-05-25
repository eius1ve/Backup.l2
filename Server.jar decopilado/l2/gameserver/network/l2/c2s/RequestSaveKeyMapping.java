/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExUISetting;

public class RequestSaveKeyMapping
extends L2GameClientPacket {
    private byte[] q;

    @Override
    protected void readImpl() {
        int n = this.readD();
        if (n > this._buf.remaining() || n > Short.MAX_VALUE || n < 0) {
            this.q = null;
            return;
        }
        this.q = new byte[n];
        this.readB(this.q);
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.q == null) {
            return;
        }
        player.setKeyBindings(this.q);
        player.sendPacket((IStaticPacket)new ExUISetting(player));
    }
}
