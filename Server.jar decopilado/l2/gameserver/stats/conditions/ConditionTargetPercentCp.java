/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetPercentCp
extends Condition {
    private final double ay;

    public ConditionTargetPercentCp(int n) {
        this.ay = (double)n / 100.0;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.target != null && env.target.getCurrentCpRatio() <= this.ay;
    }
}
