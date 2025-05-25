/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionClanPlayerMinClanLevel
extends Condition {
    private final int DB;

    public ConditionClanPlayerMinClanLevel(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Minimum clan level cannot be negative: " + n);
        }
        this.DB = n;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (env.character == null) {
            return false;
        }
        Player player = env.character.getPlayer();
        if (player == null) {
            return false;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            return false;
        }
        return clan.getLevel() >= this.DB;
    }
}
