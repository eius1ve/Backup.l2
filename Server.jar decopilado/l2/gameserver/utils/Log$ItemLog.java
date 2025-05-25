/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

public static final class Log.ItemLog
extends Enum<Log.ItemLog> {
    public static final /* enum */ Log.ItemLog Create = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog Delete = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog RemoveItem = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog Drop = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog PvPDrop = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog Crystalize = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog EnchantFail = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog EnchantReset = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog EnchantCrystallize = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog EnchantSuccess = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog Pickup = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog PetPickup = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog PartyPickup = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog PrivateStoreBuy = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog PrivateStoreSell = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog RecipeShopBuy = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog RecipeShopSell = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog CraftCreate = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog CraftDelete = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog TradeBuy = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog CastleManorSeedBuy = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog TradeSell = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog FromPet = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog ToPet = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog AuctionPlaceItem = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog AuctionSell = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog AuctionBuy = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog AuctionMoneyReceived = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog AuctionMoneyReduce = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog AuctionItemRefundReturn = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog AchievementReceived = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog OneDayReward = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog PostRecieve = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog PostSend = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog PostCancel = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog PostExpire = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog PostPrice = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog RefundSell = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog RefundReturn = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog WarehouseDeposit = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog WarehouseWithdraw = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog FreightWithdraw = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog FreightDeposit = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog ClanWarehouseDeposit = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog ClanWarehouseWithdraw = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog ExtractCreate = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog ExtractDelete = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog NpcBuy = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog NpcCreate = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog NpcDelete = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog MultiSellIngredient = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog MultiSellProduct = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog QuestCreate = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog QuestDelete = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog EventCreate = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog EventDelete = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog ChangeAppearance = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog RemoveAppearance = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog AugmentAdded = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog AugmentRemoved = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog HennaAdded = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog HennaRemove = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog QuestDrop = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog QuestReward = new Log.ItemLog();
    public static final /* enum */ Log.ItemLog CapsuledReward = new Log.ItemLog();
    private static final /* synthetic */ Log.ItemLog[] a;

    public static Log.ItemLog[] values() {
        return (Log.ItemLog[])a.clone();
    }

    public static Log.ItemLog valueOf(String string) {
        return Enum.valueOf(Log.ItemLog.class, string);
    }

    private static /* synthetic */ Log.ItemLog[] a() {
        return new Log.ItemLog[]{Create, Delete, RemoveItem, Drop, PvPDrop, Crystalize, EnchantFail, EnchantReset, EnchantCrystallize, EnchantSuccess, Pickup, PetPickup, PartyPickup, PrivateStoreBuy, PrivateStoreSell, RecipeShopBuy, RecipeShopSell, CraftCreate, CraftDelete, TradeBuy, CastleManorSeedBuy, TradeSell, FromPet, ToPet, AuctionPlaceItem, AuctionSell, AuctionBuy, AuctionMoneyReceived, AuctionMoneyReduce, AuctionItemRefundReturn, AchievementReceived, OneDayReward, PostRecieve, PostSend, PostCancel, PostExpire, PostPrice, RefundSell, RefundReturn, WarehouseDeposit, WarehouseWithdraw, FreightWithdraw, FreightDeposit, ClanWarehouseDeposit, ClanWarehouseWithdraw, ExtractCreate, ExtractDelete, NpcBuy, NpcCreate, NpcDelete, MultiSellIngredient, MultiSellProduct, QuestCreate, QuestDelete, EventCreate, EventDelete, ChangeAppearance, RemoveAppearance, AugmentAdded, AugmentRemoved, HennaAdded, HennaRemove, QuestDrop, QuestReward, CapsuledReward};
    }

    static {
        a = Log.ItemLog.a();
    }
}
