/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.templates.StatsSet;

public class StealBuff
extends Skill {
    private final int Dp;
    private final int Dq;

    public StealBuff(StatsSet statsSet) {
        super(statsSet);
        this.Dp = statsSet.getInteger("StealCount", 1);
        this.Dq = statsSet.getInteger("ChanceMod", 0);
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        if (creature2 == null || !creature2.isPlayer()) {
            creature.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return false;
        }
        return super.checkCondition(creature, creature2, bl, bl2, bl3);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        for (Creature creature2 : list) {
            if (creature2 == null) continue;
            double d = 1.0 - creature2.calcStat(Stats.CANCEL_RESIST, 0.0, null, null) * 0.01;
            LinkedList<Effect> linkedList = new LinkedList<Effect>();
            for (Effect.EEffectSlot eEffectSlot : Effect.EEffectSlot.VALUES) {
                for (Effect effect : creature2.getEffectList().getAllFirstEffects()) {
                    Object object;
                    if (effect == null || effect.getTemplate()._applyOnCaster || effect.getEffectSlot() != eEffectSlot || !((Skill)(object = effect.getSkill())).isCancelable() || !((Skill)object).isActive() || ((Skill)object).isOffensive() || ((Skill)object).isToggle() || ((Skill)object).isTrigger()) continue;
                    linkedList.add(effect);
                }
            }
            boolean bl = false;
            Iterator iterator = linkedList.descendingIterator();
            int n = 0;
            while (iterator.hasNext() && n++ < this.Dp) {
                Effect effect = (Effect)iterator.next();
                if (!StealBuff.calcSkillCancel(this, effect, this.Dq, d, true)) continue;
                Skill skill = effect.getSkill();
                for (Object object : creature2.getEffectList().getEffectsBySkill(skill)) {
                    if (object == null) continue;
                    Effect effect2 = ((Effect)object).getTemplate().getEffect(new Env(creature, creature, skill));
                    effect2.setCount(((Effect)object).getCount());
                    if (((Effect)object).getCount() == 1) {
                        effect2.setPeriod(((Effect)object).getPeriod() - ((Effect)object).getTime());
                    } else {
                        effect2.setPeriod(((Effect)object).getPeriod());
                    }
                    bl = true;
                    ((Effect)object).exit();
                    creature.getEffectList().addEffect(effect2);
                }
                creature2.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_WORN_OFF).addSkillName(skill.getId(), skill.getLevel()));
            }
            if (bl) {
                creature2.sendChanges();
                creature2.updateEffectIcons();
                creature.sendChanges();
                creature.updateEffectIcons();
            }
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }

    public static boolean calcSkillCancel(Skill skill, Effect effect, int n, double d, boolean bl) {
        int n2 = Math.max(0, skill.getMagicLevel() - effect.getSkill().getMagicLevel());
        int n3 = (int)((double)((long)(2 * n2 + n) + effect.getPeriod() * (long)effect.getCount() / 120000L) * d);
        return Rnd.chance(Math.max(Config.SKILLS_DISPEL_MOD_MIN, Math.min(Config.SKILLS_DISPEL_MOD_MAX, n3)));
    }
}
