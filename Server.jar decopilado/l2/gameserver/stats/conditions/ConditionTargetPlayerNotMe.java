/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Creature;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetPlayerNotMe
extends Condition {
    private final boolean gG;

    public ConditionTargetPlayerNotMe(boolean bl) {
        this.gG = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.character;
        Creature creature2 = env.target;
        return creature != null && creature != creature2 == this.gG;
    }
}
