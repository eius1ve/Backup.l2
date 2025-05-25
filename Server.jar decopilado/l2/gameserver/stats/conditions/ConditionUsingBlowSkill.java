/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionUsingBlowSkill
extends Condition {
    private final boolean gI;

    public ConditionUsingBlowSkill(boolean bl) {
        this.gI = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (env.skill == null) {
            return !this.gI;
        }
        return env.skill.isBlowSkill() == this.gI;
    }
}
