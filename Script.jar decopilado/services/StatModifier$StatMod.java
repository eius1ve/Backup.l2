/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.stats.Stats
 */
package services;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.stats.Stats;
import services.StatModifier;

private static class StatModifier.StatMod {
    private final StatModifier.ModCond[] a;
    private final Stats d;
    private double bd = 1.0;
    private double be = 0.0;

    private StatModifier.StatMod(StatModifier.ModCond[] modCondArray, Stats stats) {
        this.a = (StatModifier.ModCond[])modCondArray.clone();
        this.d = stats;
    }

    public boolean test(Player player, Creature creature) {
        for (StatModifier.ModCond modCond : this.a) {
            if (modCond.test(player, creature)) continue;
            return false;
        }
        return true;
    }

    public Stats getStat() {
        return this.d;
    }

    public double getMul() {
        return this.bd;
    }

    public void addMul(double d) {
        this.bd += d;
    }

    public double getAdd() {
        return this.be;
    }

    public void setAdd(double d) {
        this.be = d;
    }
}
