/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.isle_of_prayer;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;

public class WaterDragonDetractor
extends Fighter {
    private static final int bi = 9689;
    private static final int bj = 9595;

    public WaterDragonDetractor(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtDead(Creature creature) {
        Player player;
        if (creature != null && (player = creature.getPlayer()) != null) {
            NpcInstance npcInstance = this.getActor();
            npcInstance.dropItem(player, 9689, 1L);
            if (Rnd.chance((int)10)) {
                npcInstance.dropItem(player, 9595, 1L);
            }
        }
        super.onEvtDead(creature);
    }
}
