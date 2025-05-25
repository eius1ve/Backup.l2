/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerMaxLevel
extends Condition {
    private final int DN;

    public ConditionPlayerMaxLevel(int n) {
        this.DN = n;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.character.getLevel() <= this.DN;
    }
}
