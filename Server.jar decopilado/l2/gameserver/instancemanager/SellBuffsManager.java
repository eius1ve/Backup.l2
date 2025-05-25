/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.instancemanager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.SellBuffHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.s2c.ExPrivateStoreSetWholeMsg;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.HtmlUtils;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.StringUtils;

public class SellBuffsManager {
    private static final String co = "scripts/services/sellbuff/";

    protected SellBuffsManager() {
    }

    public void sendSellMenu(Player player) {
        ShowBoard.separateAndSend(player, co + (player.isSellingBuffs() ? "stop_sell.htm" : "main_menu.htm"));
    }

    public void startSellBuffs(Player player, String string) {
        player.sitDown(null);
        player.setSellingBuffs(true);
        player.setPrivateStoreType(8);
        player.setSellStoreName(string);
        player.broadcastUserInfo(false, new UserInfoType[0]);
        player.broadcastPacket(new ExPrivateStoreSetWholeMsg(player));
        player.setVar("sellbufftitle", string, -1L);
        this.sendSellMenu(player);
    }

    public void stopSellBuffs(Player player) {
        player.setSellingBuffs(false);
        player.setPrivateStoreType(0);
        player.standUp();
        player.broadcastUserInfo(false, new UserInfoType[0]);
        this.sendSellMenu(player);
    }

    public void startSellBuffsOffline(Player player) {
        String string = player.getVar("sellbufftitle");
        player.sitDown(null);
        player.setSellingBuffs(true);
        player.setPrivateStoreType(8);
        player.setSellStoreName(string);
        player.broadcastUserInfo(false, new UserInfoType[0]);
        player.broadcastPacket(new ExPrivateStoreSetWholeMsg(player));
    }

    public void stopSellBuffsOffline(Player player) {
        player.setSellingBuffs(false);
        player.setPrivateStoreType(0);
        player.standUp();
        player.unsetVar("offlinebuffs");
        player.unsetVar("sellbufftitle");
    }

    private static String a(Player player, Player player2, Skill skill, long l, int n) {
        SellBuffHolder.BuffSellInfo buffSellInfo = SellBuffHolder.getInstance().getBuffInfo(skill.getId());
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(buffSellInfo.getItemId());
        String string = new CustomMessage("sellbuff.sendBuffMenu", player, new Object[0]).toString();
        string = StringUtils.replace((String)string, (String)"%skillIcon%", (String)skill.getIcon());
        string = StringUtils.replace((String)string, (String)"%skillLevel%", (String)String.valueOf(skill.getLevel() > 100 ? SkillTable.getInstance().getMaxLevel(skill.getId()) : skill.getLevel()));
        string = StringUtils.replace((String)string, (String)"%skillName%", (String)String.valueOf(skill.getName()));
        string = StringUtils.replace((String)string, (String)"%mp_price%", (String)String.valueOf(skill.getMpConsume() * buffSellInfo.getSkillMpConsumeMultiplayer()));
        string = StringUtils.replace((String)string, (String)"%price%", (String)Util.formatAdena(l));
        string = StringUtils.replace((String)string, (String)"%item_name%", (String)(itemTemplate != null ? itemTemplate.getName() : ""));
        string = StringUtils.replace((String)string, (String)"%sellerObjectId%", (String)String.valueOf(player2.getObjectId()));
        string = StringUtils.replace((String)string, (String)"%skillId%", (String)String.valueOf(skill.getId()));
        string = StringUtils.replace((String)string, (String)"%index%", (String)String.valueOf(n));
        return string;
    }

    /*
     * WARNING - void declaration
     */
    public void sendBuffMenu(Player player, Player player2, int n) {
        void var12_18;
        Comparable<Long> comparable;
        Object object;
        if (!player2.isSellingBuffs()) {
            return;
        }
        int n2 = -1;
        int n3 = -1;
        int n4 = 0;
        LinkedHashMap<Skill, SellBuffHolder.BuffSellInfo> linkedHashMap = new LinkedHashMap<Skill, SellBuffHolder.BuffSellInfo>();
        String string = HtmCache.getInstance().getNotNull("scripts/services/sellbuff/buy_menu.htm", player);
        int n5 = 0;
        for (Skill object22 : player2.getBuffs4Sale().keySet()) {
            SellBuffHolder.BuffSellInfo stringBuilder2 = SellBuffHolder.getInstance().getBuffInfo(object22.getId());
            if (stringBuilder2 == null || ++n5 <= n || n5 > 10 + n) continue;
            linkedHashMap.put(player2.getKnownSkill(stringBuilder2.getSkillId()), stringBuilder2);
        }
        if (n5 > 10 && n5 > n + 10) {
            n2 = n + 10;
        }
        if (n >= 10) {
            n3 = n - 10;
        }
        n4 = 10 - linkedHashMap.size();
        string = string.replace("%MpGauge%", HtmlUtils.getMpGauge(250, (long)player2.getCurrentMp(), player2.getMaxMp(), false));
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            object = (Skill)entry.getKey();
            comparable = player2.getBuffs4Sale().get(object);
            if (object == null || comparable == null) {
                ++n4;
                continue;
            }
            stringBuilder.append(SellBuffsManager.a(player, player2, (Skill)object, (Long)comparable, n));
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        boolean bl = false;
        while (var12_18 < n4) {
            object = new CustomMessage("sellbuff.emptyField", player, new Object[0]).toString();
            stringBuilder2.append((String)object);
            ++var12_18;
        }
        string = string.replace("%sendEmptyHtml%", stringBuilder2.toString());
        StringBuilder stringBuilder3 = new StringBuilder();
        object = new CustomMessage("sellbuff.previousIndex", player, new Object[0]).toString();
        object = StringUtils.replace((String)object, (String)"%sellerObjectId%", (String)String.valueOf(player2.getObjectId()));
        object = StringUtils.replace((String)object, (String)"%previousIndex%", (String)String.valueOf(n3));
        stringBuilder3.append((String)object);
        string = string.replace("%sendPreviousIndex%", n3 > -1 ? stringBuilder3.toString() : "");
        comparable = new StringBuilder();
        String string2 = new CustomMessage("sellbuff.nextIndex", player, new Object[0]).toString();
        string2 = StringUtils.replace((String)string2, (String)"%sellerObjectId%", (String)String.valueOf(player2.getObjectId()));
        string2 = StringUtils.replace((String)string2, (String)"%nextIndex%", (String)String.valueOf(n2));
        ((StringBuilder)comparable).append(string2);
        string = string.replace("%sendNextIndex%", n2 > -1 ? ((StringBuilder)comparable).toString() : "");
        string = string.replace("%skillEditMenuBox%", stringBuilder.toString());
        ShowBoard.separateAndSend(string, player);
    }

    public void buildEditMenu(Player player) {
        String string = HtmCache.getInstance().getNotNull("scripts/services/sellbuff/edit_menu_empty.htm", player);
        String string2 = HtmCache.getInstance().getNotNull("scripts/services/sellbuff/edit_menu.htm", player);
        if (player.getBuffs4Sale().isEmpty()) {
            ShowBoard.separateAndSend(string, player);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Skill skill : player.getBuffs4Sale().keySet()) {
                Skill skill2 = player.getKnownSkill(skill.getId());
                SellBuffHolder.BuffSellInfo buffSellInfo = SellBuffHolder.getInstance().getBuffInfo(skill2.getId());
                Long l = player.getBuffs4Sale().get(skill);
                if (skill2 == null || buffSellInfo == null || l == null) continue;
                String string3 = new CustomMessage("sellbuff.skillEditMenuBox", player, new Object[0]).toString();
                string3 = StringUtils.replace((String)string3, (String)"%price%", (String)Util.formatAdena(l));
                string3 = StringUtils.replace((String)string3, (String)"%skillId%", (String)String.valueOf(skill2.getId()));
                string3 = StringUtils.replace((String)string3, (String)"%skillLevel%", (String)String.valueOf(skill2.getLevel() > 100 ? SkillTable.getInstance().getMaxLevel(skill2.getId()) : skill2.getLevel()));
                string3 = StringUtils.replace((String)string3, (String)"%skillName%", (String)skill2.getName());
                string3 = StringUtils.replace((String)string3, (String)"%skillIcon%", (String)skill2.getIcon());
                stringBuilder.append(string3);
            }
            string2 = string2.replace("%skillEditMenuBox%", stringBuilder.toString());
            ShowBoard.separateAndSend(string2, player);
        }
    }

    public void buildSkillMenu(Player player, int n) {
        int n2 = n + 10;
        int n3 = -1;
        int n4 = -1;
        ArrayList<Skill> arrayList = new ArrayList<Skill>();
        String string = HtmCache.getInstance().getNotNull("scripts/services/sellbuff/skill_empty_menu.htm", player);
        String string2 = HtmCache.getInstance().getNotNull("scripts/services/sellbuff/skill_choice_menu.htm", player);
        int n5 = 0;
        for (Skill object2 : player.getAllSkills()) {
            if (!SellBuffHolder.getInstance().isBuffAllowed(object2.getId()) || player.getBuffs4Sale().containsKey(object2) || ++n5 <= n || n5 > n2) continue;
            arrayList.add(object2);
        }
        if (n5 > 10 && n5 > n + 10) {
            n3 = n + 10;
        }
        if (n >= 10) {
            n4 = n - 10;
        }
        if (arrayList.isEmpty()) {
            ShowBoard.separateAndSend(string, player);
        } else {
            CharSequence charSequence;
            Object object2;
            StringBuilder stringBuilder = new StringBuilder();
            for (Object object2 : arrayList) {
                charSequence = new CustomMessage("sellbuff.skillMenuBox", player, new Object[0]).toString();
                charSequence = StringUtils.replace((String)charSequence, (String)"%skillId%", (String)String.valueOf(((Skill)object2).getId()));
                charSequence = StringUtils.replace((String)charSequence, (String)"%skillLevel%", (String)String.valueOf(((Skill)object2).getLevel() > 100 ? SkillTable.getInstance().getMaxLevel(((Skill)object2).getId()) : ((Skill)object2).getLevel()));
                charSequence = StringUtils.replace((String)charSequence, (String)"%skillName%", (String)String.valueOf(((Skill)object2).getName()));
                charSequence = StringUtils.replace((String)charSequence, (String)"%skillIcon%", (String)String.valueOf(((Skill)object2).getIcon()));
                stringBuilder.append((String)charSequence);
            }
            string2 = string2.replace("%skillMenuBox%", stringBuilder.toString());
            StringBuilder stringBuilder2 = new StringBuilder();
            object2 = new CustomMessage("sellbuff.skillMenuPreviousIndex", player, new Object[0]).toString();
            object2 = StringUtils.replace((String)object2, (String)"%previousIdx%", (String)String.valueOf(n4));
            stringBuilder2.append((String)object2);
            string2 = string2.replace("%sendPreviousIndex%", n4 > -1 ? stringBuilder2.toString() : "");
            charSequence = new StringBuilder();
            String string3 = new CustomMessage("sellbuff.skillMenuNextIndex", player, new Object[0]).toString();
            string3 = StringUtils.replace((String)string3, (String)"%nextIdx%", (String)String.valueOf(n3));
            ((StringBuilder)charSequence).append(string3);
            string2 = string2.replace("%sendNextIndex%", n3 > -1 ? ((StringBuilder)charSequence).toString() : "");
            ShowBoard.separateAndSend(string2, player);
        }
    }

    public boolean canStartSellBuffs(Player player) {
        if (player.isAlikeDead()) {
            player.sendMessage(new CustomMessage("sellbuff.cond1", player, new Object[0]));
            return false;
        }
        if (player.isOlyObserver() || player.isOlyParticipant()) {
            player.sendMessage(new CustomMessage("sellbuff.cond2", player, new Object[0]));
            return false;
        }
        if (player.getTeam() != TeamType.NONE) {
            player.sendMessage(new CustomMessage("sellbuff.cond3", player, new Object[0]));
            return false;
        }
        if (player.isCursedWeaponEquipped() || player.getKarma() > 0) {
            player.sendMessage(new CustomMessage("sellbuff.cond4", player, new Object[0]));
            return false;
        }
        if (player.isInDuel()) {
            player.sendMessage(new CustomMessage("sellbuff.cond5", player, new Object[0]));
            return false;
        }
        if (player.isFishing()) {
            player.sendMessage(new CustomMessage("sellbuff.cond6", player, new Object[0]));
            return false;
        }
        if (player.isMounted() || player.isFlying()) {
            player.sendMessage(new CustomMessage("sellbuff.cond7", player, new Object[0]));
            return false;
        }
        if (player.getTransformation() > 0) {
            player.sendMessage(new CustomMessage("sellbuff.cond8", player, new Object[0]));
            return false;
        }
        if (player.isActionBlocked("open_private_workshop") || player.isActionBlocked("open_private_store") || !player.isInZone(Zone.ZoneType.peace_zone)) {
            player.sendMessage(new CustomMessage("sellbuff.cond9", player, new Object[0]));
            return false;
        }
        return true;
    }

    public static SellBuffsManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void restoreFromOffline(Player player) {
        if (Config.SELLBUFF_ENABLED && player.getVar("offlinebuffs") != null) {
            String string = player.getVar("offlinebuffs");
            LinkedHashMap<Skill, Long> linkedHashMap = new LinkedHashMap<Skill, Long>();
            for (String string2 : StringUtils.split((String)string, (char)';')) {
                if ((string2 = StringUtils.trimToEmpty((String)string2)).isEmpty()) continue;
                int n = string2.indexOf(58);
                int n2 = Integer.parseInt(string2.substring(0, n));
                long l = Long.parseLong(string2.substring(n + 1));
                Skill skill = player.getKnownSkill(n2);
                if (skill == null || SellBuffHolder.getInstance().getBuffInfo(skill.getId()) == null) continue;
                linkedHashMap.put(skill, l);
            }
            player.setBuffs4Sale(linkedHashMap);
            SellBuffsManager.getInstance().startSellBuffsOffline(player);
        }
    }

    private static class SingletonHolder {
        protected static final SellBuffsManager INSTANCE = new SellBuffsManager();

        private SingletonHolder() {
        }
    }

    public static class Buff4Sale {
    }
}
