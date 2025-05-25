/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.OnReviveListener
 *  l2.gameserver.model.Creature
 */
package zones;

import l2.gameserver.listener.actor.OnReviveListener;
import l2.gameserver.model.Creature;

class AutoBuffZone.AutoBuffClassedZoneListener.2
implements OnReviveListener {
    AutoBuffZone.AutoBuffClassedZoneListener.2() {
    }

    public void onRevive(Creature creature) {
        AutoBuffClassedZoneListener.this.l(creature);
    }
}
