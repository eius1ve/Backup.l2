/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Announcements
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.model.Player
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.Kamaloka;

import l2.gameserver.Announcements;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class kamaloka
extends Functions
implements ScriptFile {
    private static boolean T = false;
    private static final Logger q = LoggerFactory.getLogger(kamaloka.class);
    private static String J = "[event_kamaloka_spawn_list]";

    private void spawnEventManagers() {
        SpawnManager.getInstance().spawn(J);
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (kamaloka.SetActive((String)"kamaloka", (boolean)true)) {
            this.spawnEventManagers();
            System.out.println("Event 'Kamaloka' started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.Kamaloka.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'Kamaloka' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (kamaloka.SetActive((String)"kamaloka", (boolean)false)) {
            this.unSpawnEventManagers();
            System.out.println("Event 'Kamaloka' stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.Kamaloka.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'Kamaloka' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    private static boolean isActive() {
        return kamaloka.IsActive((String)"kamaloka");
    }

    private void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(J);
    }

    public void onLoad() {
        if (kamaloka.isActive()) {
            T = true;
            this.spawnEventManagers();
            q.info("Loaded Event: Kamaloka [state: activated]");
        } else {
            q.info("Loaded Event: Kamaloka [state: deactivated]");
        }
    }

    public void onReload() {
        this.unSpawnEventManagers();
    }

    public void onShutdown() {
        this.unSpawnEventManagers();
    }
}
