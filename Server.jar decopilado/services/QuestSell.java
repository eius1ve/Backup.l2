/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.instancemanager.QuestManager
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
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
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestSell
extends Functions
implements ScriptFile {
    private static final Logger ec = LoggerFactory.getLogger(QuestSell.class);
    private static final String hI = "scripts/services";
    private static final String hJ = "-h scripts_services.QuestSell:";
    private static final int bFU = 5;

    private static Map<Set<Quest>, List<Pair<Integer, Long>>> d(String string) {
        return QuestSell.a(string, false);
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
                ec.warn("QuestSellService: Can't process quest sell list entry \"" + string2 + "\"");
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
                    ec.warn("QuestSellService: Can't get quest \"" + (String)object2 + "\" in \"" + string2 + "\"");
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
                    ec.warn("QuestSellService: Can't get price of \"" + string2 + "\"");
                    continue block0;
                }
                Integer n3 = Integer.parseInt(string5.substring(0, n2).trim());
                Long l = Long.parseLong(string5.substring(n2 + 1).trim());
                if (ItemHolder.getInstance().getTemplate(n3.intValue()) == null) {
                    if (!bl) continue block0;
                    ec.warn("QuestSellService: Can't get item \"" + n3 + "\" of \"" + string2 + "\"");
                    continue block0;
                }
                object.add(Pair.of((Object)n3, (Object)l));
            }
            if (linkedHashMap.containsKey(set)) {
                if (!bl) continue;
                ec.warn("QuestSellService: Quests already defined \"" + string3 + "\"");
                continue;
            }
            linkedHashMap.put(set, Collections.unmodifiableList(object));
        }
        return linkedHashMap;
    }

    private static List<String> a(Player player, Collection<Quest> collection) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Quest quest : collection) {
            String string = null;
            QuestState questState = player.getQuestState(quest);
            if (questState != null) {
                switch (questState.getState()) {
                    case 1: 
                    case 2: {
                        string = StringHolder.getInstance().getNotNull(player, "scripts.services.QuestSell.questInfoHave");
                        break;
                    }
                    case 3: {
                        string = StringHolder.getInstance().getNotNull(player, "scripts.services.QuestSell.questInfoDone");
                        break;
                    }
                    default: {
                        string = StringHolder.getInstance().getNotNull(player, "scripts.services.QuestSell.questInfo");
                        break;
                    }
                }
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
            arrayList.add(QuestSell.a(player, pair));
        }
        return arrayList;
    }

    private static String a(Player player, int n, Pair<Set<Quest>, List<Pair<Integer, Long>>> pair) {
        String string = StringHolder.getInstance().getNotNull(player, "scripts.services.QuestSell.questSellInfo");
        Set set = (Set)pair.getKey();
        List list = (List)pair.getValue();
        string = string.replace("%quests_list%", String.join((CharSequence)"<br1>", QuestSell.a(player, set)));
        string = string.replace("%price_list%", String.join((CharSequence)"<br1>", QuestSell.b(player, list)));
        string = string.replace("%bypass%", "-h scripts_services.QuestSell:buyQuestsListByIdx " + n);
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
        return (n2 + 1) * 5 < n;
    }

    private static String a(Player player, int n, Object[] objectArray, String string) {
        String string2 = hJ + string + " %d";
        return QuestSell.a(player, n > 0 ? String.format(string2, n - 1).trim() : null, n, QuestSell.e(objectArray.length, n) ? String.format(string2, n + 1).trim() : null);
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
            if (!QuestSell.a(player, (Collection<Quest>)entry.getKey())) continue;
            arrayList.add(Pair.of(entry.getKey(), entry.getValue()));
        }
        return arrayList.toArray(new Pair[arrayList.size()]);
    }

    private static Pair<Set<Quest>, List<Pair<Integer, Long>>>[] a(Player player) {
        return QuestSell.a(player, QuestSell.d(Config.QUEST_SELL_QUEST_PRICES));
    }

    private static void v(Player player, int n) {
        int n2;
        Object[] objectArray = QuestSell.a(player);
        StringBuilder stringBuilder = new StringBuilder();
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("scripts/services/quests_sell_list.htm");
        int n3 = n2 + 5;
        for (n2 = 5 * n; n2 < n3 && n2 < objectArray.length; ++n2) {
            Pair<Set<Quest>, List<Pair<Integer, Long>>> pair = objectArray[n2];
            stringBuilder.append(QuestSell.a(player, n2, pair));
        }
        npcHtmlMessage.replace("%list%", stringBuilder.toString());
        npcHtmlMessage.replace("%paging%", QuestSell.a(player, n, objectArray, "listAvailableQuestsForSell"));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void listAvailableQuestsForSell() {
        Player player = this.getSelf();
        if (!Config.QUEST_SELL_ENABLE) {
            player.sendMessage(new CustomMessage("common.Disabled", player, new Object[0]));
            return;
        }
        QuestSell.v(player, 0);
    }

    public void listAvailableQuestsForSell(String[] stringArray) {
        Player player = this.getSelf();
        if (!Config.QUEST_SELL_ENABLE) {
            player.sendMessage(new CustomMessage("common.Disabled", player, new Object[0]));
            return;
        }
        QuestSell.v(player, Integer.parseInt(stringArray[0]));
    }

    private static void a(Player player, Collection<Quest> collection, Collection<Pair<Integer, Long>> collection2) {
        for (Pair<Integer, Long> pair : collection2) {
            if (ItemFunctions.getItemCount((Playable)player, (int)((Integer)pair.getKey())) >= (Long)pair.getValue()) continue;
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
            return;
        }
        for (Pair<Integer, Long> pair : collection2) {
            if (ItemFunctions.removeItem((Playable)player, (int)((Integer)pair.getKey()), (long)((Long)pair.getValue()), (boolean)true) >= (Long)pair.getValue()) continue;
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
            return;
        }
        for (Quest quest : collection) {
            player.setQuestState(quest.newQuestState(player, 3));
            Log.service((String)"QuestSell", (Player)player, (String)("bought a quest " + quest.getName()));
        }
    }

    public void buyQuestsListByIdx(String[] stringArray) {
        Player player = this.getSelf();
        if (!Config.QUEST_SELL_ENABLE) {
            player.sendMessage(new CustomMessage("common.Disabled", player, new Object[0]));
            return;
        }
        Pair<Set<Quest>, List<Pair<Integer, Long>>>[] pairArray = QuestSell.a(player);
        int n = Integer.parseInt(stringArray[0]);
        Pair<Set<Quest>, List<Pair<Integer, Long>>> pair = pairArray[n];
        QuestSell.a(player, (Collection)pair.getLeft(), (Collection)pair.getRight());
        this.listAvailableQuestsForSell();
    }

    public void onLoad() {
        ec.info("QuestSellService: Loading ...");
        QuestSell.a(Config.QUEST_SELL_QUEST_PRICES, true);
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
