/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.oly.NoblesController
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Log
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Log;

public class OlympiadPointsReset
extends Functions
implements ScriptFile {
    public void olympiad_point_sell_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_OLY_POINTS_RESET) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isInPeaceZone()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_peace_zone.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/reset_olympiad_point_sell.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_OLY_POINTS_RESET_ITEM_ID));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_OLY_POINTS_RESET_ITEM_AMOUNT));
        npcHtmlMessage.replace("%reset_point_count%", String.valueOf(Config.SERVICES_OLY_POINTS_REWARD));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void olympiad_point_sell() {
        Player player = this.getSelf();
        if (player == null || !OlympiadPointsReset.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_OLY_POINTS_RESET) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isNoble()) {
            this.show("You are mpt a noble", player);
            return;
        }
        if (player.isSubClassActive()) {
            this.show("You can reset points only on main class.", player);
            return;
        }
        if (NoblesController.getInstance().getPointsOf(player.getObjectId()) >= Config.SERVICES_OLY_POINTS_THRESHOLD) {
            this.show("Your olympiad noble points count is to high.", player);
            return;
        }
        if (Functions.removeItem((Playable)player, (int)Config.SERVICES_OLY_POINTS_RESET_ITEM_ID, (long)Config.SERVICES_OLY_POINTS_RESET_ITEM_AMOUNT) < (long)Config.SERVICES_OLY_POINTS_RESET_ITEM_AMOUNT) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        NoblesController.getInstance().setPointsOf(player.getObjectId(), Config.SERVICES_OLY_POINTS_REWARD);
        player.sendMessage(new CustomMessage("services.OlympiadPointsReset.PointsAdd", player, new Object[0]));
        Log.service((String)"OlympiadPointsReset", (Player)player, (String)("Olympiad point reset to amount " + Config.SERVICES_OLY_POINTS_REWARD + " for " + Config.SERVICES_NOBLESS_SELL_ITEM + " " + Config.SERVICES_NOBLESS_SELL_PRICE));
    }

    public void olympiad_point_sell_community_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_OLY_POINTS_RESET) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        if (!player.isInPeaceZone()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_peace_zone.htm");
            return;
        }
        if (!player.isNoble()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/reset_olympiad_point_need_noble.htm");
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/reset_olympiad_point_sell.htm", player);
        string = string.replace("%item_id%", String.valueOf(Config.SERVICES_OLY_POINTS_RESET_ITEM_ID));
        string = string.replace("%item_count%", String.valueOf(Config.SERVICES_OLY_POINTS_RESET_ITEM_AMOUNT));
        string = string.replace("%reset_point_count%", String.valueOf(Config.SERVICES_OLY_POINTS_REWARD));
        string = string.replace("%current_oly_pts%", String.valueOf(NoblesController.getInstance().getPointsOf(player.getObjectId())));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void olympiad_point_community_sell() {
        Player player = this.getSelf();
        if (player == null || !OlympiadPointsReset.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_OLY_POINTS_RESET) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        if (!player.isNoble()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/reset_olympiad_point_need_noble.htm");
            return;
        }
        if (player.isSubClassActive()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/reset_olympiad_point_main_class.htm");
            return;
        }
        if (NoblesController.getInstance().getPointsOf(player.getObjectId()) >= Config.SERVICES_OLY_POINTS_THRESHOLD) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/reset_olympiad_point_threshold.htm", player);
            string = string.replace("%oly_pts_threshold%", String.valueOf(Config.SERVICES_OLY_POINTS_THRESHOLD));
            string = string.replace("%current_oly_pts%", String.valueOf(NoblesController.getInstance().getPointsOf(player.getObjectId())));
            ShowBoard.separateAndSend((String)string, (Player)player);
            return;
        }
        if (OlympiadPointsReset.getItemCount((Playable)player, (int)Config.SERVICES_OLY_POINTS_RESET_ITEM_ID) < (long)Config.SERVICES_OLY_POINTS_RESET_ITEM_AMOUNT) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_incorrect_items.htm", player), (Player)player);
            return;
        }
        OlympiadPointsReset.removeItem((Playable)player, (int)Config.SERVICES_OLY_POINTS_RESET_ITEM_ID, (long)Config.SERVICES_OLY_POINTS_RESET_ITEM_AMOUNT);
        NoblesController.getInstance().setPointsOf(player.getObjectId(), Config.SERVICES_OLY_POINTS_REWARD);
        player.sendMessage(new CustomMessage("services.OlympiadPointsReset.PointsAdd", player, new Object[0]));
        Log.service((String)"OlympiadPointsReset", (Player)player, (String)("Olympiad point reset to amount " + Config.SERVICES_OLY_POINTS_REWARD + " for " + Config.SERVICES_NOBLESS_SELL_ITEM + " " + Config.SERVICES_NOBLESS_SELL_PRICE));
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
