/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestOustFromPartyRoom
extends L2GameClientPacket {
    private int fW;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        MatchingRoom matchingRoom = player.getMatchingRoom();
        if (matchingRoom == null || matchingRoom.getType() != MatchingRoom.PARTY_MATCHING) {
            return;
        }
        if (matchingRoom.getLeader() != player) {
            return;
        }
        Player player2 = GameObjectsStorage.getPlayer(this.fW);
        if (player2 == null) {
            return;
        }
        if (player2 == matchingRoom.getLeader()) {
            return;
        }
        matchingRoom.removeMember(player2, true);
    }
}
