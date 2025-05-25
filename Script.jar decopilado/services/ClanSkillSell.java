/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.Log
 */
package services;

import java.util.Arrays;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Log;

public class ClanSkillSell
extends Functions {
    private static final List<Skill> dO = Arrays.asList(SkillTable.getInstance().getInfo(370, 3), SkillTable.getInstance().getInfo(373, 3), SkillTable.getInstance().getInfo(379, 3), SkillTable.getInstance().getInfo(391, 1), SkillTable.getInstance().getInfo(371, 3), SkillTable.getInstance().getInfo(374, 3), SkillTable.getInstance().getInfo(376, 3), SkillTable.getInstance().getInfo(377, 3), SkillTable.getInstance().getInfo(383, 3), SkillTable.getInstance().getInfo(380, 3), SkillTable.getInstance().getInfo(382, 3), SkillTable.getInstance().getInfo(384, 3), SkillTable.getInstance().getInfo(385, 3), SkillTable.getInstance().getInfo(386, 3), SkillTable.getInstance().getInfo(387, 3), SkillTable.getInstance().getInfo(388, 3), SkillTable.getInstance().getInfo(390, 3), SkillTable.getInstance().getInfo(372, 3), SkillTable.getInstance().getInfo(375, 3), SkillTable.getInstance().getInfo(378, 3), SkillTable.getInstance().getInfo(381, 3), SkillTable.getInstance().getInfo(389, 3));

    public void clan_skill_sell_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CLANSKILL_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isInPeaceZone()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_peace_zone.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/clan_skills_sell.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_CLAN_SKILL_SELL_ITEM));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_CLAN_SKILL_SELL_PRICE));
        npcHtmlMessage.replace("%clan_min_level%", String.valueOf(Config.SERVICES_CLANSKIL_SELL_MIN_LEVEL));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void clanSkillBuy() {
        Player player = this.getSelf();
        if (player == null || !ClanSkillSell.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_CLANSKILL_SELL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isInPeaceZone()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_peace_zone.htm"));
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendMessage(new CustomMessage("services.ClanSkillSell.GetClan", player, new Object[0]));
            return;
        }
        if (!player.isClanLeader()) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_CLAN_LEADER_IS_ENABLED);
            return;
        }
        if (clan.getSkillLevel(370) == 3 && clan.getSkillLevel(373) == 3 && clan.getSkillLevel(379) == 3 && clan.getSkillLevel(391) == 1 && clan.getSkillLevel(371) == 3 && clan.getSkillLevel(374) == 3 && clan.getSkillLevel(376) == 3 && clan.getSkillLevel(377) == 3 && clan.getSkillLevel(383) == 3 && clan.getSkillLevel(380) == 3 && clan.getSkillLevel(382) == 3 && clan.getSkillLevel(384) == 3 && clan.getSkillLevel(385) == 3 && clan.getSkillLevel(386) == 3 && clan.getSkillLevel(387) == 3 && clan.getSkillLevel(388) == 3 && clan.getSkillLevel(390) == 3 && clan.getSkillLevel(372) == 3 && clan.getSkillLevel(375) == 3 && clan.getSkillLevel(378) == 3 && clan.getSkillLevel(381) == 3 && clan.getSkillLevel(389) == 3) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/clan_skills_sell_already.htm"));
            return;
        }
        if (clan.getLevel() < Config.SERVICES_CLANSKIL_SELL_MIN_LEVEL) {
            player.sendMessage(new CustomMessage("services.ClanSkillSell.GetClan", player, new Object[0]));
            return;
        }
        if (ClanSkillSell.getItemCount((Playable)player, (int)Config.SERVICES_CLAN_SKILL_SELL_ITEM) < (long)Config.SERVICES_CLAN_SKILL_SELL_PRICE) {
            if (Config.SERVICES_CLAN_SKILL_SELL_ITEM == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        Functions.removeItem((Playable)player, (int)Config.SERVICES_CLAN_SKILL_SELL_ITEM, (long)Config.SERVICES_CLAN_SKILL_SELL_PRICE);
        Log.service((String)"ClanSkillSell", (Player)player, (String)("received a clan skill  for Clan " + player.getClan().getName() + " price " + Config.SERVICES_CLAN_SKILL_SELL_ITEM + " " + Config.SERVICES_CLAN_SKILL_SELL_PRICE));
        for (Skill skill : dO) {
            clan.addSkill(skill, true);
            clan.broadcastToOnlineMembers(new L2GameServerPacket[]{new SystemMessage(SystemMsg.THE_CLAN_SKILL_S1_HAS_BEEN_ADDED).addSkillName(skill)});
        }
    }

    public void clan_skill_community_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CLANSKILL_SELL_ENABLED) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        if (!player.isInPeaceZone()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_peace_zone.htm");
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/clan_skills_sell.htm", player);
        string = string.replace("%item_id%", String.valueOf(Config.SERVICES_CLAN_SKILL_SELL_ITEM));
        string = string.replace("%item_count%", String.valueOf(Config.SERVICES_CLAN_SKILL_SELL_PRICE));
        string = string.replace("%clan_min_level%", String.valueOf(Config.SERVICES_CLANSKIL_SELL_MIN_LEVEL));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void clanSkillBuyCb() {
        Player player = this.getSelf();
        if (player == null || !ClanSkillSell.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_CLANSKILL_SELL_ENABLED) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        if (!player.isInPeaceZone()) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_peace_zone.htm");
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/clan_restriction_get.htm");
            return;
        }
        if (clan.getSkillLevel(370) == 3 && clan.getSkillLevel(373) == 3 && clan.getSkillLevel(379) == 3 && clan.getSkillLevel(391) == 1 && clan.getSkillLevel(371) == 3 && clan.getSkillLevel(374) == 3 && clan.getSkillLevel(376) == 3 && clan.getSkillLevel(377) == 3 && clan.getSkillLevel(383) == 3 && clan.getSkillLevel(380) == 3 && clan.getSkillLevel(382) == 3 && clan.getSkillLevel(384) == 3 && clan.getSkillLevel(385) == 3 && clan.getSkillLevel(386) == 3 && clan.getSkillLevel(387) == 3 && clan.getSkillLevel(388) == 3 && clan.getSkillLevel(390) == 3 && clan.getSkillLevel(372) == 3 && clan.getSkillLevel(375) == 3 && clan.getSkillLevel(378) == 3 && clan.getSkillLevel(381) == 3 && clan.getSkillLevel(389) == 3) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/clan_skills_sell_already.htm");
            return;
        }
        if (clan.getLevel() < Config.SERVICES_CLANSKIL_SELL_MIN_LEVEL) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/clan_skills_sell_level_required.htm", player);
            string = string.replace("%skills_clan_level%", String.valueOf(Config.SERVICES_CLANSKIL_SELL_MIN_LEVEL));
            ShowBoard.separateAndSend((String)string, (Player)player);
            return;
        }
        if (ClanSkillSell.getItemCount((Playable)player, (int)Config.SERVICES_CLAN_SKILL_SELL_ITEM) < (long)Config.SERVICES_CLAN_SKILL_SELL_PRICE) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_incorrect_items.htm");
            return;
        }
        Functions.removeItem((Playable)player, (int)Config.SERVICES_CLAN_SKILL_SELL_ITEM, (long)Config.SERVICES_CLAN_SKILL_SELL_PRICE);
        Log.service((String)"ClanSkillSell", (Player)player, (String)("received a clan skill  for Clan " + player.getClan().getName() + " price " + Config.SERVICES_CLAN_SKILL_SELL_ITEM + " " + Config.SERVICES_CLAN_SKILL_SELL_PRICE));
        for (Skill skill : dO) {
            clan.addSkill(skill, true);
            clan.broadcastToOnlineMembers(new L2GameServerPacket[]{new SystemMessage(SystemMsg.THE_CLAN_SKILL_S1_HAS_BEEN_ADDED).addSkillName(skill)});
        }
        ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/clan_skills_sell_gratz.htm");
    }
}
