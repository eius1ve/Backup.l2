/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;

public static class GameObjectTasks.AltMagicUseTask
extends RunnableImpl {
    public final Skill _skill;
    private final HardReference<? extends Creature> x;
    private final HardReference<? extends Creature> y;

    public GameObjectTasks.AltMagicUseTask(Creature creature, Creature creature2, Skill skill) {
        this.x = creature.getRef();
        this.y = creature2.getRef();
        this._skill = skill;
    }

    @Override
    public void runImpl() {
        Creature creature;
        Creature creature2 = this.x.get();
        if (creature2 == null || (creature = this.y.get()) == null) {
            return;
        }
        creature2.altOnMagicUseTimer(creature, this._skill);
    }
}
