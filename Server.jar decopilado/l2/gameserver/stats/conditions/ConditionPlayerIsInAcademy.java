/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerIsInAcademy
extends Condition {
    private final boolean gq;

    public ConditionPlayerIsInAcademy(boolean bl) {
        this.gq = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (env.character == null || !env.character.isPlayer()) {
            return false;
        }
        Player player = env.character.getPlayer();
        return player.getPledgeType() == -1 == this.gq;
    }
}
