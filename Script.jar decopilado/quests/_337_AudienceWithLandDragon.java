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

public class _337_AudienceWithLandDragon
extends Quest
implements ScriptFile {
    private static final int aHc = 30498;
    private static final int aHd = 30678;
    private static final int aHe = 30705;
    private static final int aHf = 30720;
    private static final int aHg = 30753;
    private static final int aHh = 30754;
    private static final int aHi = 30755;
    private static final int aHj = 30851;
    private static final int aHk = 30857;
    private static final int aHl = 20679;
    private static final int aHm = 20680;
    private static final int aHn = 18001;
    private static final int aHo = 20644;
    private static final int aHp = 20645;
    private static final int aHq = 20649;
    private static final int aHr = 20650;
    private static final int aHs = 20134;
    private static final int aHt = 20246;
    private static final int aHu = 27165;
    private static final int aHv = 27166;
    private static final int aHw = 27167;
    private static final int aHx = 27168;
    private static final int aHy = 27169;
    private static final int aHz = 27170;
    private static final int aHA = 27171;
    private static final int aHB = 27172;
    private static final int aHC = 3852;
    private static final int aHD = 3853;
    private static final int aHE = 3854;
    private static final int aHF = 3857;
    private static final int aHG = 3858;
    private static final int aHH = 3856;
    private static final int aHI = 3855;
    private static final int aHJ = 3862;
    private static final int aHK = 3863;
    private static final int aHL = 3859;
    private static final int aHM = 3860;
    private static final int aHN = 3861;
    private static final int aHO = 3890;
    private static final int aHP = 3865;
    private static final int aHQ = 3864;
    private static final int[][] F = new int[][]{{2, 20679, 3853, 1, 50, 1}, {2, 20680, 3854, 1, 50, 1}, {4, 27171, 3857, 1, 50, 1}, {6, 27172, 3858, 1, 50, 1}, {8, 20649, 3856, 1, 50, 1}, {8, 20650, 3855, 1, 50, 1}, {11, 27168, 3862, 1, 50, 1}, {11, 27165, 3859, 1, 100, 1}, {13, 27169, 3863, 1, 50, 1}, {13, 27166, 3860, 1, 100, 1}, {16, 27167, 3861, 1, 100, 1}};
    private static final int[][] G = new int[][]{{4, 18001, 27171, 6}, {6, 20644, 27172, 1}, {6, 20645, 27172, 1}, {11, 27165, 27168, 4}, {13, 27166, 27169, 4}, {16, 20246, 27167, 1}, {16, 20134, 27167, 1}, {16, 27167, 27170, 6}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _337_AudienceWithLandDragon() {
        super(0);
        this.addStartNpc(30753);
        this.addTalkId(new int[]{30498});
        this.addTalkId(new int[]{30678});
        this.addTalkId(new int[]{30705});
        this.addTalkId(new int[]{30720});
        this.addTalkId(new int[]{30754});
        this.addTalkId(new int[]{30755});
        this.addTalkId(new int[]{30851});
        this.addTalkId(new int[]{30857});
        this.addKillId(new int[]{18001});
        this.addKillId(new int[]{20679});
        this.addKillId(new int[]{20680});
        this.addKillId(new int[]{27171});
        this.addKillId(new int[]{20644});
        this.addKillId(new int[]{20645});
        this.addKillId(new int[]{27172});
        this.addKillId(new int[]{20649});
        this.addKillId(new int[]{20650});
        this.addKillId(new int[]{27165});
        this.addKillId(new int[]{27166});
        this.addKillId(new int[]{20246});
        this.addKillId(new int[]{20134});
        this.addKillId(new int[]{27167});
        this.addKillId(new int[]{27168});
        this.addKillId(new int[]{27169});
        this.addKillId(new int[]{27170});
        this.addAttackId(new int[]{27165});
        this.addAttackId(new int[]{27166});
        this.addAttackId(new int[]{27167});
        this.addQuestItem(new int[]{3852, 3890, 3853, 3854, 3857, 3858, 3856, 3855, 3862, 3859, 3863, 3860, 3861, 3864});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("1")) {
            questState.set("step", "1");
            questState.setCond(1);
            questState.set("guard", "0");
            questState.setState(2);
            questState.giveItems(3852, 1L);
            string2 = "30753-02.htm";
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("2")) {
            questState.set("step", "2");
            string2 = "30720-02.htm";
        } else if (string.equalsIgnoreCase("4")) {
            questState.set("step", "4");
            string2 = "30857-02.htm";
        } else if (string.equalsIgnoreCase("6")) {
            questState.set("step", "6");
            string2 = "30851-02.htm";
        } else if (string.equalsIgnoreCase("8")) {
            questState.set("step", "8");
            string2 = "30705-02.htm";
        } else if (string.equalsIgnoreCase("10")) {
            questState.takeItems(3864, -1L);
            questState.set("step", "10");
            questState.setCond(2);
            string2 = "30753-05.htm";
        } else if (string.equalsIgnoreCase("11")) {
            questState.set("step", "11");
            string2 = "30498-02.htm";
        } else if (string.equalsIgnoreCase("13")) {
            questState.set("step", "13");
            string2 = "30678-02.htm";
        } else if (string.equalsIgnoreCase("15")) {
            questState.set("step", "15");
            questState.setCond(3);
            string2 = "30753-06.htm";
            questState.takeItems(3864, -1L);
            questState.takeItems(3852, -1L);
            questState.giveItems(3890, 1L);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("16")) {
            questState.set("step", "16");
            questState.setCond(4);
            string2 = "30754-02.htm";
            questState.takeItems(3890, -1L);
            questState.playSound("ItemSound.quest_middle");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("step");
        int n3 = questState.getCond();
        if (n == 30753) {
            if (n3 == 0) {
                if (questState.getPlayer().getLevel() < 50) {
                    string = "30753-00.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "30753-01.htm";
                }
            } else if (n2 < 9) {
                string = "30753-02.htm";
            } else if (n2 == 9) {
                string = "30753-03.htm";
            } else if (n2 > 9 && n2 < 14) {
                string = "30753-05.htm";
            } else if (n2 == 14) {
                string = "30753-04.htm";
            } else if (n2 > 14) {
                string = "30753-06.htm";
            }
        } else if (n == 30720 && n3 == 1 && n2 < 4) {
            if (questState.getQuestItemsCount(3853) < 1L && questState.getQuestItemsCount(3854) < 1L && n2 == 1) {
                string = "30720-01.htm";
            } else if (questState.getQuestItemsCount(3853) > 0L && questState.getQuestItemsCount(3854) > 0L) {
                string = "30720-03.htm";
                questState.takeItems(3853, -1L);
                questState.takeItems(3854, -1L);
                questState.giveItems(3864, 1L);
                questState.set("step", "3");
                questState.playSound("ItemSound.quest_middle");
            } else if (n2 == 2) {
                string = "30720-02.htm";
            } else if (n2 == 3) {
                string = "30720-03.htm";
            }
        } else if (n == 30857 && n3 == 1 && n2 > 2 && n2 < 6) {
            if (questState.getQuestItemsCount(3857) < 1L && n2 == 3) {
                string = "30857-01.htm";
            } else if (questState.getQuestItemsCount(3857) > 0L) {
                string = "30857-03.htm";
                questState.takeItems(3857, -1L);
                questState.giveItems(3864, 1L);
                questState.set("step", "5");
                questState.playSound("ItemSound.quest_middle");
            } else if (n2 == 4) {
                string = "30857-02.htm";
            } else if (n2 == 5) {
                string = "30857-03.htm";
            }
        } else if (n == 30851 && n3 == 1 && n2 > 4 && n2 < 8) {
            if (questState.getQuestItemsCount(3858) < 1L && n2 == 5) {
                string = "30851-01.htm";
            } else if (questState.getQuestItemsCount(3858) > 0L) {
                string = "30851-03.htm";
                questState.takeItems(3858, -1L);
                questState.giveItems(3864, 1L);
                questState.set("step", "7");
                questState.playSound("ItemSound.quest_middle");
            } else if (n2 == 6) {
                string = "30851-02.htm";
            } else if (n2 == 7) {
                string = "30851-03.htm";
            }
        } else if (n == 30705 && n3 == 1 && n2 > 6 && n2 < 10) {
            if (questState.getQuestItemsCount(3856) < 1L && questState.getQuestItemsCount(3855) < 1L && n2 == 7) {
                string = "30705-01.htm";
            } else if (questState.getQuestItemsCount(3856) > 0L && questState.getQuestItemsCount(3855) > 0L) {
                string = "30705-03.htm";
                questState.takeItems(3856, -1L);
                questState.takeItems(3855, -1L);
                questState.giveItems(3864, 1L);
                questState.set("step", "9");
                questState.playSound("ItemSound.quest_middle");
            } else if (n2 == 8) {
                string = "30705-02.htm";
            } else if (n2 == 9) {
                string = "30705-03.htm";
            }
        } else if (n == 30498 && n3 == 2 && n2 < 13) {
            if (questState.getQuestItemsCount(3862) < 1L && questState.getQuestItemsCount(3859) < 1L && n2 == 10) {
                string = "30498-01.htm";
            } else if (questState.getQuestItemsCount(3862) > 0L && questState.getQuestItemsCount(3859) > 0L) {
                string = "30498-03.htm";
                questState.takeItems(3862, -1L);
                questState.takeItems(3859, -1L);
                questState.giveItems(3864, 1L);
                questState.set("step", "12");
                questState.playSound("ItemSound.quest_middle");
            } else if (n2 == 11) {
                string = "30498-02.htm";
            } else if (n2 == 12) {
                string = "30498-03.htm";
            }
        } else if (n == 30678 && n3 == 2 && n2 > 11 && n2 < 15) {
            if (questState.getQuestItemsCount(3863) < 1L && questState.getQuestItemsCount(3860) < 1L && n2 == 12) {
                string = "30678-01.htm";
            } else if (questState.getQuestItemsCount(3863) > 0L && questState.getQuestItemsCount(3860) > 0L) {
                string = "30678-03.htm";
                questState.takeItems(3863, -1L);
                questState.takeItems(3860, -1L);
                questState.giveItems(3864, 1L);
                questState.set("step", "14");
                questState.playSound("ItemSound.quest_middle");
            } else if (n2 == 13) {
                string = "30678-02.htm";
            } else if (n2 == 14) {
                string = "30678-03.htm";
            }
        } else if (n == 30754 && n2 < 17) {
            if (questState.getQuestItemsCount(3890) > 0L && n3 == 3) {
                string = "30754-01.htm";
            } else if (n3 == 4) {
                string = "30754-02.htm";
            }
        } else if (n == 30755 && n3 == 4 && n2 == 16) {
            if (questState.getQuestItemsCount(3861) < 1L) {
                string = "30755-02.htm";
            } else {
                string = "30755-01.htm";
                questState.takeItems(3861, -1L);
                questState.unset("step");
                questState.unset("cond");
                questState.unset("guard");
                questState.exitCurrentQuest(true);
                this.giveExtraReward(questState.getPlayer());
                questState.giveItems(3865, 1L);
                questState.playSound("ItemSound.quest_finish");
            }
        }
        return string;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("step");
        for (int[] nArray : G) {
            if (n != nArray[1] || n2 != nArray[0] || !(npcInstance.getCurrentHpPercents() < 50.0) || questState.getInt("guard") != 0) continue;
            for (int i = 0; i < nArray[3]; ++i) {
                questState.addSpawn(nArray[2]);
            }
            questState.playSound("Itemsound.quest_before_battle");
            questState.set("guard", "1");
        }
        return null;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("step");
        for (int[] nArray : F) {
            if (n != nArray[1] || n2 != nArray[0] || questState.getQuestItemsCount(nArray[2]) >= (long)nArray[3] || !Rnd.chance((int)nArray[4])) continue;
            questState.giveItems(nArray[2], (long)nArray[5]);
            questState.playSound("ItemSound.quest_itemget");
        }
        for (int[] nArray : G) {
            if (n2 == nArray[0] && n == nArray[1] && Rnd.chance((int)50) && questState.getInt("guard") == 0) {
                for (int i = 0; i < nArray[3]; ++i) {
                    questState.addSpawn(nArray[2]);
                }
                questState.playSound("Itemsound.quest_before_battle");
            }
            if (n2 != nArray[0] || n != nArray[1] || questState.getInt("guard") != 1) continue;
            questState.set("guard", "0");
        }
        return null;
    }
}
