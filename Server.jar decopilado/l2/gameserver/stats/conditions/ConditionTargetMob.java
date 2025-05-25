/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetMob
extends Condition {
    private final boolean gC;

    public ConditionTargetMob(boolean bl) {
        this.gC = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.target != null && env.target.isMonster() == this.gC;
    }
}
