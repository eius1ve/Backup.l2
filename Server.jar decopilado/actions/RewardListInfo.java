/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Experience
 *  l2.gameserver.model.instances.ChestInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.instances.RaidBossInstance
 *  l2.gameserver.model.reward.RewardData
 *  l2.gameserver.model.reward.RewardGroup
 *  l2.gameserver.model.reward.RewardList
 *  l2.gameserver.model.reward.RewardType
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ActionFail
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.stats.Stats
 *  l2.gameserver.utils.HtmlUtils
 *  org.apache.commons.lang3.StringUtils
 */
package actions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.instances.ChestInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.model.reward.RewardData;
import l2.gameserver.model.reward.RewardGroup;
import l2.gameserver.model.reward.RewardList;
import l2.gameserver.model.reward.RewardType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.stats.Stats;
import l2.gameserver.utils.HtmlUtils;
import org.apache.commons.lang3.StringUtils;

public class RewardListInfo
extends Functions {
    private static final RewardType[] a = new RewardType[]{RewardType.RATED_GROUPED, RewardType.SWEEP, RewardType.NOT_RATED_GROUPED, RewardType.NOT_RATED_NOT_GROUPED, RewardType.RATED_NOT_GROUPED};
    private static final NumberFormat b = NumberFormat.getPercentInstance(Locale.ENGLISH);
    private static final NumberFormat c = NumberFormat.getInstance(Locale.ENGLISH);

    private static boolean canBypassCheck(Player player, NpcInstance npcInstance) {
        if (npcInstance == null) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            player.sendActionFailed();
            return false;
        }
        if (!Config.ALLOW_TALK_WHILE_SITTING && player.isSitting()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_MOVE_WHILE_SITTING);
            player.sendActionFailed();
            return false;
        }
        if (!npcInstance.isInRange((GameObject)player, 2500L)) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.POSITIONING_CANNOT_BE_DONE_HERE_BECAUSE_THE_DISTANCE_BETWEEN_MERCENARIES_IS_TOO_SHORT, ActionFail.STATIC});
            player.sendActionFailed();
            return false;
        }
        return true;
    }

    public static void showRewardHtml(Player player, NpcInstance npcInstance) {
        RewardListInfo.showRewardHtml(player, npcInstance, 0, null);
    }

    public static void showRewardHtml(Player player, NpcInstance npcInstance, int n) {
        RewardListInfo.showRewardHtml(player, npcInstance, n, null);
    }

    public static void showRewardHtml(Player player, NpcInstance npcInstance, int n, RewardType rewardType) {
        if (!RewardListInfo.canBypassCheck(player, npcInstance)) {
            return;
        }
        int n2 = npcInstance.calculateLevelDiffForDrop(player.isInParty() ? player.getParty().getLevel() : player.getLevel());
        double d = npcInstance.calcStat(Stats.ITEM_REWARD_MULTIPLIER, 1.0, (Creature)player, null);
        d *= Experience.penaltyModifier((long)n2, (double)9.0);
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, npcInstance);
        npcHtmlMessage.replace("%npc_name%", HtmlUtils.htmlNpcName((int)npcInstance.getNpcId()));
        if (d <= 0.0 && !player.isGM() && !Config.ALT_GAME_SHOW_DROPLIST_WEAK_MOBS) {
            npcHtmlMessage.setFile("actions/rewardlist_to_weak.htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            return;
        }
        if (npcInstance instanceof ChestInstance && !player.isGM() && !Config.ALT_GAME_SHOW_DROPLIST_TREASURE_CHEST) {
            npcHtmlMessage.setFile("actions/rewardlist_no_for_chest.htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            player.sendActionFailed();
            return;
        }
        if (npcInstance.getTemplate().getRewards().isEmpty()) {
            npcHtmlMessage.setFile("actions/rewardlist_empty.htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            return;
        }
        npcHtmlMessage.setFile("actions/rewardlist_info.htm");
        ArrayList<String> arrayList = new ArrayList<String>(16);
        block7: for (RewardType rewardType2 : a) {
            RewardList rewardList;
            if (rewardType != null && rewardType2 != rewardType || (rewardList = npcInstance.getTemplate().getRewardList(rewardType2)) == null || rewardList.isEmpty()) continue;
            switch (rewardType2) {
                case RATED_GROUPED: {
                    arrayList.add(StringHolder.getInstance().getNotNull(player, "drop.rated_grouped"));
                    RewardListInfo.ratedGroupedRewardList(arrayList, npcInstance, rewardList, player, d);
                    continue block7;
                }
                case NOT_RATED_GROUPED: {
                    arrayList.add(StringHolder.getInstance().getNotNull(player, "drop.not_rated_grouped"));
                    RewardListInfo.notRatedGroupedRewardList(arrayList, rewardList, d, player);
                    continue block7;
                }
                case NOT_RATED_NOT_GROUPED: {
                    arrayList.add(StringHolder.getInstance().getNotNull(player, "drop.not_rated_not_grouped"));
                    RewardListInfo.notGroupedRewardList(arrayList, rewardList, 1.0, d, player);
                    continue block7;
                }
                case RATED_NOT_GROUPED: {
                    arrayList.add(StringHolder.getInstance().getNotNull(player, "drop.rated_not_grouped"));
                    RewardListInfo.ratedNotGroupedRewardList(arrayList, npcInstance, rewardList, player, d);
                    continue block7;
                }
                case SWEEP: {
                    arrayList.add(StringHolder.getInstance().getNotNull(player, "drop.sweep"));
                    RewardListInfo.notGroupedRewardList(arrayList, rewardList, Config.RATE_DROP_SPOIL * player.getRateSpoil(), d, player);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n3 = arrayList.size() / Config.ALT_NPC_SHIFTCLICK_ITEM_COUNT;
        n = Math.min(n, n3);
        int n4 = n * Config.ALT_NPC_SHIFTCLICK_ITEM_COUNT;
        int n5 = Math.max(0, Math.min((n + 1) * Config.ALT_NPC_SHIFTCLICK_ITEM_COUNT - 1, arrayList.size() - 1));
        if (!arrayList.isEmpty()) {
            for (int i = n4; i <= n5; ++i) {
                stringBuilder.append((String)arrayList.get(i));
            }
        }
        npcHtmlMessage.replace("%info%", stringBuilder.toString());
        stringBuilder.setLength(0);
        stringBuilder.append("<table><tr>");
        for (int i = 0; i <= n3; ++i) {
            stringBuilder.append("<td>");
            if (i == n) {
                stringBuilder.append(i + 1);
            } else if (rewardType != null) {
                stringBuilder.append("<a action=\"bypass -h scripts_actions.RewardListInfo:showReward ").append(i).append(' ').append(rewardType).append("\">").append(i + 1).append("</a>");
            } else {
                stringBuilder.append("<a action=\"bypass -h scripts_actions.RewardListInfo:showReward ").append(i).append("\">").append(i + 1).append("</a>");
            }
            stringBuilder.append("</td>");
        }
        stringBuilder.append("</tr></table>");
        npcHtmlMessage.replace("%paging%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public static void ratedGroupedRewardList(List<String> list, NpcInstance npcInstance, RewardList rewardList, Player player, double d) {
        for (RewardGroup rewardGroup : rewardList) {
            double d2;
            List list2 = rewardGroup.getItems();
            double d3 = rewardGroup.getChance();
            double d4 = d;
            double d5 = npcInstance instanceof RaidBossInstance ? Config.RATE_DROP_RAIDBOSS * (double)player.getBonus().getDropRaidItems() : (npcInstance.isSiegeGuard() ? Config.RATE_DROP_SIEGE_GUARD : Config.RATE_DROP_ITEMS * player.getRateItems());
            double d6 = Config.RATE_DROP_ADENA * player.getRateAdena();
            double d7 = Config.RATE_DROP_SEAL_STONES * player.getRateSealStones();
            if (rewardGroup.isAdena()) {
                if (d6 == 0.0) continue;
                d2 = d6;
                if (d4 > 10.0) {
                    d4 *= rewardGroup.getChance() / 1000000.0;
                }
                d2 *= d4;
            } else if (rewardGroup.isSealStone()) {
                if (d7 == 0.0) continue;
                d2 = d7;
                d2 = rewardGroup.notRate() ? Math.min(d4, 1.0) : (d2 *= d4);
            } else {
                if (d5 == 0.0) continue;
                d2 = d5;
                d2 = rewardGroup.notRate() ? Math.min(d4, 1.0) : (d2 *= d4);
            }
            double d8 = d2;
            list.add(RewardListInfo.a(rewardGroup, player));
            for (RewardData rewardData : list2) {
                list.add(RewardListInfo.a(rewardData, d8, player));
            }
        }
    }

    public static void notRatedGroupedRewardList(List<String> list, RewardList rewardList, double d, Player player) {
        for (RewardGroup rewardGroup : rewardList) {
            List list2 = rewardGroup.getItems();
            list.add(RewardListInfo.a(rewardGroup, player));
            for (RewardData rewardData : list2) {
                list.add(RewardListInfo.a(rewardData, 1.0, player));
            }
        }
    }

    public static void notGroupedRewardList(List<String> list, RewardList rewardList, double d, double d2, Player player) {
        for (RewardGroup rewardGroup : rewardList) {
            List list2 = rewardGroup.getItems();
            double d3 = d2;
            if (d == 0.0) continue;
            double d4 = d;
            d4 = rewardGroup.notRate() ? Math.min(d3, 1.0) : (d4 *= d3);
            double d5 = Math.ceil(d4);
            for (RewardData rewardData : list2) {
                list.add(RewardListInfo.a(rewardData, d5, player));
            }
        }
    }

    public static void ratedNotGroupedRewardList(List<String> list, NpcInstance npcInstance, RewardList rewardList, Player player, double d) {
        for (RewardGroup rewardGroup : rewardList) {
            double d2;
            List list2 = rewardGroup.getItems();
            double d3 = d;
            double d4 = npcInstance instanceof RaidBossInstance ? Config.RATE_DROP_RAIDBOSS * (double)player.getBonus().getDropRaidItems() : (npcInstance.isSiegeGuard() ? Config.RATE_DROP_SIEGE_GUARD : Config.RATE_DROP_ITEMS * player.getRateItems());
            double d5 = Config.RATE_DROP_ADENA * player.getRateAdena();
            double d6 = Config.RATE_DROP_SEAL_STONES * player.getRateSealStones();
            if (rewardGroup.isAdena()) {
                if (d5 == 0.0) continue;
                d2 = d5;
                if (d3 > 10.0) {
                    d3 *= rewardGroup.getChance() / 1000000.0;
                }
                d2 *= d3;
            } else if (rewardGroup.isSealStone()) {
                if (d6 == 0.0) continue;
                d2 = d6;
                d2 = rewardGroup.notRate() ? Math.min(d3, 1.0) : (d2 *= d3);
            } else {
                if (d4 == 0.0) continue;
                d2 = d4;
                d2 = rewardGroup.notRate() ? Math.min(d3, 1.0) : (d2 *= d3);
            }
            double d7 = d2;
            for (RewardData rewardData : list2) {
                list.add(RewardListInfo.a(rewardData, d7, player));
            }
        }
    }

    private static String a(RewardGroup rewardGroup, Player player) {
        return StringUtils.replace((String)StringHolder.getInstance().getNotNull(player, "drop.group.html"), (String)"%group_chance%", (String)b.format(rewardGroup.getChance() / 1000000.0));
    }

    private static String a(RewardData rewardData, double d, Player player) {
        String string = rewardData.getItem().getIcon();
        if (string == null || string.equals("")) {
            string = "icon.etc_question_mark_i00";
        }
        return StringUtils.replaceEach((String)StringHolder.getInstance().getNotNull(player, "drop.rewardData.html"), (String[])new String[]{"%icon%", "%item%", "%drop_min%", "%drop_max%", "%chance%"}, (String[])new String[]{string, HtmlUtils.htmlItemName((int)rewardData.getItemId()), String.valueOf(rewardData.getMinDrop()), String.valueOf(Math.round((double)rewardData.getMaxDrop() * (rewardData.notRate() ? 1.0 : d))), b.format(Math.min(1.0, rewardData.getChance() / 1000000.0))});
    }

    public void showReward(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        int n = 0;
        if (stringArray.length > 0) {
            try {
                n = Integer.parseInt(stringArray[0]);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (stringArray.length > 1) {
            try {
                RewardListInfo.showRewardHtml(player, npcInstance, n, RewardType.valueOf((String)stringArray[1]));
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            RewardListInfo.showRewardHtml(player, npcInstance, n);
        }
    }

    public void showAllReward(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        int n = 0;
        if (stringArray.length > 0) {
            try {
                n = Integer.parseInt(stringArray[0]);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        RewardListInfo.showRewardHtml(player, npcInstance, n);
    }

    static {
        b.setMaximumFractionDigits(4);
        c.setMinimumFractionDigits(2);
    }
}
