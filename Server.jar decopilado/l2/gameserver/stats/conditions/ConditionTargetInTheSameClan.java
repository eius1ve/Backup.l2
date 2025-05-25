/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetInTheSameClan
extends Condition {
    private final boolean gA;

    public ConditionTargetInTheSameClan(boolean bl) {
        this.gA = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        Player player;
        Creature creature = env.character;
        Creature creature2 = env.target;
        if (!creature.isPlayable() || creature2 == null || !creature2.isPlayable()) {
            return !this.gA;
        }
        Player player2 = creature.getPlayer();
        if (player2 == (player = creature2.getPlayer())) {
            return this.gA;
        }
        if (player2.getClan() != null && player2.getClan() == player.getClan()) {
            return this.gA;
        }
        return !this.gA;
    }
}
