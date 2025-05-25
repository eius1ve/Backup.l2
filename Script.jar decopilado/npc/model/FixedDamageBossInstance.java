/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.RaidBossInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class FixedDamageBossInstance
extends RaidBossInstance {
    public FixedDamageBossInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        d = 1.0;
        super.reduceCurrentHp(d, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }

    protected void onDeath(Creature creature) {
        super.onDeath(creature);
    }

    public boolean canChampion() {
        return false;
    }
}
