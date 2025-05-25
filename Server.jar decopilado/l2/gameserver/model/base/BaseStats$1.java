/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import l2.gameserver.model.Creature;
import l2.gameserver.model.base.BaseStats;

final class BaseStats.1
extends BaseStats {
    @Override
    public final int getStat(Creature creature) {
        return creature == null ? 1 : creature.getSTR();
    }

    @Override
    public final double calcBonus(Creature creature) {
        return creature == null ? 1.0 : b[creature.getSTR()];
    }

    @Override
    public final double calcChanceMod(Creature creature) {
        return Math.min(2.0 - Math.sqrt(this.calcBonus(creature)), 1.0);
    }
}
