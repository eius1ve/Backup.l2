/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExDivideAdenaDone;

public class RequestDivideAdena
extends L2GameClientPacket {
    private long aT;

    @Override
    protected void readImpl() {
        this.readD();
        this.aT = this.readQ();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        long l = player.getAdena();
        if (this.aT > l) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_PROCEED_AS_THERE_IS_INSUFFICIENT_ADENA);
            return;
        }
        int n = player.getParty().getMemberCount();
        long l2 = (long)Math.floor(this.aT / (long)n);
        player.reduceAdena((long)n * l2, false);
        for (Player player2 : player.getParty().getPartyMembers()) {
            player2.addAdena(l2, player2.getObjectId() != player.getObjectId());
        }
        player.sendPacket((IStaticPacket)new ExDivideAdenaDone(n, this.aT, l2, player.getName()));
    }
}
