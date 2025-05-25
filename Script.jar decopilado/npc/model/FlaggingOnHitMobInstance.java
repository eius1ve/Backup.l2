/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.World
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class FlaggingOnHitMobInstance
extends MonsterInstance {
    private long y;
    private static final long ej = 40000L;

    public FlaggingOnHitMobInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    private void cb() {
        for (Player player : World.getAroundPlayers((GameObject)this, (int)2000, (int)300)) {
            player.startPvPFlag((Creature)this);
        }
    }

    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        if (creature.isPlayer() && System.currentTimeMillis() - 40000L >= this.y) {
            this.cb();
            this.y = System.currentTimeMillis();
        }
        super.reduceCurrentHp(d, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }
}
