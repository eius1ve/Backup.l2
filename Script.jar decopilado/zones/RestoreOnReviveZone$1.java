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

class RestoreOnReviveZone.1
implements OnReviveListener {
    RestoreOnReviveZone.1() {
    }

    public void onRevive(Creature creature) {
        creature.setCurrentHpMp((double)creature.getMaxHp(), (double)creature.getMaxMp());
        creature.setCurrentCp((double)creature.getMaxCp());
        creature.sendChanges();
    }
}
