/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.model;

import l2.commons.collections.LazyArrayList;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Formulas;
import l2.gameserver.stats.Stats;
import org.apache.commons.lang3.tuple.Pair;

class Skill.2
extends RunnableImpl {
    final /* synthetic */ Creature val$effector;
    final /* synthetic */ boolean val$applyOnCaster;
    final /* synthetic */ boolean val$skillReflected;
    final /* synthetic */ Creature val$effected;
    final /* synthetic */ boolean val$calcChance;
    final /* synthetic */ long val$timeConst;
    final /* synthetic */ double val$timeMult;

    Skill.2(Creature creature, boolean bl, boolean bl2, Creature creature2, boolean bl3, long l, double d) {
        this.val$effector = creature;
        this.val$applyOnCaster = bl;
        this.val$skillReflected = bl2;
        this.val$effected = creature2;
        this.val$calcChance = bl3;
        this.val$timeConst = l;
        this.val$timeMult = d;
    }

    @Override
    public void runImpl() {
        boolean bl = false;
        boolean bl2 = false;
        int n = this.val$effector.getChargedSpiritShot();
        if (this.val$effector.getSkillMastery(Skill.this.getId()) == 2) {
            bl2 = true;
            this.val$effector.removeSkillMastery(Skill.this.getId());
        }
        for (EffectTemplate effectTemplate : Skill.this.getEffectTemplates()) {
            Object object;
            if (this.val$applyOnCaster != effectTemplate._applyOnCaster || effectTemplate._count == 0) continue;
            Creature creature = effectTemplate._applyOnCaster || effectTemplate._isReflectable && this.val$skillReflected ? this.val$effector : this.val$effected;
            LazyArrayList<Object> lazyArrayList = new LazyArrayList<Object>(1);
            lazyArrayList.add(creature);
            if (effectTemplate._applyOnSummon && creature.isPlayer() && (object = creature.getPlayer().getPet()) != null && ((GameObject)object).isSummon() && !Skill.this.isOffensive() && !Skill.this.isToggle() && !Skill.this.isCubicSkill()) {
                lazyArrayList.add(object);
            }
            object = lazyArrayList.iterator();
            block1: while (object.hasNext()) {
                Effect effect;
                Creature creature2 = (Creature)object.next();
                if (creature2.isAlikeDead() && !Skill.this.isPreservedOnDeath() || creature2.isRaid() && effectTemplate.getEffectType().isRaidImmune() || Skill.this.a(effectTemplate, creature2, this.val$effector, this.val$effected) || Skill.this.isBlockedByChar(creature2, effectTemplate)) continue;
                if (effectTemplate._stackOrder == -1) {
                    if (!effectTemplate._stackType.equals("none")) {
                        for (Effect effect2 : creature2.getEffectList().getAllEffects()) {
                            if (!effect2.getStackType().equalsIgnoreCase(effectTemplate._stackType)) continue;
                            continue block1;
                        }
                    } else if (creature2.getEffectList().getEffectsBySkillId(Skill.this.getId()) != null) continue;
                }
                Env env = new Env(this.val$effector, creature2, Skill.this);
                int n2 = effectTemplate.chance(Skill.this.getActivateRate());
                if ((this.val$calcChance || n2 >= 0) && !effectTemplate._applyOnCaster) {
                    env.value = n2;
                    if (!Formulas.calcSkillSuccess(env, effectTemplate, n)) continue;
                }
                if (Skill.this._isReflectable && effectTemplate._isReflectable && Skill.this.isOffensive() && !this.val$effector.isInvul() && !this.val$effector.isDebuffImmune() && creature2 != this.val$effector && !this.val$effector.isTrap() && Rnd.chance(creature2.calcStat(Skill.this.isMagic() ? Stats.REFLECT_MAGIC_DEBUFF : Stats.REFLECT_PHYSIC_DEBUFF, 0.0, this.val$effector, Skill.this))) {
                    creature2.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_COUNTERED_C1S_ATTACK).addName(this.val$effector));
                    this.val$effector.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_DODGES_THE_ATTACK).addName(creature2));
                    env.target = creature2 = this.val$effector;
                }
                if (bl) {
                    env.value = 2.147483647E9;
                }
                if ((effect = effectTemplate.getEffect(env)) == null) continue;
                if (n2 > 0) {
                    bl = true;
                }
                if (effect.isOneTime()) {
                    if (!effect.checkCondition()) continue;
                    effect.onStart();
                    effect.onActionTime();
                    effect.onExit();
                    continue;
                }
                int n3 = effectTemplate.getCount();
                long l = effectTemplate.getPeriod();
                if (bl2) {
                    if (n3 > 1) {
                        n3 *= 2;
                    } else {
                        l *= 2L;
                    }
                }
                if (Config.CALC_EFFECT_TIME_YIELD_AND_RESIST && !effectTemplate._applyOnCaster && Skill.this.isOffensive() && !Skill.this.isIgnoreResists() && !this.val$effector.isRaid()) {
                    double d = 0.0;
                    Pair<Stats, Stats> pair = effectTemplate.getEffectType().getResistAndPowerType();
                    if (pair != null) {
                        Stats stats = (Stats)((Object)pair.getLeft());
                        Stats stats2 = (Stats)((Object)pair.getRight());
                        if (stats != null) {
                            d += this.val$effected.calcStat(stats, this.val$effector, Skill.this);
                        }
                        if (stats2 != null) {
                            d -= this.val$effector.calcStat(stats2, this.val$effected, Skill.this);
                        }
                    }
                    if ((d += this.val$effected.calcStat(Stats.DEBUFF_RESIST, this.val$effector, Skill.this)) != 0.0) {
                        double d2 = 1.0 + Math.abs(0.01 * d);
                        if (d > 0.0) {
                            d2 = 1.0 / d2;
                        }
                        if (n3 > 1) {
                            n3 = (int)Math.floor(Math.max((double)n3 * d2, 1.0));
                        } else {
                            l = (long)Math.floor(Math.max((double)l * d2, 1.0));
                        }
                    }
                }
                if (this.val$timeConst > 0L) {
                    l = n3 > 1 ? this.val$timeConst / (long)n3 : this.val$timeConst;
                } else if (this.val$timeMult > 1.0) {
                    if (n3 > 1) {
                        n3 = (int)((double)n3 * this.val$timeMult);
                    } else {
                        l = (long)((double)l * this.val$timeMult);
                    }
                }
                Skill skill = effect.getSkill();
                if (skill != null && Config.SKILL_DURATION_MOD.containsKey(skill.getId()) && (Config.SKILL_DURATION_MOD_AT_OLY || !this.val$effector.isOlyParticipant())) {
                    int n4 = Config.SKILL_DURATION_MOD.get(skill.getId());
                    if (skill.getLevel() >= 100 && skill.getLevel() < 140) {
                        if (n3 > 1) {
                            n3 = n4;
                        } else {
                            l = n4;
                        }
                    } else if (n3 > 1) {
                        n3 = n4;
                    } else {
                        l = n4;
                    }
                }
                effect.setCount(n3);
                effect.setPeriod(l);
                effect.schedule();
            }
        }
        if (this.val$calcChance) {
            if (bl) {
                this.val$effector.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_SUCCEEDED).addSkillName(Skill.this._displayId, Skill.this._displayLevel));
            } else {
                this.val$effector.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_FAILED).addSkillName(Skill.this._displayId, Skill.this._displayLevel));
            }
        }
    }
}
