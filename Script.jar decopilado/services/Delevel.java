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

public class Delevel
extends Functions {
    public void delevel_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_DELEVEL_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/level_change.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_DELEVEL_SELL_ITEM));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_DELEVEL_SELL_PRICE));
        npcHtmlMessage.replace("%level_reduction%", String.valueOf(Config.SERVICES_DELEVEL_REDUCTION));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void delevel_community_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_DELEVEL_SELL_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/level_change.htm", player);
        string = string.replace("%current_level%", String.valueOf(player.getLevel()));
        string = string.replace("%item_id%", String.valueOf(Config.SERVICES_DELEVEL_SELL_ITEM));
        string = string.replace("%item_count%", String.valueOf(Config.SERVICES_DELEVEL_SELL_PRICE));
        string = string.replace("%level_reduction%", String.valueOf(Config.SERVICES_DELEVEL_REDUCTION));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void delevel_community() {
        Player player = this.getSelf();
        if (player == null || !Delevel.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_DELEVEL_SELL_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        if (player.getVarB("NoExp")) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/exp_freezed_delevel.htm", player), (Player)player);
            return;
        }
        if (player.getLevel() < 3 || (long)player.getLevel() > player.getMaxExp()) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/level_upgrade_to_low.htm", player), (Player)player);
            return;
        }
        if (!player.getActiveClass().isBase() && !Config.SERVICES_DELEVEL_ALLOW_FOR_SUBCLASS) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/changebase_err02.htm", player), (Player)player);
            return;
        }
        if (Delevel.getItemCount((Playable)player, (int)Config.SERVICES_DELEVEL_SELL_ITEM) < (long)Config.SERVICES_DELEVEL_SELL_PRICE) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_incorrect_items.htm", player), (Player)player);
            return;
        }
        int n = player.getLevel();
        Delevel.removeItem((Playable)player, (int)Config.SERVICES_DELEVEL_SELL_ITEM, (long)Config.SERVICES_DELEVEL_SELL_PRICE);
        player.addExpAndSp(Experience.LEVEL[player.getLevel() - Config.SERVICES_DELEVEL_REDUCTION] - player.getExp(), 0L, 0L, 0L, false, false);
        Log.service((String)"Delevel", (Player)player, (String)("Reset level on - " + Config.SERVICES_DELEVEL_REDUCTION + " and old level - " + n + " bought for " + Config.SERVICES_DELEVEL_SELL_ITEM + " amount " + Config.SERVICES_DELEVEL_SELL_PRICE));
        this.delevel_community_page();
    }

    public void delevel() {
        Player player = this.getSelf();
        if (player == null || !Delevel.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_DELEVEL_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.getVarB("NoExp")) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/exp_freezed_delevel.htm"));
            return;
        }
        if (player.getLevel() < 3 || (long)player.getLevel() > player.getMaxExp()) {
            return;
        }
        if (!player.getActiveClass().isBase() && !Config.SERVICES_DELEVEL_ALLOW_FOR_SUBCLASS) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/changebase_err02.htm"));
            return;
        }
        if (Delevel.getItemCount((Playable)player, (int)Config.SERVICES_DELEVEL_SELL_ITEM) < (long)Config.SERVICES_DELEVEL_SELL_PRICE) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        int n = player.getLevel();
        Delevel.removeItem((Playable)player, (int)Config.SERVICES_DELEVEL_SELL_ITEM, (long)Config.SERVICES_DELEVEL_SELL_PRICE);
        player.addExpAndSp(Experience.LEVEL[player.getLevel() - Config.SERVICES_DELEVEL_REDUCTION] - player.getExp(), 0L, 0L, 0L, false, false);
        Log.service((String)"Delevel", (Player)player, (String)("Reset level on - " + Config.SERVICES_DELEVEL_REDUCTION + " and old level - " + n + " bought for " + Config.SERVICES_DELEVEL_SELL_ITEM + " amount " + Config.SERVICES_DELEVEL_SELL_PRICE));
    }
}
