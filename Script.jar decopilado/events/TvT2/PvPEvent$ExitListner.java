/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnPlayerExitListener
 *  l2.gameserver.model.Player
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import l2.gameserver.listener.actor.player.OnPlayerExitListener;
import l2.gameserver.model.Player;

private class PvPEvent.ExitListner
implements OnPlayerExitListener {
    private PvPEvent.ExitListner() {
    }

    public void onPlayerExit(Player player) {
        try {
            if (PvPEvent.getInstance().a() == PvPEvent.PvPEventState.STANDBY) {
                return;
            }
            PvPEvent.getInstance().getRule().getParticipantController().OnExit(player);
            PvPEvent.getInstance().a.remove(player.getObjectId());
        }
        catch (Exception exception) {
            y.warn("PVPEvent.onPlayerExit :", (Throwable)exception);
        }
    }
}
