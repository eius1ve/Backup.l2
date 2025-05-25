/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _380_BringOutTheFlavorOfIngredients
extends Quest
implements ScriptFile {
    private static final int aUv = 30069;
    private static final int aUw = 5895;
    private static final int aUxx = 5896;
    private static final int aUy = 5897;
    private static final int aUz = 1831;
    private static final int aUA = 5959;
    private static final int aUB = 5960;
    private static final int aUC = 55;
    private static final int aUD = 20205;
    private static final int aUE = 20206;
    private static final int aUF = 20225;
    private static final int[][] H = new int[][]{{1, 0, 20205, 0, 5895, 4, 10, 1}, {1, 0, 20206, 0, 5896, 20, 50, 1}, {1, 0, 20225, 0, 5897, 10, 50, 1}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _380_BringOutTheFlavorOfIngredients() {
        super(0);
        this.addStartNpc(30069);
        for (int i = 0; i < H.length; ++i) {
            this.addKillId(new int[]{H[i][2]});
            this.addQuestItem(new int[]{H[i][4]});
        }
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        if (string.equalsIgnoreCase("rollant_q0380_05.htm")) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("rollant_q0380_12.htm")) {
            questState.giveItems(5959, 1L);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        if (n == 30069) {
            if (n2 == 0) {
                if (questState.getPlayer().getLevel() >= 24) {
                    string = "rollant_q0380_02.htm";
                } else {
                    string = "rollant_q0380_01.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1) {
                string = "rollant_q0380_06.htm";
            } else if (n2 == 2 && questState.getQuestItemsCount(1831) >= 2L) {
                questState.takeItems(1831, 2L);
                questState.takeItems(5895, -1L);
                questState.takeItems(5896, -1L);
                questState.takeItems(5897, -1L);
                string = "rollant_q0380_07.htm";
                questState.setCond(3);
                questState.setState(2);
            } else if (n2 == 2) {
                string = "rollant_q0380_06.htm";
            } else if (n2 == 3) {
                string = "rollant_q0380_08.htm";
                questState.setCond(4);
            } else if (n2 == 4) {
                string = "rollant_q0380_09.htm";
                questState.setCond(5);
            }
            if (n2 == 5) {
                string = "rollant_q0380_10.htm";
                questState.setCond(6);
            }
            if (n2 == 6) {
                questState.giveItems(5960, 1L);
                if (Rnd.chance((int)55)) {
                    string = "rollant_q0380_11.htm";
                } else {
                    string = "rollant_q0380_14.htm";
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(true);
                }
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < H.length; ++i) {
            if (n2 != H[i][0] || n != H[i][2] || H[i][3] != 0 && questState.getQuestItemsCount(H[i][3]) <= 0L) continue;
            if (H[i][5] == 0) {
                questState.rollAndGive(H[i][4], H[i][7], (double)H[i][6]);
                continue;
            }
            if (!questState.rollAndGive(H[i][4], H[i][7], H[i][7], H[i][5], (double)H[i][6]) || H[i][1] == n2 || H[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(H[i][1]).intValue());
            questState.setState(2);
        }
        if (n2 == 1 && questState.getQuestItemsCount(5895) >= 4L && questState.getQuestItemsCount(5896) >= 20L && questState.getQuestItemsCount(5897) >= 10L) {
            questState.setCond(2);
            questState.setState(2);
        }
        return null;
    }
}
