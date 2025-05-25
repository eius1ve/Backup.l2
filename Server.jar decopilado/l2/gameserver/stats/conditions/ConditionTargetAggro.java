/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetAggro
extends Condition {
    private final boolean gv;

    public ConditionTargetAggro(boolean bl) {
        this.gv = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.target;
        if (creature == null) {
            return false;
        }
        if (creature.isMonster()) {
            return ((MonsterInstance)creature).isAggressive() == this.gv;
        }
        if (creature.isPlayer()) {
            return creature.getKarma() > 0;
        }
        return false;
    }
}
