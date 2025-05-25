/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.NextAction;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.World;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.MyTargetSelected;
import l2.gameserver.utils.Location;

public class PlayableAI
extends CharacterAI {
    private final ReentrantLock b = new ReentrantLock();
    private volatile int fk = 0;
    private static final int fl = 1;
    protected Object _intention_arg0 = null;
    protected Object _intention_arg1 = null;
    protected Skill _skill;
    private NextAction a;
    private Object e;
    private Object f;
    private boolean aU;
    private boolean aV;
    protected boolean _forceUse;
    private boolean aW;
    protected ScheduledFuture<?> _followTask;

    public PlayableAI(Playable playable) {
        super(playable);
    }

    @Override
    public void changeIntention(CtrlIntention ctrlIntention, Object object, Object object2) {
        super.changeIntention(ctrlIntention, object, object2);
        this._intention_arg0 = object;
        this._intention_arg1 = object2;
    }

    @Override
    public void setIntention(CtrlIntention ctrlIntention, Object object, Object object2) {
        this._intention_arg0 = null;
        this._intention_arg1 = null;
        super.setIntention(ctrlIntention, object, object2);
    }

    @Override
    protected void onIntentionCast(Skill skill, Creature creature) {
        this._skill = skill;
        super.onIntentionCast(skill, creature);
    }

    public NextAction getNextAction() {
        return this.a;
    }

    public boolean isIntendingInteract(GameObject gameObject) {
        return this.getIntention() == CtrlIntention.AI_INTENTION_INTERACT && this._intention_arg0 == gameObject;
    }

    @Override
    public void setNextAction(NextAction nextAction, Object object, Object object2, boolean bl, boolean bl2) {
        this.a = nextAction;
        this.e = object;
        this.f = object2;
        this.aU = bl;
        this.aV = bl2;
    }

    public boolean setNextIntention() {
        NextAction nextAction = this.a;
        Object object = this.e;
        Object object2 = this.f;
        boolean bl = this.aU;
        boolean bl2 = this.aV;
        Playable playable = this.getActor();
        if (nextAction == null || playable.isActionsDisabled()) {
            return false;
        }
        switch (nextAction) {
            case ATTACK: {
                if (object == null) {
                    return false;
                }
                Creature creature = (Creature)object;
                this._forceUse = bl;
                this.aW = bl2;
                this.clearNextAction();
                this.setIntention(CtrlIntention.AI_INTENTION_ATTACK, creature);
                break;
            }
            case CAST: {
                if (object == null || object2 == null) {
                    return false;
                }
                Skill skill = (Skill)object;
                Creature creature = (Creature)object2;
                this._forceUse = bl;
                this.aW = bl2;
                this.clearNextAction();
                if (!skill.checkCondition(playable, creature, this._forceUse, this.aW, true)) {
                    if (skill.getSkillNextAction() == Skill.SkillNextAction.ATTACK && !playable.equals(creature) && !this._forceUse) {
                        this.setNextAction(NextAction.ATTACK, creature, null, false, false);
                        return this.setNextIntention();
                    }
                    return false;
                }
                this.setIntention(CtrlIntention.AI_INTENTION_CAST, skill, creature);
                break;
            }
            case MOVE: {
                if (object == null || object2 == null) {
                    return false;
                }
                Location location = (Location)object;
                Integer n = (Integer)object2;
                this.clearNextAction();
                playable.moveToLocation(location, n, bl);
                break;
            }
            case REST: {
                playable.sitDown(null);
                break;
            }
            case INTERACT: {
                if (object == null) {
                    return false;
                }
                GameObject gameObject = (GameObject)object;
                this.clearNextAction();
                this.onIntentionInteract(gameObject);
                break;
            }
            case EQUIP: {
                if (object == null) {
                    return false;
                }
                ItemInstance itemInstance = (ItemInstance)object;
                itemInstance.getTemplate().getHandler().useItem(this.getActor(), itemInstance, bl);
                this.clearNextAction();
                break;
            }
            case PICKUP: {
                if (object == null) {
                    return false;
                }
                GameObject gameObject = (GameObject)object;
                this.clearNextAction();
                this.onIntentionPickUp(gameObject);
                break;
            }
            default: {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clearNextAction() {
        this.a = null;
        this.e = null;
        this.f = null;
        this.aU = false;
        this.aV = false;
    }

    @Override
    protected void onEvtFinishCasting(Skill skill, Creature creature) {
        if (!this.setNextIntention()) {
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
        }
    }

    @Override
    protected void onEvtReadyToAct() {
        if (!this.setNextIntention()) {
            this.onEvtThink();
        }
    }

    @Override
    protected void onEvtArrived() {
        if (!this.setNextIntention()) {
            if (this.getIntention() == CtrlIntention.AI_INTENTION_INTERACT || this.getIntention() == CtrlIntention.AI_INTENTION_PICK_UP) {
                this.onEvtThink();
            } else {
                this.changeIntention(CtrlIntention.AI_INTENTION_ACTIVE, null, null);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void onEvtArrivedTarget() {
        Playable playable = this.getActor();
        if (playable.isActionsDisabled()) {
            return;
        }
        this.b.lock();
        try {
            if (this.fk++ > 1) {
                return;
            }
            switch (this.getIntention()) {
                case AI_INTENTION_ATTACK: {
                    this.thinkAttack(false);
                    return;
                }
                case AI_INTENTION_CAST: {
                    this.thinkCast(false);
                    return;
                }
                case AI_INTENTION_FOLLOW: {
                    this.thinkFollow();
                    return;
                }
            }
        }
        catch (Exception exception) {
            _log.error("", (Throwable)exception);
        }
        finally {
            --this.fk;
            this.b.unlock();
        }
        this.onEvtThink();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final void onEvtThink() {
        Playable playable = this.getActor();
        if (playable.isActionsDisabled()) {
            return;
        }
        this.b.lock();
        try {
            if (this.fk++ > 1) {
                return;
            }
            switch (this.getIntention()) {
                case AI_INTENTION_ACTIVE: {
                    this.thinkActive();
                    return;
                }
                case AI_INTENTION_ATTACK: {
                    this.thinkAttack(true);
                    return;
                }
                case AI_INTENTION_CAST: {
                    this.thinkCast(true);
                    return;
                }
                case AI_INTENTION_PICK_UP: {
                    this.thinkPickUp();
                    return;
                }
                case AI_INTENTION_INTERACT: {
                    this.thinkInteract();
                    return;
                }
                case AI_INTENTION_FOLLOW: {
                    this.thinkFollow();
                    return;
                }
            }
            return;
        }
        catch (Exception exception) {
            _log.error("", (Throwable)exception);
            return;
        }
        finally {
            --this.fk;
            this.b.unlock();
        }
    }

    protected void thinkActive() {
    }

    protected static boolean isThinkImplyZ(Playable playable, GameObject gameObject) {
        if (playable.isFlying() || playable.isInWater()) {
            return true;
        }
        if (gameObject != null) {
            Creature creature;
            if (gameObject.isDoor()) {
                return false;
            }
            if (gameObject.isCreature() && ((creature = (Creature)gameObject).isInWater() || creature.isFlying())) {
                return true;
            }
        }
        return false;
    }

    protected void thinkAttack(boolean bl) {
        boolean bl2;
        final Playable playable = this.getActor();
        Player player = playable.getPlayer();
        if (player == null) {
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            return;
        }
        if (playable.isActionsDisabled() || playable.isAttackingDisabled()) {
            playable.sendActionFailed();
            return;
        }
        boolean bl3 = playable instanceof Summon && ((Summon)playable).isDepressed();
        final Creature creature = this.getAttackTarget();
        if (creature == null || creature.isDead() || !bl3 && !(!this._forceUse ? creature.isAutoAttackable(playable) : creature.isAttackable(playable))) {
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            playable.sendActionFailed();
            return;
        }
        if (!bl) {
            this.clientStopMoving();
            playable.doAttack(creature);
            return;
        }
        int n = player.getNetConnection() != null ? player.getNetConnection().getPawnClippingRange() : GameClient.DEFAULT_PAWN_CLIPPING_RANGE;
        int n2 = (int)(playable.getColRadius() + creature.getColRadius());
        boolean bl4 = PlayableAI.isThinkImplyZ(playable, creature);
        int n3 = (int)(!bl4 ? playable.getDistance(creature) : playable.getDistance3D(creature)) - n2;
        boolean bl5 = creature.isDoor();
        int n4 = !bl5 ? playable.getPhysicalAttackRange() : creature.getActingRange();
        boolean bl6 = bl2 = n3 < n && GeoEngine.canSeeTarget(playable, creature, bl4);
        if (!(bl2 || n4 <= 256 && Math.abs(playable.getZ() - creature.getZ()) <= 256)) {
            playable.sendPacket((IStaticPacket)SystemMsg.CANNOT_SEE_TARGET);
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            playable.sendActionFailed();
            return;
        }
        if (n3 <= n4) {
            if (!bl2) {
                playable.sendPacket((IStaticPacket)SystemMsg.CANNOT_SEE_TARGET);
                this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                playable.sendActionFailed();
                return;
            }
            this.clientStopMoving(false);
            playable.doAttack(creature);
        } else if (!this.aW) {
            final int n5 = PlayableAI.getIndentRange(n4) + (!bl5 ? n2 : 0);
            final int n6 = Math.max(n5, n4 + (!bl5 ? n2 : 0));
            ThreadPoolManager.getInstance().execute(new RunnableImpl(){

                @Override
                public void runImpl() throws Exception {
                    playable.moveToRelative(creature, n5, n6);
                }
            });
        } else {
            playable.sendActionFailed();
        }
    }

    protected void thinkCast(boolean bl) {
        boolean bl2;
        final Playable playable = this.getActor();
        final Creature creature = this.getAttackTarget();
        if (this._skill.getSkillType() == Skill.SkillType.CRAFT || this._skill.isToggle()) {
            if (this._skill.checkCondition(playable, creature, this._forceUse, this.aW, true)) {
                playable.doCast(this._skill, creature, this._forceUse);
            }
            return;
        }
        if (creature == null || creature.isDead() != this._skill.getCorpse() && !this._skill.isNotTargetAoE()) {
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            playable.sendActionFailed();
            return;
        }
        if (!bl) {
            if (this._skill.getSkillNextAction() == Skill.SkillNextAction.ATTACK && !playable.equals(creature) && !this._forceUse) {
                this.setNextAction(NextAction.ATTACK, creature, null, false, false);
            } else {
                this.clearNextAction();
            }
            this.clientStopMoving();
            if (this._skill.checkCondition(playable, creature, this._forceUse, this.aW, true)) {
                playable.doCast(this._skill, creature, this._forceUse);
            } else {
                this.setNextIntention();
                if (this.getIntention() == CtrlIntention.AI_INTENTION_ATTACK) {
                    this.thinkAttack(true);
                }
            }
            return;
        }
        int n = (int)(playable.getColRadius() + creature.getColRadius());
        boolean bl3 = PlayableAI.isThinkImplyZ(playable, creature);
        int n2 = (int)(!bl3 ? playable.getDistance(creature) : playable.getDistance3D(creature)) - n;
        boolean bl4 = creature.isDoor();
        int n3 = Math.max(16, playable.getMagicalAttackRange(this._skill));
        boolean bl5 = false;
        switch (this._skill.getSkillType()) {
            case TAKECASTLE: {
                bl5 = true;
                break;
            }
            default: {
                bl5 = GeoEngine.canSeeTarget(playable, creature, playable.isFlying());
            }
        }
        boolean bl6 = bl2 = this._skill.getCastRange() == Short.MAX_VALUE;
        if (!(bl2 || bl5 || n3 <= 256 && Math.abs(playable.getZ() - creature.getZ()) <= 256)) {
            playable.sendPacket((IStaticPacket)SystemMsg.CANNOT_SEE_TARGET);
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            playable.sendActionFailed();
            return;
        }
        if (bl2 || n2 <= n3) {
            if (!bl2 && !bl5) {
                playable.sendPacket((IStaticPacket)SystemMsg.CANNOT_SEE_TARGET);
                this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                playable.sendActionFailed();
                return;
            }
            if (this._skill.getSkillNextAction() == Skill.SkillNextAction.ATTACK && !playable.equals(creature) && !this._forceUse) {
                this.setNextAction(NextAction.ATTACK, creature, null, false, false);
            } else {
                this.clearNextAction();
            }
            if (this._skill.checkCondition(playable, creature, this._forceUse, this.aW, true)) {
                this.clientStopMoving(false);
                playable.doCast(this._skill, creature, this._forceUse);
            } else {
                this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                playable.sendActionFailed();
            }
        } else if (!this.aW) {
            final int n4 = PlayableAI.getIndentRange(n3) + (!bl4 ? n : 0);
            final int n5 = Math.max(n4, n3 + (!bl4 ? n : 0));
            ThreadPoolManager.getInstance().execute(new RunnableImpl(){

                @Override
                public void runImpl() throws Exception {
                    playable.moveToRelative(creature, n4, n5);
                }
            });
        } else {
            playable.sendPacket((IStaticPacket)SystemMsg.YOUR_TARGET_IS_OUT_OF_RANGE);
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            playable.sendActionFailed();
        }
    }

    protected void thinkPickUp() {
        final Playable playable = this.getActor();
        final GameObject gameObject = (GameObject)this._intention_arg0;
        if (gameObject == null) {
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            return;
        }
        if (playable.isInRange(gameObject, (long)(gameObject.getActingRange() + 16)) && Math.abs(playable.getZ() - gameObject.getZ()) < 64) {
            if (playable.isPlayer() || playable.isPet()) {
                playable.doPickupItem(gameObject);
            }
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
        } else {
            final Location location = new Location(gameObject.getX() & 0xFFFFFFF8, gameObject.getY() & 0xFFFFFFF8, gameObject.getZ());
            ThreadPoolManager.getInstance().execute(new RunnableImpl(){

                @Override
                public void runImpl() {
                    playable.moveToLocation(location, 0, true);
                    PlayableAI.this.setNextAction(NextAction.PICKUP, gameObject, null, false, false);
                }
            });
        }
    }

    protected void thinkInteract() {
        int n;
        final Playable playable = this.getActor();
        final GameObject gameObject = (GameObject)this._intention_arg0;
        if (gameObject == null) {
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            return;
        }
        boolean bl = PlayableAI.isThinkImplyZ(playable, gameObject);
        int n2 = (int)(!bl ? playable.getDistance(gameObject) : playable.getDistance3D(gameObject));
        if (n2 <= (n = gameObject.getActingRange())) {
            if (playable.isPlayer()) {
                ((Player)playable).doInteract(gameObject);
            }
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
        } else {
            final int n3 = PlayableAI.getIndentRange(n);
            final int n4 = n;
            ThreadPoolManager.getInstance().execute(new RunnableImpl(){

                @Override
                public void runImpl() throws Exception {
                    playable.moveToRelative(gameObject, n3, n4);
                }
            });
            this.setNextAction(NextAction.INTERACT, gameObject, null, false, false);
        }
    }

    protected void thinkFollow() {
        Playable playable = this.getActor();
        Creature creature = (Creature)this._intention_arg0;
        if (creature == null || creature.isAlikeDead()) {
            this.clientActionFailed();
            return;
        }
        if (playable.isFollowing() && playable.getFollowTarget() == creature) {
            this.clientActionFailed();
            return;
        }
        int n = playable.getPlayer() != null && playable.getPlayer().getNetConnection() != null ? playable.getPlayer().getNetConnection().getPawnClippingRange() : GameClient.DEFAULT_PAWN_CLIPPING_RANGE;
        int n2 = (int)(playable.getColRadius() + creature.getColRadius());
        boolean bl = PlayableAI.isThinkImplyZ(playable, creature);
        int n3 = (int)(!bl ? playable.getDistance(creature) : playable.getDistance3D(creature)) - n2;
        int n4 = Config.FOLLOW_ARRIVE_DISTANCE;
        if (n3 > n) {
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            this.clientActionFailed();
            this.clientStopMoving();
            return;
        }
        if (n3 <= n4 + 16 || playable.isMovementDisabled()) {
            this.clientActionFailed();
        }
        if (this._followTask != null) {
            this._followTask.cancel(false);
            this._followTask = null;
        }
        this._followTask = this.scheduleThinkFollowTask();
    }

    protected ScheduledFuture<?> scheduleThinkFollowTask() {
        return ThreadPoolManager.getInstance().schedule(new ThinkFollow(this.getActor()), Config.MOVE_TASK_QUANTUM_PC);
    }

    @Override
    protected void onIntentionInteract(GameObject gameObject) {
        Playable playable = this.getActor();
        if (playable.isActionsDisabled()) {
            this.setNextAction(NextAction.INTERACT, gameObject, null, false, false);
            this.clientActionFailed();
            return;
        }
        this.clearNextAction();
        this.changeIntention(CtrlIntention.AI_INTENTION_INTERACT, gameObject, null);
        this.onEvtThink();
    }

    @Override
    protected void onIntentionPickUp(GameObject gameObject) {
        Playable playable = this.getActor();
        if (playable.isActionsDisabled()) {
            this.setNextAction(NextAction.PICKUP, gameObject, null, false, false);
            this.clientActionFailed();
            return;
        }
        this.clearNextAction();
        this.changeIntention(CtrlIntention.AI_INTENTION_PICK_UP, gameObject, null);
        this.onEvtThink();
    }

    @Override
    protected void onEvtDead(Creature creature) {
        this.clearNextAction();
        super.onEvtDead(creature);
    }

    @Override
    protected void onEvtFakeDeath() {
        this.clearNextAction();
        super.onEvtFakeDeath();
    }

    public void lockTarget(Creature creature) {
        Playable playable = this.getActor();
        if (creature == null || creature.isDead()) {
            playable.setAggressionTarget(null);
        } else if (playable.getAggressionTarget() == null) {
            GameObject gameObject = playable.getTarget();
            playable.setAggressionTarget(creature);
            playable.setTarget(creature);
            this.clearNextAction();
            if (gameObject != creature) {
                playable.sendPacket((IStaticPacket)new MyTargetSelected(creature.getObjectId(), 0));
            }
        }
    }

    @Override
    public void Attack(GameObject gameObject, boolean bl, boolean bl2) {
        Playable playable = this.getActor();
        if (gameObject.isCreature() && (playable.isActionsDisabled() || playable.isAttackingDisabled())) {
            this.setNextAction(NextAction.ATTACK, gameObject, null, bl, false);
            playable.sendActionFailed();
            return;
        }
        this.aW = bl2;
        this._forceUse = bl;
        this.clearNextAction();
        this.setIntention(CtrlIntention.AI_INTENTION_ATTACK, gameObject);
    }

    @Override
    public void Cast(Skill skill, Creature creature, boolean bl, boolean bl2) {
        Playable playable = this.getActor();
        if (skill.altUse() || skill.isToggle()) {
            if ((skill.isToggle() || skill.isHandler()) && (playable.isOutOfControl() || playable.isStunned() || playable.isSleeping() || playable.isParalyzed() || playable.isAlikeDead())) {
                this.clientActionFailed();
            } else {
                playable.altUseSkill(skill, creature);
            }
            return;
        }
        if (playable.isActionsDisabled()) {
            this.setNextAction(NextAction.CAST, skill, creature, bl, bl2);
            this.clientActionFailed();
            return;
        }
        this._forceUse = bl;
        this.aW = bl2;
        this.clearNextAction();
        this.setIntention(CtrlIntention.AI_INTENTION_CAST, skill, creature);
    }

    @Override
    public Playable getActor() {
        return (Playable)super.getActor();
    }

    @Override
    protected void onEvtForgetObject(GameObject gameObject) {
        super.onEvtForgetObject(gameObject);
        if (this.getIntention() == CtrlIntention.AI_INTENTION_FOLLOW && this._intention_arg0 == gameObject) {
            this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
        }
    }

    protected static class ThinkFollow
    extends RunnableImpl {
        private final HardReference<? extends Playable> l;

        public ThinkFollow(Playable playable) {
            this.l = playable.getRef();
        }

        @Override
        public void runImpl() throws Exception {
            Playable playable = this.l.get();
            if (playable == null) {
                return;
            }
            PlayableAI playableAI = (PlayableAI)playable.getAI();
            if (playableAI.getIntention() != CtrlIntention.AI_INTENTION_FOLLOW) {
                return;
            }
            Creature creature = (Creature)playableAI._intention_arg0;
            if (creature == null || creature.isAlikeDead()) {
                playableAI.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                return;
            }
            int n = playable.getPlayer() != null && playable.getPlayer().getNetConnection() != null ? playable.getPlayer().getNetConnection().getPawnClippingRange() : GameClient.DEFAULT_PAWN_CLIPPING_RANGE;
            int n2 = (int)(playable.getColRadius() + creature.getColRadius());
            boolean bl = PlayableAI.isThinkImplyZ(playable, creature);
            int n3 = (int)(!bl ? playable.getDistance(creature) : playable.getDistance3D(creature)) - n2;
            int n4 = Math.min(n, creature.getActingRange());
            int n5 = Config.FOLLOW_ARRIVE_DISTANCE;
            if (n3 > n || n3 > 2 << World.SHIFT_BY) {
                playableAI.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                playableAI.clientStopMoving();
                return;
            }
            Player player = playable.getPlayer();
            if (player == null || player.isLogoutStarted() || (playable.isPet() || playable.isSummon()) && player.getPet() != playable) {
                playableAI.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                playableAI.clientStopMoving();
                return;
            }
            if (!(n3 <= n5 + 16 || playable.isFollowing() && playable.getFollowTarget() == creature)) {
                playable.moveToRelative(creature, n4 + n2, n5 + n2);
            }
            playableAI._followTask = playableAI.scheduleThinkFollowTask();
        }
    }
}
