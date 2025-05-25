/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.Config$RateBonusInfo
 *  l2.gameserver.dao.AccountBonusDAO
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExBR_PremiumState
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.TimeUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import java.util.Locale;
import l2.gameserver.Config;
import l2.gameserver.dao.AccountBonusDAO;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExBR_PremiumState;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.TimeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

public class RateBonus
extends Functions
implements IVoicedCommandHandler,
ScriptFile {
    private String[] o = new String[]{"premium", "pa"};

    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.SERVICES_RATE_COMMAND_ENABLED) {
            return false;
        }
        if (this.o[0].equalsIgnoreCase(string) || this.o[1].equalsIgnoreCase(string)) {
            this.listMsg(player);
            return true;
        }
        return false;
    }

    public String[] getVoicedCommandList() {
        if (!Config.SERVICES_RATE_COMMAND_ENABLED) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return this.o;
    }

    public void list() {
        Player player = this.getSelf();
        if (player == null || !RateBonus.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_RATE_ENABLED) {
            this.show(HtmCache.getInstance().getNotNull("npcdefault.htm", player), player);
            return;
        }
        String string = "";
        long l = player.getBonus().getBonusExpire();
        if (l > System.currentTimeMillis() / 1000L) {
            string = HtmCache.getInstance().getNotNull("scripts/services/RateBonusAlready.htm", player).replaceFirst("endtime", TimeUtils.toSimpleFormat((long)(l * 1000L)));
        } else {
            string = HtmCache.getInstance().getNotNull("scripts/services/RateBonus.htm", player);
            StringBuilder stringBuilder = new StringBuilder();
            for (Config.RateBonusInfo rateBonusInfo : Config.SERVICES_RATE_BONUS_INFO) {
                String string2 = StringHolder.getInstance().getNotNull(player, "scripts.services.RateBonus.BonusHtml");
                string2 = string2.replace("%bonus_idx%", String.valueOf(rateBonusInfo.id));
                string2 = string2.replace("%exp_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateXp)));
                string2 = string2.replace("%sp_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateSp)));
                string2 = string2.replace("%exp_rate_raid%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateRaidXp)));
                string2 = string2.replace("%sp_rate_raid%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateRaidSp)));
                string2 = string2.replace("%quest_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.questRewardRate)));
                string2 = string2.replace("%quest_adena_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.questAdenaRewardRate)));
                string2 = string2.replace("%quest_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.questDropRate)));
                string2 = string2.replace("%adena_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropAdena)));
                string2 = string2.replace("%items_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropItems)));
                string2 = string2.replace("%seal_stones_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropSealStones)));
                string2 = string2.replace("%spoil_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropSpoil)));
                string2 = string2.replace("%enchant_item_bonus%", String.valueOf((int)(100.0f * (rateBonusInfo.enchantItemMul - 1.0f))));
                string2 = string2.replace("%enchant_skill_bonus%", String.valueOf((int)(100.0f * (rateBonusInfo.enchantSkillMul - 1.0f))));
                string2 = string2.replace("%period_days%", TimeUtils.formatTime((int)((int)rateBonusInfo.bonusTimeSeconds), (boolean)true, (Player)player));
                string2 = string2.replace("%price%", String.valueOf(rateBonusInfo.consumeItemAmount));
                string2 = string2.replace("%price_item_id%", String.valueOf(rateBonusInfo.consumeItemId));
                string2 = string2.replace("%price_item_name%", ItemHolder.getInstance().getTemplate(rateBonusInfo.consumeItemId).getName());
                stringBuilder.append(string2);
            }
            string = string.replaceFirst("%toreplace%", stringBuilder.toString());
        }
        this.show(string, player);
    }

    public void listMsg(Player player) {
        if (!Config.SERVICES_RATE_ENABLED) {
            this.show(HtmCache.getInstance().getNotNull("npcdefault.htm", player), player);
            return;
        }
        String string = "";
        long l = player.getBonus().getBonusExpire();
        if (l > System.currentTimeMillis() / 1000L) {
            string = HtmCache.getInstance().getNotNull("scripts/services/RateBonusAlready.htm", player).replaceFirst("endtime", TimeUtils.toSimpleFormat((long)(l * 1000L)));
        } else {
            string = HtmCache.getInstance().getNotNull("scripts/services/RateBonus.htm", player);
            StringBuilder stringBuilder = new StringBuilder();
            for (Config.RateBonusInfo rateBonusInfo : Config.SERVICES_RATE_BONUS_INFO) {
                String string2 = StringHolder.getInstance().getNotNull(player, "scripts.services.RateBonus.BonusHtml");
                string2 = string2.replace("%bonus_idx%", String.valueOf(rateBonusInfo.id));
                string2 = string2.replace("%exp_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateXp)));
                string2 = string2.replace("%sp_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateSp)));
                string2 = string2.replace("%exp_rate_raid%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateRaidXp)));
                string2 = string2.replace("%sp_rate_raid%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateRaidSp)));
                string2 = string2.replace("%quest_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.questRewardRate)));
                string2 = string2.replace("%quest_adena_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.questAdenaRewardRate)));
                string2 = string2.replace("%quest_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.questDropRate)));
                string2 = string2.replace("%adena_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropAdena)));
                string2 = string2.replace("%items_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropItems)));
                string2 = string2.replace("%seal_stones_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropSealStones)));
                string2 = string2.replace("%spoil_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropSpoil)));
                string2 = string2.replace("%enchant_item_bonus%", String.valueOf((int)(100.0f * (rateBonusInfo.enchantItemMul - 1.0f))));
                string2 = string2.replace("%enchant_skill_bonus%", String.valueOf((int)(100.0f * (rateBonusInfo.enchantSkillMul - 1.0f))));
                string2 = string2.replace("%period_days%", TimeUtils.formatTime((int)((int)rateBonusInfo.bonusTimeSeconds), (boolean)true, (Player)player));
                string2 = string2.replace("%price%", String.valueOf(rateBonusInfo.consumeItemAmount));
                string2 = string2.replace("%price_item_id%", String.valueOf(rateBonusInfo.consumeItemId));
                string2 = string2.replace("%price_item_name%", ItemHolder.getInstance().getTemplate(rateBonusInfo.consumeItemId).getName());
                stringBuilder.append(string2);
            }
            string = string.replaceFirst("%toreplace%", stringBuilder.toString());
        }
        this.show(string, player);
    }

    public void get(String[] stringArray) {
        Player player = this.getSelf();
        if (!Config.SERVICES_RATE_ENABLED) {
            this.show(HtmCache.getInstance().getNotNull("npcdefault.htm", player), player);
            return;
        }
        long l = player.getBonus().getBonusExpire();
        if (l > System.currentTimeMillis() / 1000L) {
            this.show(HtmCache.getInstance().getNotNull("scripts/services/RateBonusAlready.htm", player).replaceFirst("endtime", TimeUtils.toSimpleFormat((long)(l * 1000L))), player);
            return;
        }
        int n = Integer.parseInt(stringArray[0]);
        Config.RateBonusInfo rateBonusInfo = null;
        for (Config.RateBonusInfo rateBonusInfo2 : Config.SERVICES_RATE_BONUS_INFO) {
            if (rateBonusInfo2.id != n) continue;
            rateBonusInfo = rateBonusInfo2;
        }
        if (rateBonusInfo == null) {
            return;
        }
        if (!player.getInventory().destroyItemByItemId(rateBonusInfo.consumeItemId, rateBonusInfo.consumeItemAmount)) {
            if (rateBonusInfo.consumeItemId == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        Log.service((String)"RateBonus", (Player)player, (String)("|bought a rate bonus|" + rateBonusInfo.id + "|" + rateBonusInfo.bonusTimeSeconds + "| consume " + rateBonusInfo.consumeItemId + " amount " + rateBonusInfo.consumeItemAmount));
        AccountBonusDAO.getInstance().store(player.getAccountName(), rateBonusInfo.makeBonus());
        player.sendPacket((IStaticPacket)new ExBR_PremiumState(player, true));
        player.stopBonusTask();
        player.startBonusTask();
        for (Pair pair : rateBonusInfo.rewardItem) {
            ItemFunctions.addItem((Playable)player, (int)((Integer)pair.getLeft()), (long)((Long)pair.getRight()), (boolean)true);
        }
        if (rateBonusInfo.nameColor != null) {
            player.setNameColor(rateBonusInfo.nameColor.intValue());
        }
        if (player.getParty() != null) {
            player.getParty().recalculatePartyData();
        }
        player.broadcastUserInfo(true, new UserInfoType[0]);
        this.show(HtmCache.getInstance().getNotNull("scripts/services/RateBonusGet.htm", player), player);
    }

    public void listCb() {
        Player player = this.getSelf();
        if (player == null || !RateBonus.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_RATE_ENABLED) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        String string = "";
        long l = player.getBonus().getBonusExpire();
        if (l > System.currentTimeMillis() / 1000L) {
            string = HtmCache.getInstance().getNotNull("scripts/services/community/services/rate_bonus_already.htm", player).replaceFirst("endtime", TimeUtils.toSimpleFormat((long)(l * 1000L)));
        } else {
            string = HtmCache.getInstance().getNotNull("scripts/services/community/services/rate_bonus.htm", player);
            StringBuilder stringBuilder = new StringBuilder();
            for (Config.RateBonusInfo rateBonusInfo : Config.SERVICES_RATE_BONUS_INFO) {
                String string2 = StringHolder.getInstance().getNotNull(player, "scripts.services.RateBonus.BonusHtmlCb");
                string2 = string2.replace("%bonus_idx%", String.valueOf(rateBonusInfo.id));
                string2 = string2.replace("%exp_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateXp)));
                string2 = string2.replace("%sp_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateSp)));
                string2 = string2.replace("%exp_rate_raid%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateRaidXp)));
                string2 = string2.replace("%sp_rate_raid%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.rateRaidSp)));
                string2 = string2.replace("%quest_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.questRewardRate)));
                string2 = string2.replace("%quest_adena_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.questAdenaRewardRate)));
                string2 = string2.replace("%quest_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.questDropRate)));
                string2 = string2.replace("%adena_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropAdena)));
                string2 = string2.replace("%items_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropItems)));
                string2 = string2.replace("%seal_stones_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropSealStones)));
                string2 = string2.replace("%spoil_drop_rate%", String.format(Locale.US, "%.1f", Float.valueOf(rateBonusInfo.dropSpoil)));
                string2 = string2.replace("%enchant_item_bonus%", String.valueOf((int)(100.0f * (rateBonusInfo.enchantItemMul - 1.0f))));
                string2 = string2.replace("%enchant_skill_bonus%", String.valueOf((int)(100.0f * (rateBonusInfo.enchantSkillMul - 1.0f))));
                string2 = string2.replace("%period_days%", TimeUtils.formatTime((int)((int)rateBonusInfo.bonusTimeSeconds), (boolean)true, (Player)player));
                string2 = string2.replace("%price%", String.valueOf(rateBonusInfo.consumeItemAmount));
                string2 = string2.replace("%price_item_id%", String.valueOf(rateBonusInfo.consumeItemId));
                string2 = string2.replace("%price_item_name%", ItemHolder.getInstance().getTemplate(rateBonusInfo.consumeItemId).getName());
                stringBuilder.append(string2);
            }
            string = string.replaceFirst("%toreplace%", stringBuilder.toString());
        }
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void getCb(String[] stringArray) {
        Player player = this.getSelf();
        if (!Config.SERVICES_RATE_ENABLED) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        long l = player.getBonus().getBonusExpire();
        if (l > System.currentTimeMillis() / 1000L) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/rate_bonus_already.htm", player).replaceFirst("endtime", TimeUtils.toSimpleFormat((long)(l * 1000L))), (Player)player);
            return;
        }
        int n = Integer.parseInt(stringArray[0]);
        Config.RateBonusInfo rateBonusInfo = null;
        for (Config.RateBonusInfo rateBonusInfo2 : Config.SERVICES_RATE_BONUS_INFO) {
            if (rateBonusInfo2.id != n) continue;
            rateBonusInfo = rateBonusInfo2;
        }
        if (rateBonusInfo == null) {
            return;
        }
        if (!player.getInventory().destroyItemByItemId(rateBonusInfo.consumeItemId, rateBonusInfo.consumeItemAmount)) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_incorrect_items.htm");
            return;
        }
        Log.service((String)"RateBonus", (Player)player, (String)("|bought a rate bonus|" + rateBonusInfo.id + "|" + rateBonusInfo.bonusTimeSeconds + "| consume " + rateBonusInfo.consumeItemId + " amount " + rateBonusInfo.consumeItemAmount));
        AccountBonusDAO.getInstance().store(player.getAccountName(), rateBonusInfo.makeBonus());
        player.sendPacket((IStaticPacket)new ExBR_PremiumState(player, true));
        player.stopBonusTask();
        player.startBonusTask();
        for (Pair pair : rateBonusInfo.rewardItem) {
            ItemFunctions.addItem((Playable)player, (int)((Integer)pair.getLeft()), (long)((Long)pair.getRight()), (boolean)true);
        }
        if (rateBonusInfo.nameColor != null) {
            player.setNameColor(rateBonusInfo.nameColor.intValue());
        }
        if (player.getParty() != null) {
            player.getParty().recalculatePartyData();
        }
        player.broadcastUserInfo(true, new UserInfoType[0]);
        ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/rate_bonus_get.htm");
    }

    public void onLoad() {
        if (Config.SERVICES_RATE_COMMAND_ENABLED) {
            VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)this);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
    }
}
