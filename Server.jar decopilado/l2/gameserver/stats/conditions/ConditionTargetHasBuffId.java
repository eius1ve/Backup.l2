/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public final class ConditionTargetHasBuffId
extends Condition {
    private final int DX;
    private final int DY;

    public ConditionTargetHasBuffId(int n, int n2) {
        this.DX = n;
        this.DY = n2;
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.target;
        if (creature == null) {
            return false;
        }
        if (this.DY == -1) {
            return creature.getEffectList().getEffectsBySkillId(this.DX) != null;
        }
        List<Effect> list = creature.getEffectList().getEffectsBySkillId(this.DX);
        if (list == null) {
            return false;
        }
        for (Effect effect : list) {
            if (effect == null || effect.getSkill().getLevel() < this.DY) continue;
            return true;
        }
        return false;
    }
}
