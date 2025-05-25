/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 */
package l2.gameserver.stats.conditions;

import gnu.trove.TIntHashSet;
import l2.gameserver.model.Creature;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetForbiddenClassId
extends Condition {
    private TIntHashSet f = new TIntHashSet();

    public ConditionTargetForbiddenClassId(String[] stringArray) {
        for (String string : stringArray) {
            this.f.add(Integer.parseInt(string));
        }
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.target;
        if (creature == null || !creature.isPlayable()) {
            return false;
        }
        return !creature.isPlayer() || !this.f.contains(creature.getPlayer().getActiveClassId());
    }
}
