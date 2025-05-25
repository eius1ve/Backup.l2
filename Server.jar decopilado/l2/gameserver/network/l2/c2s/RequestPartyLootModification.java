/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestPartyLootModification
extends L2GameClientPacket {
    private byte d;

    @Override
    protected void readImpl() {
        this.d = (byte)this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.d < 0 || this.d > 4) {
            return;
        }
        Party party = player.getParty();
        if (party == null || this.d == party.getLootDistribution() || party.getPartyLeader() != player) {
            return;
        }
        party.requestLootChange(this.d);
    }
}
