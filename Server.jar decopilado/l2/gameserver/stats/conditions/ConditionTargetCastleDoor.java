/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetCastleDoor
extends Condition {
    private final boolean gx;

    public ConditionTargetCastleDoor(boolean bl) {
        this.gx = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.target instanceof DoorInstance == this.gx;
    }
}
