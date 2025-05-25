/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package npc.model.residences.clanhall;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

class GustavInstance.1
extends RunnableImpl {
    final /* synthetic */ NpcInstance val$npc;

    GustavInstance.1(NpcInstance npcInstance) {
        this.val$npc = npcInstance;
    }

    public void runImpl() throws Exception {
        Location location = Location.findAroundPosition((int)177134, (int)-18807, (int)-2256, (int)50, (int)100, (int)this.val$npc.getGeoIndex());
        this.val$npc.teleToLocation(location);
        if (this.val$npc == GustavInstance.this) {
            this.val$npc.reduceCurrentHp(this.val$npc.getCurrentHp(), (Creature)this.val$npc, null, false, false, false, false, false, false, false);
        }
    }
}
