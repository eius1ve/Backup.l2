/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import l2.gameserver.model.Skill;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgeSkillList
extends L2GameServerPacket {
    private List<SkillInfo> cR = Collections.emptyList();
    private List<UnitSkillInfo> cS = new ArrayList<UnitSkillInfo>();

    public PledgeSkillList(Clan clan) {
        Collection<Skill> collection = clan.getSkills();
        this.cR = new ArrayList<SkillInfo>(collection.size());
        for (Skill object : collection) {
            this.cR.add(new SkillInfo(object.getId(), object.getLevel()));
        }
        for (SubUnit subUnit : clan.getAllSubUnits()) {
            for (Skill skill : subUnit.getSkills()) {
                this.cS.add(new UnitSkillInfo(subUnit.getType(), skill.getId(), skill.getLevel()));
            }
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(58);
        this.writeD(this.cR.size());
        this.writeD(this.cS.size());
        for (SkillInfo skillInfo : this.cR) {
            this.writeD(skillInfo._id);
            this.writeD(skillInfo._level);
        }
        for (UnitSkillInfo unitSkillInfo : this.cS) {
            this.writeD(unitSkillInfo._type);
            this.writeD(unitSkillInfo._id);
            this.writeD(unitSkillInfo._level);
        }
    }

    static class SkillInfo {
        public int _id;
        public int _level;

        public SkillInfo(int n, int n2) {
            this._id = n;
            this._level = n2;
        }
    }

    static class UnitSkillInfo
    extends SkillInfo {
        private int _type;

        public UnitSkillInfo(int n, int n2, int n3) {
            super(n2, n3);
            this._type = n;
        }
    }
}
