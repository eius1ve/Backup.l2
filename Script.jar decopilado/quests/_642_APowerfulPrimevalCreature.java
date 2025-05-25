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

public class _642_APowerfulPrimevalCreature
extends Quest
implements ScriptFile {
    private static final int bzR = 32105;
    private static final int bzS = 18344;
    private static final int bzT = 22204;
    private static final int bzU = 22203;
    private static final int bzV = 22225;
    private static final int bzW = 22220;
    private static final int bzX = 22205;
    private static final int bzY = 22201;
    private static final int bzZ = 22200;
    private static final int bAa = 22224;
    private static final int bAb = 22219;
    private static final int bAc = 22202;
    private static final int bAd = 22199;
    private static final int bAe = 22197;
    private static final int bAf = 22196;
    private static final int bAg = 22223;
    private static final int bAh = 22218;
    private static final int bAi = 22198;
    private static final int bAj = 8774;
    private static final int bAk = 8775;
    private static final int bAl = 8690;
    private static final int bAm = 8692;
    private static final int bAn = 8694;
    private static final int bAo = 8696;
    private static final int bAp = 8698;
    private static final int bAq = 8700;
    private static final int bAr = 8702;
    private static final int bAs = 8704;
    private static final int bAt = 8706;
    private static final int bAu = 8708;
    private static final int bAv = 8710;

    public _642_APowerfulPrimevalCreature() {
        super(1);
        this.addStartNpc(32105);
        this.addKillId(new int[]{18344, 22204, 22203, 22225, 22220, 22205, 22201, 22200, 22224, 22219, 22202, 22199, 22197, 22196, 22223, 22218, 22198});
        this.addQuestItem(new int[]{8774, 8775});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            if (questState.getPlayer().getLevel() >= 75) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "dindin_q0642_04.htm";
            } else {
                string2 = "dindin_q0642_01a.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (string.equalsIgnoreCase("reply_1")) {
            string2 = "dindin_q0642_02.htm";
        } else if (string.equalsIgnoreCase("reply_2")) {
            string2 = "dindin_q0642_03.htm";
        } else if (string.equalsIgnoreCase("reply_3")) {
            if (questState.getQuestItemsCount(8774) >= 1L) {
                questState.giveItems(57, 5000L * questState.getQuestItemsCount(8774));
                questState.takeItems(8774, -1L);
                string2 = "dindin_q0642_08.htm";
            } else {
                string2 = "dindin_q0642_08a.htm";
            }
        } else if (string.equalsIgnoreCase("reply_5")) {
            string2 = questState.getQuestItemsCount(8774) >= 150L && questState.getQuestItemsCount(8775) >= 1L ? "dindin_q0642_10.htm" : "dindin_q0642_11a.htm";
        } else if (string.equalsIgnoreCase("reply_100") || string.equalsIgnoreCase("reply_101") || string.equalsIgnoreCase("reply_102") || string.equalsIgnoreCase("reply_103") || string.equalsIgnoreCase("reply_104") || string.equalsIgnoreCase("reply_105") || string.equalsIgnoreCase("reply_106") || string.equalsIgnoreCase("reply_107") || string.equalsIgnoreCase("reply_108") || string.equalsIgnoreCase("reply_109") || string.equalsIgnoreCase("reply_110")) {
            if (string.equalsIgnoreCase("reply_100")) {
                questState.giveItems(8690, 1L);
            } else if (string.equalsIgnoreCase("reply_101")) {
                questState.giveItems(8692, 1L);
            } else if (string.equalsIgnoreCase("reply_102")) {
                questState.giveItems(8694, 1L);
            } else if (string.equalsIgnoreCase("reply_103")) {
                questState.giveItems(8696, 1L);
            } else if (string.equalsIgnoreCase("reply_104")) {
                questState.giveItems(8698, 1L);
            } else if (string.equalsIgnoreCase("reply_105")) {
                questState.giveItems(8700, 1L);
            } else if (string.equalsIgnoreCase("reply_106")) {
                questState.giveItems(8702, 1L);
            } else if (string.equalsIgnoreCase("reply_107")) {
                questState.giveItems(8704, 1L);
            } else if (string.equalsIgnoreCase("reply_108")) {
                questState.giveItems(8706, 1L);
            } else if (string.equalsIgnoreCase("reply_109")) {
                questState.giveItems(8708, 1L);
            } else if (string.equalsIgnoreCase("reply_110")) {
                questState.giveItems(8710, 1L);
            }
            questState.giveItems(57, 44000L);
            questState.takeItems(8775, 1L);
            questState.takeItems(8774, 150L);
            string2 = "dindin_q0642_10a.htm";
        } else if (string.equalsIgnoreCase("reply_200")) {
            string2 = "dindin_q0642_11.htm";
        } else if (string.equalsIgnoreCase("reply_6")) {
            string2 = "dindin_q0642_12.htm";
        } else if (string.equalsIgnoreCase("reply_7")) {
            if (questState.getQuestItemsCount(8775) > 0L || questState.getQuestItemsCount(8774) > 0L) {
                if (questState.getQuestItemsCount(8775) > 0L) {
                    questState.giveItems(57, questState.getQuestItemsCount(8775));
                } else if (questState.getQuestItemsCount(8774) > 0L) {
                    questState.giveItems(57, questState.getQuestItemsCount(8774) * 5000L);
                }
                questState.takeItems(8775, -1L);
                questState.takeItems(8774, -1L);
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                string2 = "dindin_q0642_13.htm";
            } else {
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
                string2 = "dindin_q0642_14.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        switch (n) {
            case 1: {
                if (questState.getPlayer().getLevel() >= 75) {
                    string = "dindin_q0642_01.htm";
                    break;
                }
                string = "dindin_q0642_01a.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (questState.getQuestItemsCount(8774) <= 0L && questState.getQuestItemsCount(8775) <= 0L) {
                    string = "dindin_q0642_06.htm";
                    break;
                }
                if (questState.getQuestItemsCount(8774) <= 0L && questState.getQuestItemsCount(8775) <= 0L) break;
                string = "dindin_q0642_07.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 18344) {
            questState.rollAndGive(8775, 1, 100.0);
        } else if (n == 22204) {
            questState.rollAndGive(8774, 1, 31.3);
        } else if (n == 22203) {
            questState.rollAndGive(8774, 1, 32.1);
        } else if (n == 22225) {
            questState.rollAndGive(8774, 1, 28.9);
        } else if (n == 22220) {
            questState.rollAndGive(8774, 1, 26.4);
        } else if (n == 22205) {
            questState.rollAndGive(8774, 1, 32.1);
        } else if (n == 22201) {
            questState.rollAndGive(8774, 1, 32.6);
        } else if (n == 22200) {
            questState.rollAndGive(8774, 1, 32.6);
        } else if (n == 22224) {
            questState.rollAndGive(8774, 1, 30.5);
        } else if (n == 22219) {
            questState.rollAndGive(8774, 1, 29.7);
        } else if (n == 22202) {
            questState.rollAndGive(8774, 1, 32.6);
        } else if (n == 22199) {
            questState.rollAndGive(8774, 1, 100.0);
        } else if (n == 22197) {
            questState.rollAndGive(8774, 1, 31.4);
        } else if (n == 22196) {
            questState.rollAndGive(8774, 1, 72.0);
        } else if (n == 22223) {
            questState.rollAndGive(8774, 1, 30.8);
        } else if (n == 22218) {
            questState.rollAndGive(8774, 1, 29.6);
        } else if (n == 22198) {
            questState.rollAndGive(8774, 1, 72.0);
        }
        return null;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
