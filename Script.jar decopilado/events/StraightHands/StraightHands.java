/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.item.ItemTemplate
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.StraightHands;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.item.ItemTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StraightHands
extends Functions
implements ScriptFile {
    private static final Logger u = LoggerFactory.getLogger(StraightHands.class);
    private static final String Q = "StraightHands";
    private static final List<Integer> G = new ArrayList<Integer>();
    private static int[] G;
    private static boolean T;

    private static boolean isActive() {
        return StraightHands.IsActive((String)Q);
    }

    private static void F() {
        for (ItemTemplate itemTemplate : ItemHolder.getInstance().getAllTemplates()) {
            if (itemTemplate == null) continue;
            for (int n : G) {
                if (itemTemplate.getItemId() != n) continue;
                itemTemplate.setStatDisabled(true);
                G.add(itemTemplate.getItemId());
                u.info("Event: StraightHands funcs of " + itemTemplate.getName() + " removed.");
            }
        }
    }

    private static void G() {
        for (int n : G) {
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
            if (itemTemplate != null) {
                itemTemplate.setStatDisabled(false);
            }
            u.info("Event: StraightHands funcs of " + itemTemplate.getName() + " restored.");
        }
    }

    private static void k(Player player) {
        for (ItemInstance itemInstance : player.getInventory().getItems()) {
            for (int n : G) {
                if (itemInstance.getItemId() != n) continue;
                if (itemInstance.isEquipped()) {
                    player.getInventory().unEquipItem(itemInstance);
                }
                player.sendMessage(new CustomMessage("scripts.events.StraightHands.ItemS1StatsRemoved", player, new Object[0]).addItemName(n));
            }
        }
    }

    private static void start() {
        G = (int[])Config.EVENT_StraightHands_Items.clone();
        StraightHands.F();
        for (Player player : GameObjectsStorage.getAllPlayers()) {
            StraightHands.k(player);
        }
    }

    private static void stop() {
        StraightHands.G();
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (StraightHands.SetActive((String)Q, (boolean)true)) {
            StraightHands.start();
            u.info("Event: StraightHands started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.StraightHands.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event StraightHands already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (StraightHands.SetActive((String)Q, (boolean)false)) {
            StraightHands.stop();
            u.info("Event: StraightHands stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.StraightHands.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event StraightHands not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    public static void OnPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.StraightHands.AnnounceEventStarted", null);
            StraightHands.k(player);
        }
    }

    public void onLoad() {
        if (StraightHands.isActive()) {
            T = true;
            StraightHands.start();
            u.info("Loaded Event: StraightHands [state: activated]");
        } else {
            u.info("Loaded Event: StraightHands [state: deactivated]");
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    static {
        T = false;
    }
}
