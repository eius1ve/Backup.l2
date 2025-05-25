/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.Creature;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.templates.item.WeaponTemplate;

private static class StatFunctions.FuncSDefAll
extends Func {
    static final StatFunctions.FuncSDefAll func = new StatFunctions.FuncSDefAll();

    private StatFunctions.FuncSDefAll() {
        super(Stats.SHIELD_RATE, 32, null);
    }

    @Override
    public void calc(Env env) {
        WeaponTemplate weaponTemplate;
        if (env.value == 0.0) {
            return;
        }
        Creature creature = env.target;
        if (creature != null && (weaponTemplate = creature.getActiveWeaponItem()) != null) {
            switch (weaponTemplate.getItemType()) {
                case BOW: {
                    env.value += 30.0;
                    break;
                }
                case DAGGER: {
                    env.value += 12.0;
                }
            }
        }
    }
}
