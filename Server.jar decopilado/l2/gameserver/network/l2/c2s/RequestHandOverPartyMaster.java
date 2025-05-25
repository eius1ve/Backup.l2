/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class RequestHandOverPartyMaster
extends L2GameClientPacket {
    private String _name;

    @Override
    protected void readImpl() {
        this._name = this.readS(16);
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Party party = player.getParty();
        if (party == null || !player.getParty().isLeader(player)) {
            player.sendActionFailed();
            return;
        }
        Player player2 = party.getPlayerByName(this._name);
        if (player2 == player) {
            player.sendPacket((IStaticPacket)SystemMsg.SLOW_DOWN_YOU_ARE_ALREADY_THE_PARTY_LEADER);
            return;
        }
        if (player2 == null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_ONLY_TRANSFER_PARTY_LEADERSHIP_TO_ANOTHER_MEMBER_OF_THE_PARTY);
            return;
        }
        player.getParty().changePartyLeader(player2);
    }
}
