/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestShortCutDel
extends L2GameClientPacket {
    private int kk;
    private int kl;

    @Override
    protected void readImpl() {
        int n = this.readD();
        this.kk = n % 12;
        this.kl = n / 12;
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        player.deleteShortCut(this.kk, this.kl);
    }
}
