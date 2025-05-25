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
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Log;

public class ClanUpgrade
extends Functions {
    public void clan_upgrade_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CLANLEVEL_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isInPeaceZone()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_peace_zone.htm"));
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_A_CLAN_MEMBER_AND_CANNOT_PERFORM_THIS_ACTION);
            return;
        }
        if (clan.getLevel() < 1 || clan.getLevel() >= Config.SERVICES_CLAN_MAX_SELL_LEVEL) {
            player.sendMessage(new CustomMessage("services.ClanUpgrade.ToHighOrToLow", player, new Object[0]));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/clan_upgrade.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_CLANLEVEL_SELL_ITEM[clan.getLevel() - 1]));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_CLANLEVEL_SELL_PRICE[clan.getLevel() - 1]));
        npcHtmlMessage.replace("%clan_level_next%", String.valueOf(clan.getLevel() + 1));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void clan_upgrade() {
        Player player = this.getSelf();
        if (player == null || !ClanUpgrade.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_CLANLEVEL_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isInPeaceZone()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_peace_zone.htm"));
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendMessage(new CustomMessage("services.ClanUpgrade.GetClan", player, new Object[0]));
            return;
        }
        if (clan.getLevel() < 1 || clan.getLevel() >= Config.SERVICES_CLAN_MAX_SELL_LEVEL) {
            player.sendMessage(new CustomMessage("services.ClanUpgrade.ToHighOrToLow", player, new Object[0]));
            return;
        }
        int n = clan.getLevel() + 1;
        int n2 = Config.SERVICES_CLANLEVEL_SELL_ITEM[Math.min(Math.max(0, n - 2), Config.SERVICES_CLANLEVEL_SELL_ITEM.length - 1)];
        long l = Config.SERVICES_CLANLEVEL_SELL_PRICE[Math.min(Math.max(0, n - 2), Config.SERVICES_CLANLEVEL_SELL_PRICE.length - 1)];
        if (Functions.getItemCount((Playable)player, (int)n2) < l) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        Functions.removeItem((Playable)player, (int)n2, (long)l);
        clan.setLevel(clan.getLevel() + 1);
        clan.updateClanInDB();
        clan.broadcastClanStatus(true, true, true);
        player.sendMessage(new CustomMessage("services.ClanUpgrade.Congratulation", player, new Object[0]));
        Log.service((String)"ClanUpgrade", (Player)player, (String)("received a clan level  for Clan " + player.getClan().getName() + " bought for " + n2 + " amount " + l));
        if (clan.getLevel() >= Config.MIN_CLAN_LEVEL_FOR_SIEGE_REGISTRATION) {
            Clan.addClanLeaderSkills((Player)player);
            player.sendSkillList();
            player.sendEtcStatusUpdate();
            player.updateStats();
        }
    }

    public void clan_upgrade_community_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CLANLEVEL_SELL_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        if (!player.isInPeaceZone()) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/service_peace_zone.htm", player), (Player)player);
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/expand_cwh_clanrestriction.htm", player), (Player)player);
            return;
        }
        if (clan.getLevel() < 1 || clan.getLevel() >= Config.SERVICES_CLAN_MAX_SELL_LEVEL) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/clan_upgrade_err1.htm", player), (Player)player);
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/clan_upgrade.htm", player);
        string = string.replace("%item_id%", String.valueOf(Config.SERVICES_CLANLEVEL_SELL_ITEM[clan.getLevel() - 1]));
        string = string.replace("%item_count%", String.valueOf(Config.SERVICES_CLANLEVEL_SELL_PRICE[clan.getLevel() - 1]));
        string = string.replace("%clan_level_next%", String.valueOf(clan.getLevel() + 1));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void clan_upgrade_community() {
        Player player = this.getSelf();
        if (player == null || !ClanUpgrade.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_CLANLEVEL_SELL_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        if (!player.isInPeaceZone()) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/service_peace_zone.htm", player), (Player)player);
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/expand_cwh_clanrestriction.htm", player), (Player)player);
            return;
        }
        if (clan.getLevel() < 1 || clan.getLevel() >= Config.SERVICES_CLAN_MAX_SELL_LEVEL) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/clan_upgrade_err1.htm", player), (Player)player);
            return;
        }
        int n = clan.getLevel() + 1;
        int n2 = Config.SERVICES_CLANLEVEL_SELL_ITEM[Math.min(Math.max(0, n - 2), Config.SERVICES_CLANLEVEL_SELL_ITEM.length - 1)];
        long l = Config.SERVICES_CLANLEVEL_SELL_PRICE[Math.min(Math.max(0, n - 2), Config.SERVICES_CLANLEVEL_SELL_PRICE.length - 1)];
        if (Functions.getItemCount((Playable)player, (int)n2) < l) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_incorrect_items.htm", player), (Player)player);
            return;
        }
        Functions.removeItem((Playable)player, (int)n2, (long)l);
        clan.setLevel(clan.getLevel() + 1);
        clan.updateClanInDB();
        clan.broadcastClanStatus(true, true, true);
        player.sendMessage(new CustomMessage("services.ClanUpgrade.Congratulation", player, new Object[0]));
        Log.service((String)"ClanUpgrade", (Player)player, (String)("received a clan level  for Clan " + player.getClan().getName() + " bought for " + n2 + " amount " + l));
        if (clan.getLevel() >= Config.MIN_CLAN_LEVEL_FOR_SIEGE_REGISTRATION) {
            Clan.addClanLeaderSkills((Player)player);
            player.sendSkillList();
            player.sendEtcStatusUpdate();
            player.updateStats();
        }
        this.clan_upgrade_community_page();
    }
}
