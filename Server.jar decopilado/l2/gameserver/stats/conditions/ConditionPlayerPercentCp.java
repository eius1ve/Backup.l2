/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerPercentCp
extends Condition {
    private final double av;

    public ConditionPlayerPercentCp(int n) {
        this.av = (double)n / 100.0;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.character.getCurrentCpRatio() <= this.av;
    }
}
