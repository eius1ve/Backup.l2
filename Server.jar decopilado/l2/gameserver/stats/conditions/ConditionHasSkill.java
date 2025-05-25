/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public final class ConditionHasSkill
extends Condition {
    private final Integer h;
    private final int DC;

    public ConditionHasSkill(Integer n, int n2) {
        this.h = n;
        this.DC = n2;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (env.skill == null) {
            return false;
        }
        return env.character.getSkillLevel(this.h) >= this.DC;
    }
}
