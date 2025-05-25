/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerClassId
extends Condition {
    private final int[] bh;

    public ConditionPlayerClassId(String[] stringArray) {
        this.bh = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            this.bh[i] = Integer.parseInt(stringArray[i]);
        }
    }

    @Override
    protected boolean testImpl(Env env) {
        if (!env.character.isPlayer()) {
            return false;
        }
        int n = ((Player)env.character).getActiveClassId();
        for (int n2 : this.bh) {
            if (n != n2) continue;
            return true;
        }
        return false;
    }
}
