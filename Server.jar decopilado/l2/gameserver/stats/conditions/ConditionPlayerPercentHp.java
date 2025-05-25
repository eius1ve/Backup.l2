/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerPercentHp
extends Condition {
    private final double aw;

    public ConditionPlayerPercentHp(int n) {
        this.aw = (double)n / 100.0;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.character.getCurrentHpRatio() <= this.aw;
    }
}
