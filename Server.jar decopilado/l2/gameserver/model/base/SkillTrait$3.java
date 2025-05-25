/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import l2.gameserver.model.base.SkillTrait;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;

final class SkillTrait.3
extends SkillTrait {
    @Override
    public final double calcVuln(Env env) {
        return env.target.calcStat(Stats.ROOT_RESIST, env.character, env.skill);
    }

    @Override
    public final double calcProf(Env env) {
        return env.character.calcStat(Stats.ROOT_POWER, env.target, env.skill);
    }
}
