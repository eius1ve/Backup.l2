/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerMinMaxDamage
extends Condition {
    private final double at;
    private final double au;

    public ConditionPlayerMinMaxDamage(double d, double d2) {
        this.at = d;
        this.au = d2;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (this.at > 0.0 && env.value < this.at) {
            return false;
        }
        return !(this.au > 0.0) || !(env.value > this.au);
    }
}
