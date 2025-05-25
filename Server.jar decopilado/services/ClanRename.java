/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.events.impl.SiegeEvent
 *  l2.gameserver.model.pledge.SubUnit
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.ClanTable
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.Util
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.Util;

public class ClanRename
extends Functions {
    public void rename_clan_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CHANGE_CLAN_NAME_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.getClan() == null || !player.isClanLeader()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_NOT_A_CLAN_LEADER).addName((GameObject)player));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/rename_clan.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_CHANGE_CLAN_NAME_ITEM));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_CHANGE_CLAN_NAME_PRICE));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void rename_clan_community_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CHANGE_CLAN_NAME_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        if (player.getClan() == null || !player.isClanLeader()) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/expand_cwh_clanrestriction.htm", player), (Player)player);
            return;
        }
        if (player.getEvent(SiegeEvent.class) != null) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/rename_clan_err03.htm", player), (Player)player);
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/rename_clan.htm", player);
        string = string.replace("%item_id%", String.valueOf(Config.SERVICES_CHANGE_CLAN_NAME_ITEM));
        string = string.replace("%item_count%", String.valueOf(Config.SERVICES_CHANGE_CLAN_NAME_PRICE));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void rename_clan_community(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null || !ClanRename.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (stringArray == null || stringArray.length < 1) {
            return;
        }
        if (!Config.SERVICES_CHANGE_CLAN_NAME_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/service_disabled.htm", player), (Player)player);
            return;
        }
        if (player.getClan() == null || !player.isClanLeader()) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/expand_cwh_clanrestriction.htm", player), (Player)player);
            return;
        }
        if (ClanTable.getInstance().getClanByName(stringArray[0]) != null) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/rename_clan_err02.htm", player), (Player)player);
            return;
        }
        if (!Util.isMatchingRegexp((String)stringArray[0], (String)Config.CLAN_NAME_TEMPLATE)) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/rename_clan_err01.htm", player), (Player)player);
            return;
        }
        if (player.getEvent(SiegeEvent.class) != null) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/rename_clan_err03.htm", player), (Player)player);
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_CHANGE_CLAN_NAME_ITEM) < (long)Config.SERVICES_CHANGE_CLAN_NAME_PRICE) {
            if (Config.SERVICES_CHANGE_CLAN_NAME_ITEM == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        ItemFunctions.removeItem((Playable)player, (int)Config.SERVICES_CHANGE_CLAN_NAME_ITEM, (long)Config.SERVICES_CHANGE_CLAN_NAME_PRICE, (boolean)true);
        String string = stringArray[0];
        SubUnit subUnit = player.getClan().getSubUnit(0);
        String string2 = subUnit.getName();
        subUnit.setName(string, true);
        player.getClan().broadcastClanStatus(true, true, false);
        ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/rename_clan_msg01.htm", player).replace("%old_name%", string2).replace("%new_name%", string), (Player)player);
        Log.service((String)"ClanReputationSell", (Player)player, (String)("Change clan name - " + string2 + "on new name " + string + " price" + Config.SERVICES_CHANGE_CLAN_NAME_ITEM + " " + Config.SERVICES_CHANGE_CLAN_NAME_PRICE));
    }

    public void rename_clan(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null || !ClanRename.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (stringArray == null || stringArray.length < 1) {
            return;
        }
        if (!Config.SERVICES_CHANGE_CLAN_NAME_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.getClan() == null || !player.isClanLeader()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_NOT_A_CLAN_LEADER).addName((GameObject)player));
            return;
        }
        if (ClanTable.getInstance().getClanByName(stringArray[0]) != null) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/rename_clan_err02.htm"));
            return;
        }
        if (!Util.isMatchingRegexp((String)stringArray[0], (String)Config.CLAN_NAME_TEMPLATE)) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/rename_clan_err01.htm"));
            return;
        }
        if (player.getEvent(SiegeEvent.class) != null) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/rename_clan_err03.htm"));
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_CHANGE_CLAN_NAME_ITEM) < (long)Config.SERVICES_CHANGE_CLAN_NAME_PRICE) {
            if (Config.SERVICES_CHANGE_CLAN_NAME_ITEM == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        ItemFunctions.removeItem((Playable)player, (int)Config.SERVICES_CHANGE_CLAN_NAME_ITEM, (long)Config.SERVICES_CHANGE_CLAN_NAME_PRICE, (boolean)true);
        String string = stringArray[0];
        SubUnit subUnit = player.getClan().getSubUnit(0);
        String string2 = subUnit.getName();
        subUnit.setName(string, true);
        player.getClan().broadcastClanStatus(true, true, false);
        player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/rename_clan_msg01.htm").replace("%old_name%", string2).replace("%new_name%", string));
        Log.service((String)"ClanReputationSell", (Player)player, (String)("Change clan name - " + string2 + "on new name " + string + " price" + Config.SERVICES_CHANGE_CLAN_NAME_ITEM + " " + Config.SERVICES_CHANGE_CLAN_NAME_PRICE));
    }
}
