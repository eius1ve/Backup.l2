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

private static abstract class StatModifier.ModCond {
    private StatModifier.ModCond() {
    }

    public abstract boolean test(Player var1, Creature var2);
}
