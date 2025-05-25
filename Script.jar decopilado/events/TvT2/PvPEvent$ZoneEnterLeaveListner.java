/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Zone
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Zone;

private class PvPEvent.ZoneEnterLeaveListner
implements OnZoneEnterLeaveListener {
    private PvPEvent.ZoneEnterLeaveListner() {
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        try {
            if (PvPEvent.getInstance().a() != PvPEvent.PvPEventState.COMPETITION || !creature.isPlayer()) {
                return;
            }
            PvPEvent.getInstance().getRule().getParticipantController().OnEnter(creature.getPlayer(), zone);
        }
        catch (Exception exception) {
            y.warn("PVPEvent.onZoneEnter :", (Throwable)exception);
        }
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        try {
            if (PvPEvent.getInstance().a() != PvPEvent.PvPEventState.COMPETITION || !creature.isPlayer()) {
                return;
            }
            PvPEvent.getInstance().getRule().getParticipantController().OnLeave(creature.getPlayer(), zone);
        }
        catch (Exception exception) {
            y.warn("PVPEvent.onZoneLeave :", (Throwable)exception);
        }
    }
}
