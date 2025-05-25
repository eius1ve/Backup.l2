/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.skills.TimeStamp;

public class SkillCoolTime
extends L2GameServerPacket {
    private List<Skill> bV = Collections.emptyList();

    public SkillCoolTime(Player player) {
        Collection<TimeStamp> collection = player.getSkillReuses();
        this.bV = new ArrayList<Skill>(collection.size());
        for (TimeStamp timeStamp : collection) {
            l2.gameserver.model.Skill skill;
            if (!timeStamp.hasNotPassed() || (double)timeStamp.getReuseCurrent() < 1000.0 || (skill = player.getKnownSkill(timeStamp.getId())) == null) continue;
            Skill skill2 = new Skill();
            skill2.skillId = skill.getId();
            skill2.level = skill.getLevel();
            skill2.reuseBase = (int)Math.floor((double)timeStamp.getReuseBasic() / 1000.0);
            skill2.reuseCurrent = (int)Math.floor((double)timeStamp.getReuseCurrent() / 1000.0);
            this.bV.add(skill2);
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(199);
        this.writeD(this.bV.size());
        for (int i = 0; i < this.bV.size(); ++i) {
            Skill skill = this.bV.get(i);
            this.writeD(skill.skillId);
            this.writeD(skill.level);
            this.writeD(skill.reuseBase);
            this.writeD(skill.reuseCurrent);
        }
    }

    private static class Skill {
        public int skillId;
        public int level;
        public int reuseBase;
        public int reuseCurrent;

        private Skill() {
        }
    }
}
