/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.Zone
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.utils.Location
 */
package zones;

import gnu.trove.TIntHashSet;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.model.Zone;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.utils.Location;

private static class ClassIdLimitZone.ClassIdLimitZoneListener
implements OnZoneEnterLeaveListener {
    private final TIntHashSet i;
    private final Location an;

    private ClassIdLimitZone.ClassIdLimitZoneListener(int[] nArray, Location location) {
        this.i = new TIntHashSet(nArray);
        this.an = location;
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        if (creature != null && creature.isPlayer()) {
            final Player player = creature.getPlayer();
            if (player.isGM()) {
                return;
            }
            if (this.i.contains(player.getActiveClassId())) {
                ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

                    public void runImpl() throws Exception {
                        player.sendMessage(new CustomMessage("scripts.zones.epic.banishClassMsg", player, new Object[0]));
                        player.teleToLocation(an);
                        String string = new CustomMessage("zone.services.ClassIdLimitZone.ClassLimit", player, new Object[0]).toString();
                        player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
                        player.sendMessage(string);
                        Summon summon = player.getPet();
                        if (summon != null) {
                            summon.teleToLocation(an);
                        }
                    }
                });
            }
        }
    }

    public void onZoneLeave(Zone zone, Creature creature) {
    }
}
