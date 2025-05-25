/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerRace
extends Condition {
    private final Race b;

    public ConditionPlayerRace(String string) {
        this.b = Race.valueOf(string.toLowerCase());
    }

    @Override
    protected boolean testImpl(Env env) {
        if (!env.character.isPlayer()) {
            return false;
        }
        return ((Player)env.character).getRace() == this.b;
    }
}
