/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExDivideAdenaStart;

public class RequestDivideAdenaStart
extends L2GameClientPacket {
    @Override
    protected void readImpl() {
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.getParty() == null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_PROCEED_AS_YOU_ARE_NOT_IN_AN_ALLIANCE_OR_PARTY);
            return;
        }
        if (player.getParty().getPartyLeader() != player) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_PROCEED_AS_YOU_ARE_NOT_A_PARTY_LEADER);
            return;
        }
        player.sendPacket((IStaticPacket)SystemMsg.ADENA_DISTRIBUTION_HAS_STARTED);
        player.sendPacket((IStaticPacket)ExDivideAdenaStart.STATIC);
    }
}
