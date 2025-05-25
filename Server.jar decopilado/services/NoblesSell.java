/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.CategoryData
 *  l2.gameserver.model.entity.oly.NoblesController
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Log
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.CategoryData;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Log;

public class NoblesSell
extends Functions {
    public void show() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_NOBLESS_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/noble_sell.htm");
        npcHtmlMessage.replace("%noble_level%", String.valueOf(Config.NOBLESS_LEVEL_FOR_SELL));
        npcHtmlMessage.replace("%noble_sell_price%", String.valueOf(Config.SERVICES_NOBLESS_SELL_PRICE));
        npcHtmlMessage.replace("%noble_sell_item_id%", String.valueOf(Config.SERVICES_NOBLESS_SELL_ITEM));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void show_community() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_NOBLESS_SELL_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/noble_sell.htm", player);
        string = string.replace("%noble_level%", String.valueOf(Config.NOBLESS_LEVEL_FOR_SELL));
        string = string.replace("%noble_sell_price%", String.valueOf(Config.SERVICES_NOBLESS_SELL_PRICE));
        string = string.replace("%noble_sell_item_id%", String.valueOf(Config.SERVICES_NOBLESS_SELL_ITEM));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void getCommunity() {
        Player player = this.getSelf();
        if (player == null || !NoblesSell.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_NOBLESS_SELL_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        if (player.isNoble()) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/noble_sell_already.htm", player), (Player)player);
            return;
        }
        if (NoblesSell.getItemCount((Playable)player, (int)Config.SERVICES_NOBLESS_SELL_ITEM) < (long)Config.SERVICES_NOBLESS_SELL_PRICE) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_incorrect_items.htm", player), (Player)player);
            return;
        }
        if (!CategoryData.fourth_class_group.isPlayerBaseClassBelong(player)) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/noble_sell_need_3_job.htm", player), (Player)player);
            return;
        }
        if (!Config.SERVICES_NOBLESS_SELL_WITHOUT_SUBCLASS && player.getSubLevel() < Config.NOBLESS_LEVEL_FOR_SELL) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/noble_sell_need_sub_class.htm", player);
            string = string.replace("%noble_level%", String.valueOf(Config.NOBLESS_LEVEL_FOR_SELL));
            ShowBoard.separateAndSend((String)string, (Player)player);
            return;
        }
        if (player.getLevel() < Config.NOBLESS_LEVEL_FOR_SELL) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/noble_sell_need_level.htm", player);
            string = string.replace("%noble_level%", String.valueOf(Config.NOBLESS_LEVEL_FOR_SELL));
            ShowBoard.separateAndSend((String)string, (Player)player);
            return;
        }
        NoblesSell.removeItem((Playable)player, (int)Config.SERVICES_NOBLESS_SELL_ITEM, (long)Config.SERVICES_NOBLESS_SELL_PRICE);
        NoblesSell.addItem((Playable)player, (int)7694, (long)1L);
        NoblesController.getInstance().addNoble(player);
        player.setNoble(true);
        player.updatePledgeClass();
        player.updateNobleSkills();
        player.sendSkillList();
        player.broadcastUserInfo(false, new UserInfoType[0]);
        Log.service((String)"NoblesSell", (Player)player, (String)("Buy Nobles status bought for " + Config.SERVICES_NOBLESS_SELL_ITEM + " " + Config.SERVICES_NOBLESS_SELL_PRICE));
        ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/noble_sell_congratulations.htm", player), (Player)player);
    }

    public void get() {
        Player player = this.getSelf();
        if (player == null || !NoblesSell.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_NOBLESS_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (NoblesSell.getItemCount((Playable)player, (int)Config.SERVICES_NOBLESS_SELL_ITEM) < (long)Config.SERVICES_NOBLESS_SELL_PRICE) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        if (player.isNoble()) {
            player.sendMessage(new CustomMessage("services.NoblesSell.AlreadyHave", player, new Object[0]));
            return;
        }
        if (!CategoryData.fourth_class_group.isPlayerBaseClassBelong(player)) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/service_noble_sell_need_3_job.htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            return;
        }
        if (!Config.SERVICES_NOBLESS_SELL_WITHOUT_SUBCLASS && player.getSubLevel() < Config.NOBLESS_LEVEL_FOR_SELL) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/service_noble_sell_need_sub_class.htm");
            npcHtmlMessage.replace("%noble_level%", String.valueOf(Config.NOBLESS_LEVEL_FOR_SELL));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            return;
        }
        if (player.getLevel() < Config.NOBLESS_LEVEL_FOR_SELL) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/service_noble_sell_need_level.htm");
            npcHtmlMessage.replace("%noble_level%", String.valueOf(Config.NOBLESS_LEVEL_FOR_SELL));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            return;
        }
        NoblesSell.removeItem((Playable)player, (int)Config.SERVICES_NOBLESS_SELL_ITEM, (long)Config.SERVICES_NOBLESS_SELL_PRICE);
        NoblesSell.addItem((Playable)player, (int)7694, (long)1L);
        NoblesController.getInstance().addNoble(player);
        player.setNoble(true);
        player.updatePledgeClass();
        player.updateNobleSkills();
        player.sendSkillList();
        player.broadcastUserInfo(false, new UserInfoType[0]);
        Log.service((String)"NoblesSell", (Player)player, (String)("Buy Nobles status bought for " + Config.SERVICES_NOBLESS_SELL_ITEM + " " + Config.SERVICES_NOBLESS_SELL_PRICE));
    }
}
