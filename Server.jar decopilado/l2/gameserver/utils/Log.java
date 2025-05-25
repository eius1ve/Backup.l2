/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.utils;

import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.templates.item.ItemTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger dH = LoggerFactory.getLogger(Log.class);
    private static final Logger dI = LoggerFactory.getLogger((String)"chat");
    private static final Logger dJ = LoggerFactory.getLogger((String)"gmactions");
    private static final Logger dK = LoggerFactory.getLogger((String)"item");
    private static final Logger dL = LoggerFactory.getLogger((String)"game");
    private static final Logger dM = LoggerFactory.getLogger((String)"debug");
    private static final Logger dN = LoggerFactory.getLogger((String)"network");
    private static final Logger dO = LoggerFactory.getLogger((String)"service");

    public static void add(String string, String string2, Player player) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        if (player != null) {
            stringBuilder.append(' ');
            stringBuilder.append(player);
        }
        stringBuilder.append(' ');
        stringBuilder.append(string);
        dL.info(stringBuilder.toString());
    }

    public static void add(String string, String string2) {
        Log.add(string, string2, null);
    }

    public static void debug(String string) {
        dM.debug(string);
    }

    public static void debug(String string, Throwable throwable) {
        dM.debug(string, throwable);
    }

    public static void network(String string) {
        dN.error(string);
    }

    public static void network(String string, Throwable throwable) {
        dN.error(string, throwable);
    }

    public static void LogChat(String string, String string2, String string3, String string4, int n) {
        if (!Config.LOG_CHAT) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        if (n > 0) {
            stringBuilder.append(' ');
            stringBuilder.append(n);
        }
        stringBuilder.append(' ');
        stringBuilder.append('[');
        stringBuilder.append(string2);
        if (string3 != null) {
            stringBuilder.append(" -> ");
            stringBuilder.append(string3);
        }
        stringBuilder.append(']');
        stringBuilder.append(' ');
        stringBuilder.append(string4);
        dI.info(stringBuilder.toString());
    }

    public static void LogCommand(Player player, GameObject gameObject, String string, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bl) {
            stringBuilder.append("SUCCESS");
        } else {
            stringBuilder.append("FAIL   ");
        }
        stringBuilder.append(' ');
        stringBuilder.append(player);
        if (gameObject != null) {
            stringBuilder.append(" -> ");
            stringBuilder.append(gameObject);
        }
        stringBuilder.append(' ');
        stringBuilder.append(string);
        dJ.info(stringBuilder.toString());
    }

    public static void LogItem(Player player, ItemLog itemLog, ItemInstance itemInstance) {
        Log.a(player, itemLog, itemInstance, itemInstance.getItemId(), itemInstance.getCount(), 0L, 0);
    }

    public static void LogItem(Player player, ItemLog itemLog, ItemInstance itemInstance, long l) {
        Log.a(player, itemLog, itemInstance, itemInstance.getItemId(), l, 0L, 0);
    }

    public static void LogItem(Player player, ItemLog itemLog, ItemInstance itemInstance, long l, long l2) {
        Log.a(player, itemLog, itemInstance, itemInstance.getItemId(), l, l2, 0);
    }

    public static void LogItem(Player player, ItemLog itemLog, ItemInstance itemInstance, long l, long l2, int n) {
        Log.a(player, itemLog, itemInstance, itemInstance.getItemId(), l, l2, n);
    }

    public static void LogItem(Player player, ItemLog itemLog, int n, long l) {
        Log.a(player, itemLog, null, n, l, 0L, 0);
    }

    public static void LogItem(Player player, ItemLog itemLog, int n, long l, long l2) {
        Log.a(player, itemLog, null, n, l, l2, 0);
    }

    public static void LogItem(Player player, ItemLog itemLog, int n, long l, long l2, int n2) {
        Log.a(player, itemLog, null, n, l, l2, n2);
    }

    private static void a(Player player, ItemLog itemLog, ItemInstance itemInstance, int n, long l, long l2, int n2) {
        if (!Config.LOG_ITEM) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((Object)itemLog);
        stringBuilder.append(' ');
        stringBuilder.append(player.getName());
        stringBuilder.append('[').append(player.getObjectId()).append(']').append(' ');
        stringBuilder.append('(').append("IP: ").append(player.getIP()).append(' ').append("Account: ").append(player.getAccountName()).append(')').append(' ');
        stringBuilder.append('(').append("X: ").append(player.getX()).append(' ').append("Y: ").append(player.getY()).append(' ').append("Z: ").append(player.getZ()).append(')');
        stringBuilder.append(' ');
        stringBuilder.append(n);
        stringBuilder.append(' ');
        if (itemInstance != null) {
            if (itemInstance.getEnchantLevel() > 0) {
                stringBuilder.append('+');
                stringBuilder.append(itemInstance.getEnchantLevel());
                stringBuilder.append(' ');
            }
            stringBuilder.append(itemInstance.getTemplate().getName());
            if (!itemInstance.getTemplate().getAdditionalName().isEmpty()) {
                stringBuilder.append(' ');
                stringBuilder.append('<').append(itemInstance.getTemplate().getAdditionalName()).append('>');
            }
            stringBuilder.append(' ');
            if (itemInstance.getAttributes().getValue() > 0) {
                stringBuilder.append('(');
                stringBuilder.append("Fire: ");
                stringBuilder.append(itemInstance.getAttributes().getFire());
                stringBuilder.append(' ');
                stringBuilder.append("Water: ");
                stringBuilder.append(itemInstance.getAttributes().getWater());
                stringBuilder.append(' ');
                stringBuilder.append("Wind: ");
                stringBuilder.append(itemInstance.getAttributes().getWind());
                stringBuilder.append(' ');
                stringBuilder.append("Earth: ");
                stringBuilder.append(itemInstance.getAttributes().getEarth());
                stringBuilder.append(' ');
                stringBuilder.append("Holy: ");
                stringBuilder.append(itemInstance.getAttributes().getHoly());
                stringBuilder.append(' ');
                stringBuilder.append("Unholy: ");
                stringBuilder.append(itemInstance.getAttributes().getUnholy());
                stringBuilder.append(')');
                stringBuilder.append(' ');
            }
            if (itemInstance.isAugmented()) {
                stringBuilder.append('(');
                stringBuilder.append("Aug1: ");
                stringBuilder.append(itemInstance.getVariationStat1());
                stringBuilder.append(' ');
                stringBuilder.append("Aug2: ");
                stringBuilder.append(itemInstance.getVariationStat2());
                stringBuilder.append(')');
                stringBuilder.append(' ');
            }
            stringBuilder.append('(');
            stringBuilder.append(itemInstance.getCount());
            stringBuilder.append(')');
            stringBuilder.append('[');
            stringBuilder.append(itemInstance.getObjectId());
            stringBuilder.append(']');
        } else {
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
            stringBuilder.append(itemTemplate.getName());
            if (!itemTemplate.getAdditionalName().isEmpty()) {
                stringBuilder.append(' ');
                stringBuilder.append('<').append(itemTemplate.getAdditionalName()).append('>');
            }
        }
        stringBuilder.append(' ');
        stringBuilder.append("Count: ").append(l);
        switch (itemLog) {
            case CraftCreate: 
            case CraftDelete: {
                stringBuilder.append(' ');
                stringBuilder.append("Recipe: ").append(n2);
                break;
            }
            case PrivateStoreBuy: 
            case PrivateStoreSell: 
            case RecipeShopBuy: 
            case RecipeShopSell: {
                stringBuilder.append(' ');
                stringBuilder.append("Price: ").append(l2);
                break;
            }
            case MultiSellIngredient: 
            case MultiSellProduct: {
                stringBuilder.append(' ');
                stringBuilder.append("MultiSell: ").append(n2);
                break;
            }
            case NpcBuy: {
                stringBuilder.append(' ');
                stringBuilder.append("BuyList: ").append(n2);
                stringBuilder.append(' ');
                stringBuilder.append("Price: ").append(l2);
                break;
            }
            case NpcCreate: 
            case NpcDelete: {
                stringBuilder.append(' ');
                stringBuilder.append("NPC: ").append(n2);
                break;
            }
            case QuestCreate: 
            case QuestDelete: {
                stringBuilder.append(' ');
                stringBuilder.append("Quest: ").append(n2);
                break;
            }
            case EventCreate: 
            case EventDelete: {
                stringBuilder.append(' ');
                stringBuilder.append("Event: ").append(n2);
                break;
            }
            case AuctionPlaceItem: {
                stringBuilder.append(' ');
                stringBuilder.append("currency: ").append(n2);
                break;
            }
            case ChangeAppearance: {
                stringBuilder.append(' ');
                stringBuilder.append("Changed Appearance to: ").append(n2);
                break;
            }
            case EnchantCrystallize: 
            case EnchantFail: 
            case EnchantReset: 
            case EnchantSuccess: {
                stringBuilder.append(' ');
                stringBuilder.append("Enchant Scroll_Id: ").append(n2);
            }
        }
        dK.info(stringBuilder.toString());
    }

    public static void LogPetition(Player player, Integer n, String string) {
    }

    public static void LogAudit(Player player, String string, String string2) {
    }

    public static void service(String string, Player player, String string2) {
        if (!Config.LOG_SERVICES) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        stringBuilder.append(string);
        stringBuilder.append(']');
        stringBuilder.append(' ');
        stringBuilder.append('[');
        stringBuilder.append(player.getName());
        stringBuilder.append('-');
        stringBuilder.append(player.getObjectId());
        stringBuilder.append(']');
        stringBuilder.append(' ');
        stringBuilder.append(string2);
        dO.info(stringBuilder.toString());
    }

    public static final class ItemLog
    extends Enum<ItemLog> {
        public static final /* enum */ ItemLog Create = new ItemLog();
        public static final /* enum */ ItemLog Delete = new ItemLog();
        public static final /* enum */ ItemLog RemoveItem = new ItemLog();
        public static final /* enum */ ItemLog Drop = new ItemLog();
        public static final /* enum */ ItemLog PvPDrop = new ItemLog();
        public static final /* enum */ ItemLog Crystalize = new ItemLog();
        public static final /* enum */ ItemLog EnchantFail = new ItemLog();
        public static final /* enum */ ItemLog EnchantReset = new ItemLog();
        public static final /* enum */ ItemLog EnchantCrystallize = new ItemLog();
        public static final /* enum */ ItemLog EnchantSuccess = new ItemLog();
        public static final /* enum */ ItemLog Pickup = new ItemLog();
        public static final /* enum */ ItemLog PetPickup = new ItemLog();
        public static final /* enum */ ItemLog PartyPickup = new ItemLog();
        public static final /* enum */ ItemLog PrivateStoreBuy = new ItemLog();
        public static final /* enum */ ItemLog PrivateStoreSell = new ItemLog();
        public static final /* enum */ ItemLog RecipeShopBuy = new ItemLog();
        public static final /* enum */ ItemLog RecipeShopSell = new ItemLog();
        public static final /* enum */ ItemLog CraftCreate = new ItemLog();
        public static final /* enum */ ItemLog CraftDelete = new ItemLog();
        public static final /* enum */ ItemLog TradeBuy = new ItemLog();
        public static final /* enum */ ItemLog CastleManorSeedBuy = new ItemLog();
        public static final /* enum */ ItemLog TradeSell = new ItemLog();
        public static final /* enum */ ItemLog FromPet = new ItemLog();
        public static final /* enum */ ItemLog ToPet = new ItemLog();
        public static final /* enum */ ItemLog AuctionPlaceItem = new ItemLog();
        public static final /* enum */ ItemLog AuctionSell = new ItemLog();
        public static final /* enum */ ItemLog AuctionBuy = new ItemLog();
        public static final /* enum */ ItemLog AuctionMoneyReceived = new ItemLog();
        public static final /* enum */ ItemLog AuctionMoneyReduce = new ItemLog();
        public static final /* enum */ ItemLog AuctionItemRefundReturn = new ItemLog();
        public static final /* enum */ ItemLog AchievementReceived = new ItemLog();
        public static final /* enum */ ItemLog OneDayReward = new ItemLog();
        public static final /* enum */ ItemLog PostRecieve = new ItemLog();
        public static final /* enum */ ItemLog PostSend = new ItemLog();
        public static final /* enum */ ItemLog PostCancel = new ItemLog();
        public static final /* enum */ ItemLog PostExpire = new ItemLog();
        public static final /* enum */ ItemLog PostPrice = new ItemLog();
        public static final /* enum */ ItemLog RefundSell = new ItemLog();
        public static final /* enum */ ItemLog RefundReturn = new ItemLog();
        public static final /* enum */ ItemLog WarehouseDeposit = new ItemLog();
        public static final /* enum */ ItemLog WarehouseWithdraw = new ItemLog();
        public static final /* enum */ ItemLog FreightWithdraw = new ItemLog();
        public static final /* enum */ ItemLog FreightDeposit = new ItemLog();
        public static final /* enum */ ItemLog ClanWarehouseDeposit = new ItemLog();
        public static final /* enum */ ItemLog ClanWarehouseWithdraw = new ItemLog();
        public static final /* enum */ ItemLog ExtractCreate = new ItemLog();
        public static final /* enum */ ItemLog ExtractDelete = new ItemLog();
        public static final /* enum */ ItemLog NpcBuy = new ItemLog();
        public static final /* enum */ ItemLog NpcCreate = new ItemLog();
        public static final /* enum */ ItemLog NpcDelete = new ItemLog();
        public static final /* enum */ ItemLog MultiSellIngredient = new ItemLog();
        public static final /* enum */ ItemLog MultiSellProduct = new ItemLog();
        public static final /* enum */ ItemLog QuestCreate = new ItemLog();
        public static final /* enum */ ItemLog QuestDelete = new ItemLog();
        public static final /* enum */ ItemLog EventCreate = new ItemLog();
        public static final /* enum */ ItemLog EventDelete = new ItemLog();
        public static final /* enum */ ItemLog ChangeAppearance = new ItemLog();
        public static final /* enum */ ItemLog RemoveAppearance = new ItemLog();
        public static final /* enum */ ItemLog AugmentAdded = new ItemLog();
        public static final /* enum */ ItemLog AugmentRemoved = new ItemLog();
        public static final /* enum */ ItemLog HennaAdded = new ItemLog();
        public static final /* enum */ ItemLog HennaRemove = new ItemLog();
        public static final /* enum */ ItemLog QuestDrop = new ItemLog();
        public static final /* enum */ ItemLog QuestReward = new ItemLog();
        public static final /* enum */ ItemLog CapsuledReward = new ItemLog();
        private static final /* synthetic */ ItemLog[] a;

        public static ItemLog[] values() {
            return (ItemLog[])a.clone();
        }

        public static ItemLog valueOf(String string) {
            return Enum.valueOf(ItemLog.class, string);
        }

        private static /* synthetic */ ItemLog[] a() {
            return new ItemLog[]{Create, Delete, RemoveItem, Drop, PvPDrop, Crystalize, EnchantFail, EnchantReset, EnchantCrystallize, EnchantSuccess, Pickup, PetPickup, PartyPickup, PrivateStoreBuy, PrivateStoreSell, RecipeShopBuy, RecipeShopSell, CraftCreate, CraftDelete, TradeBuy, CastleManorSeedBuy, TradeSell, FromPet, ToPet, AuctionPlaceItem, AuctionSell, AuctionBuy, AuctionMoneyReceived, AuctionMoneyReduce, AuctionItemRefundReturn, AchievementReceived, OneDayReward, PostRecieve, PostSend, PostCancel, PostExpire, PostPrice, RefundSell, RefundReturn, WarehouseDeposit, WarehouseWithdraw, FreightWithdraw, FreightDeposit, ClanWarehouseDeposit, ClanWarehouseWithdraw, ExtractCreate, ExtractDelete, NpcBuy, NpcCreate, NpcDelete, MultiSellIngredient, MultiSellProduct, QuestCreate, QuestDelete, EventCreate, EventDelete, ChangeAppearance, RemoveAppearance, AugmentAdded, AugmentRemoved, HennaAdded, HennaRemove, QuestDrop, QuestReward, CapsuledReward};
        }

        static {
            a = ItemLog.a();
        }
    }
}
