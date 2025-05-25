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
import l2.gameserver.utils.Log;

public class KarmaClean
extends Functions {
    public void karmaclean_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_KARMA_CLEAN_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.isCursedWeaponEquipped()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_cursed_weapon.htm"));
            return;
        }
        if (player.getKarma() == 0) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_karma_clean.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/karma_clean.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_KARMA_CLEAN_SELL_ITEM));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_KARMA_CLEAN_SELL_PRICE));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void karmaclean() {
        Player player = this.getSelf();
        if (player == null || !KarmaClean.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_KARMA_CLEAN_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.isCursedWeaponEquipped()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_cursed_weapon.htm"));
            return;
        }
        if (player.getKarma() == 0) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_karma_clean.htm"));
            return;
        }
        if (KarmaClean.getItemCount((Playable)player, (int)Config.SERVICES_KARMA_CLEAN_SELL_ITEM) < Config.SERVICES_KARMA_CLEAN_SELL_PRICE) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        KarmaClean.removeItem((Playable)player, (int)Config.SERVICES_KARMA_CLEAN_SELL_ITEM, (long)Config.SERVICES_KARMA_CLEAN_SELL_PRICE);
        player.setKarma(0, true);
        Log.service((String)"KarmaClean", (Player)player, (String)("clean karma for " + Config.SERVICES_KARMA_CLEAN_SELL_ITEM + " amount " + Config.SERVICES_KARMA_CLEAN_SELL_PRICE));
    }

    public void karmaclean_community_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_KARMA_CLEAN_ENABLED) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        if (player.isCursedWeaponEquipped()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/service_cursed_weapon.htm");
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/karma_clean.htm", player);
        string = string.replace("%item_id%", String.valueOf(Config.SERVICES_KARMA_CLEAN_SELL_ITEM));
        string = string.replace("%item_count%", String.valueOf(Config.SERVICES_KARMA_CLEAN_SELL_PRICE));
        string = string.replace("%service_karma_player%", String.valueOf(player.getKarma()));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void karmaclean_buy_cb() {
        Player player = this.getSelf();
        if (player == null || !KarmaClean.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_KARMA_CLEAN_ENABLED) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        if (player.isCursedWeaponEquipped()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/service_cursed_weapon.htm");
            return;
        }
        if (player.getKarma() == 0) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/karma_clean_empty.htm");
            return;
        }
        if (KarmaClean.getItemCount((Playable)player, (int)Config.SERVICES_KARMA_CLEAN_SELL_ITEM) < Config.SERVICES_KARMA_CLEAN_SELL_PRICE) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_incorrect_items.htm");
            return;
        }
        KarmaClean.removeItem((Playable)player, (int)Config.SERVICES_KARMA_CLEAN_SELL_ITEM, (long)Config.SERVICES_KARMA_CLEAN_SELL_PRICE);
        player.setKarma(0, true);
        Log.service((String)"KarmaClean", (Player)player, (String)("clean karma for " + Config.SERVICES_KARMA_CLEAN_SELL_ITEM + " amount " + Config.SERVICES_KARMA_CLEAN_SELL_PRICE));
    }
}
