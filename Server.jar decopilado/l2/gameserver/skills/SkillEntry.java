/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills;

import java.util.AbstractMap;
import l2.gameserver.model.Skill;
import l2.gameserver.skills.SkillEntryType;

public class SkillEntry
extends AbstractMap.SimpleImmutableEntry<SkillEntryType, Skill> {
    private boolean bf;

    public SkillEntry(SkillEntryType skillEntryType, Skill skill) {
        super(skillEntryType, skill);
    }

    public boolean isDisabled() {
        return this.bf;
    }

    public void setDisabled(boolean bl) {
        this.bf = bl;
    }
}
