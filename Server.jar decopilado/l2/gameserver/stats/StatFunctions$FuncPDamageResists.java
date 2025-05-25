/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.Config;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Formulas;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.templates.item.WeaponTemplate;

private static class StatFunctions.FuncPDamageResists
extends Func {
    static final StatFunctions.FuncPDamageResists func = new StatFunctions.FuncPDamageResists();

    private StatFunctions.FuncPDamageResists() {
        super(Stats.PHYSICAL_DAMAGE, 48, null);
    }

    @Override
    public void calc(Env env) {
        if (env.target.isRaid() && env.character.getLevel() - env.target.getLevel() > Config.RAID_MAX_LEVEL_DIFF) {
            env.value = 1.0;
            return;
        }
        WeaponTemplate weaponTemplate = env.character.getActiveWeaponItem();
        if (weaponTemplate == null) {
            env.value *= 0.01 * env.target.calcStat(Stats.FIST_WPN_VULNERABILITY, env.character, env.skill);
        } else if (weaponTemplate.getItemType().getDefence() != null) {
            env.value *= 0.01 * env.target.calcStat(weaponTemplate.getItemType().getDefence(), env.character, env.skill);
        }
        env.value = Formulas.calcDamageResists(env.skill, env.character, env.target, env.value);
    }
}
