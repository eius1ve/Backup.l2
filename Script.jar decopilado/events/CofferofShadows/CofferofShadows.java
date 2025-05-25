/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.handler.bypass.INpcHtmlAppendHandler
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Util
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.CofferofShadows;

import l2.commons.listener.Listener;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.handler.bypass.INpcHtmlAppendHandler;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CofferofShadows
extends Functions
implements INpcHtmlAppendHandler,
OnPlayerEnterListener,
ScriptFile {
    private static int bH = 50000;
    private static int bI = 8659;
    private static String z = "[event_coffer_of_shadows]";
    private static final Logger k = LoggerFactory.getLogger(CofferofShadows.class);
    private static boolean T = false;
    private static int[] C = new int[]{1, 5, 10, 50};

    private void spawnEventManagers() {
        SpawnManager.getInstance().spawn(z);
    }

    private void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(z);
    }

    private static boolean isActive() {
        return CofferofShadows.IsActive((String)"CofferofShadows");
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (CofferofShadows.SetActive((String)"CofferofShadows", (boolean)true)) {
            this.spawnEventManagers();
            System.out.println("Event: Coffer of Shadows started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.CofferofShadows.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'Coffer of Shadows' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (CofferofShadows.SetActive((String)"CofferofShadows", (boolean)false)) {
            this.unSpawnEventManagers();
            System.out.println("Event: Coffer of Shadows stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.CofferofShadows.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'Coffer of Shadows' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    public void buycoffer(String[] stringArray) {
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)player.getLastNpc())) {
            return;
        }
        int n = 1;
        try {
            n = Integer.valueOf(stringArray[0]);
        }
        catch (Exception exception) {
            // empty catch block
        }
        long l = (long)((double)bH * Config.EVENT_CofferOfShadowsPriceRate * (double)n);
        if (player.getAdena() < l) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            return;
        }
        player.reduceAdena(l, true);
        Functions.addItem((Playable)player, (int)bI, (long)n);
    }

    public String getHtmlAppends(Integer n) {
        if (n != 0 || !CofferofShadows.isActive()) {
            return "";
        }
        Object object = "";
        for (int n2 : C) {
            String string = Util.formatAdena((long)((long)((double)bH * Config.EVENT_CofferOfShadowsPriceRate * (double)n2)));
            object = (String)object + "<a action=\"bypass -h scripts_events.CofferofShadows.CofferofShadows:buycoffer " + n2 + "\">";
            object = n2 == 1 ? (String)object + new CustomMessage("scripts.events.CofferofShadows.buycoffer", this.getSelf(), new Object[0]).addString(string) : (String)object + new CustomMessage("scripts.events.CofferofShadows.buycoffers", this.getSelf(), new Object[0]).addNumber((long)n2).addString(string);
            object = (String)object + "</a><br>";
        }
        return object;
    }

    public void onLoad() {
        CharListenerList.addGlobal((Listener)this);
        if (CofferofShadows.isActive()) {
            T = true;
            this.spawnEventManagers();
            k.info("Loaded Event: Coffer of Shadows [state: activated]");
        } else {
            k.info("Loaded Event: Coffer of Shadows [state: deactivated]");
        }
    }

    public void onReload() {
        this.unSpawnEventManagers();
    }

    public void onShutdown() {
        this.unSpawnEventManagers();
    }

    public void onPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.CofferofShadows.AnnounceEventStarted", null);
        }
    }

    public int[] getNpcIds() {
        return Config.EVENT_CofferOfShadowsNpcId;
    }

    public String getAppend(Player player, int n, int n2) {
        CofferofShadows cofferofShadows = new CofferofShadows();
        cofferofShadows.self = player.getRef();
        return cofferofShadows.getHtmlAppends(n2);
    }
}
