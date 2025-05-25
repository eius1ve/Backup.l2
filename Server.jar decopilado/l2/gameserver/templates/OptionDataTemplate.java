/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Skill;
import l2.gameserver.stats.StatTemplate;

public class OptionDataTemplate
extends StatTemplate {
    private final List<Skill> dr = new ArrayList<Skill>(0);
    private final int Fx;

    public OptionDataTemplate(int n) {
        this.Fx = n;
    }

    public void addSkill(Skill skill) {
        this.dr.add(skill);
    }

    public List<Skill> getSkills() {
        return this.dr;
    }

    public int getId() {
        return this.Fx;
    }
}
