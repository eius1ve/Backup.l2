/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import java.util.Collections;
import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import org.apache.commons.lang3.tuple.Pair;
import services.SupportMagic;

private static class SupportMagic.NewbieBuffsList {
    private final SupportMagic.NewbieBuffsListType a;
    private final int bFY;
    private final int bFZ;
    private final List<Pair<Pair<Integer, Integer>, Skill>> dX;

    private SupportMagic.NewbieBuffsList(SupportMagic.NewbieBuffsListType newbieBuffsListType, int n, int n2, List<Pair<Pair<Integer, Integer>, Skill>> list) {
        this.a = newbieBuffsListType;
        this.bFY = n;
        this.bFZ = n2;
        this.dX = list;
    }

    public SupportMagic.NewbieBuffsListType getType() {
        return this.a;
    }

    public int getMinLevel() {
        return this.bFY;
    }

    public int getMaxLevel() {
        return this.bFZ;
    }

    public List<Pair<Pair<Integer, Integer>, Skill>> getBuffs() {
        return this.dX;
    }

    public void apply(Creature creature, Creature creature2) {
        int n = creature2.getLevel();
        for (Pair<Pair<Integer, Integer>, Skill> pair : this.getBuffs()) {
            Pair pair2 = (Pair)pair.getKey();
            if (n < (Integer)pair2.getLeft() || n > (Integer)pair2.getRight()) continue;
            Skill skill = (Skill)pair.getValue();
            creature.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse(creature, creature2, skill, 0, 0L)});
            creature.callSkill(skill, Collections.singletonList(creature2), true);
        }
    }
}
