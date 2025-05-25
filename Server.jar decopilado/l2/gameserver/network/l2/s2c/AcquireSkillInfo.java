/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AcquireSkillInfo
extends L2GameServerPacket {
    private SkillLearn a;
    private AcquireType a;
    private List<Require> bS = Collections.emptyList();

    public AcquireSkillInfo(AcquireType acquireType, SkillLearn skillLearn) {
        this(acquireType, skillLearn, skillLearn.getItemId(), (int)skillLearn.getItemCount());
    }

    public AcquireSkillInfo(AcquireType acquireType, SkillLearn skillLearn, int n, int n2) {
        this.a = acquireType;
        this.a = skillLearn;
        if (this.a.getItemId() != 0) {
            this.bS = new ArrayList<Require>(1);
            this.bS.add(new Require(99, this.a.getItemId(), this.a.getItemCount(), 50));
        }
    }

    @Override
    public void writeImpl() {
        this.writeC(145);
        this.writeD(this.a.getId());
        this.writeD(this.a.getLevel());
        this.writeQ(this.a.getCost());
        this.writeD(this.a.getId());
        this.writeD(this.bS.size());
        for (Require require : this.bS) {
            this.writeD(require.type);
            this.writeD(require.itemId);
            this.writeQ(require.count);
            this.writeD(require.unk);
        }
    }

    private static class Require {
        public int itemId;
        public long count;
        public int type;
        public int unk;

        public Require(int n, int n2, long l, int n3) {
            this.itemId = n2;
            this.type = n;
            this.count = l;
            this.unk = n3;
        }
    }
}
