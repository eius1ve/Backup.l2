/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import l2.gameserver.model.Player;
import l2.gameserver.model.SubClass;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExSubjobInfo
extends L2GameServerPacket {
    private final int xG;
    private final int xH;
    private final int xI;
    private List<SubInfo> cF = new ArrayList<SubInfo>(4);

    public ExSubjobInfo(Player player) {
        this.xG = player.getClassId().getId();
        this.xI = player.getRace().ordinal();
        SubClass subClass = player.getActiveClass();
        this.xH = subClass.isBase() ? 0 : 0;
        Map<Integer, SubClass> map = player.getSubClasses();
        int n = 0;
        for (SubClass subClass2 : map.values()) {
            SubInfo subInfo = new SubInfo();
            subInfo.index = n;
            subInfo.classId = subClass2.getClassId();
            subInfo.level = subClass2.getLevel();
            subInfo.type = subClass2.isBase() ? 0 : 0;
            this.cF.add(subInfo);
        }
    }

    @Override
    protected void writeImpl() {
        this.writeEx(234);
        this.writeC(this.xH);
        this.writeD(this.xG);
        this.writeD(this.xI);
        this.writeD(this.cF.size());
        for (SubInfo subInfo : this.cF) {
            this.writeD(subInfo.index);
            this.writeD(subInfo.classId);
            this.writeD(subInfo.level);
            this.writeC(subInfo.type);
        }
    }

    private static class SubInfo {
        int index;
        int classId;
        int level;
        int type;

        private SubInfo() {
        }
    }
}
