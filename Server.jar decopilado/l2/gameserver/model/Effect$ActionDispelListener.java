/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.listener.actor.OnAttackListener;
import l2.gameserver.listener.actor.OnMagicUseListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;

private class Effect.ActionDispelListener
implements OnAttackListener,
OnMagicUseListener {
    private Effect.ActionDispelListener() {
    }

    @Override
    public void onMagicUse(Creature creature, Skill skill, Creature creature2, boolean bl) {
        Effect.this.exit();
    }

    @Override
    public void onAttack(Creature creature, Creature creature2) {
        Effect.this.exit();
    }
}
