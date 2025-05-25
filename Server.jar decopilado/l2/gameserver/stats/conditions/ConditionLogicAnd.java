/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionLogicAnd
extends Condition {
    private static final Condition[] a = new Condition[0];
    public Condition[] _conditions = a;

    public void add(Condition condition) {
        if (condition == null) {
            return;
        }
        int n = this._conditions.length;
        Condition[] conditionArray = new Condition[n + 1];
        System.arraycopy(this._conditions, 0, conditionArray, 0, n);
        conditionArray[n] = condition;
        this._conditions = conditionArray;
    }

    @Override
    protected boolean testImpl(Env env) {
        for (Condition condition : this._conditions) {
            if (condition.test(env)) continue;
            return false;
        }
        return true;
    }
}
