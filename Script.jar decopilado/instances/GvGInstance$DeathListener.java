/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 */
package instances;

import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

private class GvGInstance.DeathListener
implements OnDeathListener {
    private GvGInstance.DeathListener() {
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (!GvGInstance.this.isActive()) {
            return;
        }
        if (creature.getReflection() != creature2.getReflection() || creature.getReflection() != GvGInstance.this) {
            return;
        }
        if (creature.isPlayer() && creature2.isPlayable()) {
            if (GvGInstance.this.a.containsMember(creature.getPlayer()) && GvGInstance.this.b.containsMember(creature2.getPlayer())) {
                GvGInstance.this.q(creature2.getPlayer());
                GvGInstance.this.a(1, 5, 3, true, true, creature2.getPlayer());
            } else if (GvGInstance.this.b.containsMember(creature.getPlayer()) && GvGInstance.this.a.containsMember(creature2.getPlayer())) {
                GvGInstance.this.q(creature2.getPlayer());
                GvGInstance.this.a(2, 5, 3, true, true, creature2.getPlayer());
            }
            GvGInstance.this.resurrectAtBase(creature.getPlayer());
        } else if (creature.isPlayer() && !creature2.isPlayable()) {
            GvGInstance.this.resurrectAtBase(creature.getPlayer());
        } else if (creature.isNpc() && creature2.isPlayable()) {
            if (creature.getNpcId() == 25656) {
                if (GvGInstance.this.a.containsMember(creature2.getPlayer())) {
                    GvGInstance.this.a(1, 20, 0, false, false, creature2.getPlayer());
                } else if (GvGInstance.this.b.containsMember(creature2.getPlayer())) {
                    GvGInstance.this.a(2, 20, 0, false, false, creature2.getPlayer());
                }
            } else if (creature.getNpcId() == 25655) {
                if (GvGInstance.this.a.containsMember(creature2.getPlayer())) {
                    GvGInstance.this.a(1, 100, 0, false, false, creature2.getPlayer());
                } else if (GvGInstance.this.b.containsMember(creature2.getPlayer())) {
                    GvGInstance.this.a(2, 100, 0, false, false, creature2.getPlayer());
                }
                GvGInstance.this.a((L2GameServerPacket)new ExShowScreenMessage("Treasure guard Gerald died at the hand " + creature2.getName(), 5000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
                GvGInstance.this.end();
            }
        }
    }
}
