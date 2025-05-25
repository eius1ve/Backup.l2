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

public class _037_PleaseMakeMeFormalWear
extends Quest
implements ScriptFile {
    private static final int MF = 30842;
    private static final int MG = 31520;
    private static final int MH = 31521;
    private static final int MI = 31627;
    private static final int MJ = 7076;
    private static final int MK = 7077;
    private static final int ML = 7078;
    private static final int MM = 7113;
    private static final int MN = 7164;
    private static final int MO = 7160;
    private static final int MP = 7159;
    private static final int MQ = 6408;

    public _037_PleaseMakeMeFormalWear() {
        super(0);
        this.addStartNpc(30842);
        this.addTalkId(new int[]{31520, 31521, 31627});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("make_my_wedding_dress_cookie");
        int n2 = npcInstance.getNpcId();
        if (n2 == 30842) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("make_my_wedding_dress", String.valueOf(11), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "trader_alexis_q0037_0104.htm";
            }
        } else if (n2 == 31520) {
            if (string.equalsIgnoreCase("reply_1") && n == 1) {
                questState.setCond(2);
                questState.set("make_my_wedding_dress", String.valueOf(21), true);
                questState.giveItems(7164, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "leikar_q0037_0201.htm";
            } else if (string.equalsIgnoreCase("reply_1") && n == 5) {
                if (questState.getQuestItemsCount(7159) >= 1L) {
                    questState.setCond(6);
                    questState.set("make_my_wedding_dress", String.valueOf(61), true);
                    questState.takeItems(7159, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "leikar_q0037_0601.htm";
                } else {
                    string2 = "leikar_q0037_0602.htm";
                }
            } else if (string.equalsIgnoreCase("reply_1") && n == 6) {
                if (questState.getQuestItemsCount(7076) >= 1L && questState.getQuestItemsCount(7077) >= 1L && questState.getQuestItemsCount(7078) >= 1L) {
                    questState.setCond(7);
                    questState.set("make_my_wedding_dress", String.valueOf(71), true);
                    questState.takeItems(7076, 1L);
                    questState.takeItems(7077, 1L);
                    questState.takeItems(7078, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "leikar_q0037_0701.htm";
                } else {
                    string2 = "leikar_q0037_0702.htm";
                }
            } else if (string.equalsIgnoreCase("reply_3") && n == 7) {
                if (questState.getQuestItemsCount(7113) >= 1L && questState.getQuestItemsCount(7164) >= 1L) {
                    questState.takeItems(7113, -1L);
                    questState.takeItems(7164, -1L);
                    questState.giveItems(6408, 1L);
                    questState.unset("make_my_wedding_dress");
                    questState.unset("make_my_wedding_dress_cookie");
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    string2 = "leikar_q0037_0801.htm";
                } else {
                    string2 = "leikar_q0037_0802.htm";
                }
            }
        } else if (n2 == 31521) {
            if (string.equalsIgnoreCase("reply_1") && n == 2) {
                questState.setCond(3);
                questState.set("make_my_wedding_dress", String.valueOf(31), true);
                questState.giveItems(7160, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "jeremy_q0037_0301.htm";
            } else if (string.equalsIgnoreCase("reply_1") && n == 4) {
                questState.setCond(5);
                questState.set("make_my_wedding_dress", String.valueOf(51), true);
                questState.giveItems(7159, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "jeremy_q0037_0501.htm";
            }
        } else if (n2 == 31627 && string.equalsIgnoreCase("reply_1") && n == 3) {
            if (questState.getQuestItemsCount(7160) >= 1L) {
                questState.setCond(4);
                questState.set("make_my_wedding_dress", String.valueOf(41), true);
                questState.takeItems(7160, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "mist_q0037_0401.htm";
            } else {
                string2 = "mist_q0037_0402.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("make_my_wedding_dress");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30842) break;
                if (questState.getPlayer().getLevel() < 60) {
                    string = "trader_alexis_q0037_0103.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "trader_alexis_q0037_0101.htm";
                break;
            }
            case 2: {
                if (n2 == 30842) {
                    if (n != 11) break;
                    string = "trader_alexis_q0037_0105.htm";
                    break;
                }
                if (n2 == 31520) {
                    if (n == 11) {
                        questState.set("make_my_wedding_dress_cookie", String.valueOf(1), true);
                        string = "leikar_q0037_0101.htm";
                        break;
                    }
                    if (n == 21) {
                        string = "leikar_q0037_0202.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(7159) >= 1L && n == 51) {
                        questState.set("make_my_wedding_dress_cookie", String.valueOf(5), true);
                        string = "leikar_q0037_0501.htm";
                        break;
                    }
                    if (n == 61) {
                        if (questState.getQuestItemsCount(7076) >= 1L && questState.getQuestItemsCount(7077) >= 1L && questState.getQuestItemsCount(7078) >= 1L) {
                            questState.set("make_my_wedding_dress_cookie", String.valueOf(6), true);
                            string = "leikar_q0037_0603.htm";
                            break;
                        }
                        string = "leikar_q0037_0604.htm";
                        break;
                    }
                    if (n != 71) break;
                    if (questState.getQuestItemsCount(7113) >= 1L) {
                        questState.set("make_my_wedding_dress_cookie", String.valueOf(7), true);
                        string = "leikar_q0037_0703.htm";
                        break;
                    }
                    string = "leikar_q0037_0704.htm";
                    break;
                }
                if (n2 == 31521) {
                    if (questState.getQuestItemsCount(7164) >= 1L && n == 21) {
                        questState.set("make_my_wedding_dress_cookie", String.valueOf(2), true);
                        string = "jeremy_q0037_0201.htm";
                        break;
                    }
                    if (n == 31) {
                        string = "jeremy_q0037_0303.htm";
                        break;
                    }
                    if (n == 41) {
                        questState.set("make_my_wedding_dress_cookie", String.valueOf(4), true);
                        string = "jeremy_q0037_0401.htm";
                        break;
                    }
                    if (n != 51) break;
                    string = "jeremy_q0037_0502.htm";
                    break;
                }
                if (n2 != 31627) break;
                if (questState.getQuestItemsCount(7160) >= 1L && n == 31) {
                    questState.set("make_my_wedding_dress_cookie", String.valueOf(3), true);
                    string = "mist_q0037_0301.htm";
                    break;
                }
                if (n != 41) break;
                string = "mist_q0037_0403.htm";
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
