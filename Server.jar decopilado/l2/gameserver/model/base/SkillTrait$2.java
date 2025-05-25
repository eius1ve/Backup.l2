/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import l2.gameserver.model.base.SkillTrait;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;

final class SkillTrait.2
extends SkillTrait {
    @Override
    public final double calcVuln(Env env) {
        return env.target.calcStat(Stats.MENTAL_RESIST, env.character, env.skill);
    }

    @Override
    public final double calcProf(Env env) {
        return Math.min(40.0, env.character.calcStat(Stats.MENTAL_POWER, env.target, env.skill) + SkillTrait.2.calcEnchantMod(env));
    }
}
