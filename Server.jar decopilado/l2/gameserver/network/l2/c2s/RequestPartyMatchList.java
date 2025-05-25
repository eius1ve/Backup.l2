/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.model.matching.PartyMatchingRoom;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestPartyMatchList
extends L2GameClientPacket {
    private int rE;
    private int rF;
    private int b;
    private int c;
    private int qC;
    private String es;

    @Override
    protected void readImpl() {
        this.qC = this.readD();
        this.rF = this.readD();
        this.b = this.readD();
        this.c = this.readD();
        this.rE = this.readD();
        this.es = this.readS(64);
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        MatchingRoom matchingRoom = player.getMatchingRoom();
        if (matchingRoom == null) {
            new PartyMatchingRoom(player, this.b, this.c, this.rF, this.rE, this.es);
        } else if (matchingRoom.getId() == this.qC && matchingRoom.getType() == MatchingRoom.PARTY_MATCHING && matchingRoom.getLeader() == player) {
            matchingRoom.setMinLevel(this.b);
            matchingRoom.setMaxLevel(this.c);
            matchingRoom.setMaxMemberSize(this.rF);
            matchingRoom.setTopic(this.es);
            matchingRoom.setLootType(this.rE);
            matchingRoom.broadCast(matchingRoom.infoRoomPacket());
        }
    }
}
