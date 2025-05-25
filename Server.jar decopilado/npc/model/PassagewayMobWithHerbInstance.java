/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public final class PassagewayMobWithHerbInstance
extends MonsterInstance {
    public static final int FieryDemonBloodHerb = 9849;

    public PassagewayMobWithHerbInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void calculateRewards(Creature creature) {
        if (creature == null) {
            return;
        }
        super.calculateRewards(creature);
        if (creature.isPlayable()) {
            this.dropItem(creature.getPlayer(), 9849, 1L);
        }
    }
}
