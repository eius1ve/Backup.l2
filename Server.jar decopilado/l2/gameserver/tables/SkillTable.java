/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntIntHashMap
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.tables;

import gnu.trove.TIntIntHashMap;
import java.util.Map;
import l2.gameserver.model.Skill;
import l2.gameserver.skills.SkillsEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkillTable {
    private static final Logger dt = LoggerFactory.getLogger(SkillTable.class);
    private static final SkillTable a = new SkillTable();
    private Map<Integer, Map<Integer, Skill>> _skills;
    private TIntIntHashMap f;
    private TIntIntHashMap g;

    public static final SkillTable getInstance() {
        return a;
    }

    public void load() {
        this._skills = SkillsEngine.getInstance().loadAllSkills();
        this.bW();
    }

    public void reload() {
        this.load();
    }

    public Skill getInfo(int n, int n2) {
        Map<Integer, Skill> map = this._skills.get(n);
        if (map == null) {
            return null;
        }
        return map.get(n2);
    }

    public int getMaxLevel(int n) {
        return this.f.get(n);
    }

    public int getBaseLevel(int n) {
        return this.g.get(n);
    }

    private void bW() {
        this.f = new TIntIntHashMap();
        this.g = new TIntIntHashMap();
        for (Map<Integer, Skill> map : this._skills.values()) {
            for (Skill skill : map.values()) {
                int n;
                int n2 = skill.getId();
                int n3 = skill.getLevel();
                if (n3 > (n = this.f.get(n2))) {
                    this.f.put(n2, n3);
                }
                if (this.g.get(n2) != 0) continue;
                this.g.put(n2, skill.getBaseLevel());
            }
        }
    }
}
