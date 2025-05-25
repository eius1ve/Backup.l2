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

import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _418_PathToArtisan
extends Quest
implements ScriptFile {
    private static final int bcB = 30527;
    private static final int bcC = 30317;
    private static final int bcD = 30298;
    private static final int bcE = 1632;
    private static final int bcF = 1636;
    private static final int bcG = 1637;
    private static final int bcH = 1633;
    private static final int bcI = 1638;
    private static final int bcJ = 1639;
    private static final int bcK = 1640;
    private static final int bcL = 1634;
    private static final int bcM = 1641;
    private static final int bcN = 1635;
    private static final int bcO = 20389;
    private static final int bcP = 20390;
    private static final int bcQ = 20017;
    private static final int[][] T = new int[][]{{1, 0, 20389, 1632, 1636, 10, 35, 1}, {1, 0, 20390, 1632, 1637, 2, 25, 1}, {5, 6, 20017, 1639, 1640, 1, 20, 1}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _418_PathToArtisan() {
        super(0);
        this.addStartNpc(30527);
        this.addTalkId(new int[]{30317, 30298});
        for (int i = 0; i < T.length; ++i) {
            this.addKillId(new int[]{T[i][2]});
            this.addQuestItem(new int[]{T[i][4]});
        }
        this.addQuestItem(new int[]{1632, 1633, 1641, 1638, 1639, 1634});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("blacksmith_silvery_q0418_06.htm")) {
            questState.giveItems(1632, 1L);
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("blacksmith_kluto_q0418_04.htm") || string.equalsIgnoreCase("blacksmith_kluto_q0418_07.htm")) {
            questState.giveItems(1638, 1L);
            questState.setCond(4);
            questState.setState(2);
        } else if (string.equalsIgnoreCase("blacksmith_pinter_q0418_03.htm")) {
            questState.takeItems(1638, -1L);
            questState.giveItems(1639, 1L);
            questState.setCond(5);
            questState.setState(2);
        } else if (string.equalsIgnoreCase("blacksmith_pinter_q0418_06.htm")) {
            questState.takeItems(1640, -1L);
            questState.takeItems(1639, -1L);
            questState.giveItems(1641, 1L);
            questState.giveItems(1634, 1L);
            questState.setCond(7);
            questState.setState(2);
        } else if (string.equalsIgnoreCase("blacksmith_kluto_q0418_10.htm") || string.equalsIgnoreCase("blacksmith_kluto_q0418_12.htm")) {
            questState.takeItems(1633, -1L);
            questState.takeItems(1634, -1L);
            questState.takeItems(1641, -1L);
            if (questState.getPlayer().getClassId().getLevel() == 1) {
                questState.giveItems(1635, 1L);
                if (!questState.getPlayer().getVarB("prof1")) {
                    questState.getPlayer().setVar("prof1", "1", -1L);
                    questState.addExpAndSp(3200L, 2790L);
                    this.giveExtraReward(questState.getPlayer());
                }
            }
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        if (n == 30527) {
            if (questState.getQuestItemsCount(1635) != 0L) {
                string = "blacksmith_silvery_q0418_04.htm";
                questState.exitCurrentQuest(true);
            } else if (n2 == 0) {
                if (questState.getPlayer().getClassId().getId() != 53) {
                    string = questState.getPlayer().getClassId().getId() == 56 ? "blacksmith_silvery_q0418_02a.htm" : "blacksmith_silvery_q0418_02.htm";
                    questState.exitCurrentQuest(true);
                } else if (questState.getPlayer().getLevel() < 18) {
                    string = "blacksmith_silvery_q0418_03.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "blacksmith_silvery_q0418_01.htm";
                }
            } else if (n2 == 1) {
                string = "blacksmith_silvery_q0418_07.htm";
            } else if (n2 == 2) {
                questState.takeItems(1636, -1L);
                questState.takeItems(1637, -1L);
                questState.takeItems(1632, -1L);
                questState.giveItems(1633, 1L);
                string = "blacksmith_silvery_q0418_08.htm";
                questState.setCond(3);
            } else if (n2 == 3) {
                string = "blacksmith_silvery_q0418_09.htm";
            }
        } else if (n == 30317) {
            if (n2 == 3) {
                string = "blacksmith_kluto_q0418_01.htm";
            } else if (n2 == 4 || n2 == 5) {
                string = "blacksmith_kluto_q0418_08.htm";
            } else if (n2 == 7) {
                string = "blacksmith_kluto_q0418_09.htm";
            }
        } else if (n == 30298) {
            if (n2 == 4) {
                string = "blacksmith_pinter_q0418_01.htm";
            } else if (n2 == 5) {
                string = "blacksmith_pinter_q0418_04.htm";
            } else if (n2 == 6) {
                string = "blacksmith_pinter_q0418_05.htm";
            } else if (n2 == 7) {
                string = "blacksmith_pinter_q0418_07.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < T.length; ++i) {
            if (n2 != T[i][0] || n != T[i][2] || T[i][3] != 0 && questState.getQuestItemsCount(T[i][3]) <= 0L) continue;
            if (T[i][5] == 0) {
                questState.rollAndGive(T[i][4], T[i][7], (double)T[i][6]);
                continue;
            }
            if (!questState.rollAndGive(T[i][4], T[i][7], T[i][7], T[i][5], (double)T[i][6]) || T[i][1] == n2 || T[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(T[i][1]).intValue());
            questState.setState(2);
        }
        if (n2 == 1 && questState.getQuestItemsCount(1636) >= 10L && questState.getQuestItemsCount(1637) >= 2L) {
            questState.setCond(2);
            questState.setState(2);
        }
        return null;
    }
}
