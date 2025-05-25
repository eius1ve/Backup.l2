/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.instancemanager.ServerVariables
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.scripts.ScriptFile
 */
package services;

import java.util.concurrent.TimeUnit;
import l2.commons.listener.Listener;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.scripts.ScriptFile;

public class ServerLifeTime
implements OnPlayerEnterListener,
ScriptFile {
    private static final String hX = "server_first_start";

    public void onLoad() {
        if (Config.SERVICE_ANNOUNCE_SERVER_LIFE_TIME) {
            long l = System.currentTimeMillis();
            long l2 = ServerVariables.getLong((String)hX, (long)0L);
            if (l2 == 0L) {
                ServerVariables.set((String)hX, (long)l);
            }
            PlayerListenerList.addGlobal((Listener)this);
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public void onPlayerEnter(Player player) {
        long l = System.currentTimeMillis();
        long l2 = l - ServerVariables.getLong((String)hX, (long)l);
        int n = (int)TimeUnit.MILLISECONDS.toDays(l2);
        int n2 = (int)TimeUnit.MILLISECONDS.toHours(l2 -= TimeUnit.DAYS.toMillis(n));
        int n3 = (int)TimeUnit.MILLISECONDS.toMinutes(l2 -= TimeUnit.HOURS.toMillis(n2));
        Announcements.getInstance().announceToPlayerByCustomMessage(player, "services.ServerLifeTime", new String[]{String.valueOf(n), String.valueOf(n2), String.valueOf(n3)});
    }
}
