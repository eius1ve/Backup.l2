/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _223_TestOfChampion
extends Quest
implements ScriptFile {
    private static final int ahC = 1;
    private static final int ahD = 2;
    private static final int ahE = 3;
    private static final int ahF = 4;
    private static final int ahG = 5;
    private static final int ahH = 6;
    private static final int ahI = 7;
    private static final int ahJ = 8;
    private static final int ahK = 9;
    private static final int ahL = 10;
    private static final int ahM = 11;
    private static final int ahN = 12;
    private static final int ahO = 13;
    private static final int ahP = 14;
    private static final int ahQ = 117454;
    private static final int ahR = 25000;
    private static final int ahS = 3276;
    private static final int ahT = 3277;
    private static final int ahU = 3278;
    private static final int ahV = 3279;
    private static final int ahW = 3280;
    private static final int ahX = 3281;
    private static final int ahY = 3282;
    private static final int ahZ = 3283;
    private static final int aia = 3284;
    private static final int aib = 3285;
    private static final int aic = 3286;
    private static final int aid = 3287;
    private static final int aie = 3288;
    private static final int aif = 3289;
    private static final int aig = 3290;
    private static final int aih = 3291;
    private static final int aii = 3292;
    private static final int aij = 30624;
    private static final int aik = 30093;
    private static final int ail = 30196;
    private static final int aim = 30625;
    private static final int ain = 20145;
    private static final int aio = 27088;
    private static final int aip = 20158;
    private static final int aiq = 20553;
    private static final int air = 20551;
    private static final int ais = 20577;
    private static final int ait = 20578;
    private static final int aiu = 20579;
    private static final int aiv = 20580;
    private static final int aiw = 20581;
    private static final int aix = 20582;
    private static final int aiy = 20780;
    private static final int[][] r = new int[][]{{2, 3, 20780, 3290, 100, 100}, {6, 7, 20145, 3287, 100, 30}, {6, 7, 27088, 3287, 100, 30}, {6, 7, 20158, 3288, 50, 30}, {6, 7, 20553, 3289, 50, 30}, {10, 11, 20551, 3291, 100, 100}, {12, 13, 20577, 3292, 50, 100}, {12, 13, 20578, 3292, 60, 100}, {12, 13, 20579, 3292, 70, 100}, {12, 13, 20580, 3292, 80, 100}, {12, 13, 20581, 3292, 90, 100}, {12, 13, 20582, 3292, 100, 100}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _223_TestOfChampion() {
        super(0);
        this.addStartNpc(30624);
        this.addTalkId(new int[]{30093});
        this.addTalkId(new int[]{30196});
        this.addTalkId(new int[]{30625});
        this.addKillId(new int[]{20145, 20158, 27088, 20551, 20553, 20577, 20578, 20579, 20580, 20581, 20582, 20780});
        this.addQuestItem(new int[]{3278, 3288, 3289, 3281, 3287, 3282, 3286, 3277, 3279, 3290, 3280, 3283, 3284, 3291, 3285, 3292});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("1")) {
            string2 = "30624-06.htm";
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            questState.giveItems(3277, 1L);
        } else if (string.equals("30624_1")) {
            string2 = "30624-05.htm";
        } else if (string.equals("30624_2")) {
            string2 = "30624-10.htm";
            questState.setCond(5);
            questState.takeItems(3278, -1L);
            questState.giveItems(3280, 1L);
        } else if (string.equals("30624_3")) {
            string2 = "30624-14.htm";
            questState.setCond(9);
            questState.takeItems(3282, -1L);
            questState.giveItems(3283, 1L);
        } else if (string.equals("30625_1")) {
            string2 = "30625-02.htm";
        } else if (string.equals("30625_2")) {
            string2 = "30625-03.htm";
            questState.setCond(2);
            questState.takeItems(3277, -1L);
            questState.giveItems(3279, 1L);
        } else if (string.equals("30093_1")) {
            string2 = "30093-02.htm";
            questState.setCond(6);
            questState.takeItems(3280, -1L);
            questState.giveItems(3281, 1L);
        } else if (string.equals("30196_1")) {
            string2 = "30196-02.htm";
        } else if (string.equals("30196_2")) {
            string2 = "30196-03.htm";
            questState.setCond(10);
            questState.takeItems(3283, -1L);
            questState.giveItems(3284, 1L);
        } else if (string.equals("30196_3")) {
            string2 = "30196-06.htm";
            questState.setCond(12);
            questState.takeItems(3284, -1L);
            questState.takeItems(3291, -1L);
            questState.giveItems(3285, 1L);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        if (questState.getQuestItemsCount(3276) > 0L) {
            questState.exitCurrentQuest(true);
            return "completed";
        }
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        String string = "noquest";
        if (n == 30624) {
            if (n2 == 0) {
                ClassId classId = questState.getPlayer().getClassId();
                if (classId != ClassId.warrior && classId != ClassId.orc_raider) {
                    questState.exitCurrentQuest(true);
                    return "30624-01.htm";
                }
                if (questState.getPlayer().getLevel() < 39) {
                    questState.exitCurrentQuest(true);
                    return "30624-02.htm";
                }
                return classId == ClassId.warrior ? "30624-03.htm" : "30624-04.htm";
            }
            if (n2 == 1) {
                string = "30624-07.htm";
            } else if (n2 == 2 || n2 == 3) {
                string = "30624-08.htm";
            } else if (n2 == 4) {
                string = "30624-09.htm";
            } else if (n2 == 5) {
                string = "30624-11.htm";
            } else if (n2 == 6 || n2 == 7) {
                string = "30624-12.htm";
            } else if (n2 == 8) {
                string = "30624-13.htm";
            } else if (n2 == 9) {
                string = "30624-15.htm";
            } else if (n2 > 9 && n2 < 14) {
                string = "30624-16.htm";
            } else if (n2 == 14) {
                string = "30624-17.htm";
                questState.takeItems(3286, -1L);
                questState.giveItems(3276, 1L);
                if (!questState.getPlayer().getVarB("prof2.3")) {
                    questState.giveItems(7562, 8L);
                    questState.addExpAndSp(117454L, 25000L);
                    questState.getPlayer().setVar("prof2.3", "1", -1L);
                    this.giveExtraReward(questState.getPlayer());
                }
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(false);
            }
        } else {
            if (n2 == 0) {
                return "noquest";
            }
            if (n == 30625) {
                if (n2 == 1) {
                    string = "30625-01.htm";
                } else if (n2 == 2) {
                    string = "30625-04.htm";
                } else if (n2 == 3) {
                    string = "30625-05.htm";
                    questState.takeItems(3290, -1L);
                    questState.takeItems(3279, -1L);
                    questState.giveItems(3278, 1L);
                    questState.setCond(4);
                } else {
                    string = n2 == 4 ? "30625-06.htm" : "30625-07.htm";
                }
            } else if (n == 30093) {
                if (n2 == 5) {
                    string = "30093-01.htm";
                } else if (n2 == 6) {
                    string = "30093-03.htm";
                } else if (n2 == 7) {
                    string = "30093-04.htm";
                    questState.takeItems(3281, -1L);
                    questState.takeItems(3287, -1L);
                    questState.takeItems(3288, -1L);
                    questState.takeItems(3289, -1L);
                    questState.giveItems(3282, 1L);
                    questState.setCond(8);
                } else if (n2 == 8) {
                    string = "30093-05.htm";
                } else if (n2 > 8) {
                    string = "30093-06.htm";
                }
            } else if (n == 30196) {
                if (n2 == 9) {
                    string = "30196-01.htm";
                } else if (n2 == 10) {
                    string = "30196-04.htm";
                } else if (n2 == 11) {
                    string = "30196-05.htm";
                } else if (n2 == 12) {
                    string = "30196-07.htm";
                } else if (n2 == 13) {
                    string = "30196-08.htm";
                    questState.takeItems(3285, -1L);
                    questState.takeItems(3292, -1L);
                    questState.giveItems(3286, 1L);
                    questState.setCond(14);
                } else if (n2 == 13) {
                    string = "30196-09.htm";
                }
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getCond();
        if (n == 0) {
            return null;
        }
        int n2 = npcInstance.getNpcId();
        for (int[] nArray : r) {
            if (nArray[2] != n2 || nArray[0] != n) continue;
            questState.rollAndGive(nArray[3], 1, 1, nArray[5], (double)nArray[4]);
            for (int[] nArray2 : r) {
                if (nArray2[0] != n || questState.getQuestItemsCount(nArray2[3]) >= (long)nArray2[5]) continue;
                return null;
            }
            questState.setCond(n + 1);
            return null;
        }
        return null;
    }
}
