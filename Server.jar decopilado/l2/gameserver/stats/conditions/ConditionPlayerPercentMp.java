/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerPercentMp
extends Condition {
    private final double ax;

    public ConditionPlayerPercentMp(int n) {
        this.ax = (double)n / 100.0;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.character.getCurrentMpRatio() <= this.ax;
    }
}
