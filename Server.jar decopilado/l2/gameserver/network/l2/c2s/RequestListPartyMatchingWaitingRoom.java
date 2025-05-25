/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExListPartyMatchingWaitingRoom;

public class RequestListPartyMatchingWaitingRoom
extends L2GameClientPacket {
    private int b;
    private int c;
    private int kl;
    private int[] aS;

    @Override
    protected void readImpl() {
        this.kl = this.readD();
        this.b = this.readD();
        this.c = this.readD();
        int n = this.readD();
        if (n > 127 || n < 0) {
            n = 0;
        }
        this.aS = new int[n];
        for (int i = 0; i < n; ++i) {
            this.aS[i] = this.readD();
        }
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        player.sendPacket((IStaticPacket)new ExListPartyMatchingWaitingRoom(player, this.b, this.c, this.kl, this.aS));
    }
}
