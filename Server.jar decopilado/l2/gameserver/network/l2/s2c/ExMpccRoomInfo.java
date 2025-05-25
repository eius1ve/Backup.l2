/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExMpccRoomInfo
extends L2GameServerPacket {
    private int vL;
    private int qT;
    private int b;
    private int c;
    private int oO;
    private int qR;
    private String dI;

    public ExMpccRoomInfo(MatchingRoom matchingRoom) {
        this.vL = matchingRoom.getId();
        this.qR = matchingRoom.getLocationId();
        this.dI = matchingRoom.getTopic();
        this.b = matchingRoom.getMinLevel();
        this.c = matchingRoom.getMaxLevel();
        this.qT = matchingRoom.getMaxMembersSize();
        this.oO = matchingRoom.getLootType();
    }

    @Override
    public void writeImpl() {
        this.writeEx(156);
        this.writeD(this.vL);
        this.writeD(this.qT);
        this.writeD(this.b);
        this.writeD(this.c);
        this.writeD(this.oO);
        this.writeD(this.qR);
        this.writeS(this.dI);
    }
}
