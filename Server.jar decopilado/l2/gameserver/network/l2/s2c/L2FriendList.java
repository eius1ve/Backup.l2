/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.Friend;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class L2FriendList
extends L2GameServerPacket {
    private List<FriendInfo> bV = Collections.emptyList();

    public L2FriendList(Player player) {
        Map<Integer, Friend> map = player.getFriendList().getList();
        this.bV = new ArrayList<FriendInfo>(map.size());
        for (Map.Entry<Integer, Friend> entry : map.entrySet()) {
            FriendInfo friendInfo = new FriendInfo();
            friendInfo.fW = entry.getKey();
            friendInfo._name = entry.getValue().getName();
            friendInfo.aq = entry.getValue().isOnline();
            friendInfo.classId = entry.getValue().getClassId();
            friendInfo.level = entry.getValue().getLevel();
            this.bV.add(friendInfo);
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(117);
        this.writeD(this.bV.size());
        for (FriendInfo friendInfo : this.bV) {
            this.writeD(friendInfo.fW);
            this.writeS(friendInfo._name);
            this.writeD(friendInfo.aq ? 1 : 0);
            this.writeD(friendInfo.aq ? friendInfo.fW : 0);
            this.writeD(friendInfo.level);
            this.writeD(friendInfo.classId);
            this.writeH(0);
        }
    }

    private static class FriendInfo {
        private int fW;
        private String _name;
        private boolean aq;
        private int level;
        private int classId;

        private FriendInfo() {
        }
    }
}
