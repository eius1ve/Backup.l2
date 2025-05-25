/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.CategoryData
 *  l2.gameserver.model.entity.oly.NoblesController
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 */
package handler.items;

import handler.items.SimpleItemHandler;
import l2.gameserver.Config;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.CategoryData;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;

public class NobleObtain
extends SimpleItemHandler {
    @Override
    protected boolean useItemImpl(Player player, ItemInstance itemInstance, boolean bl) {
        int n = itemInstance.getItemId();
        if (player == null || !Functions.CheckPlayerConditions((Player)player)) {
            return false;
        }
        if (!Config.SERVICES_ITEM_NOBLESS_SELL_ENABLED) {
            player.sendMessage(new CustomMessage("services.NoblesItemSell.Disabled", player, new Object[0]));
            return false;
        }
        if (!CategoryData.fourth_class_group.isPlayerBaseClassBelong(player)) {
            player.sendMessage(new CustomMessage("services.NoblesItemSell.Need3Job", player, new Object[0]));
            return false;
        }
        if (!Config.SERVICES_ITEM_NOBLESS_SELL_WITHOUT_SUBCLASS && player.getSubLevel() < Config.NOBLESS_ITEM_LEVEL_FOR_SELL) {
            player.sendMessage(new CustomMessage("services.NoblesItemSell.NeedSubAndLevel", player, new Object[0]).addNumber((long)Config.NOBLESS_LEVEL_FOR_SELL));
            return false;
        }
        if (player.isNoble()) {
            player.sendMessage(new CustomMessage("services.NoblesItemSell.AlreadyHave", player, new Object[0]));
            return false;
        }
        if (player.getLevel() < Config.NOBLESS_ITEM_LEVEL_FOR_SELL) {
            player.sendMessage(new CustomMessage("services.NoblesItemSell.NeedLevel", player, new Object[0]).addNumber((long)Config.NOBLESS_ITEM_LEVEL_FOR_SELL));
            return false;
        }
        for (int i = 0; i < Config.SERVICES_ITEM_NOBLESS_SELL_ITEM_ID.length; ++i) {
            if (n != Config.SERVICES_ITEM_NOBLESS_SELL_ITEM_ID[i] || Functions.removeItem((Playable)player, (int)n, (long)1L) <= 0L) continue;
            player.getInventory().addItem(7694, 1L);
            NoblesController.getInstance().addNoble(player);
            player.setNoble(true);
            player.updatePledgeClass();
            player.updateNobleSkills();
            player.sendSkillList();
            player.broadcastUserInfo(false, new UserInfoType[0]);
            return true;
        }
        return false;
    }

    public int[] getItemIds() {
        return Config.SERVICES_ITEM_NOBLESS_SELL_ITEM_ID;
    }
}
