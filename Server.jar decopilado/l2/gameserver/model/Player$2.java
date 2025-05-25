/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.LinkedList;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.s2c.MagicSkillUse;

class Player.2
extends RunnableImpl {
    final /* synthetic */ HardReference val$playerRef;

    Player.2(HardReference hardReference) {
        this.val$playerRef = hardReference;
    }

    @Override
    public void runImpl() throws Exception {
        Player player = (Player)this.val$playerRef.get();
        if (player == null || player.isTeleporting()) {
            return;
        }
        LinkedList<MagicSkillUse> linkedList = new LinkedList<MagicSkillUse>();
        for (Player player2 : World.getAroundPlayers(player)) {
            if (player2 == null || player2.isTeleporting() || !player2.isCastingNow()) continue;
            Creature creature = player2.getCastingTarget();
            Skill skill = player2.getCastingSkill();
            long l = player2.getAnimationEndTime();
            if (skill == null || creature == null || !creature.isCreature() || l <= 0L) continue;
            linkedList.add(new MagicSkillUse((Creature)player2, creature, skill, (int)(l - System.currentTimeMillis()), 0L));
        }
        player.sendActionFailed();
        if (!linkedList.isEmpty()) {
            player.sendPacket(linkedList);
        }
    }
}
