/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.instancemanager.QuestManager
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestSellCb
extends Functions
implements ScriptFile {
    private static final Logger ed = LoggerFactory.getLogger(QuestSellCb.class);
    private static final String hK = "-h scripts_services.QuestSellCb:";
    private static final int bFV = 10;

    private static Map<Set<Quest>, List<Pair<Integer, Long>>> d(String string) {
        return QuestSellCb.a(string, false);
    }

    private static Map<Set<Quest>, List<Pair<Integer, Long>>> a(String string, boolean bl) {
        LinkedHashMap<Set<Quest>, List<Pair<Integer, Long>>> linkedHashMap = new LinkedHashMap<Set<Quest>, List<Pair<Integer, Long>>>();
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        block0: while (stringTokenizer.hasMoreTokens()) {
            Object object;
            Object object2;
            String string2 = stringTokenizer.nextToken().trim();
            if (string2.isEmpty()) continue;
            int n = string2.indexOf(58);
            if (n <= 0) {
                if (!bl) continue;
                ed.warn("QuestSellService: Can't process quest sell list entry \"" + string2 + "\"");
                continue;
            }
            String string3 = string2.substring(0, n).trim();
            String string4 = string2.substring(n + 1).trim();
            StringTokenizer stringTokenizer2 = new StringTokenizer(string3, ",");
            Set<Quest> set = new LinkedHashSet();
            while (stringTokenizer2.hasMoreTokens()) {
                object2 = stringTokenizer2.nextToken();
                object = QuestManager.getQuest2((String)object2);
                if (object == null) {
                    if (!bl) continue block0;
                    ed.warn("QuestSellService: Can't get quest \"" + (String)object2 + "\" in \"" + string2 + "\"");
                    continue block0;
                }
                set.add((Quest)object);
            }
            set = Collections.unmodifiableSet(set);
            object2 = new StringTokenizer(string4, ",");
            object = new ArrayList();
            while (((StringTokenizer)object2).hasMoreTokens()) {
                String string5 = ((StringTokenizer)object2).nextToken().trim();
                int n2 = string5.indexOf(45);
                if (n2 <= 0) {
                    if (!bl) continue block0;
                    ed.warn("QuestSellService: Can't get price of \"" + string2 + "\"");
                    continue block0;
                }
                int n3 = Integer.parseInt(string5.substring(0, n2).trim());
                Long l = Long.parseLong(string5.substring(n2 + 1).trim());
                if (ItemHolder.getInstance().getTemplate(n3) == null) {
                    if (!bl) continue block0;
                    ed.warn("QuestSellService: Can't get item \"" + n3 + "\" of \"" + string2 + "\"");
                    continue block0;
                }
                object.add(Pair.of((Object)n3, (Object)l));
            }
            if (linkedHashMap.containsKey(set)) {
                if (!bl) continue;
                ed.warn("QuestSellService: Quests already defined \"" + string3 + "\"");
                continue;
            }
            linkedHashMap.put(set, Collections.unmodifiableList(object));
        }
        return linkedHashMap;
    }

    private static List<String> a(Player player, Collection<Quest> collection) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Quest quest : collection) {
            String string;
            QuestState questState = player.getQuestState(quest);
            if (questState != null) {
                string = switch (questState.getState()) {
                    case 1, 2 -> StringHolder.getInstance().getNotNull(player, "scripts.services.QuestSell.questInfoHave");
                    case 3 -> StringHolder.getInstance().getNotNull(player, "scripts.services.QuestSell.questInfoDone");
                    default -> StringHolder.getInstance().getNotNull(player, "scripts.services.QuestSell.questInfo");
                };
            } else {
                string = StringHolder.getInstance().getNotNull(player, "scripts.services.QuestSell.questInfo");
            }
            string = string.replace("%quest_name%", quest.getDescr(player));
            string = string.replace("%quest_id%", String.valueOf(quest.getQuestIntId()));
            arrayList.add(string);
        }
        return arrayList;
    }

    private static String a(Player player, Pair<Integer, Long> pair) {
        int n = (Integer)pair.getKey();
        long l = (Long)pair.getValue();
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
        String string = StringHolder.getInstance().getNotNull(player, "scripts.services.QuestSell.requiredPriceItemInfo");
        string = string.replace("%item_id%", String.valueOf(itemTemplate.getItemId()));
        string = string.replace("%item_name%", itemTemplate.getName());
        string = string.replace("%item_amount%", String.valueOf(l));
        return string;
    }

    private static List<String> b(Player player, Collection<Pair<Integer, Long>> collection) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Pair<Integer, Long> pair : collection) {
            arrayList.add(QuestSellCb.a(player, pair));
        }
        return arrayList;
    }

    private static String a(Player player, int n, Pair<Set<Quest>, List<Pair<Integer, Long>>> pair) {
        String string = StringHolder.getInstance().getNotNull(player, "scripts.services.QuestSell.questSellInfo");
        Set set = (Set)pair.getKey();
        List list = (List)pair.getValue();
        string = string.replace("%quests_list%", String.join((CharSequence)"<br1>", QuestSellCb.a(player, set)));
        string = string.replace("%price_list%", String.join((CharSequence)"<br1>", QuestSellCb.b(player, list)));
        string = string.replace("%bypass%", "-h scripts_services.QuestSellCb:buyQuestsListByIdx " + n);
        return string;
    }

    private static String a(Player player, String string, int n, String string2) {
        String string3 = StringHolder.getInstance().getNotNull(player, "scripts.services.QuestSell.paging");
        string3 = string3.replace("%prev_button%", string != null ? "<button value=\"&$1037;\" action=\"bypass %prev_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">".replace("%prev_bypass%", string) : "");
        string3 = string3.replace("%curr_page%", Integer.toString(n + 1));
        string3 = string3.replace("%next_button%", string2 != null ? "<button value=\"&$1038;\" action=\"bypass %next_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">".replace("%next_bypass%", string2) : "");
        return string3;
    }

    private static boolean e(int n, int n2) {
        return (n2 + 1) * 10 < n;
    }

    private static String a(Player player, int n, Object[] objectArray, String string) {
        String string2 = hK + string + " %d";
        return QuestSellCb.a(player, n > 0 ? String.format(string2, n - 1).trim() : null, n, QuestSellCb.e(objectArray.length, n) ? String.format(string2, n + 1).trim() : null);
    }

    private static boolean a(Player player, Collection<Quest> collection) {
        for (Quest quest : collection) {
            QuestState questState;
            if (!quest.isVisible() || (questState = player.getQuestState(quest)) != null && questState.getState() == 3) continue;
            return true;
        }
        return false;
    }

    private static Pair<Set<Quest>, List<Pair<Integer, Long>>>[] a(Player player, Map<Set<Quest>, List<Pair<Integer, Long>>> map) {
        ArrayList<Pair> arrayList = new ArrayList<Pair>();
        for (Map.Entry<Set<Quest>, List<Pair<Integer, Long>>> entry : map.entrySet()) {
            if (!QuestSellCb.a(player, (Collection<Quest>)entry.getKey())) continue;
            arrayList.add(Pair.of(entry.getKey(), entry.getValue()));
        }
        return arrayList.toArray(new Pair[arrayList.size()]);
    }

    private static Pair<Set<Quest>, List<Pair<Integer, Long>>>[] a(Player player) {
        return QuestSellCb.a(player, QuestSellCb.d(Config.QUEST_SELL_QUEST_PRICES));
    }

    private static void v(Player player, int n) {
        int n2;
        Object[] objectArray = QuestSellCb.a(player);
        StringBuilder stringBuilder = new StringBuilder();
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/quests_sell_list.htm", player);
        int n3 = n2 + 10;
        for (n2 = 10 * n; n2 < n3 && n2 < objectArray.length; ++n2) {
            Pair<Set<Quest>, List<Pair<Integer, Long>>> pair = objectArray[n2];
            stringBuilder.append(QuestSellCb.a(player, n2, pair));
        }
        string = string.replace("%list%", stringBuilder.toString());
        string = string.replace("%paging%", QuestSellCb.a(player, n, objectArray, "listAvailableQuestsForSell"));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void listAvailableQuestsForSell() {
        Player player = this.getSelf();
        if (!Config.QUEST_SELL_ENABLE) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/service_disabled.htm");
            return;
        }
        QuestSellCb.v(player, 0);
    }

    public void listAvailableQuestsForSell(String[] stringArray) {
        Player player = this.getSelf();
        if (!Config.QUEST_SELL_ENABLE) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/service_disabled.htm");
            return;
        }
        QuestSellCb.v(player, Integer.parseInt(stringArray[0]));
    }

    private static boolean a(Player player, Collection<Quest> collection, Collection<Pair<Integer, Long>> collection2) {
        for (Pair<Integer, Long> object : collection2) {
            if (ItemFunctions.getItemCount((Playable)player, (int)((Integer)object.getKey())) >= (Long)object.getValue()) continue;
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_incorrect_items.htm", player), (Player)player);
            return false;
        }
        for (Pair<Integer, Long> pair : collection2) {
            if (ItemFunctions.removeItem((Playable)player, (int)((Integer)pair.getKey()), (long)((Long)pair.getValue()), (boolean)true) >= (Long)pair.getValue()) continue;
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_incorrect_items.htm", player), (Player)player);
            return false;
        }
        boolean bl = false;
        for (Quest quest : collection) {
            player.setQuestState(quest.newQuestState(player, 3));
            Log.service((String)"QuestSell", (Player)player, (String)("bought a quest " + quest.getName()));
            bl = true;
        }
        return bl;
    }

    public void buyQuestsListByIdx(String[] stringArray) {
        int n;
        Player player = this.getSelf();
        if (!Config.QUEST_SELL_ENABLE) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/service_disabled.htm");
            return;
        }
        Pair<Set<Quest>, List<Pair<Integer, Long>>>[] pairArray = QuestSellCb.a(player);
        Pair<Set<Quest>, List<Pair<Integer, Long>>> pair = pairArray[n = Integer.parseInt(stringArray[0])];
        if (QuestSellCb.a(player, (Collection)pair.getLeft(), (Collection)pair.getRight())) {
            this.listAvailableQuestsForSell();
        } else {
            player.sendActionFailed();
        }
    }

    public void onLoad() {
        ed.info("QuestSellService: Loading ...");
        QuestSellCb.a(Config.QUEST_SELL_QUEST_PRICES, true);
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
