/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.templates.item.WeaponTemplate;

private static class StatFunctions.FuncAttackRange
extends Func {
    static final StatFunctions.FuncAttackRange func = new StatFunctions.FuncAttackRange();

    private StatFunctions.FuncAttackRange() {
        super(Stats.POWER_ATTACK_RANGE, 32, null);
    }

    @Override
    public void calc(Env env) {
        WeaponTemplate weaponTemplate = env.character.getActiveWeaponItem();
        if (weaponTemplate != null) {
            env.value = weaponTemplate.getAttackRange();
        }
    }
}
