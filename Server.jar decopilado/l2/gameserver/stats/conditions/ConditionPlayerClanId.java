/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerClanId
extends Condition {
    private final int[] bg;

    public ConditionPlayerClanId(String[] stringArray) {
        this.bg = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            this.bg[i] = Integer.parseInt(stringArray[i]);
        }
    }

    @Override
    protected boolean testImpl(Env env) {
        if (!env.character.isPlayer()) {
            return false;
        }
        Player player = (Player)env.character;
        if (player.getClan() == null) {
            return false;
        }
        int n = player.getClan().getClanId();
        for (int n2 : this.bg) {
            if (n != n2) continue;
            return true;
        }
        return false;
    }
}
