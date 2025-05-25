/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.MultiSellHolder
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.TheFallHarvest;

import java.io.File;
import l2.commons.listener.Listener;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheFallHarvest
extends Functions
implements OnDeathListener,
OnPlayerEnterListener,
ScriptFile {
    private static final Logger v = LoggerFactory.getLogger(TheFallHarvest.class);
    private static boolean T = false;
    private static boolean U = false;
    private static String R = "[the_fall_harvest_spawn]";
    private static File b = new File(Config.DATAPACK_ROOT, "data/html-en/scripts/events/TheFallHarvest/31255.xml");

    public void onLoad() {
        CharListenerList.addGlobal((Listener)this);
        if (TheFallHarvest.isActive()) {
            T = true;
            TheFallHarvest.x();
            this.spawnEventManagers();
            v.info("Loaded Event: The Fall Harvest [state: activated]");
        } else {
            v.info("Loaded Event: The Fall Harvest [state: deactivated]");
        }
    }

    private static boolean isActive() {
        return TheFallHarvest.IsActive((String)"TheFallHarvest");
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (TheFallHarvest.SetActive((String)"TheFallHarvest", (boolean)true)) {
            TheFallHarvest.x();
            this.spawnEventManagers();
            System.out.println("Event 'The Fall Harvest' started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.TheFallHarvest.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'The Fall Harvest' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (TheFallHarvest.SetActive((String)"TheFallHarvest", (boolean)false)) {
            this.unSpawnEventManagers();
            System.out.println("Event 'The Fall Harvest' stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.TheFallHarvest.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'The Fall Harvest' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    private void spawnEventManagers() {
        SpawnManager.getInstance().spawn(R);
    }

    private void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(R);
    }

    private static void x() {
        if (U) {
            return;
        }
        MultiSellHolder.getInstance().parseFile(b);
        U = true;
    }

    public void onReload() {
        this.unSpawnEventManagers();
        if (U) {
            MultiSellHolder.getInstance().remove(b);
            U = false;
        }
    }

    public void onShutdown() {
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (T && TheFallHarvest.simpleCheckDrop((Creature)creature, (Creature)creature2) && Rnd.chance((double)(Config.EVENT_TFH_POLLEN_CHANCE * creature2.getPlayer().getRateItems() * ((NpcInstance)creature).getTemplate().rateHp))) {
            ((NpcInstance)creature).dropItem(creature2.getPlayer(), 6391, 1L);
        }
    }

    public void onPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.TheFallHarvest.AnnounceEventStarted", null);
        }
    }
}
