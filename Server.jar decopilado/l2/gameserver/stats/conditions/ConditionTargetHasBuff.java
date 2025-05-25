/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.skills.EffectType;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public final class ConditionTargetHasBuff
extends Condition {
    private final EffectType b;
    private final int DW;

    public ConditionTargetHasBuff(EffectType effectType, int n) {
        this.b = effectType;
        this.DW = n;
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.target;
        if (creature == null) {
            return false;
        }
        Effect effect = creature.getEffectList().getEffectByType(this.b);
        if (effect == null) {
            return false;
        }
        return this.DW == -1 || effect.getSkill().getLevel() >= this.DW;
    }
}
