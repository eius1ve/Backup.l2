/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.OnReviveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.SkillCoolTime
 */
package zones;

import l2.gameserver.listener.actor.OnReviveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SkillCoolTime;

class RestoreOnReviveZone.2
implements OnReviveListener {
    RestoreOnReviveZone.2() {
    }

    public void onRevive(Creature creature) {
        Player player = creature.getPlayer();
        player.resetReuse();
        player.sendPacket((IStaticPacket)new SkillCoolTime(player));
    }
}
