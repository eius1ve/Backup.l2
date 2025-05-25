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

public class NickColor
extends Functions {
    public void list() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CHANGE_NICK_COLOR_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/change_nick_color.htm");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Config.SERVICES_CHANGE_NICK_COLOR_LIST.length; ++i) {
            String string = Config.SERVICES_CHANGE_NICK_COLOR_LIST[i];
            String string2 = StringHolder.getInstance().getNotNull(player, "services.NameColor.entryHtml");
            string2 = string2.replaceAll("%color%", string);
            string2 = string2.replaceAll("%fontColor%", string.substring(4, 6) + string.substring(2, 4) + string.substring(0, 2));
            string2 = string2.replaceAll("%itemId%", String.valueOf(Config.SERVICES_CHANGE_NICK_COLOR_ITEM[i]));
            string2 = string2.replaceAll("%itemCount%", String.valueOf(Config.SERVICES_CHANGE_NICK_COLOR_PRICE[i]));
            string2 = string2.replaceAll("%playerName%", player.getName());
            stringBuilder.append(string2);
        }
        npcHtmlMessage.replace("%list%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void listCommunityBoard() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        String string = !Config.SERVICES_CHANGE_NICK_COLOR_ENABLED ? HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player) : HtmCache.getInstance().getNotNull("scripts/services/community/services/change_nick_color.htm", player);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Config.SERVICES_CHANGE_NICK_COLOR_LIST.length; ++i) {
            String string2 = Config.SERVICES_CHANGE_NICK_COLOR_LIST[i];
            String string3 = StringHolder.getInstance().getNotNull(player, "services.NameColor.entryHtml");
            string3 = string3.replaceAll("%color%", string2);
            string3 = string3.replaceAll("%fontColor%", string2.substring(4, 6) + string2.substring(2, 4) + string2.substring(0, 2));
            string3 = string3.replaceAll("%itemId%", String.valueOf(Config.SERVICES_CHANGE_NICK_COLOR_ITEM[i]));
            string3 = string3.replaceAll("%itemCount%", String.valueOf(Config.SERVICES_CHANGE_NICK_COLOR_PRICE[i]));
            string3 = string3.replaceAll("%playerName%", player.getName());
            stringBuilder.append(string3);
        }
        string = string.replace("%list%", stringBuilder.toString());
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void change(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null || !NickColor.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (stringArray == null || stringArray.length < 1) {
            return;
        }
        if (!Config.SERVICES_CHANGE_NICK_COLOR_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (stringArray[0].equalsIgnoreCase("FFFFFF")) {
            player.setNameColor(Integer.decode("0xFFFFFF").intValue());
            player.broadcastUserInfo(true, new UserInfoType[]{UserInfoType.COLOR});
            return;
        }
        String string = StringUtils.trimToEmpty((String)stringArray[0]);
        if (string.isEmpty()) {
            return;
        }
        for (int i = 0; i < Config.SERVICES_CHANGE_NICK_COLOR_LIST.length; ++i) {
            if (!StringUtils.equalsIgnoreCase((CharSequence)Config.SERVICES_CHANGE_NICK_COLOR_LIST[i], (CharSequence)string)) continue;
            int n = Config.SERVICES_CHANGE_NICK_COLOR_ITEM[i];
            long l = Config.SERVICES_CHANGE_NICK_COLOR_PRICE[i];
            if (NickColor.getItemCount((Playable)player, (int)n) < l) {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                return;
            }
            if (ItemFunctions.removeItem((Playable)player, (int)n, (long)l, (boolean)true) < l) continue;
            player.setNameColor(Integer.decode("0x" + stringArray[0]).intValue());
            player.broadcastUserInfo(true, new UserInfoType[]{UserInfoType.COLOR});
            Log.service((String)"NickColor", (Player)player, (String)("change nick color on " + Integer.decode("0x" + stringArray[0]) + " for " + n + " amount " + l));
        }
    }
}
