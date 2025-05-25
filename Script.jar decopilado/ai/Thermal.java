/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Effect
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.StatsSet
 */
package ai;

import java.util.ArrayList;
import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.StatsSet;

public class Thermal
extends Fighter {
    private final double a;
    private final List<Skill> m;

    public Thermal(NpcInstance npcInstance) {
        super(npcInstance);
        StatsSet statsSet = npcInstance.getTemplate().getAIParams();
        this.a = statsSet.getDouble((Object)"thermalDebuffIncrementChance", 10.0);
        this.m = new ArrayList<Skill>();
        for (int n : statsSet.getIntegerArray((Object)"thermalDebuffIncrementId")) {
            this.m.add(SkillTable.getInstance().getInfo(n, 1));
        }
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature != null && Rnd.chance((double)this.a)) {
            Skill skill = this.m.get(Rnd.get((int)this.m.size()));
            Effect effect = creature.getEffectList().getFirstEffectBySkill(skill);
            if (effect != null) {
                int n2;
                int n3 = effect.getSkill().getLevel();
                if (n3 < (n2 = SkillTable.getInstance().getMaxLevel(skill.getId()))) {
                    effect.exit();
                    Skill skill2 = SkillTable.getInstance().getInfo(skill.getId(), n3 + 1);
                    skill2.getEffects((Creature)npcInstance, creature, false, false);
                }
            } else {
                skill.getEffects((Creature)npcInstance, creature, false, false);
            }
        }
        super.onEvtAttacked(creature, n);
    }
}
