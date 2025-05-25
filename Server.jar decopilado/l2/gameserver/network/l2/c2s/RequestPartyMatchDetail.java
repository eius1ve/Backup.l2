/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.instancemanager.MatchingRoomManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestPartyMatchDetail
extends L2GameClientPacket {
    private int qC;
    private int rD;
    private int _level;

    @Override
    protected void readImpl() {
        this.qC = this.readD();
        this.rD = this.readD();
        this._level = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.getMatchingRoom() != null) {
            return;
        }
        if (this.qC > 0) {
            MatchingRoom matchingRoom = MatchingRoomManager.getInstance().getMatchingRoom(MatchingRoom.PARTY_MATCHING, this.qC);
            if (matchingRoom == null) {
                return;
            }
            matchingRoom.addMember(player);
        } else {
            for (MatchingRoom matchingRoom : MatchingRoomManager.getInstance().getMatchingRooms(MatchingRoom.PARTY_MATCHING, this.rD, this._level == 1, player)) {
                if (matchingRoom.addMember(player)) break;
            }
        }
    }
}
