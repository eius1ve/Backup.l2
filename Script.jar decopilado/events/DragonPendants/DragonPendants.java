/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.MultiSellHolder
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.DragonPendants;

import java.io.File;
import java.util.concurrent.TimeUnit;
import l2.commons.listener.Listener;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DragonPendants
extends Functions
implements OnPlayerEnterListener,
ScriptFile {
    private static final Logger l = LoggerFactory.getLogger(DragonPendants.class);
    private static final int[] D = new int[]{29724, 29725, 29726, 29727};
    private static final String B = "[dimensions_event_manager]";
    private static final String[] p = new String[]{"fire_pendant", "water_pendant", "wind_pendant", "land_pendant"};
    private static final File b = new File(Config.DATAPACK_ROOT, "data/html-en/events/dragon_pendant/40033.xml");
    private static boolean T = false;
    private static boolean U = false;

    private static void x() {
        if (U) {
            return;
        }
        MultiSellHolder.getInstance().parseFile(b);
        U = true;
    }

    private static boolean isActive() {
        return DragonPendants.IsActive((String)"DragonPendantsEvent");
    }

    public void receivePendant(String[] stringArray) {
        Player player = this.getSelf();
        if (stringArray[0].equalsIgnoreCase("fire")) {
            Functions.giveLimitedItem((Player)player, (String)p[0], (int)D[0], (int)1, (long)TimeUnit.HOURS.toMillis(24L), (boolean)true);
        }
        if (stringArray[0].equalsIgnoreCase("water")) {
            Functions.giveLimitedItem((Player)player, (String)p[1], (int)D[1], (int)1, (long)TimeUnit.HOURS.toMillis(24L), (boolean)true);
        }
        if (stringArray[0].equalsIgnoreCase("wind")) {
            Functions.giveLimitedItem((Player)player, (String)p[2], (int)D[2], (int)1, (long)TimeUnit.HOURS.toMillis(24L), (boolean)true);
        }
        if (stringArray[0].equalsIgnoreCase("land")) {
            Functions.giveLimitedItem((Player)player, (String)p[3], (int)D[3], (int)1, (long)TimeUnit.HOURS.toMillis(24L), (boolean)true);
        }
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (DragonPendants.SetActive((String)"DragonPendantsEvent", (boolean)true)) {
            DragonPendants.x();
            this.spawnEventManagers();
            l.info("Event 'Dragon Pendants' started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.DragonPendants.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'Dragon Pendants' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (DragonPendants.SetActive((String)"DragonPendantsEvent", (boolean)false)) {
            this.unSpawnEventManagers();
            l.info("Event 'Dragon Pendants' stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.DragonPendants.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'DragonPendants' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    private void spawnEventManagers() {
        SpawnManager.getInstance().spawn(B);
    }

    private void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(B);
    }

    public void onPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.DragonPendants.AnnounceEventStarted", null);
        }
    }

    public void onLoad() {
        CharListenerList.addGlobal((Listener)this);
        if (DragonPendants.isActive()) {
            T = true;
            DragonPendants.x();
            this.spawnEventManagers();
            l.info("Loaded Event: Dragon Pendants [state: activated]");
        } else {
            l.info("Loaded Event: Dragon Pendants [state: deactivated]");
        }
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
}
