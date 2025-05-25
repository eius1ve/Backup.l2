/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public class SkillTrait
extends Enum<SkillTrait> {
    public static final /* enum */ SkillTrait NONE = new SkillTrait();
    public static final /* enum */ SkillTrait BLEED = new SkillTrait(){

        @Override
        public final double calcVuln(Env env) {
            return env.target.calcStat(Stats.BLEED_RESIST, env.character, env.skill);
        }

        @Override
        public final double calcProf(Env env) {
            return env.character.calcStat(Stats.BLEED_POWER, env.target, env.skill);
        }
    };
    public static final /* enum */ SkillTrait BOSS = new SkillTrait();
    public static final /* enum */ SkillTrait DEATH = new SkillTrait();
    public static final /* enum */ SkillTrait DERANGEMENT = new SkillTrait(){

        @Override
        public final double calcVuln(Env env) {
            return env.target.calcStat(Stats.MENTAL_RESIST, env.character, env.skill);
        }

        @Override
        public final double calcProf(Env env) {
            return Math.min(40.0, env.character.calcStat(Stats.MENTAL_POWER, env.target, env.skill) + 2.calcEnchantMod(env));
        }
    };
    public static final /* enum */ SkillTrait ETC = new SkillTrait();
    public static final /* enum */ SkillTrait GUST = new SkillTrait();
    public static final /* enum */ SkillTrait HOLD = new SkillTrait(){

        @Override
        public final double calcVuln(Env env) {
            return env.target.calcStat(Stats.ROOT_RESIST, env.character, env.skill);
        }

        @Override
        public final double calcProf(Env env) {
            return env.character.calcStat(Stats.ROOT_POWER, env.target, env.skill);
        }
    };
    public static final /* enum */ SkillTrait PARALYZE = new SkillTrait(){

        @Override
        public final double calcVuln(Env env) {
            return env.target.calcStat(Stats.PARALYZE_RESIST, env.character, env.skill);
        }

        @Override
        public final double calcProf(Env env) {
            return env.character.calcStat(Stats.PARALYZE_POWER, env.target, env.skill);
        }
    };
    public static final /* enum */ SkillTrait PHYSICAL_BLOCKADE = new SkillTrait();
    public static final /* enum */ SkillTrait POISON = new SkillTrait(){

        @Override
        public final double calcVuln(Env env) {
            return env.target.calcStat(Stats.POISON_RESIST, env.character, env.skill);
        }

        @Override
        public final double calcProf(Env env) {
            return env.character.calcStat(Stats.POISON_POWER, env.target, env.skill);
        }
    };
    public static final /* enum */ SkillTrait SHOCK = new SkillTrait(){

        @Override
        public final double calcVuln(Env env) {
            return env.target.calcStat(Stats.STUN_RESIST, env.character, env.skill);
        }

        @Override
        public final double calcProf(Env env) {
            return Math.min(40.0, env.character.calcStat(Stats.STUN_POWER, env.target, env.skill) + 6.calcEnchantMod(env));
        }
    };
    public static final /* enum */ SkillTrait SLEEP = new SkillTrait(){

        @Override
        public final double calcVuln(Env env) {
            return env.target.calcStat(Stats.SLEEP_RESIST, env.character, env.skill);
        }

        @Override
        public final double calcProf(Env env) {
            return env.character.calcStat(Stats.SLEEP_POWER, env.target, env.skill);
        }
    };
    public static final /* enum */ SkillTrait VALAKAS = new SkillTrait();
    private static final /* synthetic */ SkillTrait[] a;

    public static SkillTrait[] values() {
        return (SkillTrait[])a.clone();
    }

    public static SkillTrait valueOf(String string) {
        return Enum.valueOf(SkillTrait.class, string);
    }

    public double calcVuln(Env env) {
        return 0.0;
    }

    public double calcProf(Env env) {
        return 0.0;
    }

    public static double calcEnchantMod(Env env) {
        int n = env.skill.getDisplayLevel();
        if (n <= 100) {
            return 0.0;
        }
        return env.skill.getEnchantLevelCount() == 15 ? (double)(n * 2) : (double)(n %= 100);
    }

    private static /* synthetic */ SkillTrait[] a() {
        return new SkillTrait[]{NONE, BLEED, BOSS, DEATH, DERANGEMENT, ETC, GUST, HOLD, PARALYZE, PHYSICAL_BLOCKADE, POISON, SHOCK, SLEEP, VALAKAS};
    }

    static {
        a = SkillTrait.a();
    }
}
