/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Creature;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetSummon
extends Condition {
    private final boolean gH;

    public ConditionTargetSummon(boolean bl) {
        this.gH = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.target;
        return creature != null && creature.isSummon() == this.gH;
    }
}
