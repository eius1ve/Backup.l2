/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExMagicAttackInfo;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.skills.EffectType;
import l2.gameserver.stats.Formulas;
import l2.gameserver.templates.StatsSet;

public class NegateEffects
extends Skill {
    private Map<EffectType, Integer> bI = new HashMap<EffectType, Integer>();
    private Map<String, Integer> bJ = new HashMap<String, Integer>();
    private final boolean fY;
    private final boolean fZ;

    public NegateEffects(StatsSet statsSet) {
        super(statsSet);
        String[] stringArray = statsSet.getString("negateEffects", "").split(";");
        for (int i = 0; i < stringArray.length; ++i) {
            String[] stringArray2;
            if (stringArray[i].isEmpty()) continue;
            this.bI.put(Enum.valueOf(EffectType.class, stringArray2[0]), (stringArray2 = stringArray[i].split(":")).length > 1 ? Integer.decode(stringArray2[1]) : Integer.MAX_VALUE);
        }
        String[] stringArray3 = statsSet.getString("negateStackType", "").split(";");
        for (int i = 0; i < stringArray3.length; ++i) {
            if (stringArray3[i].isEmpty()) continue;
            String[] stringArray4 = stringArray3[i].split(":");
            this.bJ.put(stringArray4[0], stringArray4.length > 1 ? Integer.decode(stringArray4[1]) : Integer.MAX_VALUE);
        }
        this.fY = statsSet.getBool("onlyPhysical", false);
        this.fZ = statsSet.getBool("negateDebuffs", true);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        for (Creature creature2 : list) {
            if (creature2 == null) continue;
            if (!this.fZ && !Formulas.calcSkillSuccess(creature, creature2, this, this.getActivateRate())) {
                creature.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_RESISTED_YOUR_S2).addString(creature2.getName())).addSkillName(this.getDisplayId(), this.getDisplayLevel()));
                creature.sendPacket((IStaticPacket)new ExMagicAttackInfo(creature.getObjectId(), creature2.getObjectId(), 6));
                continue;
            }
            if (!this.bI.isEmpty()) {
                for (Map.Entry<EffectType, Integer> entry : this.bI.entrySet()) {
                    this.a(creature2, entry.getKey(), (int)entry.getValue());
                }
            }
            if (!this.bJ.isEmpty()) {
                for (Map.Entry<Object, Integer> entry : this.bJ.entrySet()) {
                    this.a(creature2, (String)entry.getKey(), (int)entry.getValue());
                }
            }
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }

    private void a(Creature creature, EffectType effectType, int n) {
        for (Effect effect : creature.getEffectList().getAllEffects()) {
            Skill skill = effect.getSkill();
            if (this.fY && skill.isMagic() || !skill.isCancelable() || skill.isOffensive() && !this.fZ || !skill.isOffensive() && skill.getMagicLevel() > this.getMagicLevel() && Rnd.chance(skill.getMagicLevel() - this.getMagicLevel()) || effect.getEffectType() != effectType || effect.getStackOrder() > n) continue;
            effect.exit();
        }
    }

    private void a(Creature creature, String string, int n) {
        for (Effect effect : creature.getEffectList().getAllEffects()) {
            Skill skill = effect.getSkill();
            if (this.fY && skill.isMagic() || !skill.isCancelable() || skill.isOffensive() && !this.fZ || !skill.isOffensive() && skill.getMagicLevel() > this.getMagicLevel() && Rnd.chance(skill.getMagicLevel() - this.getMagicLevel()) || !effect.isStackTypeMatch(string) || effect.getStackOrder() > n) continue;
            effect.exit();
        }
    }
}
