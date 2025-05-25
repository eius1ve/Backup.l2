/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;

public static class GameObjectTasks.MagicUseTask
extends RunnableImpl {
    public boolean _forceUse;
    private final HardReference<? extends Creature> J;

    public GameObjectTasks.MagicUseTask(Creature creature, boolean bl) {
        this.J = creature.getRef();
        this._forceUse = bl;
    }

    @Override
    public void runImpl() {
        Creature creature = this.J.get();
        if (creature == null) {
            return;
        }
        Skill skill = creature.getCastingSkill();
        Creature creature2 = creature.getCastingTarget();
        if (skill == null || creature2 == null) {
            creature.clearCastVars();
            return;
        }
        creature.onMagicUseTimer(creature2, skill, this._forceUse);
    }
}
