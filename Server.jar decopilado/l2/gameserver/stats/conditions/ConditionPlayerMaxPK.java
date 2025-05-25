/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerMaxPK
extends Condition {
    private final int DO;

    public ConditionPlayerMaxPK(int n) {
        this.DO = n;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (env.character.isPlayer()) {
            return ((Player)env.character).getPkKills() <= this.DO;
        }
        return false;
    }
}
