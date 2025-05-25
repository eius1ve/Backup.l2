/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Announcements
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.ChatType
 */
package events.Finder;

import events.Finder.Finder;
import l2.gameserver.Announcements;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.ChatType;

private class Finder.HostageKilledTask
implements Runnable {
    private Finder.HostageKilledTask() {
    }

    @Override
    public void run() {
        if (!T || o == null || p == null) {
            return;
        }
        Finder.A();
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.Finder.AnnounceHostageKilled", null, ChatType.ANNOUNCEMENT);
        }
    }
}
