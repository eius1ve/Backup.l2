/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import l2.gameserver.model.Skill;
import l2.gameserver.templates.StatsSet;

public class DocumentSkill.SkillLoad {
    public final int id;
    public final String name;
    public final Map<Integer, StatsSet> sets;
    public final List<Skill> skills;
    public final Map<Integer, Skill> currentSkills;
    public int currentLevel;

    public DocumentSkill.SkillLoad(int n, String string) {
        this.id = n;
        this.name = string;
        this.sets = new TreeMap<Integer, StatsSet>(f);
        this.skills = new ArrayList<Skill>();
        this.currentSkills = new TreeMap<Integer, Skill>(f);
    }
}
