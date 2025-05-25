/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.database.mysql
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.SubClass
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.HtmlUtils
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 */
package services;

import java.util.Map;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.database.mysql;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.SubClass;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.HtmlUtils;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;

public class SubClassSeparate
extends Functions {
    public void separate_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_SEPARATE_SUB_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (player.getSubClasses().size() == 1) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate_err01.htm"));
            return;
        }
        if (!player.getActiveClass().isBase()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate_err03.htm"));
            return;
        }
        if (player.getActiveClass().getLevel() < 75) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate_err04.htm"));
            return;
        }
        if (player.isHero()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate_err05.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_SEPARATE_SUB_ITEM));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_SEPARATE_SUB_PRICE));
        npcHtmlMessage.replace("%min_level%", String.valueOf(Config.SERVICES_SEPARATE_SUB_MIN_LEVEL));
        String string = HtmCache.getInstance().getNotNull("scripts/services/subclass_separate_list.htm", player);
        StringBuilder stringBuilder = new StringBuilder();
        for (SubClass subClass : player.getSubClasses().values()) {
            if (subClass.isBase()) continue;
            stringBuilder.append(string.replace("%class_id%", String.valueOf(subClass.getClassId())).replace("%class_name%", HtmlUtils.htmlClassName((int)subClass.getClassId(), (Player)player)));
        }
        npcHtmlMessage.replace("%list%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void separate_community_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_SEPARATE_SUB_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        if (player.getSubClasses().size() == 1) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/subclass_separate_err01.htm", player), (Player)player);
            return;
        }
        if (!player.getActiveClass().isBase()) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/subclass_separate_err03.htm", player), (Player)player);
            return;
        }
        if (player.getActiveClass().getLevel() < 75) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/subclass_separate_err04.htm", player), (Player)player);
            return;
        }
        if (player.isHero()) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/subclass_separate_err05.htm", player), (Player)player);
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/subclass_separate.htm", player);
        string = string.replace("%item_id%", String.valueOf(Config.SERVICES_SEPARATE_SUB_ITEM));
        string = string.replace("%item_count%", String.valueOf(Config.SERVICES_SEPARATE_SUB_PRICE));
        string = string.replace("%min_level%", String.valueOf(Config.SERVICES_SEPARATE_SUB_MIN_LEVEL));
        String string2 = HtmCache.getInstance().getNotNull("scripts/services/community/services/subclass_separate_list.htm", player);
        StringBuilder stringBuilder = new StringBuilder();
        for (SubClass subClass : player.getSubClasses().values()) {
            if (subClass.isBase()) continue;
            stringBuilder.append(string2.replace("%class_id%", String.valueOf(subClass.getClassId())).replace("%class_name%", HtmlUtils.htmlClassName((int)subClass.getClassId(), (Player)player)));
        }
        string = string.replace("%list%", stringBuilder.toString());
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void separate(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null || !SubClassSeparate.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_SEPARATE_SUB_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (stringArray == null || stringArray.length < 2) {
            return;
        }
        if (player.getSubClasses().size() == 1) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate_err01.htm"));
            return;
        }
        if (!player.getActiveClass().isBase()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate_err03.htm"));
            return;
        }
        if (player.getActiveClass().getLevel() < 75) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate_err04.htm"));
            return;
        }
        if (player.isHero()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate_err05.htm"));
            return;
        }
        int n = Integer.parseInt(stringArray[0]);
        int n2 = 0;
        for (Map.Entry entry : player.getAccountChars().entrySet()) {
            if (!((String)entry.getValue()).equalsIgnoreCase(stringArray[1])) continue;
            n2 = (Integer)entry.getKey();
        }
        if (n2 == 0) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate_err06.htm"));
            return;
        }
        if (mysql.simple_get_int((String)"level", (String)"character_subclasses", (String)("char_obj_id=" + n2 + " AND level > " + Config.SERVICES_SEPARATE_SUB_MIN_LEVEL)) > 1) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate_err07.htm"));
            return;
        }
        if (!player.isInPeaceZone() || !player.getReflection().isDefault()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/subclass_separate_err08.htm"));
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_SEPARATE_SUB_ITEM) < (long)Config.SERVICES_SEPARATE_SUB_PRICE) {
            if (Config.SERVICES_SEPARATE_SUB_ITEM == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        ItemFunctions.removeItem((Playable)player, (int)Config.SERVICES_SEPARATE_SUB_ITEM, (long)Config.SERVICES_SEPARATE_SUB_PRICE, (boolean)true);
        mysql.set((String)("DELETE FROM character_subclasses WHERE char_obj_id=" + n2));
        mysql.set((String)("DELETE FROM character_skills WHERE char_obj_id=" + n2));
        mysql.set((String)("DELETE FROM character_skills_save WHERE char_obj_id=" + n2));
        mysql.set((String)("DELETE FROM character_effects_save WHERE object_id=" + n2));
        mysql.set((String)("DELETE FROM character_hennas WHERE char_obj_id=" + n2));
        mysql.set((String)("DELETE FROM character_shortcuts WHERE object_id=" + n2));
        mysql.set((String)("DELETE FROM character_variables WHERE obj_id=" + n2));
        mysql.set((String)("UPDATE character_subclasses SET char_obj_id=" + n2 + ", isBase=1 WHERE char_obj_id=" + player.getObjectId() + " AND class_id=" + n));
        mysql.set((String)("UPDATE character_skills SET char_obj_id=" + n2 + " WHERE char_obj_id=" + player.getObjectId() + " AND class_index=" + n));
        mysql.set((String)("UPDATE character_skills_save SET char_obj_id=" + n2 + " WHERE char_obj_id=" + player.getObjectId() + " AND class_index=" + n));
        mysql.set((String)("UPDATE character_effects_save SET object_id=" + n2 + " WHERE object_id=" + player.getObjectId() + " AND id=" + n));
        mysql.set((String)("UPDATE character_hennas SET char_obj_id=" + n2 + " WHERE char_obj_id=" + player.getObjectId() + " AND class_index=" + n));
        mysql.set((String)("UPDATE character_shortcuts SET object_id=" + n2 + " WHERE object_id=" + player.getObjectId() + " AND class_index=" + n));
        player.modifySubClass(n, 0);
        player.logout();
        Log.service((String)"SubClassSeparate", (Player)player, (String)(" subclass separated to " + stringArray[1] + " for " + Config.SERVICES_SEPARATE_SUB_ITEM + " " + Config.SERVICES_SEPARATE_SUB_PRICE));
    }
}
