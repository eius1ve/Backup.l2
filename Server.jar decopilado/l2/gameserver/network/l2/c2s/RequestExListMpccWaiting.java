/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExListMpccWaiting;

public class RequestExListMpccWaiting
extends L2GameClientPacket {
    private int fq;
    private int qR;
    private boolean dW;

    @Override
    protected void readImpl() throws Exception {
        this.fq = this.readD();
        this.qR = this.readD();
        this.dW = this.readD() == 1;
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        player.sendPacket((IStaticPacket)new ExListMpccWaiting(player, this.fq, this.qR, this.dW));
    }
}
