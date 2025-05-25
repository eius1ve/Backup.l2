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

class StatModifier.3
extends StatModifier.ModCond {
    final /* synthetic */ int val$classId;
    final /* synthetic */ StatModifier.OlyMode val$olyMode;

    StatModifier.3(int n, StatModifier.OlyMode olyMode) {
        this.val$classId = n;
        this.val$olyMode = olyMode;
    }

    @Override
    public boolean test(Player player, Creature creature) {
        if (player != null && player.getActiveClassId() == this.val$classId) {
            if (this.val$olyMode != StatModifier.OlyMode.ANY) {
                if (this.val$olyMode == StatModifier.OlyMode.OLY_ONLY && !player.isOlyParticipant()) {
                    return false;
                }
                if (this.val$olyMode == StatModifier.OlyMode.NON_OLY_ONLY && player.isOlyParticipant()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
