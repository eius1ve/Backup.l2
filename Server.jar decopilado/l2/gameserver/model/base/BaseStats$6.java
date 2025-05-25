/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import l2.gameserver.model.Creature;
import l2.gameserver.model.base.BaseStats;

final class BaseStats.6
extends BaseStats {
    @Override
    public final int getStat(Creature creature) {
        return creature == null ? 1 : creature.getMEN();
    }

    @Override
    public final double calcBonus(Creature creature) {
        return creature == null ? 1.0 : g[creature.getMEN()];
    }
}
