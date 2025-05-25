/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PartyRoomInfo
extends L2GameServerPacket {
    private int _id;
    private int b;
    private int c;
    private int rE;
    private int rF;
    private int zO;
    private String _title;

    public PartyRoomInfo(MatchingRoom matchingRoom) {
        this._id = matchingRoom.getId();
        this.b = matchingRoom.getMinLevel();
        this.c = matchingRoom.getMaxLevel();
        this.rE = matchingRoom.getLootType();
        this.rF = matchingRoom.getMaxMembersSize();
        this.zO = matchingRoom.getLocationId();
        this._title = matchingRoom.getTopic();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(157);
        this.writeD(this._id);
        this.writeD(this.rF);
        this.writeD(this.b);
        this.writeD(this.c);
        this.writeD(this.rE);
        this.writeD(this.zO);
        this.writeS(this._title);
    }
}
