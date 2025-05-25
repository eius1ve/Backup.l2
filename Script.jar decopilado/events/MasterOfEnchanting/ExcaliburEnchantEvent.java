/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.util.RandomUtils
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apache.commons.lang3.tuple.Triple
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.MasterOfEnchanting;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import l2.commons.listener.Listener;
import l2.commons.util.RandomUtils;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcaliburEnchantEvent
extends Functions
implements OnPlayerEnterListener,
ScriptFile {
    private static final String K = "[event_excalibur_spawn_list]";
    private static boolean T;
    private static final Logger r;

    private static boolean isActive() {
        return ExcaliburEnchantEvent.IsActive((String)"excalibur");
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (ExcaliburEnchantEvent.SetActive((String)"excalibur", (boolean)true)) {
            this.spawnEventManagers();
            player.sendMessage("Event 'Excalibur' started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.Excalibur.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'Excalibur' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (ExcaliburEnchantEvent.SetActive((String)"excalibur", (boolean)false)) {
            this.unSpawnEventManagers();
            System.out.println("Event 'Excalibur' stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.Excalibur.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'Excalibur' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    private void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(K);
    }

    private void spawnEventManagers() {
        SpawnManager.getInstance().spawn(K);
    }

    public void buy_staff() {
        Player player = this.getSelf();
        if (ExcaliburEnchantEvent.getItemCount((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_ID) == 0L && ExcaliburEnchantEvent.getItemCount((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID) >= Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_AMOUNT) {
            ExcaliburEnchantEvent.removeItem((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID, (long)Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_AMOUNT);
            ExcaliburEnchantEvent.addItem((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_ID, (long)1L);
            this.show("scripts/events/MasterOfEnchanting/staffbuyed.htm", player);
        } else {
            this.show("scripts/events/MasterOfEnchanting/staffcant.htm", player);
        }
    }

    public void buy_scroll_lim() {
        Player player = this.getSelf();
        long l = System.currentTimeMillis();
        String string = player.getVar("MasterOfEnch");
        long l2 = string != null ? l - Long.parseLong(string) : Config.EVENT_EXCALIBUR_TEMPORAL_SCROLL_REUSE;
        if (l2 >= Config.EVENT_EXCALIBUR_TEMPORAL_SCROLL_REUSE) {
            if (ExcaliburEnchantEvent.getItemCount((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID) >= (long)Config.EVENT_EXCALIBUR_TEMPORAL_SCROLL_PRICE) {
                ExcaliburEnchantEvent.removeItem((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID, (long)Config.EVENT_EXCALIBUR_TEMPORAL_SCROLL_PRICE);
                ExcaliburEnchantEvent.addItem((Playable)player, (int)Config.EVENT_EXCALIBUR_SCROLL_ID, (long)24L);
                player.setVar("MasterOfEnch", String.valueOf(l), -1L);
                this.show("scripts/events/MasterOfEnchanting/scroll24.htm", player);
            } else {
                this.show("scripts/events/MasterOfEnchanting/s24-no.htm", player);
            }
        } else {
            int n = (int)(Config.EVENT_EXCALIBUR_TEMPORAL_SCROLL_REUSE - l2) / 3600000;
            int n2 = (int)(Config.EVENT_EXCALIBUR_TEMPORAL_SCROLL_REUSE - l2) % 3600000 / 60000;
            if (n > 0) {
                SystemMessage systemMessage = new SystemMessage(SystemMsg.THERE_ARE_S1_HOURSS_AND_S2_MINUTES_REMAINING_UNTIL_THE_ITEM_CAN_BE_PURCHASED_AGAIN);
                systemMessage.addNumber(n);
                systemMessage.addNumber(n2);
                player.sendPacket((IStaticPacket)systemMessage);
                this.show("scripts/events/MasterOfEnchanting/scroll24.htm", player);
            } else if (n2 > 0) {
                SystemMessage systemMessage = new SystemMessage(SystemMsg.THERE_ARE_S1_MINUTES_REMAINING_UNTIL_THE_ITEM_CAN_BE_PURCHASED_AGAIN);
                systemMessage.addNumber(n2);
                player.sendPacket((IStaticPacket)systemMessage);
                this.show("scripts/events/MasterOfEnchanting/scroll24.htm", player);
            } else if (ExcaliburEnchantEvent.getItemCount((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID) >= (long)Config.EVENT_EXCALIBUR_TEMPORAL_SCROLL_PRICE) {
                ExcaliburEnchantEvent.removeItem((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID, (long)Config.EVENT_EXCALIBUR_TEMPORAL_SCROLL_PRICE);
                ExcaliburEnchantEvent.addItem((Playable)player, (int)Config.EVENT_EXCALIBUR_SCROLL_ID, (long)24L);
                player.setVar("MasterOfEnch", String.valueOf(l), -1L);
                this.show("scripts/events/MasterOfEnchanting/scroll24.htm", player);
            } else {
                this.show("scripts/events/MasterOfEnchanting/s24-no.htm", player);
            }
        }
    }

    public void buy_scroll_1() {
        Player player = this.getSelf();
        if (ExcaliburEnchantEvent.getItemCount((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID) >= (long)Config.EVENT_EXCALIBUR_ONE_SCROLL_PRICE_PRICE) {
            ExcaliburEnchantEvent.removeItem((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID, (long)Config.EVENT_EXCALIBUR_ONE_SCROLL_PRICE_PRICE);
            ExcaliburEnchantEvent.addItem((Playable)player, (int)Config.EVENT_EXCALIBUR_SCROLL_ID, (long)1L);
            this.show("scripts/events/MasterOfEnchanting/scroll-ok.htm", player);
        } else {
            this.show("scripts/events/MasterOfEnchanting/s1-no.htm", player);
        }
    }

    public void buy_scroll_10() {
        Player player = this.getSelf();
        if (ExcaliburEnchantEvent.getItemCount((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID) >= (long)Config.EVENT_EXCALIBUR_TEN_SCROLL_PRICE_PRICE) {
            ExcaliburEnchantEvent.removeItem((Playable)player, (int)Config.EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID, (long)Config.EVENT_EXCALIBUR_TEN_SCROLL_PRICE_PRICE);
            ExcaliburEnchantEvent.addItem((Playable)player, (int)Config.EVENT_EXCALIBUR_SCROLL_ID, (long)10L);
            this.show("scripts/events/MasterOfEnchanting/scroll-ok.htm", player);
        } else {
            this.show("scripts/events/MasterOfEnchanting/s10-no.htm", player);
        }
    }

    public void receive_reward() {
        Player player = this.getSelf();
        int n = player.getInventory().getPaperdollItemId(5);
        if ((double)player.getInventoryLimit() * 0.9 < (double)player.getInventory().getSize()) {
            this.show("scripts/events/MasterOfEnchanting/inventory_limit.htm", player);
            return;
        }
        if (n != Config.EVENT_EXCALIBUR_WEAPON_ID) {
            this.show("scripts/events/MasterOfEnchanting/rewardnostaff.htm", player);
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByItemId(n);
        int n2 = itemInstance.getEnchantLevel();
        if (n == Config.EVENT_EXCALIBUR_WEAPON_ID && n2 >= Config.EVENT_EXCALIBUR_MIN_ENCHANT_LEVEL_REWARD) {
            List list = (List)Config.EVENT_EXCALIBUR_REWARD.get(n2);
            ExcaliburEnchantEvent.removeItem((Playable)player, (int)n, (long)1L);
            if (list != null) {
                Pair pair;
                LinkedList<Pair> linkedList = new LinkedList<Pair>();
                LinkedList<Pair> linkedList2 = new LinkedList<Pair>();
                for (Triple triple : list) {
                    pair = Pair.of((Object)ItemHolder.getInstance().getTemplate(((Integer)triple.getLeft()).intValue()), (Object)((Long)triple.getMiddle()));
                    double d = (Double)triple.getRight();
                    if (d == 100.0) {
                        linkedList.add(pair);
                        continue;
                    }
                    linkedList2.add(Pair.of((Object)pair, (Object)d));
                }
                if (!linkedList2.isEmpty()) {
                    Collections.sort(linkedList2, RandomUtils.DOUBLE_GROUP_COMPARATOR);
                    Pair pair2 = (Pair)RandomUtils.pickRandomSortedGroup(linkedList2, (double)100.0);
                    if (pair2 != null) {
                        linkedList.add(pair2);
                    }
                }
                for (Pair pair2 : linkedList) {
                    pair = (ItemTemplate)pair2.getLeft();
                    long l = (Long)pair2.getRight();
                    ItemFunctions.addItem((Playable)player, (ItemTemplate)pair, (long)l, (boolean)true);
                }
            }
            this.show("scripts/events/MasterOfEnchanting/rewardok.htm", player);
        } else {
            this.show("scripts/events/MasterOfEnchanting/rewardnostaff.htm", player);
        }
    }

    public void onPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.Excalibur.AnnounceEventStarted", null);
        }
    }

    public void onLoad() {
        if (ExcaliburEnchantEvent.isActive()) {
            T = true;
            this.spawnEventManagers();
            CharListenerList.addGlobal((Listener)this);
            r.info("Loaded Event: Excalibur [state: activated]");
        } else {
            r.info("Loaded Event: Excalibur [state: deactivated]");
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    static {
        r = LoggerFactory.getLogger(ExcaliburEnchantEvent.class);
    }
}
