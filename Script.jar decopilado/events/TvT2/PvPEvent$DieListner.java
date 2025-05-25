/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;

private class PvPEvent.DieListner
implements OnDeathListener {
    private PvPEvent.DieListner() {
    }

    public void onDeath(Creature creature, Creature creature2) {
        try {
            if (PvPEvent.getInstance().a() != PvPEvent.PvPEventState.COMPETITION) {
                return;
            }
            Player player = creature.getPlayer();
            Player player2 = creature2.getPlayer();
            if (player != null) {
                PvPEvent.getInstance().getRule().getParticipantController().OnPlayerDied(player, player2);
            }
        }
        catch (Exception exception) {
            y.warn("PVPEvent.onDeath :", (Throwable)exception);
        }
    }
}
