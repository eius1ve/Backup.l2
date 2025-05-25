/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.instancemanager.MatchingRoomManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExListMpccWaiting
extends L2GameServerPacket {
    private static final int vA = 10;
    private int vB;
    private List<MatchingRoom> bV;

    public ExListMpccWaiting(Player player, int n, int n2, boolean bl) {
        int n3 = (n - 1) * 10;
        int n4 = n * 10;
        int n5 = 0;
        List<MatchingRoom> list = MatchingRoomManager.getInstance().getMatchingRooms(MatchingRoom.CC_MATCHING, n2, bl, player);
        this.vB = list.size();
        this.bV = new ArrayList<MatchingRoom>(10);
        for (MatchingRoom matchingRoom : list) {
            if (n5 < n3 || n5 >= n4) continue;
            this.bV.add(matchingRoom);
            ++n5;
        }
    }

    @Override
    public void writeImpl() {
        this.writeEx(157);
        this.writeD(this.vB);
        this.writeD(this.bV.size());
        for (MatchingRoom matchingRoom : this.bV) {
            this.writeD(matchingRoom.getId());
            this.writeS(matchingRoom.getTopic());
            this.writeD(matchingRoom.getPlayers().size());
            this.writeD(matchingRoom.getMinLevel());
            this.writeD(matchingRoom.getMaxLevel());
            this.writeD(1);
            this.writeD(matchingRoom.getMaxMembersSize());
            Player player = matchingRoom.getLeader();
            this.writeS(player == null ? "" : player.getName());
        }
    }
}
