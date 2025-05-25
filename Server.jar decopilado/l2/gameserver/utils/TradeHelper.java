/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.Zone;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public final class TradeHelper {
    private TradeHelper() {
    }

    public static boolean checksIfCanOpenStore(Player player, int n) {
        String string;
        if (!player.getPlayerAccess().UseTrade) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_____);
            return false;
        }
        if (player.getLevel() < Config.SERVICES_TRADE_MIN_LEVEL) {
            player.sendMessage(new CustomMessage("trade.NotHavePermission", player, new Object[0]).addNumber(Config.SERVICES_TRADE_MIN_LEVEL));
            return false;
        }
        if (!player.getPlayerAccess().UseTrade || player.isTradeBannedByGM()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_CURRENTLY_BLOCKED_FROM_USING_THE_PRIVATE_STORE_AND_PRIVATE_WORKSHOP);
            return false;
        }
        if (player.getPrivateStoreType() != 0 && player.getPrivateStoreType() != n) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_CANCEL_BECAUSE_THE_PRIVATE_SHOP_OR_WORKSHOP_IS_IN_PROGRESS);
            return false;
        }
        String string2 = string = n == 5 ? "open_private_workshop" : "open_private_store";
        if (player.isActionBlocked(string) && (!Config.SERVICES_NO_TRADE_ONLY_OFFLINE || Config.SERVICES_NO_TRADE_ONLY_OFFLINE && player.isInOfflineMode())) {
            player.sendPacket(n == 5 ? new SystemMessage(SystemMsg.YOU_CANNOT_OPEN_A_PRIVATE_WORKSHOP_HERE) : SystemMsg.YOU_CANNOT_OPEN_A_PRIVATE_STORE_HERE);
            return false;
        }
        if (player.isCastingNow()) {
            player.sendPacket((IStaticPacket)SystemMsg.A_PRIVATE_STORE_MAY_NOT_BE_OPENED_WHILE_USING_A_SKILL);
            return false;
        }
        if (player.getTransformation() != 0) {
            player.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_TRADE_HAS_FAILED);
            return false;
        }
        if (player.isInCombat()) {
            player.sendPacket((IStaticPacket)SystemMsg.WHILE_YOU_ARE_ENGAGED_IN_COMBAT_YOU_CANNOT_OPERATE_A_PRIVATE_STORE_OR_PRIVATE_WORKSHOP);
            return false;
        }
        if (player.isMoving() && !Config.ALLOW_TRADE_ON_THE_MOVE) {
            player.sendMessage(new CustomMessage("trade.YouCanOpenStoreOnMove", player, new Object[0]));
            return false;
        }
        if (player.isActionsDisabled() || player.isMounted() || player.isOlyParticipant() || player.isInDuel() || player.isProcessingRequest()) {
            return false;
        }
        if (Config.SERVICES_TRADE_ONLY_FAR) {
            boolean bl = false;
            for (Player player2 : World.getAroundPlayers(player, Config.SERVICES_TRADE_RADIUS, 200)) {
                if (!player2.isInStoreMode()) continue;
                bl = true;
                break;
            }
            if (World.getAroundNpc(player, Config.SERVICES_TRADE_RADIUS + 100, 200).size() > 0) {
                bl = true;
            }
            if (bl) {
                player.sendMessage(new CustomMessage("trade.OtherTradersNear", player, new Object[0]));
                return false;
            }
        }
        return true;
    }

    public static final void purchaseItem(Player player, Player player2, TradeItem tradeItem) {
        long l = tradeItem.getCount() * tradeItem.getOwnersPrice();
        if (!tradeItem.getItem().isStackable()) {
            if (tradeItem.getEnchantLevel() > 0) {
                player2.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.S2S3_HAS_BEEN_SOLD_TO_C1_AT_THE_PRICE_OF_S4_ADENA).addString(player.getName())).addNumber(tradeItem.getEnchantLevel())).addItemName(tradeItem.getItemId())).addNumber(l));
                player.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.S2S3_HAS_BEEN_PURCHASED_FROM_C1_AT_THE_PRICE_OF_S4_ADENA).addString(player2.getName())).addNumber(tradeItem.getEnchantLevel())).addItemName(tradeItem.getItemId())).addNumber(l));
            } else {
                player2.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.S2_IS_SOLD_TO_C1_FOR_THE_PRICE_OF_S3_ADENA).addString(player.getName())).addItemName(tradeItem.getItemId())).addNumber(l));
                player.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.S2_HAS_BEEN_PURCHASED_FROM_C1_AT_THE_PRICE_OF_S3_ADENA).addString(player2.getName())).addItemName(tradeItem.getItemId())).addNumber(l));
            }
        } else {
            player2.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.S2_S3_HAVE_BEEN_SOLD_TO_C1_FOR_S4_ADENA).addString(player.getName())).addItemName(tradeItem.getItemId())).addNumber(tradeItem.getCount())).addNumber(l));
            player.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.S3_S2_HAS_BEEN_PURCHASED_FROM_C1_FOR_S4_ADENA).addString(player2.getName())).addItemName(tradeItem.getItemId())).addNumber(tradeItem.getCount())).addNumber(l));
        }
    }

    public static final long getTax(Player player, long l) {
        long l2 = (long)((double)l * Config.SERVICES_TRADE_TAX / 100.0);
        if (player.isInZone(Zone.ZoneType.offshore)) {
            l2 = (long)((double)l * Config.SERVICES_OFFSHORE_TRADE_TAX / 100.0);
        }
        if (Config.SERVICES_TRADE_TAX_ONLY_OFFLINE && !player.isInOfflineMode()) {
            l2 = 0L;
        }
        if (Config.SERVICES_GIRAN_HARBOR_NOTAX && player.getReflection() == ReflectionManager.GIRAN_HARBOR) {
            l2 = 0L;
        }
        return l2;
    }

    public static void cancelStore(Player player) {
        player.setPrivateStoreType(0);
        if (player.isInOfflineMode()) {
            player.setOfflineMode(false);
            player.kick();
        }
    }
}
