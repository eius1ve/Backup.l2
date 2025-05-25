/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Creature;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetPlayable
extends Condition {
    private final boolean gE;

    public ConditionTargetPlayable(boolean bl) {
        this.gE = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.target;
        return creature != null && creature.isPlayable() == this.gE;
    }
}
