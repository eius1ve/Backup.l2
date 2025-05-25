/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.commons.listener.Listener
 *  l2.commons.math.SafeMath
 *  l2.gameserver.Config
 *  l2.gameserver.GameServer
 *  l2.gameserver.dao.CharacterDAO
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.xml.holder.CrystalGradeDataHolder
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.data.xml.holder.OptionDataHolder
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.listener.game.OnCharacterDeleteListener
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.World
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.ItemInstance$ItemLocation
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.taskmanager.DelayedItemsManager
 *  l2.gameserver.templates.OptionDataTemplate
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.Log$ItemLog
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services.pawnshop;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.dbutils.DbUtils;
import l2.commons.listener.Listener;
import l2.commons.math.SafeMath;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.xml.holder.CrystalGradeDataHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.OptionDataHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.listener.game.OnCharacterDeleteListener;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.taskmanager.DelayedItemsManager;
import l2.gameserver.templates.OptionDataTemplate;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PawnShop
extends Functions
implements ScriptFile {
    private static final Logger et = LoggerFactory.getLogger(PawnShop.class);
    private static final String iq = "mods/pawnshop";
    private static final String ir = "-h scripts_services.pawnshop.PawnShop:";
    private static final AtomicInteger u = new AtomicInteger(0);
    private static final Comparator<PawnShopItem> g = new Comparator<PawnShopItem>(){

        @Override
        public int compare(PawnShopItem pawnShopItem, PawnShopItem pawnShopItem2) {
            if (pawnShopItem.getCurrencyItemId() == pawnShopItem2.getCurrencyItemId()) {
                return pawnShopItem.getPrice() - pawnShopItem2.getPrice();
            }
            return pawnShopItem2.getCurrencyItemId() - pawnShopItem.getCurrencyItemId();
        }
    };
    private static CopyOnWriteArrayList<PawnShopItem> a = new CopyOnWriteArrayList();
    private static Map<String, Set<Integer>> cw = new HashMap<String, Set<Integer>>();

    private static void cp() {
        List<PawnShopItem> list = PawnShopItem.loadItems();
        int n = 0;
        for (PawnShopItem pawnShopItem : list) {
            if (pawnShopItem.getId() <= n) continue;
            n = pawnShopItem.getId();
        }
        int n2 = u.get();
        if (n > n2) {
            u.compareAndSet(n2, n);
        }
        a = new CopyOnWriteArrayList<PawnShopItem>(list);
    }

    public static void showStartPage(Player player, NpcInstance npcInstance) {
        if (!Config.PAWNSHOP_ENABLED || !PawnShop.a(player, npcInstance)) {
            return;
        }
        PawnShop.a(player, npcInstance, 0, "");
    }

    private static boolean a(Player player, NpcInstance npcInstance) {
        if (player == null || npcInstance == null) {
            return false;
        }
        return !player.isActionsDisabled() && (Config.ALLOW_TALK_WHILE_SITTING || !player.isSitting()) && npcInstance.isInActingRange((GameObject)player);
    }

    private static void a(String[] stringArray, int[] nArray) {
        PawnShop.a(stringArray, nArray, null);
    }

    private static void a(String[] stringArray, int[] nArray, StringBuilder stringBuilder) {
        int n = 0;
        if (nArray != null) {
            for (int i = 0; n < stringArray.length && i < nArray.length; ++n, ++i) {
                String string = stringArray[n];
                try {
                    long l = Long.parseLong(string);
                    nArray[i] = (int)(l & 0xFFFFFFFFL);
                    continue;
                }
                catch (Exception exception) {
                    et.warn("Can't parse int arg \"" + string + "\"");
                }
            }
        }
        if (stringBuilder != null && n < stringArray.length) {
            stringBuilder.append(stringArray[n]);
            ++n;
            while (n < stringArray.length) {
                stringBuilder.append(' ').append(stringArray[n]);
                ++n;
            }
        }
    }

    private static void a(Player player, NpcInstance npcInstance, int n, String string) {
        int n2;
        String string2 = PawnShop.j(string);
        List<PawnShopItem> list = PawnShop.a(player, string2);
        StringBuilder stringBuilder = new StringBuilder();
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, npcInstance);
        npcHtmlMessage.setFile("mods/pawnshop/buy_list.htm");
        int n3 = n2 + Config.PAWNSHOP_ITEMS_PER_PAGE;
        for (n2 = Config.PAWNSHOP_ITEMS_PER_PAGE * n; n2 < n3 && n2 < list.size(); ++n2) {
            PawnShopItem pawnShopItem = list.get(n2);
            stringBuilder.append(PawnShop.a(player, "pawnshop.buy_item_element", String.format("-h scripts_services.pawnshop.PawnShop:buyItem %d %d %s", pawnShopItem.getId(), n, string2), pawnShopItem.getItemTemplate(), pawnShopItem.getEnchantLevel(), pawnShopItem.getAmount(), ItemHolder.getInstance().getTemplate(pawnShopItem.getCurrencyItemId()), pawnShopItem.getPrice(), pawnShopItem.getOwnerName(), pawnShopItem.getVariationSkill()));
        }
        npcHtmlMessage.replace("%list%", stringBuilder.toString());
        npcHtmlMessage.replace("%paging%", PawnShop.a(player, n, list, "buyList", string2));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private static List<PawnShopItem> l() {
        LinkedList<PawnShopItem> linkedList = new LinkedList<PawnShopItem>();
        for (PawnShopItem pawnShopItem : a) {
            if (!PawnShop.a(pawnShopItem)) continue;
            linkedList.add(pawnShopItem);
        }
        return linkedList;
    }

    private static synchronized void a(PawnShopItem pawnShopItem) {
        if (a.remove(pawnShopItem)) {
            pawnShopItem.delete();
        }
    }

    private static synchronized void b(PawnShopItem pawnShopItem) {
        if (pawnShopItem.store()) {
            a.add(0, pawnShopItem);
        }
    }

    private static List<PawnShopItem> a(Player player, String string) {
        ArrayList<PawnShopItem> arrayList = new ArrayList<PawnShopItem>();
        boolean bl = true;
        if (!string.isEmpty()) {
            if (string.charAt(0) == '+') {
                String string2 = string.substring(1);
                if (StringUtils.isNumeric((CharSequence)string2)) {
                    try {
                        int n = Integer.parseInt(string2);
                        for (PawnShopItem pawnShopItem : PawnShop.l()) {
                            if (pawnShopItem.getEnchantLevel() != n) continue;
                            arrayList.add(pawnShopItem);
                        }
                        bl = false;
                    }
                    catch (Exception exception) {
                        et.warn("PawnShop: Can't process item enchant level query \"" + string + "\"", (Throwable)exception);
                    }
                } else if (string.length() > 2 && string.charAt(1) == '+') {
                    try {
                        String string3 = string.substring(2);
                        Set<Integer> set = cw.get(string3);
                        if (set != null) {
                            for (PawnShopItem pawnShopItem : PawnShop.l()) {
                                if (!set.contains(pawnShopItem.getItemTypeId())) continue;
                                arrayList.add(pawnShopItem);
                            }
                            bl = false;
                        } else if ("augmented".equals(string3)) {
                            for (PawnShopItem pawnShopItem : PawnShop.l()) {
                                if (!pawnShopItem.isAugmented()) continue;
                                arrayList.add(pawnShopItem);
                            }
                            bl = false;
                        }
                    }
                    catch (Exception exception) {
                        et.warn("PawnShop: Can't process item subgroup query \"" + string + "\"", (Throwable)exception);
                    }
                }
            }
            if (bl && StringUtils.isNumeric((CharSequence)string)) {
                try {
                    int n = Integer.parseInt(string);
                    for (PawnShopItem pawnShopItem : PawnShop.l()) {
                        if (pawnShopItem.getItemTypeId() != n) continue;
                        arrayList.add(pawnShopItem);
                    }
                    bl = false;
                }
                catch (Exception exception) {
                    et.warn("PawnShop: Can't process item id query \"" + string + "\"", (Throwable)exception);
                }
            }
            if (bl) {
                if (string.length() >= Config.PAWNSHOP_MIN_QUERY_LENGTH) {
                    for (PawnShopItem pawnShopItem : PawnShop.l()) {
                        if (!StringUtils.containsIgnoreCase((CharSequence)pawnShopItem.getNameForQuery(), (CharSequence)string)) continue;
                        arrayList.add(pawnShopItem);
                    }
                    bl = false;
                } else {
                    player.sendMessage(new CustomMessage("pawnshop.query_to_short", player, new Object[0]));
                }
            }
            if (!bl && Config.PAWNSHOP_PRICE_SORT) {
                arrayList.sort(g);
            }
        }
        if (bl) {
            arrayList.addAll(PawnShop.l());
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static PawnShopItem a(int n) {
        PawnShopItem pawnShopItem = null;
        for (PawnShopItem pawnShopItem2 : a) {
            if (pawnShopItem2.getId() != n) continue;
            pawnShopItem = pawnShopItem2;
            break;
        }
        return pawnShopItem;
    }

    private static void x(Player player, int n) {
        if (!player.getPlayerAccess().UseTrade || player.isTradeBannedByGM()) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            return;
        }
        if (n <= 0) {
            player.sendPacket((IStaticPacket)SystemMsg.SYSTEM_ERROR);
            return;
        }
        if (player.getWeightPenalty() >= 3 || player.getInventory().getSize() > player.getInventoryLimit() - 10) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
            return;
        }
        PawnShopItem pawnShopItem = PawnShop.a(n);
        if (pawnShopItem == null || !PawnShop.a(pawnShopItem)) {
            return;
        }
        int n2 = pawnShopItem.getCurrencyItemId();
        long l = pawnShopItem.getPrice();
        int n3 = pawnShopItem.getOwnerId();
        if (n3 == player.getObjectId()) {
            player.sendMessage(new CustomMessage("pawnshop.owner_cant_buy_own_item", player, new Object[0]));
            return;
        }
        if (n2 <= 0 || l <= 0L || player.getInventory().getCountOf(n2) < l) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(l)).addItemName(n2)});
            return;
        }
        if (pawnShopItem.getDeleted().compareAndSet(false, true)) {
            try {
                if (n2 <= 0 || l <= 0L || ItemFunctions.removeItem((Playable)player, (int)n2, (long)l, (boolean)true) < l) {
                    pawnShopItem.getDeleted().set(false);
                    player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(l)).addItemName(n2)});
                    return;
                }
            }
            catch (Exception exception) {
                et.error("PawnShop: Can't buy pawnshop item " + n, (Throwable)exception);
                pawnShopItem.getDeleted().set(false);
                return;
            }
            PawnShop.a(pawnShopItem);
            ItemInstance itemInstance = ItemFunctions.createItem((int)pawnShopItem.getItemTypeId());
            itemInstance.setCount((long)pawnShopItem.getAmount());
            itemInstance.setEnchantLevel(pawnShopItem.getEnchantLevel());
            itemInstance.setVariationStat1(pawnShopItem.getVarOpt1());
            itemInstance.setVariationStat2(pawnShopItem.getVarOpt2());
            Log.LogItem((Player)player, (Log.ItemLog)Log.ItemLog.AuctionBuy, (ItemInstance)itemInstance);
            player.getInventory().addItem(itemInstance);
            player.sendPacket((IStaticPacket)SystemMessage.obtainItems((int)itemInstance.getItemId(), (long)itemInstance.getCount(), (int)itemInstance.getEnchantLevel()));
            Player player2 = World.getPlayer((int)n3);
            if (player2 != null && player2.isOnline()) {
                ItemFunctions.addItem((Playable)player2, (int)n2, (long)l, (boolean)true);
                Log.LogItem((Player)player2, (Log.ItemLog)Log.ItemLog.AuctionMoneyReceived, (int)n2, (long)l);
            } else {
                DelayedItemsManager.getInstance().addDelayed(n3, n2, (int)l, 0, 0, 0, "Reward for pawnshop item " + n + " bought by " + player + " item_obj_id=" + itemInstance.getObjectId());
            }
        }
    }

    private static void a(Player player, NpcInstance npcInstance, int n) {
        int n2;
        List<PawnShopItem> list = PawnShop.c(player);
        StringBuilder stringBuilder = new StringBuilder();
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, npcInstance);
        npcHtmlMessage.setFile("mods/pawnshop/refund_list.htm");
        int n3 = n2 + Config.PAWNSHOP_ITEMS_PER_PAGE;
        for (n2 = Config.PAWNSHOP_ITEMS_PER_PAGE * n; n2 < n3 && n2 < list.size(); ++n2) {
            PawnShopItem pawnShopItem = list.get(n2);
            stringBuilder.append(PawnShop.a(player, "pawnshop.refund_item_element", String.format("-h scripts_services.pawnshop.PawnShop:refundItem %d %d", pawnShopItem.getId(), n), pawnShopItem.getItemTemplate(), pawnShopItem.getEnchantLevel(), pawnShopItem.getAmount(), ItemHolder.getInstance().getTemplate(pawnShopItem.getCurrencyItemId()), pawnShopItem.getPrice(), pawnShopItem.getOwnerName(), pawnShopItem.getVariationSkill()));
        }
        npcHtmlMessage.replace("%paging%", PawnShop.a(player, n, list, "refundList"));
        npcHtmlMessage.replace("%list%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private static void y(Player player, int n) {
        if (!player.getPlayerAccess().UseTrade || player.isTradeBannedByGM()) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            return;
        }
        if (n <= 0) {
            player.sendPacket((IStaticPacket)SystemMsg.SYSTEM_ERROR);
            return;
        }
        if (player.getWeightPenalty() >= 3 || player.getInventory().getSize() > player.getInventoryLimit() - 10) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
            return;
        }
        PawnShopItem pawnShopItem = PawnShop.a(n);
        if (pawnShopItem == null || !PawnShop.a(pawnShopItem)) {
            return;
        }
        if (Config.PAWNSHOP_REFUND_ITEM_ID > 0 && Config.PAWNSHOP_REFUND_ITEM_COUNT > 0L && player.getInventory().getCountOf(Config.PAWNSHOP_REFUND_ITEM_ID) < Config.PAWNSHOP_REFUND_ITEM_COUNT) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(Config.PAWNSHOP_REFUND_ITEM_COUNT)).addItemName(Config.PAWNSHOP_REFUND_ITEM_ID)});
            return;
        }
        if (pawnShopItem.getDeleted().compareAndSet(false, true)) {
            try {
                if (Config.PAWNSHOP_REFUND_ITEM_ID > 0 && Config.PAWNSHOP_REFUND_ITEM_COUNT > 0L && ItemFunctions.removeItem((Playable)player, (int)Config.PAWNSHOP_REFUND_ITEM_ID, (long)Config.PAWNSHOP_REFUND_ITEM_COUNT, (boolean)true) < Config.PAWNSHOP_REFUND_ITEM_COUNT) {
                    pawnShopItem.getDeleted().set(false);
                    player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(Config.PAWNSHOP_REFUND_ITEM_COUNT)).addItemName(Config.PAWNSHOP_REFUND_ITEM_ID)});
                    return;
                }
            }
            catch (Exception exception) {
                et.error("PawnShop: Can't refund pawnshop item " + n, (Throwable)exception);
                pawnShopItem.getDeleted().set(false);
                return;
            }
            PawnShop.a(player, pawnShopItem);
        }
    }

    private static void a(Player player, PawnShopItem pawnShopItem) {
        PawnShop.a(pawnShopItem);
        ItemInstance itemInstance = ItemFunctions.createItem((int)pawnShopItem.getItemTypeId());
        itemInstance.setCount((long)pawnShopItem.getAmount());
        itemInstance.setEnchantLevel(pawnShopItem.getEnchantLevel());
        itemInstance.setVariationStat1(pawnShopItem.getVarOpt1());
        itemInstance.setVariationStat2(pawnShopItem.getVarOpt2());
        Log.LogItem((Player)player, (Log.ItemLog)Log.ItemLog.AuctionItemRefundReturn, (ItemInstance)itemInstance);
        player.getInventory().addItem(itemInstance);
        player.sendPacket((IStaticPacket)SystemMessage.obtainItems((int)itemInstance.getItemId(), (long)itemInstance.getCount(), (int)itemInstance.getEnchantLevel()));
    }

    private static List<PawnShopItem> c(Player player) {
        ArrayList<PawnShopItem> arrayList = new ArrayList<PawnShopItem>();
        for (PawnShopItem pawnShopItem : PawnShop.l()) {
            if (pawnShopItem.getOwnerId() != player.getObjectId()) continue;
            arrayList.add(pawnShopItem);
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static void b(Player player, NpcInstance npcInstance, int n) {
        int n2;
        List<ItemInstance> list = PawnShop.d(player);
        StringBuilder stringBuilder = new StringBuilder();
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, npcInstance);
        npcHtmlMessage.setFile("mods/pawnshop/sell_list.htm");
        int n3 = n2 + Config.PAWNSHOP_ITEMS_PER_PAGE;
        for (n2 = Config.PAWNSHOP_ITEMS_PER_PAGE * n; n2 < n3 && n2 < list.size(); ++n2) {
            ItemInstance itemInstance = list.get(n2);
            stringBuilder.append(PawnShop.a(player, "pawnshop.sell_item_element", String.format("-h scripts_services.pawnshop.PawnShop:sellItem %d %d", itemInstance.getObjectId(), n), itemInstance.getTemplate(), itemInstance.getEnchantLevel(), (int)itemInstance.getCount(), PawnShop.b(itemInstance.getVariationStat1(), itemInstance.getVariationStat2())));
        }
        npcHtmlMessage.replace("%paging%", PawnShop.a(player, n, list, "sellList"));
        npcHtmlMessage.replace("%list%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private static void a(Player player, NpcInstance npcInstance, int n, int n2) {
        if (!player.getPlayerAccess().UseTrade) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            return;
        }
        if (n <= 0) {
            player.sendPacket((IStaticPacket)SystemMsg.SYSTEM_ERROR);
            return;
        }
        List<ItemInstance> list = PawnShop.d(player);
        ItemInstance itemInstance = null;
        for (ItemInstance itemInstance2 : list) {
            if (itemInstance2.getObjectId() != n) continue;
            itemInstance = itemInstance2;
        }
        if (itemInstance == null || !PawnShop.a(itemInstance)) {
            player.sendPacket((IStaticPacket)SystemMsg.SYSTEM_ERROR);
            return;
        }
        if (itemInstance.isAugmented() && Config.PAWNSHOP_TAX_AUGMENTED_ITEM_ID > 0 && Config.PAWNSHOP_TAX_AUGMENTED_ITEM_COUNT > 0L && player.getInventory().getCountOf(Config.PAWNSHOP_TAX_AUGMENTED_ITEM_ID) < Config.PAWNSHOP_TAX_AUGMENTED_ITEM_COUNT) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(Config.PAWNSHOP_TAX_AUGMENTED_ITEM_COUNT)).addItemName(Config.PAWNSHOP_TAX_AUGMENTED_ITEM_ID)});
            return;
        }
        if (!itemInstance.isAugmented() && Config.PAWNSHOP_TAX_ITEM_ID > 0 && Config.PAWNSHOP_TAX_ITEM_COUNT > 0L && player.getInventory().getCountOf(Config.PAWNSHOP_TAX_ITEM_ID) < Config.PAWNSHOP_TAX_ITEM_COUNT) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(Config.PAWNSHOP_TAX_ITEM_COUNT)).addItemName(Config.PAWNSHOP_TAX_ITEM_ID)});
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, npcInstance);
        npcHtmlMessage.setFile("mods/pawnshop/sell_item.htm");
        npcHtmlMessage.replace("%back_page%", String.valueOf(n2));
        npcHtmlMessage.replace("%sell_item_name%", itemInstance.getName());
        npcHtmlMessage.replace("%currency_list%", PawnShop.j());
        npcHtmlMessage.replace("%item%", PawnShop.a(player, "pawnshop.sell_item", null, itemInstance.getTemplate(), itemInstance.getEnchantLevel(), (int)itemInstance.getCount(), PawnShop.b(itemInstance.getVariationStat1(), itemInstance.getVariationStat2())));
        npcHtmlMessage.replace("%item_obj_id%", String.valueOf(itemInstance.getObjectId()));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private static void a(Player player, NpcInstance npcInstance, int n, ItemTemplate itemTemplate, int n2) {
        if (!player.getPlayerAccess().UseTrade || player.isTradeBannedByGM()) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            return;
        }
        if (n <= 0 || itemTemplate == null) {
            player.sendPacket((IStaticPacket)SystemMsg.SYSTEM_ERROR);
            return;
        }
        List<ItemInstance> list = PawnShop.d(player);
        ItemInstance itemInstance = null;
        for (ItemInstance itemInstance2 : list) {
            if (itemInstance2.getObjectId() != n) continue;
            itemInstance = itemInstance2;
        }
        if (itemInstance == null || !PawnShop.a(itemInstance) || SafeMath.mulAndCheck((long)n2, (long)itemInstance.getCount()) <= 0L) {
            player.sendPacket((IStaticPacket)SystemMsg.SYSTEM_ERROR);
            return;
        }
        if (itemInstance.isAugmented() && Config.PAWNSHOP_TAX_AUGMENTED_ITEM_ID > 0 && Config.PAWNSHOP_TAX_AUGMENTED_ITEM_COUNT > 0L && ItemFunctions.removeItem((Playable)player, (int)Config.PAWNSHOP_TAX_AUGMENTED_ITEM_ID, (long)Config.PAWNSHOP_TAX_AUGMENTED_ITEM_COUNT, (boolean)true) < Config.PAWNSHOP_TAX_AUGMENTED_ITEM_COUNT) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(Config.PAWNSHOP_TAX_AUGMENTED_ITEM_COUNT)).addItemName(Config.PAWNSHOP_TAX_AUGMENTED_ITEM_ID)});
            return;
        }
        if (!itemInstance.isAugmented() && Config.PAWNSHOP_TAX_ITEM_ID > 0 && Config.PAWNSHOP_TAX_ITEM_COUNT > 0L && ItemFunctions.removeItem((Playable)player, (int)Config.PAWNSHOP_TAX_ITEM_ID, (long)Config.PAWNSHOP_TAX_ITEM_COUNT, (boolean)true) < Config.PAWNSHOP_TAX_ITEM_COUNT) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(Config.PAWNSHOP_TAX_ITEM_COUNT)).addItemName(Config.PAWNSHOP_TAX_ITEM_ID)});
            return;
        }
        if (itemInstance.isAugmented() && Config.PAWNSHOP_TAX_AUGMENTED_ITEM_PERCENT > 0.0) {
            long l = (long)Math.ceil((double)n2 * Config.PAWNSHOP_TAX_AUGMENTED_ITEM_PERCENT / 100.0);
            if (ItemFunctions.removeItem((Playable)player, (int)itemTemplate.getItemId(), (long)l, (boolean)true) < l) {
                player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(l)).addItemName(Config.PAWNSHOP_TAX_AUGMENTED_ITEM_ID)});
                return;
            }
        } else if (!itemInstance.isAugmented() && Config.PAWNSHOP_TAX_ITEM_PERCENT > 0.0) {
            long l = (long)Math.ceil((double)n2 * Config.PAWNSHOP_TAX_ITEM_PERCENT / 100.0);
            if (ItemFunctions.removeItem((Playable)player, (int)itemTemplate.getItemId(), (long)l, (boolean)true) < l) {
                player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(l)).addItemName(Config.PAWNSHOP_TAX_ITEM_ID)});
                return;
            }
        }
        int n3 = (int)itemInstance.getCount();
        PawnShopItem pawnShopItem = new PawnShopItem(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()), u.incrementAndGet(), player.getObjectId(), itemInstance.getItemId(), n3, itemInstance.getEnchantLevel(), itemTemplate.getItemId(), itemInstance.getVariationStat1(), itemInstance.getVariationStat2(), n2);
        Log.LogItem((Player)player, (Log.ItemLog)Log.ItemLog.AuctionPlaceItem, (ItemInstance)itemInstance, (long)n2, (long)n3, (int)itemTemplate.getItemId());
        if (!player.getInventory().destroyItem(itemInstance)) {
            return;
        }
        player.sendPacket((IStaticPacket)SystemMessage.removeItems((int)pawnShopItem.getItemTypeId(), (long)n3));
        PawnShop.b(pawnShopItem);
    }

    private static List<ItemInstance> d(Player player) {
        ItemInstance[] itemInstanceArray;
        ArrayList<ItemInstance> arrayList = new ArrayList<ItemInstance>();
        for (ItemInstance itemInstance : itemInstanceArray = player.getInventory().getItems()) {
            if (!PawnShop.a(itemInstance) || itemInstance.getOwnerId() != player.getObjectId()) continue;
            arrayList.add(itemInstance);
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static String j(String string) {
        return string.replaceAll("[!\"#\\$%&'\\(\\)\\*,\\-\\./:;<=>\\?@\\[\\\\\\]\\^_`{\\|}~]+", "").replaceAll("[^\\w\\d+]+", " ").trim();
    }

    private static boolean a(ItemInstance itemInstance) {
        if (itemInstance == null) {
            return false;
        }
        if (itemInstance.getTemplate().isQuest()) {
            return false;
        }
        if (itemInstance.getLocation() != ItemInstance.ItemLocation.INVENTORY) {
            return false;
        }
        if (itemInstance.isAugmented() && !Config.PAWNSHOP_ALLOW_SELL_AUGMENTED_ITEMS) {
            return false;
        }
        if (itemInstance.isWeapon() && itemInstance.getEnchantLevel() < Config.PAWNSHOP_MIN_ENCHANT_WEAPON_LEVEL) {
            return false;
        }
        if (itemInstance.isArmor() && itemInstance.getEnchantLevel() < Config.PAWNSHOP_MIN_ENCHANT_ARMOR_LEVEL) {
            return false;
        }
        if (itemInstance.isAccessory() && itemInstance.getEnchantLevel() < Config.PAWNSHOP_MIN_ENCHANT_ACCESSORY_LEVEL) {
            return false;
        }
        return PawnShop.a(itemInstance.getTemplate());
    }

    private static boolean a(PawnShopItem pawnShopItem) {
        if (pawnShopItem == null || pawnShopItem.getDeleted().get() || !pawnShopItem.isActive()) {
            return false;
        }
        if (!ArrayUtils.contains((int[])Config.PAWNSHOP_CURRENCY_ITEM_IDS, (int)pawnShopItem.getCurrencyItemId())) {
            return false;
        }
        return PawnShop.a(pawnShopItem.getItemTemplate());
    }

    private static boolean a(ItemTemplate itemTemplate) {
        if (itemTemplate == null) {
            return false;
        }
        if (itemTemplate.isTemporal() || itemTemplate.isShadowItem() || itemTemplate.isQuest()) {
            return false;
        }
        if (!ArrayUtils.contains((Object[])Config.PAWNSHOP_ITEMS_CLASSES, (Object)itemTemplate.getItemClass())) {
            return false;
        }
        if (itemTemplate.getItemGrade().gradeOrd() < CrystalGradeDataHolder.getInstance().getGrade(Config.PAWNSHOP_MIN_GRADE).gradeOrd()) {
            return false;
        }
        return !ArrayUtils.contains((int[])Config.PAWNSHOP_PROHIBITED_ITEM_IDS, (int)itemTemplate.getItemId());
    }

    public static String formatOptSkillText(Skill skill) {
        if (skill == null) {
            return "";
        }
        if (skill.isAoE()) {
            return "AoE " + skill.getName() + " Lv." + skill.getLevel();
        }
        if (skill.isActive()) {
            return "Active " + skill.getName() + " Lv." + skill.getLevel();
        }
        if (!skill.getTriggerList().isEmpty()) {
            return "Chance " + skill.getName() + " Lv." + skill.getLevel();
        }
        if (skill.isPassive()) {
            return "Passive " + skill.getName() + " Lv." + skill.getLevel();
        }
        return skill.getName();
    }

    private static String a(Player player, String string, String string2, ItemTemplate itemTemplate, int n, int n2, Skill skill) {
        String string3 = StringHolder.getInstance().getNotNull(player, string);
        string3 = string3.replace("%bypass%", string2 != null ? string2 : "");
        string3 = string3.replace("%item_id%", String.format("%d", itemTemplate.getItemId()));
        string3 = string3.replace("%item_name%", itemTemplate.getName());
        string3 = string3.replace("%item_add_name%", itemTemplate.getAdditionalName());
        string3 = string3.replace("%item_icon%", itemTemplate.getIcon());
        string3 = string3.replace("%item_enchant%", n > 0 ? String.format("(+%d)", n) : "");
        string3 = player.isLangRus() ? string3.replace("%item_amount%", n2 > 1 ? String.format("\u041a\u043e\u043b-\u0432\u043e: %d", n2) : "") : string3.replace("%item_amount%", n2 > 1 ? String.format("Amount: %d", n2) : "");
        string3 = string3.replace("%option%", PawnShop.formatOptSkillText(skill));
        return string3;
    }

    private static String a(Player player, String string, String string2, ItemTemplate itemTemplate, int n, int n2, ItemTemplate itemTemplate2, long l, String string3, Skill skill) {
        String string4 = StringHolder.getInstance().getNotNull(player, string);
        string4 = string4.replace("%bypass%", string2 != null ? string2 : "");
        string4 = string4.replace("%item_id%", String.valueOf(itemTemplate.getItemId()));
        string4 = string4.replace("%item_name%", itemTemplate.getName());
        string4 = string4.replace("%item_add_name%", itemTemplate.getAdditionalName());
        string4 = string4.replace("%item_icon%", itemTemplate.getIcon());
        string4 = string4.replace("%item_enchant%", n > 0 ? String.format("(+%d)", n) : "");
        string4 = player.isLangRus() ? string4.replace("%item_amount%", n2 > 1 ? String.format("\u041a\u043e\u043b-\u0432\u043e: %d", n2) : "") : string4.replace("%item_amount%", n2 > 1 ? String.format("Amount: %d", n2) : "");
        string4 = string4.replace("%option%", PawnShop.formatOptSkillText(skill));
        if (itemTemplate2 != null) {
            string4 = string4.replace("%currency_id%", String.valueOf(itemTemplate2.getItemId()));
            string4 = string4.replace("%currency_name%", itemTemplate2.getName());
            string4 = string4.replace("%price%", String.valueOf(l));
        } else {
            string4 = string4.replace("%currency_id%", "");
            string4 = string4.replace("%currency_name%", "");
            string4 = string4.replace("%price%", "");
        }
        string4 = string4.replace("%owner%", string3 != null ? string3 : "");
        return string4;
    }

    private static String j() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Config.PAWNSHOP_CURRENCY_ITEM_IDS.length; ++i) {
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(Config.PAWNSHOP_CURRENCY_ITEM_IDS[i]);
            if (itemTemplate == null) continue;
            if (stringBuilder.length() > 0) {
                stringBuilder.append(';');
            }
            stringBuilder.append(itemTemplate.getName());
        }
        return stringBuilder.toString();
    }

    private static ItemTemplate a(String string) {
        string = PawnShop.j(string);
        for (int i = 0; i < Config.PAWNSHOP_CURRENCY_ITEM_IDS.length; ++i) {
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(Config.PAWNSHOP_CURRENCY_ITEM_IDS[i]);
            if (itemTemplate == null || !string.equalsIgnoreCase(PawnShop.j(itemTemplate.getName()))) continue;
            return itemTemplate;
        }
        return null;
    }

    private static boolean e(int n, int n2) {
        return (n2 + 1) * Config.PAWNSHOP_ITEMS_PER_PAGE < n;
    }

    private static <T> String a(Player player, int n, List<T> list, String string) {
        return PawnShop.a(player, n, list, string, null);
    }

    private static <T> String a(Player player, int n, List<T> list, String string, String string2) {
        String string3 = ir + string + (String)(string2 != null ? " %d " + string2 : " %d");
        return PawnShop.a(player, n > 0 ? String.format(string3, n - 1).trim() : null, n, PawnShop.e(list.size(), n) ? String.format(string3, n + 1).trim() : null);
    }

    private static String a(Player player, String string, int n, String string2) {
        String string3 = StringHolder.getInstance().getNotNull(player, "pawnshop.paging");
        string3 = string3.replace("%prev_button%", string != null ? "<button value=\"&$1037;\" action=\"bypass %prev_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">".replace("%prev_bypass%", string) : "");
        string3 = string3.replace("%curr_page%", Integer.toString(n + 1));
        string3 = string3.replace("%next_button%", string2 != null ? "<button value=\"&$1038;\" action=\"bypass %next_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">".replace("%next_bypass%", string2) : "");
        return string3;
    }

    private static Skill b(int n, int n2) {
        Skill skill = null;
        if (n > 0 || n2 > 0) {
            OptionDataTemplate optionDataTemplate = OptionDataHolder.getInstance().getTemplate(n);
            OptionDataTemplate optionDataTemplate2 = OptionDataHolder.getInstance().getTemplate(n2);
            if (optionDataTemplate2 != null && !optionDataTemplate2.getSkills().isEmpty()) {
                skill = (Skill)optionDataTemplate2.getSkills().get(0);
            }
            if (optionDataTemplate != null && !optionDataTemplate.getSkills().isEmpty()) {
                skill = (Skill)optionDataTemplate.getSkills().get(0);
            }
        }
        return skill;
    }

    private static void cq() {
        for (ItemTemplate itemTemplate : ItemHolder.getInstance().getAllTemplates()) {
            String string = null;
            if (itemTemplate == null) continue;
            if (itemTemplate.isWeapon()) {
                string = "weapons";
            } else if (itemTemplate.isAccessory()) {
                string = "accessories";
            } else if (itemTemplate.isArmor()) {
                string = "armors";
            }
            if (StringUtils.isBlank((CharSequence)string)) continue;
            Set<Integer> set = cw.get(string);
            if (set == null) {
                set = new HashSet<Integer>();
                cw.put(string, set);
            }
            set.add(itemTemplate.getItemId());
        }
    }

    public void onLoad() {
        if (Config.PAWNSHOP_ENABLED) {
            PawnShop.cp();
            PawnShop.cq();
            CharacterDAO.getInstance().getCharacterDeleteListenerList().add((Listener)((OnCharacterDeleteListener)n -> this.onCharacterDelete(n)));
            PlayerListenerList.addGlobal((Listener)((OnPlayerEnterListener)player -> this.onPlayerEnter(player)));
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
    }

    public void buyList() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (!Config.PAWNSHOP_ENABLED || !PawnShop.a(player, npcInstance)) {
            return;
        }
        PawnShop.a(player, npcInstance, 0, "");
    }

    public void buyList(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (!Config.PAWNSHOP_ENABLED || !PawnShop.a(player, npcInstance)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int[] nArray = new int[]{0};
        PawnShop.a(stringArray, nArray, stringBuilder);
        PawnShop.a(player, npcInstance, nArray[0], stringBuilder.toString());
    }

    public void buyItem(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (!Config.PAWNSHOP_ENABLED || !PawnShop.a(player, npcInstance)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int[] nArray = new int[]{-1, 0};
        PawnShop.a(stringArray, nArray, stringBuilder);
        PawnShop.x(player, nArray[0]);
        PawnShop.a(player, npcInstance, nArray[1], stringBuilder.toString());
    }

    public void refundList() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (!Config.PAWNSHOP_ENABLED || !PawnShop.a(player, npcInstance)) {
            return;
        }
        PawnShop.a(player, npcInstance, 0);
    }

    public void refundList(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (!Config.PAWNSHOP_ENABLED || !PawnShop.a(player, npcInstance)) {
            return;
        }
        int[] nArray = new int[]{0};
        PawnShop.a(stringArray, nArray);
        PawnShop.a(player, npcInstance, nArray[0]);
    }

    public void refundItem(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (!Config.PAWNSHOP_ENABLED || !PawnShop.a(player, npcInstance)) {
            return;
        }
        int[] nArray = new int[]{-1, 0};
        PawnShop.a(stringArray, nArray);
        PawnShop.y(player, nArray[0]);
        PawnShop.a(player, npcInstance, nArray[1]);
    }

    public void sellList() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (!Config.PAWNSHOP_ENABLED || !PawnShop.a(player, npcInstance)) {
            return;
        }
        PawnShop.b(player, npcInstance, 0);
    }

    public void sellList(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (!Config.PAWNSHOP_ENABLED || !PawnShop.a(player, npcInstance)) {
            return;
        }
        int[] nArray = new int[]{0};
        PawnShop.a(stringArray, nArray);
        PawnShop.b(player, npcInstance, nArray[0]);
    }

    public void sellItem(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (!Config.PAWNSHOP_ENABLED || !PawnShop.a(player, npcInstance)) {
            return;
        }
        int[] nArray = new int[]{-1, 0};
        PawnShop.a(stringArray, nArray);
        PawnShop.a(player, npcInstance, nArray[0], nArray[1]);
    }

    public void sellApply(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (!Config.PAWNSHOP_ENABLED || !PawnShop.a(player, npcInstance)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int[] nArray = new int[]{0, 0, -1};
        PawnShop.a(stringArray, nArray, stringBuilder);
        PawnShop.a(player, npcInstance, nArray[1], PawnShop.a(stringBuilder.toString()), nArray[2]);
        PawnShop.b(player, npcInstance, nArray[0]);
    }

    public void onCharacterDelete(int n) {
        ArrayList<PawnShopItem> arrayList = new ArrayList<PawnShopItem>();
        for (PawnShopItem pawnShopItem : a) {
            if (pawnShopItem == null || pawnShopItem.getOwnerId() != n) continue;
            arrayList.add(pawnShopItem);
        }
        for (PawnShopItem pawnShopItem : arrayList) {
            if (!pawnShopItem.getDeleted().compareAndSet(false, true)) continue;
            PawnShop.a(pawnShopItem);
        }
    }

    public void onPlayerEnter(Player player) {
        if (!Config.PAWNSHOP_ENABLED) {
            return;
        }
        ArrayList<PawnShopItem> arrayList = new ArrayList<PawnShopItem>();
        for (PawnShopItem pawnShopItem : a) {
            if (pawnShopItem.getOwnerId() != player.getObjectId() || pawnShopItem.getDeleted().get() || pawnShopItem.isActive()) continue;
            arrayList.add(pawnShopItem);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        for (PawnShopItem pawnShopItem : arrayList) {
            PawnShop.a(player, pawnShopItem);
        }
    }

    private static class PawnShopItem {
        private final int bGt;
        private final int bGu;
        private final int bGv;
        private final int bGw;
        private final int bGx;
        private final int bGy;
        private final int bGz;
        private final int bGA;
        private final int bGB;
        private final long eB;
        private final AtomicBoolean m;
        private String _ownerName;
        private Skill A;
        private String is;

        private PawnShopItem(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
            this.eB = l;
            this.bGt = n;
            this.bGu = n2;
            this.bGv = n3;
            this.bGw = n4;
            this.bGx = n5;
            this.bGy = n6;
            this.bGz = n9;
            this.bGA = n7;
            this.bGB = n8;
            this.A = PawnShop.b(n7, n8);
            this.m = new AtomicBoolean(false);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public static List<PawnShopItem> loadItems() {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            LinkedList<PawnShopItem> linkedList = new LinkedList<PawnShopItem>();
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                GameServer.getInstance().getDbmsStructureSynchronizer(connection).synchronize(new String[]{"pawnshop"});
                statement = connection.createStatement();
                resultSet = statement.executeQuery("{CALL `lip_ex_PawnShopLoadItems`()}");
                while (resultSet.next()) {
                    PawnShopItem pawnShopItem = new PawnShopItem(resultSet.getLong("createdAt"), resultSet.getInt("id"), resultSet.getInt("ownerId"), resultSet.getInt("itemType"), resultSet.getInt("amount"), resultSet.getInt("enchantLevel"), resultSet.getInt("currency"), resultSet.getInt("varOpt1"), resultSet.getInt("varOpt2"), resultSet.getInt("price"));
                    linkedList.add(0, pawnShopItem);
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)statement, (ResultSet)resultSet);
            }
            catch (SQLException sQLException) {
                et.error("PawnShop: Can't load items", (Throwable)sQLException);
            }
            finally {
                DbUtils.closeQuietly((Connection)connection, statement, resultSet);
            }
            return linkedList;
        }

        public AtomicBoolean getDeleted() {
            return this.m;
        }

        public String getOwnerName() {
            if (this._ownerName == null) {
                Player player = World.getPlayer((int)this.getOwnerId());
                if (player != null) {
                    this._ownerName = player.getName();
                } else {
                    this._ownerName = CharacterDAO.getInstance().getNameByObjectId(this.getOwnerId());
                    if (this._ownerName == null) {
                        this._ownerName = "";
                    }
                }
            }
            return this._ownerName;
        }

        public long getCreatedAt() {
            return this.eB;
        }

        public boolean isActive() {
            return TimeUnit.SECONDS.toDays(Math.max(0L, TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - this.getCreatedAt())) <= (long)Config.PAWNSHOP_ITEM_TERM;
        }

        public int getId() {
            return this.bGt;
        }

        public int getOwnerId() {
            return this.bGu;
        }

        public int getItemTypeId() {
            return this.bGv;
        }

        public int getAmount() {
            return this.bGw;
        }

        public int getEnchantLevel() {
            return this.bGx;
        }

        public int getCurrencyItemId() {
            return this.bGy;
        }

        public int getPrice() {
            return this.bGz;
        }

        public int getVarOpt1() {
            return this.bGA;
        }

        public int getVarOpt2() {
            return this.bGB;
        }

        public Skill getVariationSkill() {
            return this.A;
        }

        public boolean isAugmented() {
            return this.getVarOpt1() > 0 || this.getVarOpt2() > 0;
        }

        public ItemTemplate getItemTemplate() {
            return ItemHolder.getInstance().getTemplate(this.getItemTypeId());
        }

        public String getNameForQuery() {
            if (this.is == null) {
                ItemTemplate itemTemplate = this.getItemTemplate();
                this.is = itemTemplate != null ? PawnShop.j(itemTemplate.getName()) : "";
            }
            return this.is;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean store() {
            boolean bl;
            Connection connection = null;
            CallableStatement callableStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                callableStatement = connection.prepareCall("{CALL `lip_ex_PawnShopStoreItem`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, (int)this.eB);
                callableStatement.setInt(2, this.bGt);
                callableStatement.setInt(3, this.bGu);
                callableStatement.setInt(4, this.bGv);
                callableStatement.setInt(5, this.bGw);
                callableStatement.setInt(6, this.bGx);
                callableStatement.setInt(7, this.bGy);
                callableStatement.setInt(8, this.bGz);
                callableStatement.setInt(9, this.bGA);
                callableStatement.setInt(10, this.bGB);
                callableStatement.execute();
                bl = true;
            }
            catch (SQLException sQLException) {
                try {
                    et.error("", (Throwable)sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly((Connection)connection, callableStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)callableStatement);
                return false;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)callableStatement);
            return bl;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void delete() {
            Connection connection = null;
            CallableStatement callableStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                callableStatement = connection.prepareCall("{CALL `lip_ex_PawnShopDeleteItem`(?)}");
                callableStatement.setInt(1, this.bGt);
                callableStatement.execute();
                this.m.set(true);
            }
            catch (SQLException sQLException) {
                try {
                    et.error("", (Throwable)sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly((Connection)connection, callableStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)callableStatement);
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)callableStatement);
        }
    }
}
