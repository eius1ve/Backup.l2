/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 */
package services;

import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;

public class Welcome
extends Functions
implements OnPlayerEnterListener,
ScriptFile {
    public void change_lang(String[] stringArray) {
        Player player = this.getSelf();
        if (stringArray[0].equalsIgnoreCase("en")) {
            player.setVar("lang@", "en", -1L);
        } else if (stringArray[0].equalsIgnoreCase("ru")) {
            player.setVar("lang@", "ru", -1L);
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("welcome.htm");
        npcHtmlMessage.replace("%char_name%", player.getName());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void onPlayerEnter(Player player) {
        if (Config.SHOW_HTML_WELCOME && (player.getClan() == null || player.getClan().getNotice() == null || player.getClan().getNotice().isEmpty())) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("welcome.htm");
            npcHtmlMessage.replace("%char_name%", player.getName());
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        }
    }

    public void onLoad() {
        if (Config.SHOW_HTML_WELCOME) {
            CharListenerList.addGlobal((Listener)this);
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
