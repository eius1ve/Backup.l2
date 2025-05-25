/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Log
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Log;

public class CharRecommendationService
extends Functions {
    public void char_recommendation_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CHAR_RECOMMENDATION_ENABLE) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isInPeaceZone()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_peace_zone.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/char_recommendation_sell.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_CHAR_RECOMMENDATION_ITEM_ID));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_CHAR_RECOMMENDATION_ITEM_COUNT));
        npcHtmlMessage.replace("%recommendation_amount%", String.valueOf(Config.SERVICES_CHAR_RECOMMENDATION_AMOUNT));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void char_reputation_up() {
        Player player = this.getSelf();
        if (player == null || !CharRecommendationService.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_CHAR_RECOMMENDATION_ENABLE) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isInPeaceZone()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_peace_zone.htm"));
            return;
        }
        if (player.getReceivedRec() >= 255) {
            player.sendMessage(new CustomMessage("services.CharRecommendationService.RecLimit", player, new Object[0]));
            return;
        }
        if (CharRecommendationService.getItemCount((Playable)player, (int)Config.SERVICES_CHAR_RECOMMENDATION_ITEM_ID) < (long)Config.SERVICES_CHAR_RECOMMENDATION_ITEM_COUNT) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        CharRecommendationService.removeItem((Playable)player, (int)Config.SERVICES_CHAR_RECOMMENDATION_ITEM_ID, (long)Config.SERVICES_CHAR_RECOMMENDATION_ITEM_COUNT);
        player.setReceivedRec(Math.min(255, player.getReceivedRec() + Config.SERVICES_CHAR_RECOMMENDATION_AMOUNT));
        player.broadcastUserInfo(false, new UserInfoType[0]);
        player.sendMessage(new CustomMessage("services.CharRecommendationService.RecAdded", player, new Object[0]).addNumber((long)Config.SERVICES_CHAR_RECOMMENDATION_AMOUNT));
        Log.service((String)"CharRecommendationService", (Player)player, (String)("buy recommend count - " + Config.SERVICES_CHAR_RECOMMENDATION_AMOUNT + " price " + Config.SERVICES_CHAR_RECOMMENDATION_ITEM_ID + " " + Config.SERVICES_CHAR_RECOMMENDATION_ITEM_COUNT));
    }
}
