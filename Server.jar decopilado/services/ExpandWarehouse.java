/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
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
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;

public class ExpandWarehouse
extends Functions {
    public void get() {
        Player player = this.getSelf();
        if (player == null || !ExpandWarehouse.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_EXPAND_WAREHOUSE_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.getWarehouseLimit() >= Config.SERVICES_EXPAND_WAREHOUSE_MAX) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/expand_warehouse_max.htm"));
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_EXPAND_WAREHOUSE_ITEM) < (long)Config.SERVICES_EXPAND_WAREHOUSE_PRICE) {
            if (Config.SERVICES_EXPAND_WAREHOUSE_ITEM == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        if (ItemFunctions.removeItem((Playable)player, (int)Config.SERVICES_EXPAND_WAREHOUSE_ITEM, (long)Config.SERVICES_EXPAND_WAREHOUSE_PRICE, (boolean)true) >= (long)Config.SERVICES_EXPAND_WAREHOUSE_PRICE) {
            player.setExpandWarehouse(player.getExpandWarehouse() + Config.SERVICES_EXPAND_WAREHOUSE_SLOT_AMOUNT);
            player.setVar("ExpandWarehouse", String.valueOf(player.getExpandWarehouse()), -1L);
            Log.service((String)"ExpandWarehouse", (Player)player, (String)("increased Warehouse " + Config.SERVICES_EXPAND_WAREHOUSE_SLOT_AMOUNT + " slots for " + Config.SERVICES_EXPAND_WAREHOUSE_ITEM + " amount " + Config.SERVICES_EXPAND_WAREHOUSE_PRICE));
        }
        this.show();
    }

    public void show() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_EXPAND_WAREHOUSE_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/expand_warehouse.htm");
        npcHtmlMessage.replace("%wh_limit%", String.valueOf(player.getWarehouseLimit()));
        npcHtmlMessage.replace("%wh_exp_price%", String.valueOf(Config.SERVICES_EXPAND_WAREHOUSE_PRICE));
        npcHtmlMessage.replace("%wh_exp_item%", String.valueOf(Config.SERVICES_EXPAND_WAREHOUSE_ITEM));
        npcHtmlMessage.replace("%wh_exp_slot_amount%", String.valueOf(Config.SERVICES_EXPAND_WAREHOUSE_SLOT_AMOUNT));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void getCommunityBoard() {
        Player player = this.getSelf();
        if (player == null || !ExpandWarehouse.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_EXPAND_WAREHOUSE_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        if (player.getWarehouseLimit() >= Config.SERVICES_EXPAND_WAREHOUSE_MAX) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/expand_warehouse_max.htm", player), (Player)player);
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_EXPAND_WAREHOUSE_ITEM) < (long)Config.SERVICES_EXPAND_WAREHOUSE_PRICE) {
            if (Config.SERVICES_EXPAND_WAREHOUSE_ITEM == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        if (ItemFunctions.removeItem((Playable)player, (int)Config.SERVICES_EXPAND_WAREHOUSE_ITEM, (long)Config.SERVICES_EXPAND_WAREHOUSE_PRICE, (boolean)true) >= (long)Config.SERVICES_EXPAND_WAREHOUSE_PRICE) {
            player.setExpandWarehouse(player.getExpandWarehouse() + Config.SERVICES_EXPAND_WAREHOUSE_SLOT_AMOUNT);
            player.setVar("ExpandWarehouse", String.valueOf(player.getExpandWarehouse()), -1L);
            Log.service((String)"ExpandWarehouse", (Player)player, (String)("increased Warehouse " + Config.SERVICES_EXPAND_WAREHOUSE_SLOT_AMOUNT + " slots for " + Config.SERVICES_EXPAND_WAREHOUSE_ITEM + " amount " + Config.SERVICES_EXPAND_WAREHOUSE_PRICE));
        }
        this.showCommunityBoard();
    }

    public void showCommunityBoard() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_EXPAND_WAREHOUSE_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/expand_warehouse.htm", player);
        string = string.replace("%wh_limit%", String.valueOf(player.getWarehouseLimit()));
        string = string.replace("%wh_exp_price%", String.valueOf(Config.SERVICES_EXPAND_WAREHOUSE_PRICE));
        string = string.replace("%wh_exp_item%", String.valueOf(Config.SERVICES_EXPAND_WAREHOUSE_ITEM));
        string = string.replace("%wh_exp_slot_amount%", String.valueOf(Config.SERVICES_EXPAND_WAREHOUSE_SLOT_AMOUNT));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }
}
