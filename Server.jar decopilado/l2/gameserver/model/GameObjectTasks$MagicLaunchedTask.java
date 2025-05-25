/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.List;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.MagicSkillLaunched;

public static class GameObjectTasks.MagicLaunchedTask
extends RunnableImpl {
    public boolean _forceUse;
    private final HardReference<? extends Creature> I;

    public GameObjectTasks.MagicLaunchedTask(Creature creature, boolean bl) {
        this.I = creature.getRef();
        this._forceUse = bl;
    }

    @Override
    public void runImpl() {
        Creature creature = this.I.get();
        if (creature == null) {
            return;
        }
        Skill skill = creature.getCastingSkill();
        Creature creature2 = creature.getCastingTarget();
        if (skill == null || creature2 == null) {
            creature.clearCastVars();
            return;
        }
        if (!skill.checkCondition(creature, creature2, this._forceUse, false, false)) {
            creature.abortCast(true, false);
            return;
        }
        List<Creature> list = skill.getTargets(creature, creature2, this._forceUse);
        creature.broadcastPacket(new MagicSkillLaunched(creature, skill, list));
    }
}
