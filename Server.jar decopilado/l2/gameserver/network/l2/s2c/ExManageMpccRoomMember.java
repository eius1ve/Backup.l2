/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.instancemanager.MatchingRoomManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExManageMpccRoomMember
extends L2GameServerPacket {
    public static int ADD_MEMBER = 0;
    public static int UPDATE_MEMBER = 1;
    public static int REMOVE_MEMBER = 2;
    private int _type;
    private MpccRoomMemberInfo a;

    public ExManageMpccRoomMember(int n, MatchingRoom matchingRoom, Player player) {
        this._type = n;
        this.a = new MpccRoomMemberInfo(player, matchingRoom.getMemberType(player));
    }

    @Override
    protected void writeImpl() {
        this.writeEx(159);
        this.writeD(this._type);
        this.writeD(this.a.objectId);
        this.writeS(this.a.name);
        this.writeD(this.a.level);
        this.writeD(this.a.classId);
        this.writeD(this.a.location);
        this.writeD(this.a.memberType);
    }

    static class MpccRoomMemberInfo {
        public final int objectId;
        public final int classId;
        public final int level;
        public final int location;
        public final int memberType;
        public final String name;

        public MpccRoomMemberInfo(Player player, int n) {
            this.objectId = player.getObjectId();
            this.name = player.getName();
            this.classId = player.getClassId().ordinal();
            this.level = player.getLevel();
            this.location = MatchingRoomManager.getInstance().getLocation(player);
            this.memberType = n;
        }
    }
}
