/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.model.Player
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.PcCafePointsExchange;

import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PcCafePointsExchange
extends Functions
implements ScriptFile {
    private static final Logger s = LoggerFactory.getLogger(PcCafePointsExchange.class);
    private static final String L = "PcCafePointsExchange";
    private static final String N = "[pc_cafe_exchange_spawn]";

    private void spawnEventManagers() {
        SpawnManager.getInstance().spawn(N);
    }

    private void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(N);
    }

    private static boolean isActive() {
        return PcCafePointsExchange.IsActive((String)L);
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (PcCafePointsExchange.SetActive((String)L, (boolean)true)) {
            this.spawnEventManagers();
            System.out.println("Event: 'PcCafePointsExchange' started.");
        } else {
            player.sendMessage("Event 'PcCafePointsExchange' already started.");
        }
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (PcCafePointsExchange.SetActive((String)L, (boolean)false)) {
            this.unSpawnEventManagers();
            System.out.println("Event: 'PcCafePointsExchange' stopped.");
        } else {
            player.sendMessage("Event: 'PcCafePointsExchange' not started.");
        }
        this.show("admin/events/events.htm", player);
    }

    public void onLoad() {
        if (PcCafePointsExchange.isActive()) {
            this.spawnEventManagers();
            s.info("Loaded Event: PcCafePointsExchange [state: activated]");
        } else {
            s.info("Loaded Event: PcCafePointsExchange [state: deactivated]");
        }
    }

    public void onReload() {
        this.unSpawnEventManagers();
    }

    public void onShutdown() {
        this.unSpawnEventManagers();
    }
}
