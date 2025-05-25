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

public class _638_SeekersOfTheHolyGrail
extends Quest
implements ScriptFile {
    private static final int bxR = 31328;
    private static final int bxS = 8068;
    private static final int bxT = 960;
    private static final int bxU = 959;
    private static final int bxV = 22176;
    private static final int bxW = 22146;
    private static final int bxX = 22151;
    private static final int bxY = 22138;
    private static final int bxZ = 22141;
    private static final int bya = 22175;
    private static final int byb = 22155;
    private static final int byc = 22159;
    private static final int byd = 22163;
    private static final int bye = 22167;
    private static final int byf = 22171;
    private static final int byg = 22143;
    private static final int byh = 22137;
    private static final int byi = 22194;
    private static final int byj = 22164;
    private static final int byk = 22156;
    private static final int byl = 22166;
    private static final int bym = 22173;
    private static final int byn = 22170;
    private static final int byo = 22157;
    private static final int byp = 22160;
    private static final int byq = 22165;
    private static final int byr = 22168;
    private static final int bys = 22174;
    private static final int byt = 22158;
    private static final int byu = 22162;
    private static final int byv = 22149;
    private static final int byw = 22147;
    private static final int byx = 22154;
    private static final int byy = 22161;
    private static final int byz = 22169;
    private static final int byA = 22172;
    private static final int byB = 22145;
    private static final int byC = 22152;
    private static final int byD = 22153;
    private static final int byE = 22136;
    private static final int byF = 22150;
    private static final int byG = 22148;
    private static final int byH = 22142;
    private static final int byI = 22144;
    private static final int byJ = 22139;
    private static final int byK = 22140;
    private static final int byL = 8275;

    public _638_SeekersOfTheHolyGrail() {
        super(1);
        this.addStartNpc(31328);
        this.addQuestItem(new int[]{8068});
        this.addKillId(new int[]{22176, 22146, 22151, 22138, 22141, 22175, 22155, 22159, 22163, 22167, 22171, 22143, 22137, 22194, 22164, 22156, 22166, 22173, 22170, 22157, 22160, 22165, 22168, 22174, 22158, 22162, 22149, 22147, 22154, 22161, 22169, 22172, 22145, 22152, 22153, 22136, 22150, 22148, 22142, 22144, 22139, 22140});
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (npcInstance.getNpcId() == 31328) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "highpriest_innocentin_q0638_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "highpriest_innocentin_q0638_06.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getCond() == 1 && questState.getQuestItemsCount(8068) >= 2000L) {
                    this.giveExtraReward(questState.getPlayer());
                    if (Rnd.get((int)100) < 80) {
                        if (Rnd.get((int)2) == 0) {
                            questState.giveItems(960, 1L, true);
                        } else {
                            questState.giveItems(959, 1L, true);
                        }
                        questState.takeItems(8068, 2000L);
                        string2 = "highpriest_innocentin_q0638_07.htm";
                    } else {
                        questState.giveItems(57, 3576000L);
                        questState.takeItems(8068, 2000L);
                        string2 = "highpriest_innocentin_q0638_08.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(8068) > 0L) {
                    questState.giveItems(57, 1700L * questState.getQuestItemsCount(8068));
                    questState.takeItems(8068, -1L);
                }
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "highpriest_innocentin_q0638_09.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getState();
        if (npcInstance.getNpcId() == 31328) {
            if (n == 1) {
                string = questState.getPlayer().getLevel() >= 73 ? "highpriest_innocentin_q0638_01.htm" : "highpriest_innocentin_q0638_02.htm";
            } else if (questState.getQuestItemsCount(8068) >= 2000L && questState.getCond() == 1) {
                string = "highpriest_innocentin_q0638_04.htm";
            } else if (questState.getQuestItemsCount(8068) < 2000L && questState.getCond() == 1) {
                string = "highpriest_innocentin_q0638_05.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 22176) {
            questState.rollAndGive(8068, 1, 6.0);
        } else if (n == 22146) {
            questState.rollAndGive(8068, 1, 54.0);
            questState.rollAndGive(8275, 1, 10.0);
        } else if (n == 22151) {
            questState.rollAndGive(8068, 1, 62.0);
            questState.rollAndGive(8275, 1, 10.0);
        } else if (n == 22149) {
            questState.rollAndGive(8068, 1, 54.0);
            questState.rollAndGive(8275, 6, 100.0);
        } else if (n == 22143) {
            questState.rollAndGive(8068, 1, 62.0);
            questState.rollAndGive(8275, 1, 100.0);
        } else if (n == 22142) {
            questState.rollAndGive(8068, 1, 54.0);
            questState.rollAndGive(8275, 1, 100.0);
        } else if (n == 22140) {
            questState.rollAndGive(8068, 1, 54.0);
            questState.rollAndGive(8275, 1, 100.0);
        } else if (n == 22141 || n == 22147 || n == 22152 || n == 22136) {
            questState.rollAndGive(8068, 1, 55.0);
        } else if (n == 22175) {
            questState.rollAndGive(8068, 1, 3.0);
        } else if (n == 22155 || n == 22159 || n == 22163 || n == 22167) {
            questState.rollAndGive(8068, 1, 75.0);
        } else if (n == 22171) {
            questState.rollAndGive(8068, 1, 87.0);
        } else if (n == 22137 || n == 22194 || n == 22138) {
            questState.rollAndGive(8068, 1, 6.0);
        } else if (n == 22156 || n == 22164 || n == 22170 || n == 22160 || n == 22174 || n == 22158 || n == 22162) {
            questState.rollAndGive(8068, 1, 67.0);
        } else if (n == 22166 || n == 22173 || n == 22157 || n == 22165 || n == 22168) {
            questState.rollAndGive(8068, 1, 66.0);
        } else if (n == 22154 || n == 22145 || n == 22153) {
            questState.rollAndGive(8068, 1, 53.0);
        } else if (n == 22161 || n == 22169 || n == 22172) {
            questState.rollAndGive(8068, 1, 78.0);
        } else if (n == 22150 || n == 22148) {
            questState.rollAndGive(8068, 1, 46.0);
        } else if (n == 22144 || n == 22139) {
            questState.rollAndGive(8068, 1, 54.0);
        }
        return null;
    }
}
