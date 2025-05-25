/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExAcquirableSkillListByClass
extends L2GameServerPacket {
    private AcquireType a;
    private final List<Skill> bZ;

    public ExAcquirableSkillListByClass(AcquireType acquireType, int n) {
        this.bZ = new ArrayList<Skill>(n);
        this.a = acquireType;
    }

    public void addSkill(int n, int n2, int n3, int n4, int n5, int n6) {
        this.bZ.add(new Skill(n, n2, n3, n4, n5, n6));
    }

    public void addSkill(int n, int n2, int n3, int n4, int n5) {
        this.bZ.add(new Skill(n, n2, n3, n4, n5, 0));
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(250);
        this.writeH(this.a.getId());
        this.writeH(this.bZ.size());
        for (Skill skill : this.bZ) {
            this.writeD(skill.id);
            this.writeH(skill.nextLevel);
            this.writeH(skill.nextLevel);
            this.writeC(skill.getLevel);
            this.writeQ(skill.cost);
            this.writeC(skill.requirements);
            if (this.a != AcquireType.SUB_UNIT) continue;
            this.writeH(skill.subUnit);
        }
    }

    class Skill {
        public int id;
        public int nextLevel;
        public int getLevel;
        public int cost;
        public int requirements;
        public int subUnit;

        Skill(int n, int n2, int n3, int n4, int n5, int n6) {
            this.id = n;
            this.nextLevel = n2;
            this.getLevel = n3;
            this.cost = n4;
            this.requirements = n5;
            this.subUnit = n6;
        }
    }
}
