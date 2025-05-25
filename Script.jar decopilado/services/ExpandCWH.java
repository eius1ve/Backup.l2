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
 *  l2.gameserver.utils.ItemFunctions
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
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;

public class ExpandCWH
extends Functions {
    public void get() {
        Player player = this.getSelf();
        if (player == null || !ExpandCWH.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_EXPAND_CWH_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/expand_cwh_clanrestriction.htm"));
            return;
        }
        if (clan.getWhBonus() >= Config.SEVICES_EXPAND_CWH_SLOT_LIMITS) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/expand_cwh_slotlimits.htm"));
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_EXPAND_CWH_ITEM) < (long)Config.SERVICES_EXPAND_CWH_PRICE) {
            if (Config.SERVICES_EXPAND_CWH_ITEM == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        if (ItemFunctions.removeItem((Playable)player, (int)Config.SERVICES_EXPAND_CWH_ITEM, (long)Config.SERVICES_EXPAND_CWH_PRICE, (boolean)true) >= (long)Config.SERVICES_EXPAND_CWH_PRICE) {
            clan.setWhBonus(clan.getWhBonus() + Config.SERVICES_EXPAND_CWH_SLOT_AMOUNT);
            player.sendMessage(new CustomMessage("services.ExpandCWH.CapacityNow", player, new Object[0]).addNumber((long)(Config.WAREHOUSE_SLOTS_CLAN + player.getClan().getWhBonus())));
            clan.updateClanInDB();
            Log.service((String)"ExpandCWH", (Player)player, (String)("increased clan warehouse " + Config.SERVICES_EXPAND_CWH_SLOT_AMOUNT + " slots for " + Config.SERVICES_EXPAND_CWH_ITEM + " amount " + Config.SERVICES_EXPAND_CWH_PRICE));
        }
        this.show();
    }

    public void show() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_EXPAND_CWH_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.getClan() == null) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/expand_cwh_clanrestriction.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/expand_cwh.htm");
        npcHtmlMessage.replace("%cwh_cap_now%", String.valueOf(Config.WAREHOUSE_SLOTS_CLAN + player.getClan().getWhBonus()));
        npcHtmlMessage.replace("%cwh_exp_price%", String.valueOf(Config.SERVICES_EXPAND_CWH_PRICE));
        npcHtmlMessage.replace("%cwh_exp_item%", String.valueOf(Config.SERVICES_EXPAND_CWH_ITEM));
        npcHtmlMessage.replace("%cwh_slot_amount%", String.valueOf(Config.SERVICES_EXPAND_CWH_SLOT_AMOUNT));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void getCommunity() {
        Player player = this.getSelf();
        if (player == null || !ExpandCWH.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_EXPAND_CWH_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        Clan clan = player.getClan();
        if (player.getClan() == null) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/expand_cwh_clanrestriction.htm", player), (Player)player);
            return;
        }
        if (clan.getWhBonus() >= Config.SEVICES_EXPAND_CWH_SLOT_LIMITS) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/expand_cwh_slotlimits.htm", player), (Player)player);
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_EXPAND_CWH_ITEM) < (long)Config.SERVICES_EXPAND_CWH_PRICE) {
            if (Config.SERVICES_EXPAND_CWH_ITEM == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        if (ItemFunctions.removeItem((Playable)player, (int)Config.SERVICES_EXPAND_CWH_ITEM, (long)Config.SERVICES_EXPAND_CWH_PRICE, (boolean)true) >= (long)Config.SERVICES_EXPAND_CWH_PRICE) {
            clan.setWhBonus(clan.getWhBonus() + Config.SERVICES_EXPAND_CWH_SLOT_AMOUNT);
            player.sendMessage(new CustomMessage("services.ExpandCWH.CapacityNow", player, new Object[0]).addNumber((long)(Config.WAREHOUSE_SLOTS_CLAN + player.getClan().getWhBonus())));
            clan.updateClanInDB();
            Log.service((String)"ExpandCWH", (Player)player, (String)("increased clan warehouse " + Config.SERVICES_EXPAND_CWH_SLOT_AMOUNT + " slots for " + Config.SERVICES_EXPAND_CWH_ITEM + " amount " + Config.SERVICES_EXPAND_CWH_PRICE));
        }
        this.showCommunityBoard();
    }

    public void showCommunityBoard() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_EXPAND_CWH_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        if (player.getClan() == null) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/expand_cwh_clanrestriction.htm", player), (Player)player);
            return;
        }
        if (player.getClan().getWhBonus() >= Config.SEVICES_EXPAND_CWH_SLOT_LIMITS) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/expand_cwh_slotlimits.htm", player), (Player)player);
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/expand_cwh.htm", player);
        string = string.replace("%cwh_cap_now%", String.valueOf(Config.WAREHOUSE_SLOTS_CLAN + player.getClan().getWhBonus()));
        string = string.replace("%cwh_exp_price%", String.valueOf(Config.SERVICES_EXPAND_CWH_PRICE));
        string = string.replace("%cwh_exp_item%", String.valueOf(Config.SERVICES_EXPAND_CWH_ITEM));
        string = string.replace("%cwh_slot_amount%", String.valueOf(Config.SERVICES_EXPAND_CWH_SLOT_AMOUNT));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }
}
