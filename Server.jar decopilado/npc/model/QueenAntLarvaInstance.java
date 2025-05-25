/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class QueenAntLarvaInstance
extends MonsterInstance {
    public QueenAntLarvaInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        d = this.getCurrentHp() - d > 1.0 ? d : this.getCurrentHp() - 1.0;
        super.reduceCurrentHp(d, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }

    public boolean canChampion() {
        return false;
    }

    public boolean isImmobilized() {
        return true;
    }
}
