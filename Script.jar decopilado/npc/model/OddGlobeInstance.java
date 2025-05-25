/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.EventTrigger
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.ReflectionUtils
 */
package npc.model;

import l2.commons.listener.Listener;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.EventTrigger;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ReflectionUtils;

public final class OddGlobeInstance
extends NpcInstance {
    private static final int HJ = 151;

    public OddGlobeInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!OddGlobeInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.equalsIgnoreCase("monastery_enter")) {
            Reflection reflection = player.getActiveReflection();
            if (reflection != null) {
                if (player.canReenterInstance(151)) {
                    player.teleToLocation(reflection.getTeleportLoc(), reflection);
                }
            } else if (player.canEnterInstance(151)) {
                Reflection reflection2 = ReflectionUtils.enterReflection((Player)player, (int)151);
                ZoneListener zoneListener = new ZoneListener();
                reflection2.getZone("[ssq_holy_burial_ground]").addListener((Listener)zoneListener);
                ZoneListener2 zoneListener2 = new ZoneListener2();
                reflection2.getZone("[ssq_holy_seal]").addListener((Listener)zoneListener2);
            }
        } else {
            super.onBypassFeedback(player, string);
        }
    }

    public class ZoneListener
    implements OnZoneEnterLeaveListener {
        private boolean hu = false;

        public void onZoneEnter(Zone zone, Creature creature) {
            Player player = creature.getPlayer();
            if (player == null || !creature.isPlayer() || this.hu) {
                return;
            }
            this.hu = true;
            player.showQuestMovie(24);
        }

        public void onZoneLeave(Zone zone, Creature creature) {
        }
    }

    public class ZoneListener2
    implements OnZoneEnterLeaveListener {
        private boolean hu = false;

        public void onZoneEnter(Zone zone, Creature creature) {
            Player player = creature.getPlayer();
            if (player == null || !creature.isPlayer()) {
                return;
            }
            player.broadcastPacket(new L2GameServerPacket[]{new EventTrigger(21100100, true)});
            if (!this.hu) {
                this.hu = true;
                player.showQuestMovie(26);
            }
        }

        public void onZoneLeave(Zone zone, Creature creature) {
        }
    }
}
