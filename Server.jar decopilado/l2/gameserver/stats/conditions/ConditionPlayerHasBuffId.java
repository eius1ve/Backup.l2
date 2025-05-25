/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerHasBuffId
extends Condition {
    private final int DK;
    private final int DL;

    public ConditionPlayerHasBuffId(int n, int n2) {
        this.DK = n;
        this.DL = n2;
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.character;
        if (creature == null) {
            return false;
        }
        if (this.DL == -1) {
            return creature.getEffectList().getEffectsBySkillId(this.DK) != null;
        }
        List<Effect> list = creature.getEffectList().getEffectsBySkillId(this.DK);
        if (list == null) {
            return false;
        }
        for (Effect effect : list) {
            if (effect == null || effect.getSkill().getLevel() < this.DL) continue;
            return true;
        }
        return false;
    }
}
