/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import java.util.HashMap;
import java.util.Map;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _378_MagnificentFeast
extends Quest
implements ScriptFile {
    private static int aUh = 30594;
    private static int aUi = 5956;
    private static int aUj = 5957;
    private static int aUk = 5958;
    private static int aUl = 4421;
    private static int aUm = 5959;
    private static int aUn = 1455;
    private static int aUo = 1456;
    private static int aUp = 1457;
    private Map<Integer, int[]> ce = new HashMap<Integer, int[]>();

    public _378_MagnificentFeast() {
        super(0);
        this.addStartNpc(aUh);
        this.ce.put(9, new int[]{847, 1, 5700});
        this.ce.put(10, new int[]{846, 2, 0});
        this.ce.put(12, new int[]{909, 1, 25400});
        this.ce.put(17, new int[]{846, 2, 1200});
        this.ce.put(18, new int[]{879, 1, 6900});
        this.ce.put(20, new int[]{890, 2, 8500});
        this.ce.put(33, new int[]{879, 1, 8100});
        this.ce.put(34, new int[]{910, 1, 0});
        this.ce.put(36, new int[]{910, 1, 0});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getState();
        int n2 = questState.getCond();
        int n3 = questState.getInt("score");
        if (string.equalsIgnoreCase("quest_accept") && n == 1) {
            string2 = "warehouse_chief_ranspo_q0378_03.htm";
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("378_1") && n == 2) {
            if (n2 == 1 && questState.getQuestItemsCount(aUi) > 0L) {
                string2 = "warehouse_chief_ranspo_q0378_05.htm";
                questState.takeItems(aUi, 1L);
                questState.setCond(2);
                questState.set("score", String.valueOf(n3 + 1));
            } else {
                string2 = "warehouse_chief_ranspo_q0378_08.htm";
            }
        } else if (string.equalsIgnoreCase("378_2") && n == 2) {
            if (n2 == 1 && questState.getQuestItemsCount(aUj) > 0L) {
                string2 = "warehouse_chief_ranspo_q0378_06.htm";
                questState.takeItems(aUj, 1L);
                questState.setCond(2);
                questState.set("score", String.valueOf(n3 + 2));
            } else {
                string2 = "warehouse_chief_ranspo_q0378_08.htm";
            }
        } else if (string.equalsIgnoreCase("378_3") && n == 2) {
            if (n2 == 1 && questState.getQuestItemsCount(aUk) > 0L) {
                string2 = "warehouse_chief_ranspo_q0378_07.htm";
                questState.takeItems(aUk, 1L);
                questState.setCond(2);
                questState.set("score", String.valueOf(n3 + 4));
            } else {
                string2 = "warehouse_chief_ranspo_q0378_08.htm";
            }
        } else if (string.equalsIgnoreCase("378_5") && n == 2) {
            if (n2 == 2 && questState.getQuestItemsCount(aUl) > 0L) {
                string2 = "warehouse_chief_ranspo_q0378_12.htm";
                questState.takeItems(aUl, 1L);
                questState.setCond(3);
            } else {
                string2 = "warehouse_chief_ranspo_q0378_10.htm";
            }
        } else if (string.equalsIgnoreCase("378_6") && n == 2) {
            if (n2 == 3 && questState.getQuestItemsCount(aUn) > 0L) {
                string2 = "warehouse_chief_ranspo_q0378_14.htm";
                questState.takeItems(aUn, 1L);
                questState.setCond(4);
                questState.set("score", String.valueOf(n3 + 8));
            } else {
                string2 = "warehouse_chief_ranspo_q0378_17.htm";
            }
        } else if (string.equalsIgnoreCase("378_7") && n == 2) {
            if (n2 == 3 && questState.getQuestItemsCount(aUo) > 0L) {
                string2 = "warehouse_chief_ranspo_q0378_15.htm";
                questState.takeItems(aUo, 1L);
                questState.setCond(4);
                questState.set("score", String.valueOf(n3 + 16));
            } else {
                string2 = "warehouse_chief_ranspo_q0378_17.htm";
            }
        } else if (string.equalsIgnoreCase("378_8") && n == 2) {
            if (n2 == 3 && questState.getQuestItemsCount(aUp) > 0L) {
                string2 = "warehouse_chief_ranspo_q0378_16.htm";
                questState.takeItems(aUp, 1L);
                questState.setCond(4);
                questState.set("score", String.valueOf(n3 + 32));
            } else {
                string2 = "warehouse_chief_ranspo_q0378_17.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        if (npcInstance.getNpcId() != aUh) {
            return string;
        }
        int n = questState.getState();
        int n2 = questState.getCond();
        if (n == 1) {
            if (questState.getPlayer().getLevel() < 20) {
                string = "warehouse_chief_ranspo_q0378_01.htm";
                questState.exitCurrentQuest(true);
            } else {
                string = "warehouse_chief_ranspo_q0378_02.htm";
                questState.setCond(0);
            }
        } else if (n2 == 1 && n == 2) {
            string = "warehouse_chief_ranspo_q0378_04.htm";
        } else if (n2 == 2 && n == 2) {
            string = questState.getQuestItemsCount(aUl) > 0L ? "warehouse_chief_ranspo_q0378_11.htm" : "warehouse_chief_ranspo_q0378_10.htm";
        } else if (n2 == 3 && n == 2) {
            string = "warehouse_chief_ranspo_q0378_13.htm";
        } else if (n2 == 4 && n == 2) {
            int[] nArray = this.ce.get(questState.getInt("score"));
            if (questState.getQuestItemsCount(aUm) > 0L && nArray != null) {
                string = "warehouse_chief_ranspo_q0378_20.htm";
                questState.takeItems(aUm, 1L);
                questState.giveItems(nArray[0], (long)nArray[1]);
                if (nArray[2] > 0) {
                    questState.giveItems(57, (long)nArray[2]);
                }
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else {
                string = "warehouse_chief_ranspo_q0378_19.htm";
            }
        }
        return string;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
