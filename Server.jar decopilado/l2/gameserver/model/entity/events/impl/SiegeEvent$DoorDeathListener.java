/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.impl;

import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.templates.DoorTemplate;

public class SiegeEvent.DoorDeathListener
implements OnDeathListener {
    @Override
    public void onDeath(Creature creature, Creature creature2) {
        if (!SiegeEvent.this.isInProgress()) {
            return;
        }
        DoorInstance doorInstance = (DoorInstance)creature;
        if (doorInstance.getDoorType() == DoorTemplate.DoorType.WALL) {
            return;
        }
        SiegeEvent.this.broadcastTo(SystemMsg.THE_CASTLE_GATE_HAS_BEEN_DESTROYED, SiegeEvent.ATTACKERS, SiegeEvent.DEFENDERS);
    }
}
