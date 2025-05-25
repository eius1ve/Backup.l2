/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 */
package l2.gameserver.stats.conditions;

import gnu.trove.TIntHashSet;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetMobId
extends Condition {
    private final TIntHashSet g;

    public ConditionTargetMobId(TIntHashSet tIntHashSet) {
        this.g = tIntHashSet;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.target != null && this.g.contains(env.target.getNpcId());
    }
}
