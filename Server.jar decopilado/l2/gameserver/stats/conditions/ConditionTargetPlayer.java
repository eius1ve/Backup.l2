/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Creature;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetPlayer
extends Condition {
    private final boolean gF;

    public ConditionTargetPlayer(boolean bl) {
        this.gF = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.target;
        return creature != null && creature.isPlayer() == this.gF;
    }
}
