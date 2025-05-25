/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.Christmas;

import l2.commons.listener.Listener;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Christmas
extends Functions
implements OnDeathListener,
OnPlayerEnterListener,
ScriptFile {
    private static String x = "[christmas_event]";
    private static String y = "[christmas_event_chest]";
    private static final Logger j = LoggerFactory.getLogger(Christmas.class);
    private static int[][] b = new int[][]{{5556, 20}, {5557, 20}, {5558, 50}, {5559, 5}};
    private static boolean T = false;

    public void onLoad() {
        CharListenerList.addGlobal((Listener)this);
        if (Christmas.isActive()) {
            T = true;
            this.spawnEventManagers();
            j.info("Loaded Event: Christmas [state: activated]");
        } else {
            j.info("Loaded Event: Christmas [state: deactivated]");
        }
    }

    private static boolean isActive() {
        return Christmas.IsActive((String)"Christmas");
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (Christmas.SetActive((String)"Christmas", (boolean)true)) {
            this.spawnEventManagers();
            System.out.println("Event 'Christmas' started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.Christmas.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'Christmas' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (Christmas.SetActive((String)"Christmas", (boolean)false)) {
            this.unSpawnEventManagers();
            System.out.println("Event 'Christmas' stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.Christmas.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'Christmas' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    private void spawnEventManagers() {
        SpawnManager.getInstance().spawn(y);
        SpawnManager.getInstance().spawn(x);
    }

    private void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(x);
        SpawnManager.getInstance().despawn(y);
    }

    public void onReload() {
        this.unSpawnEventManagers();
    }

    public void onShutdown() {
        this.unSpawnEventManagers();
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (T && Christmas.simpleCheckDrop((Creature)creature, (Creature)creature2)) {
            int n = 0;
            for (int[] nArray : b) {
                if (!Rnd.chance((double)((double)nArray[1] * creature2.getPlayer().getRateItems() * Config.EVENT_CHRISTMAS_CHANCE * 0.1))) continue;
                ((NpcInstance)creature).dropItem(creature2.getPlayer(), nArray[0], 1L);
                if (++n > 2) break;
            }
        }
    }

    public void exchange(String[] stringArray) {
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        if (player.isActionsDisabled() || player.isSitting() || player.getLastNpc() == null || player.getLastNpc().getDistance((GameObject)player) > 300.0) {
            return;
        }
        if (stringArray[0].equalsIgnoreCase("0")) {
            if (Christmas.getItemCount((Playable)player, (int)5556) >= 4L && Christmas.getItemCount((Playable)player, (int)5557) >= 4L && Christmas.getItemCount((Playable)player, (int)5558) >= 10L && Christmas.getItemCount((Playable)player, (int)5559) >= 1L) {
                Christmas.removeItem((Playable)player, (int)5556, (long)4L, (boolean)false);
                Christmas.removeItem((Playable)player, (int)5557, (long)4L, (boolean)false);
                Christmas.removeItem((Playable)player, (int)5558, (long)10L, (boolean)false);
                Christmas.removeItem((Playable)player, (int)5559, (long)1L, (boolean)false);
                Christmas.addItem((Playable)player, (int)5560, (long)1L);
                return;
            }
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
        }
        if (stringArray[0].equalsIgnoreCase("1")) {
            if (Christmas.getItemCount((Playable)player, (int)5560) >= 10L) {
                Christmas.removeItem((Playable)player, (int)5560, (long)10L, (boolean)false);
                Christmas.addItem((Playable)player, (int)5561, (long)1L);
                return;
            }
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
        }
        if (stringArray[0].equalsIgnoreCase("2")) {
            if (Christmas.getItemCount((Playable)player, (int)5560) >= 10L) {
                Christmas.removeItem((Playable)player, (int)5560, (long)10L, (boolean)false);
                Christmas.addItem((Playable)player, (int)7836, (long)1L);
                return;
            }
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
        }
        if (stringArray[0].equalsIgnoreCase("3")) {
            if (Christmas.getItemCount((Playable)player, (int)5560) >= 10L) {
                Christmas.removeItem((Playable)player, (int)5560, (long)10L, (boolean)false);
                Christmas.addItem((Playable)player, (int)8936, (long)1L);
                return;
            }
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
        }
    }

    public void onPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.Christmas.AnnounceEventStarted", null);
        }
    }
}
