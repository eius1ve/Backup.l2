/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.NextAction;
import l2.gameserver.ai.PlayableAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectTasks;

public static class GameObjectTasks.HitTask
extends RunnableImpl {
    boolean _crit;
    boolean _miss;
    boolean _shld;
    boolean _soulshot;
    boolean _unchargeSS;
    boolean _notify;
    boolean _byBow;
    int _damage;
    long _actDelay;
    private final HardReference<? extends Creature> E;
    private final HardReference<? extends Creature> F;

    public GameObjectTasks.HitTask(Creature creature, Creature creature2, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        this.E = creature.getRef();
        this.F = creature2.getRef();
        this._damage = n;
        this._crit = bl;
        this._shld = bl4;
        this._miss = bl2;
        this._soulshot = bl3;
        this._unchargeSS = bl5;
        this._notify = bl6;
        this._actDelay = 0L;
        this._byBow = false;
    }

    public GameObjectTasks.HitTask(Creature creature, Creature creature2, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, long l) {
        this.E = creature.getRef();
        this.F = creature2.getRef();
        this._damage = n;
        this._crit = bl;
        this._shld = bl4;
        this._miss = bl2;
        this._soulshot = bl3;
        this._unchargeSS = bl5;
        this._notify = bl6;
        this._actDelay = l;
        this._byBow = false;
    }

    public GameObjectTasks.HitTask(Creature creature, Creature creature2, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, long l) {
        this.E = creature.getRef();
        this.F = creature2.getRef();
        this._damage = n;
        this._crit = bl;
        this._shld = bl4;
        this._miss = bl2;
        this._soulshot = bl3;
        this._unchargeSS = bl5;
        this._notify = bl7;
        this._actDelay = l;
        this._byBow = bl6;
    }

    @Override
    public void runImpl() {
        Creature creature;
        Creature creature2 = this.E.get();
        if (creature2 == null || (creature = this.F.get()) == null) {
            return;
        }
        if (creature2.isAttackAborted()) {
            return;
        }
        creature2.onHitTimer(creature, this._damage, this._crit, this._miss, this._soulshot, this._shld, this._unchargeSS);
        if (this._notify) {
            if (creature2.isPlayable() && ((PlayableAI)creature2.getAI()).getNextAction() == NextAction.MOVE) {
                creature2.getAI().notifyEvent(CtrlEvent.EVT_READY_TO_ACT);
            } else if (this._actDelay > 0L) {
                ThreadPoolManager.getInstance().schedule(new GameObjectTasks.ActReadyTask(creature2), this._actDelay);
            } else {
                creature2.getAI().notifyEvent(CtrlEvent.EVT_READY_TO_ACT);
            }
        }
    }
}
