/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.skills.effects.EffectSkillSeed;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerSkillMinSeed
extends Condition {
    private final int DS;
    private final int DT;

    public ConditionPlayerSkillMinSeed(int n, int n2) {
        this.DS = n;
        this.DT = n2;
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.character;
        if (creature == null) {
            return false;
        }
        List<Effect> list = creature.getEffectList().getEffectsBySkillId(this.DS);
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (Effect effect : list) {
            EffectSkillSeed effectSkillSeed;
            if (!(effect instanceof EffectSkillSeed) || (effectSkillSeed = (EffectSkillSeed)effect).getSeeds() < this.DT) continue;
            return true;
        }
        return false;
    }
}
