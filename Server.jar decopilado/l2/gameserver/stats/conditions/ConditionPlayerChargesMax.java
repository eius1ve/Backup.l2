/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerChargesMax
extends Condition {
    private final int DF;

    public ConditionPlayerChargesMax(int n) {
        this.DF = n;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (env.character == null || !env.character.isPlayer()) {
            return false;
        }
        if (((Player)env.character).getIncreasedForce() >= this.DF) {
            env.character.sendPacket((IStaticPacket)SystemMsg.YOUR_FORCE_HAS_REACHED_MAXIMUM_CAPACITY);
            return false;
        }
        return true;
    }
}
