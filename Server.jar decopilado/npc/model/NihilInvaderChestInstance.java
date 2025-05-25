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

public final class NihilInvaderChestInstance
extends MonsterInstance {
    public NihilInvaderChestInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        this.setHasChatWindow(true);
    }

    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        super.reduceCurrentHp(1.0, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }

    public boolean isFearImmune() {
        return true;
    }

    public boolean isParalyzeImmune() {
        return true;
    }

    public boolean isLethalImmune() {
        return true;
    }
}
