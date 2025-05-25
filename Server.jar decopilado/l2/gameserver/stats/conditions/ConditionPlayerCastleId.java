/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerCastleId
extends Condition {
    private final int DE;

    public ConditionPlayerCastleId(int n) {
        this.DE = n;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.character != null && env.character.getClan() != null && env.character.getClan().getCastle() == this.DE;
    }
}
