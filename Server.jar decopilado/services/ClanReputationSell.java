/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Log
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Log;

public class ClanReputationSell
extends Functions {
    public void clan_reputation_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CLAN_REPUTATION_ENABLE) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isInPeaceZone()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_peace_zone.htm"));
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendMessage(new CustomMessage("services.ClanReputationSell.GetClan", player, new Object[0]));
            return;
        }
        if (clan.getLevel() < Config.MIN_CLAN_LEVEL_FOR_REPUTATION) {
            player.sendMessage(new CustomMessage("services.ClanReputationSell.GetClanLevelFirst", player, new Object[0]));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/clan_reputation_sell.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_CLAN_REPUTATION_ITEM_ID));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_CLAN_REPUTATION_ITEM_COUNT));
        npcHtmlMessage.replace("%reputation_amount%", String.valueOf(Config.SERVICES_CLAN_REPUTATION_AMOUNT));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void clan_reputation_up() {
        Player player = this.getSelf();
        if (player == null || !ClanReputationSell.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_CLAN_REPUTATION_ENABLE) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isInPeaceZone()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_peace_zone.htm"));
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendMessage(new CustomMessage("services.ClanReputationSell.GetClan", player, new Object[0]));
            return;
        }
        if (clan.getLevel() < Config.MIN_CLAN_LEVEL_FOR_REPUTATION) {
            player.sendMessage(new CustomMessage("services.ClanReputationSell.GetClanLevelFirst", player, new Object[0]));
            return;
        }
        if (ClanReputationSell.getItemCount((Playable)player, (int)Config.SERVICES_CLAN_REPUTATION_ITEM_ID) < (long)Config.SERVICES_CLAN_REPUTATION_ITEM_COUNT) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        int n = player.getClan().getReputationScore();
        ClanReputationSell.removeItem((Playable)player, (int)Config.SERVICES_CLAN_REPUTATION_ITEM_ID, (long)Config.SERVICES_CLAN_REPUTATION_ITEM_COUNT);
        clan.incReputation(Config.SERVICES_CLAN_REPUTATION_AMOUNT, true, "ClanReputationServicesAdd");
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_SUCCESSFULLY_COMPLETED_A_CLAN_QUEST_S1_POINTS_HAVE_BEEN_ADDED_TO_YOUR_CLANS_REPUTATION_SCORE).addNumber(Config.SERVICES_CLAN_REPUTATION_AMOUNT));
        Log.service((String)"ClanReputationSell", (Player)player, (String)("buy reputation count - " + Config.SERVICES_CLAN_REPUTATION_AMOUNT + " old reputation - " + n + " price " + Config.SERVICES_CLAN_REPUTATION_ITEM_ID + " " + Config.SERVICES_CLAN_REPUTATION_ITEM_COUNT));
    }

    public void clan_reputation_up_community() {
        Player player = this.getSelf();
        if (player == null || !ClanReputationSell.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_CLAN_REPUTATION_ENABLE) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        if (!player.isInPeaceZone()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_peace_zone.htm");
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/expand_cwh_clanrestriction.htm");
            return;
        }
        if (clan.getLevel() < Config.MIN_CLAN_LEVEL_FOR_REPUTATION) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/clan_restriction_level.htm");
            return;
        }
        if (ClanReputationSell.getItemCount((Playable)player, (int)Config.SERVICES_CLAN_REPUTATION_ITEM_ID) < (long)Config.SERVICES_CLAN_REPUTATION_ITEM_COUNT) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_incorrect_items.htm");
            return;
        }
        int n = player.getClan().getReputationScore();
        ClanReputationSell.removeItem((Playable)player, (int)Config.SERVICES_CLAN_REPUTATION_ITEM_ID, (long)Config.SERVICES_CLAN_REPUTATION_ITEM_COUNT);
        clan.incReputation(Config.SERVICES_CLAN_REPUTATION_AMOUNT, true, "ClanReputationServicesAdd");
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_SUCCESSFULLY_COMPLETED_A_CLAN_QUEST_S1_POINTS_HAVE_BEEN_ADDED_TO_YOUR_CLANS_REPUTATION_SCORE).addNumber(Config.SERVICES_CLAN_REPUTATION_AMOUNT));
        Log.service((String)"ClanReputationSell", (Player)player, (String)("buy reputation count - " + Config.SERVICES_CLAN_REPUTATION_AMOUNT + " old reputation - " + n + " price " + Config.SERVICES_CLAN_REPUTATION_ITEM_ID + " " + Config.SERVICES_CLAN_REPUTATION_ITEM_COUNT));
        this.clan_reputation_community_page();
    }

    public void clan_reputation_community_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CLAN_REPUTATION_ENABLE) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        if (!player.isInPeaceZone()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_peace_zone.htm");
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/expand_cwh_clanrestriction.htm");
            return;
        }
        if (clan.getLevel() < Config.MIN_CLAN_LEVEL_FOR_REPUTATION) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/clan_restriction_level.htm");
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/clan_reputation_sell.htm", player);
        string = string.replace("%item_id%", String.valueOf(Config.SERVICES_CLAN_REPUTATION_ITEM_ID));
        string = string.replace("%item_count%", String.valueOf(Config.SERVICES_CLAN_REPUTATION_ITEM_COUNT));
        string = string.replace("%reputation_amount%", String.valueOf(Config.SERVICES_CLAN_REPUTATION_AMOUNT));
        string = string.replace("%current_clan_reputation%", String.valueOf(clan == null ? 0 : player.getClan().getReputationScore()));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }
}
