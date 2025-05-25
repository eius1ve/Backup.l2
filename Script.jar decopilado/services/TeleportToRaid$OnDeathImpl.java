/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 */
package services;

import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import services.TeleportToRaid;

private static class TeleportToRaid.OnDeathImpl
implements OnDeathListener {
    private TeleportToRaid.OnDeathImpl() {
    }

    public void onDeath(Creature creature, Creature creature2) {
        TeleportToRaid.r(creature.getNpcId());
    }
}
