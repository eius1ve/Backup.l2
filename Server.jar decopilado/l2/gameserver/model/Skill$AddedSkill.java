/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.model.Skill;
import l2.gameserver.tables.SkillTable;

public static class Skill.AddedSkill {
    public static final Skill.AddedSkill[] EMPTY_ARRAY = new Skill.AddedSkill[0];
    public int id;
    public int level;
    private Skill _skill;

    public Skill.AddedSkill(int n, int n2) {
        this.id = n;
        this.level = n2;
    }

    public Skill getSkill() {
        if (this._skill == null) {
            this._skill = SkillTable.getInstance().getInfo(this.id, this.level);
        }
        return this._skill;
    }
}
