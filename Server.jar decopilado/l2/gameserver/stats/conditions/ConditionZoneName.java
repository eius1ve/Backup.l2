/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionZoneName
extends Condition {
    private final String fZ;

    public ConditionZoneName(String string) {
        this.fZ = string;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (!env.character.isPlayer()) {
            return false;
        }
        return env.character.isInZone(this.fZ);
    }
}
