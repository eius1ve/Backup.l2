/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 */
package services;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import services.StatModifier;

class StatModifier.1
extends StatModifier.ModCond {
    final /* synthetic */ int val$targetClassId;

    StatModifier.1(int n) {
        this.val$targetClassId = n;
    }

    @Override
    public boolean test(Player player, Creature creature) {
        return creature != null && creature.isPlayer() && creature.getPlayer().getActiveClassId() == this.val$targetClassId;
    }
}
