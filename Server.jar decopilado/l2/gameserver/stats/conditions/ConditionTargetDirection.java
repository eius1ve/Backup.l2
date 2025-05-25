/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;
import l2.gameserver.utils.PositionUtils;

public class ConditionTargetDirection
extends Condition {
    private final PositionUtils.TargetDirection a;

    public ConditionTargetDirection(PositionUtils.TargetDirection targetDirection) {
        this.a = targetDirection;
    }

    @Override
    protected boolean testImpl(Env env) {
        return PositionUtils.getDirectionTo(env.target, env.character) == this.a;
    }
}
