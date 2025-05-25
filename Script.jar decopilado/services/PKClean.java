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
 *  l2.gameserver.network.l2.s2c.UserInfoType
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
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Log;

public class PKClean
extends Functions {
    public void pkclean_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_PK_CLEAN_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.getPkKills() == 0) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_no_pk.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/pk_clean.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_PK_CLEAN_SELL_ITEM));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_PK_CLEAN_SELL_PRICE));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void pkclean() {
        Player player = this.getSelf();
        if (player == null || !PKClean.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_PK_CLEAN_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.getPkKills() == 0) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_no_pk.htm"));
            return;
        }
        if (PKClean.getItemCount((Playable)player, (int)Config.SERVICES_PK_CLEAN_SELL_ITEM) < Config.SERVICES_PK_CLEAN_SELL_PRICE) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        PKClean.removeItem((Playable)player, (int)Config.SERVICES_PK_CLEAN_SELL_ITEM, (long)Config.SERVICES_PK_CLEAN_SELL_PRICE);
        player.setPkKills(0);
        player.sendUserInfo(true, new UserInfoType[]{UserInfoType.SOCIAL});
        Log.service((String)"PKClean", (Player)player, (String)("reduced PK kills to " + player.getPkKills() + " bought for" + Config.SERVICES_PK_CLEAN_SELL_ITEM + " " + Config.SERVICES_PK_CLEAN_SELL_PRICE));
    }

    public void pkclean_community_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/pk_clean.htm", player);
        string = string.replace("%item_id%", String.valueOf(Config.SERVICES_PK_CLEAN_SELL_ITEM));
        string = string.replace("%item_count%", String.valueOf(Config.SERVICES_PK_CLEAN_SELL_PRICE));
        string = string.replace("%service_current_karma%", String.valueOf(player.getPkKills()));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void pkclean_buy_cb() {
        Player player = this.getSelf();
        if (player == null || !PKClean.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_PK_CLEAN_ENABLED) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        if (player.getPkKills() == 0) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/pk_clean_no_pk.htm");
            return;
        }
        if (PKClean.getItemCount((Playable)player, (int)Config.SERVICES_PK_CLEAN_SELL_ITEM) < Config.SERVICES_PK_CLEAN_SELL_PRICE) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_incorrect_items.htm");
            return;
        }
        PKClean.removeItem((Playable)player, (int)Config.SERVICES_PK_CLEAN_SELL_ITEM, (long)Config.SERVICES_PK_CLEAN_SELL_PRICE);
        player.setPkKills(0);
        player.sendUserInfo(true, new UserInfoType[]{UserInfoType.SOCIAL});
        Log.service((String)"PKClean", (Player)player, (String)("reduced PK kills to " + player.getPkKills() + " bought for" + Config.SERVICES_PK_CLEAN_SELL_ITEM + " " + Config.SERVICES_PK_CLEAN_SELL_PRICE));
        ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/pk_clean_gratz.htm");
    }
}
