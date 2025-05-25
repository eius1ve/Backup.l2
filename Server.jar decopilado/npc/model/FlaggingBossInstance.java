/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.instances.RaidBossInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class FlaggingBossInstance
extends RaidBossInstance {
    public FlaggingBossInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    protected void onDeath(Creature creature) {
        List list = World.getAroundPlayers((GameObject)this, (int)2000, (int)300);
        for (Player player : list) {
            if (player == null) continue;
            player.startPvPFlag((Creature)player);
        }
        super.onDeath(creature);
    }
}
