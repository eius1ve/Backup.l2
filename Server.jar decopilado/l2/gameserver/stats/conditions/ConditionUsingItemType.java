/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Playable;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public final class ConditionUsingItemType
extends Condition {
    private final long dK;

    public ConditionUsingItemType(long l) {
        this.dK = l;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (!env.character.isPlayable()) {
            return false;
        }
        return (this.dK & ((Playable)env.character).getWearedMask()) != 0L;
    }
}
