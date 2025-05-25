/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.Config$RateBonusInfo
 *  l2.gameserver.dao.AccountBonusDAO
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Log
 */
package handler.items;

import handler.items.SimpleItemHandler;
import l2.gameserver.Config;
import l2.gameserver.dao.AccountBonusDAO;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Log;

public class PremiumAccountItem
extends SimpleItemHandler {
    @Override
    protected boolean useItemImpl(Player player, ItemInstance itemInstance, boolean bl) {
        int n = itemInstance.getItemId();
        if (player == null) {
            return false;
        }
        if (!Config.SERVICES_RATE_ENABLED || !Config.ITEM_PREMIUM_ACCOUNT_ENABLE) {
            player.sendMessage("Service Premium Account is Disabled");
            return false;
        }
        if (player.hasBonus()) {
            player.sendMessage(new CustomMessage("promo.box.already.premium", player, new Object[0]));
            return false;
        }
        Config.RateBonusInfo rateBonusInfo = null;
        for (int i = 0; i < Config.ITEM_PREMIUM_ACCOUNT_ITEM_ID.length; ++i) {
            if (n != Config.ITEM_PREMIUM_ACCOUNT_ITEM_ID[i]) continue;
            for (Config.RateBonusInfo rateBonusInfo2 : Config.SERVICES_RATE_BONUS_INFO) {
                if (rateBonusInfo2.id != Config.ITEM_PREMIUM_ACCOUNT_BONUS_ID[i]) continue;
                rateBonusInfo = rateBonusInfo2;
            }
            if (rateBonusInfo == null || Config.ITEM_PREMIUM_ACCOUNT_BONUS_ID[i] < 1) {
                player.sendMessage("Undefined bonus!");
                return false;
            }
            if (Functions.removeItem((Playable)player, (int)n, (long)1L) <= 0L) continue;
            AccountBonusDAO.getInstance().store(player.getAccountName(), rateBonusInfo.makeBonus());
            player.stopBonusTask();
            player.startBonusTask();
            if (player.getParty() != null) {
                player.getParty().recalculatePartyData();
            }
            player.broadcastUserInfo(true, new UserInfoType[0]);
            Functions.show((String)"scripts/services/RateBonusGet.htm", (Player)player, null, (Object[])new Object[0]);
            Log.add((String)("Premium Account Item Bonus added " + player.getName() + "|" + player.getObjectId() + "|rate bonus|" + rateBonusInfo.id + "|" + rateBonusInfo.bonusTimeSeconds + "|"), (String)"services");
            return true;
        }
        return false;
    }

    public int[] getItemIds() {
        return Config.ITEM_PREMIUM_ACCOUNT_ITEM_ID;
    }
}
