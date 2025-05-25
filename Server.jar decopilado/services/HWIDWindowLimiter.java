/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.network.l2.GameClient
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.apache.commons.lang3.StringUtils
 */
package services;

import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.StringUtils;

public class HWIDWindowLimiter
extends Functions
implements OnPlayerEnterListener,
ScriptFile {
    public void onPlayerEnter(Player player) {
        int n = 1;
        GameClient gameClient = player.getNetConnection();
        if (Config.ALT_LIMIT_HWID_PREMIUM_WINDOW != 0 || Config.ALT_LIMIT_HWID_WINDOW != 0) {
            String string;
            if (gameClient != null && !StringUtils.isBlank((CharSequence)(string = gameClient.getHwid()))) {
                for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                    GameClient gameClient2;
                    if (player2 == null || player2 == player || !player2.isOnline() || (gameClient2 = player2.getNetConnection()) == null || !StringUtils.equalsIgnoreCase((CharSequence)string, (CharSequence)gameClient2.getHwid())) continue;
                    ++n;
                }
            }
            if (player.hasBonus()) {
                if (Config.ALT_LIMIT_HWID_PREMIUM_WINDOW > 0 && n > Config.ALT_LIMIT_HWID_PREMIUM_WINDOW) {
                    this.as(player);
                }
            } else if (Config.ALT_LIMIT_HWID_WINDOW > 0 && n > Config.ALT_LIMIT_HWID_WINDOW) {
                this.as(player);
            }
        }
    }

    private void as(Player player) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/hwid_limits.htm");
        npcHtmlMessage.replace("%hwid_window_limits_prem%", String.valueOf(Config.ALT_LIMIT_HWID_PREMIUM_WINDOW));
        npcHtmlMessage.replace("%hwid_window_limits%", String.valueOf(Config.ALT_LIMIT_HWID_WINDOW));
        player.sendMessage(new CustomMessage("hwidWindowLimiter", player, new Object[0]).addNumber((long)Config.ALT_LIMIT_HWID_PREMIUM_WINDOW).addNumber((long)Config.ALT_LIMIT_HWID_WINDOW));
        player.block();
        player.sitDown(null);
        player.sendPacket((IStaticPacket)npcHtmlMessage);
        player.startKickTask(Config.ALT_LIMIT_HWID_REPORT_TIMER);
    }

    public void onLoad() {
        if (Config.ALT_LIMIT_HWID_PREMIUM_WINDOW != 0 || Config.ALT_LIMIT_HWID_WINDOW != 0) {
            CharListenerList.addGlobal((Listener)this);
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
