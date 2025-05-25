/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  org.apache.commons.lang3.StringUtils
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.StringUtils;

public class TitleColor
extends Functions {
    public void list() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CHANGE_TITLE_COLOR_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (StringUtils.isEmpty((CharSequence)player.getTitle())) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_title_empty.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/change_title_color.htm");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Config.SERVICES_CHANGE_TITLE_COLOR_LIST.length; ++i) {
            String string = Config.SERVICES_CHANGE_TITLE_COLOR_LIST[i];
            String string2 = StringHolder.getInstance().getNotNull(player, "services.TitleColor.entryHtml");
            string2 = string2.replaceAll("%color%", string);
            string2 = string2.replaceAll("%fontColor%", string.substring(4, 6) + string.substring(2, 4) + string.substring(0, 2));
            string2 = string2.replaceAll("%itemId%", String.valueOf(Config.SERVICES_CHANGE_TITLE_COLOR_ITEM[i]));
            string2 = string2.replaceAll("%itemCount%", String.valueOf(Config.SERVICES_CHANGE_TITLE_COLOR_PRICE[i]));
            string2 = string2.replaceAll("%playerNameTitle%", player.getTitle());
            stringBuilder.append(string2);
        }
        npcHtmlMessage.replace("%list%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void listCommunityBoard() {
        String string;
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        String string2 = string = !Config.SERVICES_CHANGE_TITLE_COLOR_ENABLED ? HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player) : HtmCache.getInstance().getNotNull("scripts/services/community/services/change_title_color.htm", player);
        if (StringUtils.isEmpty((CharSequence)player.getTitle())) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/change_title_color_empty.htm", player), (Player)player);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Config.SERVICES_CHANGE_TITLE_COLOR_LIST.length; ++i) {
            String string3 = Config.SERVICES_CHANGE_TITLE_COLOR_LIST[i];
            String string4 = StringHolder.getInstance().getNotNull(player, "services.TitleColor.entryHtml");
            string4 = string4.replaceAll("%color%", string3);
            string4 = string4.replaceAll("%fontColor%", string3.substring(4, 6) + string3.substring(2, 4) + string3.substring(0, 2));
            string4 = string4.replaceAll("%itemId%", String.valueOf(Config.SERVICES_CHANGE_TITLE_COLOR_ITEM[i]));
            string4 = string4.replaceAll("%itemCount%", String.valueOf(Config.SERVICES_CHANGE_TITLE_COLOR_PRICE[i]));
            string4 = string4.replaceAll("%playerNameTitle%", player.getTitle());
            stringBuilder.append(string4);
        }
        string = string.replace("%list%", stringBuilder.toString());
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void change(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null || !TitleColor.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (stringArray == null || stringArray.length < 1) {
            return;
        }
        if (!Config.SERVICES_CHANGE_TITLE_COLOR_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (StringUtils.isEmpty((CharSequence)player.getTitle())) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_title_empty.htm"));
            return;
        }
        if (stringArray[0].equalsIgnoreCase("FFFF77")) {
            player.setTitleColor(Integer.decode("0xFFFF77").intValue());
            player.broadcastUserInfo(true, new UserInfoType[]{UserInfoType.COLOR});
            return;
        }
        String string = StringUtils.trimToEmpty((String)stringArray[0]);
        if (string.isEmpty()) {
            return;
        }
        for (int i = 0; i < Config.SERVICES_CHANGE_TITLE_COLOR_LIST.length; ++i) {
            if (!StringUtils.equalsIgnoreCase((CharSequence)Config.SERVICES_CHANGE_TITLE_COLOR_LIST[i], (CharSequence)string)) continue;
            int n = Config.SERVICES_CHANGE_TITLE_COLOR_ITEM[i];
            long l = Config.SERVICES_CHANGE_TITLE_COLOR_PRICE[i];
            if (TitleColor.getItemCount((Playable)player, (int)n) < l) {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                return;
            }
            if (ItemFunctions.removeItem((Playable)player, (int)n, (long)l, (boolean)true) >= l) {
                player.setTitleColor(Integer.decode("0x" + string).intValue());
                player.broadcastUserInfo(true, new UserInfoType[]{UserInfoType.COLOR});
                Log.service((String)"TitleColor", (Player)player, (String)("change title color on " + Integer.decode("0x" + string) + " for " + n + " amount " + l));
            } else if (n == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
    }
}
