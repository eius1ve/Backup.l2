/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.instances.NpcInstance;

protected class FollowNpc.ThinkFollow
extends RunnableImpl {
    protected FollowNpc.ThinkFollow() {
    }

    public NpcInstance getActor() {
        return FollowNpc.this.getActor();
    }

    public void runImpl() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null) {
            return;
        }
        Creature creature = npcInstance.getFollowTarget();
        if (creature == null) {
            creature = npcInstance.getPlayer();
        }
        if (creature == null || creature.isAlikeDead() || npcInstance.getDistance((GameObject)creature) > 4000.0) {
            FollowNpc.this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            return;
        }
        if (!(npcInstance.isInRange((GameObject)creature, 150L) || npcInstance.isFollowing() && npcInstance.getFollowTarget() == creature)) {
            npcInstance.moveToRelative((GameObject)creature, 100, 150, false);
        }
        FollowNpc.this._followTask = ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), 500L);
    }
}
