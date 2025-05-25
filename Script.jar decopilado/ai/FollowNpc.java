/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import java.util.concurrent.ScheduledFuture;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;

public class FollowNpc
extends DefaultAI {
    private boolean n = false;
    private ScheduledFuture<?> _followTask;

    public FollowNpc(NpcInstance npcInstance) {
        super(npcInstance);
        this.AI_TASK_DELAY_CURRENT = 1000L;
        this.AI_TASK_ACTIVE_DELAY = 1000L;
    }

    protected boolean randomWalk() {
        return this.getActor() instanceof MonsterInstance;
    }

    protected void onEvtThink() {
        NpcInstance npcInstance = this.getActor();
        if (this.n || npcInstance.isActionsDisabled() || npcInstance.isAfraid() || npcInstance.isDead() || npcInstance.isMovementDisabled()) {
            return;
        }
        this.n = true;
        try {
            if (!(Config.BLOCK_ACTIVE_TASKS || this.getIntention() != CtrlIntention.AI_INTENTION_ACTIVE && this.getIntention() != CtrlIntention.AI_INTENTION_IDLE)) {
                this.thinkActive();
            } else if (this.getIntention() == CtrlIntention.AI_INTENTION_FOLLOW) {
                this.thinkFollow();
            }
        }
        catch (Exception exception) {
            _log.error("", (Throwable)exception);
        }
        finally {
            this.n = false;
        }
    }

    protected void thinkFollow() {
        NpcInstance npcInstance = this.getActor();
        Creature creature = npcInstance.getFollowTarget();
        if (creature == null) {
            creature = npcInstance.getPlayer();
        }
        if (creature == null || creature.isAlikeDead() || npcInstance.getDistance((GameObject)creature) > 4000.0 || npcInstance.isMovementDisabled()) {
            this.clientActionFailed();
            return;
        }
        if (npcInstance.isFollowing() && npcInstance.getFollowTarget() == creature) {
            this.clientActionFailed();
            return;
        }
        if (npcInstance.isInRange((GameObject)creature, 100L)) {
            this.clientActionFailed();
        }
        if (this._followTask != null) {
            this._followTask.cancel(false);
            this._followTask = null;
        }
        this._followTask = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThinkFollow()), 500L);
    }

    protected class ThinkFollow
    extends RunnableImpl {
        protected ThinkFollow() {
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
}
