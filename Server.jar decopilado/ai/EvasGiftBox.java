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
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;

public class EvasGiftBox
extends Fighter {
    private static final int[] a = new int[]{1073, 3141, 3252};
    private static final int I = 9692;
    private static final int J = 9693;

    public EvasGiftBox(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtDead(Creature creature) {
        Player player;
        NpcInstance npcInstance = this.getActor();
        if (creature != null && (player = creature.getPlayer()) != null && player.getEffectList().containEffectFromSkills(a)) {
            npcInstance.dropItem(player, Rnd.chance((int)50) ? 9692 : 9693, 1L);
        }
        super.onEvtDead(creature);
    }

    protected boolean randomWalk() {
        return false;
    }
}
