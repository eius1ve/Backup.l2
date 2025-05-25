/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetNpc
extends Condition {
    private final boolean gD;

    public ConditionTargetNpc(boolean bl) {
        this.gD = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.target != null && env.target.isNpc() == this.gD;
    }
}
