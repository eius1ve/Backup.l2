/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerMinLevel
extends Condition {
    private final int DP;

    public ConditionPlayerMinLevel(int n) {
        this.DP = n;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.character.getLevel() >= this.DP;
    }
}
