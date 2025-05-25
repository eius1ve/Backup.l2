/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;

private class Creature.AttackStanceTask
extends RunnableImpl {
    private Creature.AttackStanceTask() {
    }

    @Override
    public void runImpl() throws Exception {
        if (!Creature.this.isInCombat()) {
            Creature.this.stopAttackStanceTask();
        }
    }
}
