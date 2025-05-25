/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.skills.EffectType;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerHasBuff
extends Condition {
    private final EffectType a;
    private final int DJ;

    public ConditionPlayerHasBuff(EffectType effectType, int n) {
        this.a = effectType;
        this.DJ = n;
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.character;
        if (creature == null) {
            return false;
        }
        Effect effect = creature.getEffectList().getEffectByType(this.a);
        if (effect == null) {
            return false;
        }
        return this.DJ == -1 || effect.getSkill().getLevel() >= this.DJ;
    }
}
