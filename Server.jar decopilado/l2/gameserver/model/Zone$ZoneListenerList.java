/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.listener.ListenerList;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Zone;

public class Zone.ZoneListenerList
extends ListenerList<Zone> {
    public void onEnter(Creature creature) {
        this.forEachListener(OnZoneEnterLeaveListener.class, onZoneEnterLeaveListener -> onZoneEnterLeaveListener.onZoneEnter(Zone.this, creature));
    }

    public void onLeave(Creature creature) {
        this.forEachListener(OnZoneEnterLeaveListener.class, onZoneEnterLeaveListener -> onZoneEnterLeaveListener.onZoneLeave(Zone.this, creature));
    }
}
