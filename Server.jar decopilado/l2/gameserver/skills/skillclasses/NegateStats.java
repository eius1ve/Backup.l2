/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.ArrayList;
import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExMagicAttackInfo;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Formulas;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.FuncTemplate;
import l2.gameserver.templates.StatsSet;

public class NegateStats
extends Skill {
    private final List<Stats> di;
    private final boolean ga;
    private final int Dm;

    public NegateStats(StatsSet statsSet) {
        super(statsSet);
        String[] stringArray = statsSet.getString("negateStats", "").split(" ");
        this.di = new ArrayList<Stats>(stringArray.length);
        for (String string : stringArray) {
            if (string.isEmpty()) continue;
            this.di.add(Stats.valueOfXml(string));
        }
        this.ga = statsSet.getBool("negateDebuffs", false);
        this.Dm = statsSet.getInteger("negateCount", 0);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        for (Creature creature2 : list) {
            if (creature2 == null) continue;
            if (!this.ga && !Formulas.calcSkillSuccess(creature, creature2, this, this.getActivateRate())) {
                creature.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_RESISTED_YOUR_S2).addString(creature2.getName())).addSkillName(this.getId(), this.getLevel()));
                creature.sendPacket((IStaticPacket)new ExMagicAttackInfo(creature.getObjectId(), creature2.getObjectId(), 6));
                continue;
            }
            int n = 0;
            List<Effect> list2 = creature2.getEffectList().getAllEffects();
            block1: for (Stats stats : this.di) {
                for (Effect effect : list2) {
                    Skill skill = effect.getSkill();
                    if (!skill.isOffensive() && skill.getMagicLevel() > this.getMagicLevel() && Rnd.chance(skill.getMagicLevel() - this.getMagicLevel())) {
                        ++n;
                        continue;
                    }
                    if (skill.isOffensive() == this.ga && this.a(effect, stats) && skill.isCancelable()) {
                        creature2.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_EFFECT_OF_S1_HAS_BEEN_REMOVED).addSkillName(effect.getSkill().getId(), effect.getSkill().getDisplayLevel()));
                        effect.exit();
                        ++n;
                    }
                    if (this.Dm <= 0 || n < this.Dm) continue;
                    continue block1;
                }
            }
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }

    private boolean a(Effect effect, Stats stats) {
        for (FuncTemplate funcTemplate : effect.getTemplate().getAttachedFuncs()) {
            if (funcTemplate._stat != stats) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isOffensive() {
        return !this.ga;
    }

    public List<Stats> getNegateStats() {
        return this.di;
    }
}
