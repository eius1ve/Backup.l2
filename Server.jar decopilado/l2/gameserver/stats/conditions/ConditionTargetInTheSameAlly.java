/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetInTheSameAlly
extends Condition {
    private final boolean gz;

    public ConditionTargetInTheSameAlly(boolean bl) {
        this.gz = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        Player player;
        Creature creature = env.character;
        Creature creature2 = env.target;
        if (!creature.isPlayable() || creature2 == null || !creature2.isPlayable()) {
            return !this.gz;
        }
        Player player2 = creature.getPlayer();
        if (player2 == (player = creature2.getPlayer())) {
            return this.gz;
        }
        if (player2.getClanId() != 0 && player.getClanId() == player2.getClanId() || player2.getAllyId() != 0 && player.getAllyId() == player2.getAllyId()) {
            return this.gz;
        }
        return !this.gz;
    }
}
