/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetBoss
extends Condition {
    private final boolean gw;

    public ConditionTargetBoss(boolean bl) {
        this.gw = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.target != null && env.target.isRaid() == this.gw;
    }
}
