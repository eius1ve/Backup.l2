/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerOnPvPEvent
extends Condition {
    private final boolean gt;

    public ConditionPlayerOnPvPEvent(boolean bl) {
        this.gt = bl;
    }

    private boolean q(Player player) {
        return (Boolean)Scripts.getInstance().callScripts(player, "events.TvT2.PvPEvent", "isEventParticipant") != false;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (env.character == null) {
            return false;
        }
        Player player = env.character.getPlayer();
        return player != null && this.q(player) == this.gt;
    }
}
