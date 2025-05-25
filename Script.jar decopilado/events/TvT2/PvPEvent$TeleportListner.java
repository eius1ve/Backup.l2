/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnTeleportListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.Reflection
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import l2.gameserver.listener.actor.player.OnTeleportListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.Reflection;

@Deprecated
private class PvPEvent.TeleportListner
implements OnTeleportListener {
    private PvPEvent.TeleportListner() {
    }

    public void onTeleport(Player player, int n, int n2, int n3, Reflection reflection) {
        try {
            if (PvPEvent.getInstance().a() != PvPEvent.PvPEventState.COMPETITION) {
                return;
            }
            PvPEvent.getInstance().getRule().getParticipantController().OnTeleport(player, n, n2, n3, reflection);
        }
        catch (Exception exception) {
            y.warn("PVPEvent.onTeleport :", (Throwable)exception);
        }
    }
}
