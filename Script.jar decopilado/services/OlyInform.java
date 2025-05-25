/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.model.entity.oly.OlyController
 *  l2.gameserver.scripts.ScriptFile
 */
package services;

import l2.commons.listener.Listener;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.model.entity.oly.OlyController;
import l2.gameserver.scripts.ScriptFile;

public class OlyInform
implements ScriptFile {
    private static final long ev = 60L;
    private static final long ew = 3600L;
    private static final long ex = 86400L;

    public static void informOlyEnd(Player player) {
        long l;
        long l2 = System.currentTimeMillis();
        if (l2 / 1000L > (l = OlyController.getInstance().getSeasonEndTime())) {
            return;
        }
        long l3 = l - l2 / 1000L;
        int n = (int)(l3 / 86400L);
        int n2 = (int)((l3 %= 86400L) / 3600L);
        int n3 = (int)((l3 %= 3600L) / 60L);
        Announcements.getInstance().announceToPlayerByCustomMessage(player, "l2p.gameserver.model.entity.OlympiadGame.EndSeasonTime", new String[]{String.valueOf(n), String.valueOf(n2), String.valueOf(n3)});
    }

    public void onLoad() {
        if (Config.ANNOUNCE_OLYMPIAD_GAME_END) {
            PlayerListenerList.addGlobal((Listener)new OnPlayerEnterListener(){

                public void onPlayerEnter(Player player) {
                    OlyInform.informOlyEnd(player);
                }
            });
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
