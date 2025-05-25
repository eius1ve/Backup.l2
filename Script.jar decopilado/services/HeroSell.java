/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  org.apache.commons.lang3.StringUtils
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.StringUtils;

public class HeroSell
extends Functions {
    private static boolean makeCustomHero(Player player, long l) {
        if (player.isHero() || l <= 0L) {
            return false;
        }
        player.setCustomHero(true, l, Config.ALT_ALLOW_CUSTOM_HERO_SKILLS);
        player.broadcastPacket(new L2GameServerPacket[]{new SocialAction(player.getObjectId(), 20016)});
        player.broadcastUserInfo(false, new UserInfoType[0]);
        return true;
    }

    public void hero_sell_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_HERO_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isInPeaceZone()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_peace_zone.htm"));
            return;
        }
        assert (Config.SERVICES_HERO_SELLER_ITEM_IDS.length == Config.SERVICES_HERO_SELLER_ITEM_COUNTS.length && Config.SERVICES_HERO_SELLER_ITEM_IDS.length == Config.SERVICE_HERO_STATUS_DURATIONS.length);
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/hero_sell.htm");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Config.SERVICE_HERO_STATUS_DURATIONS.length; ++i) {
            stringBuilder.append(new CustomMessage("services.HeroSell.requestLine", player, new Object[]{String.valueOf(i), String.valueOf(Config.SERVICES_HERO_SELLER_ITEM_IDS[i]), String.valueOf(Config.SERVICES_HERO_SELLER_ITEM_COUNTS[i]), String.valueOf(Config.SERVICE_HERO_STATUS_DURATIONS[i])}).toString());
        }
        npcHtmlMessage.replace("%hero_options_list%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void hero_sell(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null || !HeroSell.CheckPlayerConditions((Player)player) || stringArray == null || stringArray.length == 0 || !StringUtils.isNumeric((CharSequence)stringArray[0])) {
            return;
        }
        int n = Integer.parseInt(stringArray[0]);
        if (n < 0 || n >= Config.SERVICES_HERO_SELLER_ITEM_IDS.length) {
            return;
        }
        if (!Config.SERVICES_HERO_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.isHero()) {
            this.show("You are already Hero.", player);
            return;
        }
        if (!Config.SERVICE_HERO_ALLOW_WITHOUT_NOBLE && !player.isNoble()) {
            this.show("You are not a Nobles, only Nobles can become a Hero.", player);
            return;
        }
        if (!Config.SERVICE_HERO_ALLOW_ON_SUB_CLASS && player.isSubClassActive()) {
            this.show("You can get Hero status only on main class.", player);
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_HERO_SELLER_ITEM_IDS[n]) < Config.SERVICES_HERO_SELLER_ITEM_COUNTS[n]) {
            if (Config.SERVICES_HERO_SELLER_ITEM_IDS[n] == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        if (Functions.removeItem((Playable)player, (int)Config.SERVICES_HERO_SELLER_ITEM_IDS[n], (long)Config.SERVICES_HERO_SELLER_ITEM_COUNTS[n]) < Config.SERVICES_HERO_SELLER_ITEM_COUNTS[n]) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        HeroSell.makeCustomHero(player, Config.SERVICE_HERO_STATUS_DURATIONS[n] * 24L * 60L * 60L);
        Log.service((String)"HeroSell", (Player)player, (String)("bought a custom hero for a period " + Config.SERVICE_HERO_STATUS_DURATIONS[n] * 24L * 60L * 60L + " for " + Config.SERVICES_HERO_SELLER_ITEM_IDS[n] + " amount " + Config.SERVICES_HERO_SELLER_ITEM_COUNTS[n]));
    }

    public void hero_sell_community_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_HERO_SELL_ENABLED) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        if (!player.isInPeaceZone()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_peace_zone.htm");
            return;
        }
        assert (Config.SERVICES_HERO_SELLER_ITEM_IDS.length == Config.SERVICES_HERO_SELLER_ITEM_COUNTS.length && Config.SERVICES_HERO_SELLER_ITEM_IDS.length == Config.SERVICE_HERO_STATUS_DURATIONS.length);
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/hero_sell.htm", player);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Config.SERVICE_HERO_STATUS_DURATIONS.length; ++i) {
            stringBuilder.append(new CustomMessage("services.HeroSell.requestLineCb", player, new Object[]{String.valueOf(i), String.valueOf(Config.SERVICES_HERO_SELLER_ITEM_IDS[i]), String.valueOf(Config.SERVICES_HERO_SELLER_ITEM_COUNTS[i]), String.valueOf(Config.SERVICE_HERO_STATUS_DURATIONS[i])}).toString());
        }
        string = string.replace("%hero_options_list%", stringBuilder.toString());
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void hero_community_sell(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null || !HeroSell.CheckPlayerConditions((Player)player) || stringArray == null || stringArray.length == 0 || !StringUtils.isNumeric((CharSequence)stringArray[0])) {
            return;
        }
        int n = Integer.parseInt(stringArray[0]);
        if (n < 0 || n >= Config.SERVICES_HERO_SELLER_ITEM_IDS.length) {
            player.sendActionFailed();
            return;
        }
        if (!Config.SERVICES_HERO_SELL_ENABLED) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        if (player.isHero()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/hero_sell_already.htm");
            return;
        }
        if (!Config.SERVICE_HERO_ALLOW_WITHOUT_NOBLE && !player.isNoble()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/hero_sell_need_noble.htm");
            return;
        }
        if (!Config.SERVICE_HERO_ALLOW_ON_SUB_CLASS && player.isSubClassActive()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/hero_sell_main_class.htm");
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_HERO_SELLER_ITEM_IDS[n]) < Config.SERVICES_HERO_SELLER_ITEM_COUNTS[n]) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_incorrect_items.htm");
            return;
        }
        if (Functions.removeItem((Playable)player, (int)Config.SERVICES_HERO_SELLER_ITEM_IDS[n], (long)Config.SERVICES_HERO_SELLER_ITEM_COUNTS[n]) < Config.SERVICES_HERO_SELLER_ITEM_COUNTS[n]) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        HeroSell.makeCustomHero(player, Config.SERVICE_HERO_STATUS_DURATIONS[n] * 24L * 60L * 60L);
        ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/hero_sell_congratulations.htm");
        Log.service((String)"HeroSell", (Player)player, (String)("bought a custom hero for a period " + Config.SERVICE_HERO_STATUS_DURATIONS[n] * 24L * 60L * 60L + " for " + Config.SERVICES_HERO_SELLER_ITEM_IDS[n] + " amount " + Config.SERVICES_HERO_SELLER_ITEM_COUNTS[n]));
    }
}
