/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerClanLevel
extends Condition {
    private final int DH;

    public ConditionPlayerClanLevel(int n) {
        this.DH = n;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.character != null && env.character.getClan() != null && env.character.getClan().getLevel() == this.DH;
    }
}
