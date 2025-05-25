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

public class _336_CoinOfMagic
extends Quest
implements ScriptFile {
    private static final int aFq = 3811;
    private static final int aFr = 3812;
    private static final int aFs = 3813;
    private static final int aFt = 3814;
    private static final int aFu = 3815;
    private static final int aFv = 3472;
    private static final int aFw = 3473;
    private static final int aFx = 3474;
    private static final int aFy = 3475;
    private static final int aFz = 3476;
    private static final int aFA = 3477;
    private static final int aFB = 3478;
    private static final int aFC = 3479;
    private static final int aFD = 3480;
    private static final int aFE = 3481;
    private static final int aFF = 3482;
    private static final int aFG = 3483;
    private static final int aFH = 3484;
    private static final int aFI = 3485;
    private static final int aFJ = 3486;
    private static final int aFK = 3487;
    private static final int aFL = 3488;
    private static final int aFM = 3489;
    private static final int aFN = 3490;
    private static final int aFO = 3491;
    private static final int aFP = 3492;
    private static final int aFQ = 3493;
    private static final int aFR = 3494;
    private static final int aFS = 3495;
    private static final int aFT = 3496;
    private static final int aFU = 3497;
    private static final int aFV = 3498;
    private static final int[] bH = new int[]{3472, 3482, 3490};
    private static final int aFW = 30232;
    private static final int aFX = 30702;
    private static final int aFY = 30696;
    private static final int aFZ = 30183;
    private static final int aGa = 30200;
    private static final int aGb = 30165;
    private static final int aGc = 30847;
    private static final int aGd = 30092;
    private static final int aGe = 30078;
    private static final int aGf = 30688;
    private static final int aGg = 30673;
    private static final int aGh = 20584;
    private static final int aGi = 20585;
    private static final int aGj = 20587;
    private static final int aGk = 20604;
    private static final int aGl = 20678;
    private static final int aGm = 20663;
    private static final int aGn = 20235;
    private static final int aGo = 20583;
    private static final int aGp = 20146;
    private static final int aGq = 20240;
    private static final int aGr = 20245;
    private static final int aGs = 20568;
    private static final int aGt = 20569;
    private static final int aGu = 20685;
    private static final int aGv = 20572;
    private static final int aGw = 20161;
    private static final int aGx = 20575;
    private static final int aGy = 20645;
    private static final int aGz = 20644;
    private static final int aGA = 20279;
    private static final int aGB = 20280;
    private static final int aGC = 20284;
    private static final int aGD = 20276;
    private static final int aGE = 21003;
    private static final int aGF = 21006;
    private static final int aGG = 21008;
    private static final int aGH = 20674;
    private static final int aGI = 21276;
    private static final int aGJ = 21275;
    private static final int aGK = 21274;
    private static final int aGL = 21278;
    private static final int aGM = 21279;
    private static final int aGN = 21280;
    private static final int aGO = 21282;
    private static final int aGP = 21284;
    private static final int aGQ = 21283;
    private static final int aGR = 21287;
    private static final int aGS = 21288;
    private static final int aGT = 21286;
    private static final int aGU = 21521;
    private static final int aGV = 21526;
    private static final int aGW = 21531;
    private static final int aGX = 21539;
    private static final int aGY = 20954;
    private static final int aGZ = 20960;
    private static final int aHa = 20957;
    private static final int aHb = 20959;
    private static final int[][] C = new int[][]{new int[0], new int[0], {3492, 3474, 3476, 3495, 3484, 3486}, {3473, 3485, 3491, 3475, 3483, 3494}};
    private static final int[][] D = new int[][]{{30696, 3}, {30673, 3}, {30183, 3}, {30165, 2}, {30200, 2}, {30688, 2}, {30847, 1}, {30092, 1}, {30078, 1}};
    private static final int[][] E = new int[][]{{20584, 3472}, {20585, 3472}, {20587, 3472}, {20604, 3472}, {20678, 3472}, {20663, 3472}, {20583, 3482}, {20235, 3482}, {20146, 3482}, {20240, 3482}, {20245, 3482}, {20568, 3490}, {20569, 3490}, {20685, 3490}, {20572, 3490}, {20161, 3490}, {20575, 3490}};
    private static final int[] bI = new int[]{21003, 21006, 21008, 20674, 21276, 21275, 21274, 21278, 21279, 21280, 21282, 21284, 21283, 21287, 21288, 21286, 21521, 21526, 21531, 21539, 20954, 20960, 20957, 20959};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _336_CoinOfMagic() {
        super(1);
        this.addStartNpc(30232);
        this.addTalkId(new int[]{30232, 30702, 30696, 30183, 30200, 30165, 30847, 30092, 30078, 30688, 30673});
        for (int[] nArray : E) {
            this.addKillId(new int[]{nArray[0]});
        }
        this.addKillId(bI);
        this.addKillId(new int[]{20645});
        this.addKillId(new int[]{20644});
        this.addQuestItem(new int[]{3811, 3812, 3813, 3814, 3815});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getCond();
        if (string.equalsIgnoreCase("30702-06.htm")) {
            if (n < 7) {
                questState.setCond(7);
                questState.playSound("ItemSound.quest_accept");
            }
        } else if (string.equalsIgnoreCase("30232-22.htm")) {
            if (n < 6) {
                questState.setCond(6);
            }
        } else if (string.equalsIgnoreCase("30232-23.htm")) {
            if (n < 5) {
                questState.setCond(5);
            }
        } else if (string.equalsIgnoreCase("30702-02.htm")) {
            questState.setCond(2);
        } else if (string.equalsIgnoreCase("30232-05.htm")) {
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            questState.giveItems(3811, 1L);
            questState.setCond(1);
        } else if (string.equalsIgnoreCase("30232-04.htm") || string.equalsIgnoreCase("30232-18a.htm")) {
            questState.exitCurrentQuest(true);
            questState.playSound("ItemSound.quest_giveup");
        } else if (string.equalsIgnoreCase("raise")) {
            string2 = this.a(questState);
        }
        return string2;
    }

    private String a(QuestState questState) {
        Object object;
        int n = questState.getInt("grade");
        if (n == 1) {
            object = "30232-15.htm";
        } else {
            int n2 = 0;
            for (int n3 : C[n]) {
                if (questState.getQuestItemsCount(n3) <= 0L) continue;
                ++n2;
            }
            if (n2 == 6) {
                for (int n3 : C[n]) {
                    questState.takeItems(n3, 1L);
                }
                object = "30232-" + this.str(19 - n) + ".htm";
                questState.takeItems(3812 + n, -1L);
                questState.giveItems(3811 + n, 1L);
                questState.set("grade", this.str(n - 1));
                if (n == 3) {
                    questState.setCond(9);
                } else if (n == 2) {
                    questState.setCond(11);
                }
                questState.playSound("ItemSound.quest_fanfare_middle");
            } else {
                object = "30232-" + this.str(16 - n) + ".htm";
                if (n == 3) {
                    questState.setCond(8);
                } else if (n == 2) {
                    questState.setCond(9);
                }
            }
        }
        return object;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        Object object;
        block7: {
            int n;
            int n2;
            block11: {
                block12: {
                    block5: {
                        block10: {
                            block9: {
                                block8: {
                                    block6: {
                                        object = "noquest";
                                        n2 = npcInstance.getNpcId();
                                        int n3 = questState.getState();
                                        n = questState.getInt("grade");
                                        if (n2 != 30232) break block5;
                                        if (n3 != 1) break block6;
                                        if (questState.getPlayer().getLevel() < 40) {
                                            object = "30232-01.htm";
                                            questState.exitCurrentQuest(true);
                                        } else {
                                            object = "30232-02.htm";
                                        }
                                        break block7;
                                    }
                                    if (questState.getQuestItemsCount(3811) <= 0L) break block8;
                                    if (questState.getQuestItemsCount(3812) > 0L) {
                                        questState.takeItems(3812, -1L);
                                        questState.takeItems(3811, -1L);
                                        questState.giveItems(3815, 1L);
                                        questState.set("grade", "3");
                                        questState.setCond(4);
                                        questState.playSound("ItemSound.quest_fanfare_middle");
                                        object = "30232-07.htm";
                                    } else {
                                        object = "30232-06.htm";
                                    }
                                    break block7;
                                }
                                if (n != 3) break block9;
                                object = "30232-12.htm";
                                break block7;
                            }
                            if (n != 2) break block10;
                            object = "30232-11.htm";
                            break block7;
                        }
                        if (n != 1) break block7;
                        object = "30232-10.htm";
                        break block7;
                    }
                    if (n2 != 30702) break block11;
                    if (questState.getQuestItemsCount(3811) <= 0L || n != 0) break block12;
                    object = "30702-01.htm";
                    break block7;
                }
                if (n != 3) break block7;
                object = "30702-05.htm";
                break block7;
            }
            for (int[] nArray : D) {
                if (n2 != nArray[0] || n > nArray[1]) continue;
                object = n2 + "-01.htm";
            }
        }
        return object;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getCond();
        int n2 = questState.getInt("grade");
        int n3 = npcInstance.getLevel() + n2 * 3 - 20;
        int n4 = npcInstance.getNpcId();
        if (n4 == 20645 || n4 == 20644) {
            if (n == 2 && questState.rollAndGive(3812, 1, 1, 1, 10.0 * npcInstance.getTemplate().rateHp)) {
                questState.setCond(3);
            }
            return null;
        }
        for (int[] nArray : E) {
            if (nArray[0] != n4) continue;
            questState.rollAndGive(nArray[1], 1, (double)n3);
            return null;
        }
        for (int n5 : bI) {
            if (n5 != n4) continue;
            questState.rollAndGive(bH[Rnd.get((int)bH.length)], 1, (double)n3 * npcInstance.getTemplate().rateHp);
            return null;
        }
        return null;
    }
}
