/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.tables.SkillTreeTable;

public class SkillList
extends L2GameServerPacket {
    private final List<SkillListEntry> dc = new ArrayList<SkillListEntry>();
    private final int BB;

    public SkillList(Player player, int n) {
        this.BB = n;
        for (Skill skill : player.getAllSkills()) {
            if (skill.isInternal()) continue;
            this.dc.add(new SkillListEntry(skill.getDisplayId(), skill.getLevelForPacket(), skill.getSubLvl(), -1, !skill.isActive() && !skill.isToggle(), player.isUnActiveSkill(skill.getId()), player.getTransformation() == 0 && SkillTreeTable.isEnchantable(skill) != 0));
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(95);
        this.writeD(this.dc.size());
        for (SkillListEntry skillListEntry : this.dc) {
            this.writeD(skillListEntry.passive ? 1 : 0);
            this.writeH(skillListEntry.level);
            this.writeH(skillListEntry.subLevel);
            this.writeD(skillListEntry.id);
            this.writeD(skillListEntry.reuseDelayGroup);
            this.writeC(skillListEntry.disabled ? 1 : 0);
            this.writeC(skillListEntry.enchanted ? 1 : 0);
        }
        this.writeD(this.BB);
    }

    static class SkillListEntry {
        public int id;
        public int level;
        public int subLevel;
        public int reuseDelayGroup;
        public boolean passive;
        public boolean disabled;
        public boolean enchanted;

        SkillListEntry(int n, int n2, int n3, int n4, boolean bl, boolean bl2, boolean bl3) {
            this.id = n;
            this.level = n2;
            this.subLevel = n3;
            this.reuseDelayGroup = n4;
            this.passive = bl;
            this.disabled = bl2;
            this.enchanted = bl3;
        }
    }
}
