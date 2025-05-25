/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Experience
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Log
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Experience;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Log;

public class Levelup
extends Functions {
    public void levelup_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_LEVEL_UP_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/levelup_change.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_LEVEL_UP_SELL_ITEM));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_LEVEL_UP_SELL_PRICE));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void levelup_community_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_LEVEL_UP_SELL_ENABLED) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/levelup_change.htm", player);
        string = string.replace("%item_id%", String.valueOf(Config.SERVICES_LEVEL_UP_SELL_ITEM));
        string = string.replace("%item_count%", String.valueOf(Config.SERVICES_LEVEL_UP_SELL_PRICE));
        string = string.replace("%service_lvl_up_count%", String.valueOf(Config.SERVICES_LEVEL_UP_COUNT));
        string = string.replace("%service_current_level%", String.valueOf(player.getLevel()));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void levelup_buy_cb() {
        Player player = this.getSelf();
        if (player == null || !Levelup.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_LEVEL_UP_SELL_ENABLED) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/service_disabled.htm");
            return;
        }
        if (player.getVarB("NoExp")) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/levelup_exp_freezed.htm");
            return;
        }
        if (player.getLevel() >= (player.isSubClassActive() ? Experience.getMaxSubLevel() : Experience.getMaxLevel()) || player.getExp() > player.getMaxExp()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/level_upchange_max_level.htm");
            return;
        }
        if (player.getLevel() >= (player.isSubClassActive() ? Config.SERVICES_LEVEL_MAX_LEVEL_FOR_SUB_CLASS : Config.SERVICES_LEVEL_MAX_LEVEL_FOR_MAIN_CLASS) || player.getExp() > player.getMaxExp()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/level_upchange_max_level.htm");
            return;
        }
        if (Levelup.getItemCount((Playable)player, (int)Config.SERVICES_LEVEL_UP_SELL_ITEM) < (long)Config.SERVICES_LEVEL_UP_SELL_PRICE) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_incorrect_items.htm");
            return;
        }
        int n = player.getLevel();
        Levelup.removeItem((Playable)player, (int)Config.SERVICES_LEVEL_UP_SELL_ITEM, (long)Config.SERVICES_LEVEL_UP_SELL_PRICE);
        player.addExpAndSp(Experience.LEVEL[Math.min(player.getLevel() + Config.SERVICES_LEVEL_UP_COUNT, Experience.LEVEL.length - 1)] - player.getExp(), 0L, 0L, 0L, false, false);
        Log.service((String)"Levelup", (Player)player, (String)("Levelup on - " + Config.SERVICES_LEVEL_UP_COUNT + " and old level - " + n + " bought for " + Config.SERVICES_LEVEL_UP_SELL_ITEM + " amount " + Config.SERVICES_LEVEL_UP_SELL_PRICE));
        this.levelup_community_page();
    }

    public void levelup() {
        Player player = this.getSelf();
        if (player == null || !Levelup.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_LEVEL_UP_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.getVarB("NoExp")) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/exp_freezed_levelup.htm"));
            return;
        }
        if (player.getLevel() >= (player.isSubClassActive() ? Experience.getMaxSubLevel() : Experience.getMaxLevel()) || player.getExp() > player.getMaxExp()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/level_upchange_max_level.htm"));
            return;
        }
        if (player.getLevel() >= (player.isSubClassActive() ? Config.SERVICES_LEVEL_MAX_LEVEL_FOR_SUB_CLASS : Config.SERVICES_LEVEL_MAX_LEVEL_FOR_MAIN_CLASS) || player.getExp() > player.getMaxExp()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/level_upchange_max_level.htm"));
            return;
        }
        if (Levelup.getItemCount((Playable)player, (int)Config.SERVICES_LEVEL_UP_SELL_ITEM) < (long)Config.SERVICES_LEVEL_UP_SELL_PRICE) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        int n = player.getLevel();
        Levelup.removeItem((Playable)player, (int)Config.SERVICES_LEVEL_UP_SELL_ITEM, (long)Config.SERVICES_LEVEL_UP_SELL_PRICE);
        player.addExpAndSp(Experience.LEVEL[Math.min(player.getLevel() + Config.SERVICES_LEVEL_UP_COUNT, Experience.LEVEL.length - 1)] - player.getExp(), 0L, 0L, 0L, false, false);
        Log.service((String)"Levelup", (Player)player, (String)("Levelup on - " + Config.SERVICES_LEVEL_UP_COUNT + " and old level - " + n + " bought for " + Config.SERVICES_LEVEL_UP_SELL_ITEM + " amount " + Config.SERVICES_LEVEL_UP_SELL_PRICE));
        this.levelup_page();
    }
}
