/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 */
package npc.model;

import l2.gameserver.ThreadPoolManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import npc.model.FreyaDeaconKeeperInstance;

private class FreyaDeaconKeeperInstance.Listner
implements OnDeathListener {
    private FreyaDeaconKeeperInstance.Listner() {
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (FreyaDeaconKeeperInstance.this.R != null) {
            FreyaDeaconKeeperInstance.this.R.cancel(false);
            FreyaDeaconKeeperInstance.this.R = null;
        }
        ThreadPoolManager.getInstance().execute((Runnable)((Object)new FreyaDeaconKeeperInstance.IceCastleRunner(FreyaDeaconKeeperInstance.this, 1007)));
    }
}
