/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.instancemanager.MatchingRoomManager;
import l2.gameserver.model.Player;

static class ExMpccRoomMember.MpccRoomMemberInfo {
    public final int objectId;
    public final int classId;
    public final int level;
    public final int location;
    public final int memberType;
    public final String name;

    public ExMpccRoomMember.MpccRoomMemberInfo(Player player, int n) {
        this.objectId = player.getObjectId();
        this.name = player.getName();
        this.classId = player.getClassId().ordinal();
        this.level = player.getLevel();
        this.location = MatchingRoomManager.getInstance().getLocation(player);
        this.memberType = n;
    }
}
