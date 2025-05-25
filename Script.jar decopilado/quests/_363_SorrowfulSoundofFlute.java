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

public class _363_SorrowfulSoundofFlute
extends Quest
implements ScriptFile {
    private final int aOm = 30956;
    private final int aOn = 30959;
    private final int aOo = 30458;
    private final int aOp = 30058;
    private final int aOq = 30057;
    private final int aOr = 30595;
    private final int aOs = 30594;
    private final int aOt = 4318;
    private final int aOu = 4319;
    private final int aOv = 4320;
    private final int aOw = 4420;

    public _363_SorrowfulSoundofFlute() {
        super(0);
        this.addStartNpc(30956);
        this.addTalkId(new int[]{30458, 30058, 30959, 30057, 30595, 30594});
        this.addQuestItem(new int[]{4318, 4319, 4320});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30956) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (questState.getPlayer().getLevel() >= 15) {
                    questState.set("sorrowful_sound", 2);
                    questState.setCond(1);
                    string2 = "nanarin_q0363_02.htm";
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                } else {
                    string2 = "nanarin_q0363_03.htm";
                    questState.exitCurrentQuest(true);
                }
            }
            if (string.equalsIgnoreCase("reply_1")) {
                questState.giveItems(4318, 1L);
                questState.set("sorrowful_sound", 4);
                questState.setCond(3);
                string2 = "nanarin_q0363_05.htm";
            }
            if (string.equalsIgnoreCase("reply_2")) {
                questState.giveItems(4319, 1L);
                questState.set("sorrowful_sound", 4);
                questState.setCond(3);
                string2 = "nanarin_q0363_06.htm";
            }
            if (string.equalsIgnoreCase("reply_3")) {
                questState.giveItems(4320, 1L);
                questState.set("sorrowful_sound", 4);
                questState.setCond(3);
                string2 = "nanarin_q0363_07.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("sorrowful_sound");
        int n2 = questState.getInt("ex_1_sorrowful_sound");
        int n3 = questState.getState();
        int n4 = npcInstance.getNpcId();
        switch (n3) {
            case 1: {
                if (n4 != 30956) break;
                string = "nanarin_q0363_01.htm";
                break;
            }
            case 2: {
                if (n4 == 30956) {
                    if (n == 2) {
                        string = "nanarin_q0363_04.htm";
                        break;
                    }
                    if (n == 4) {
                        string = "nanarin_q0363_08.htm";
                        break;
                    }
                    if (n == 5) {
                        questState.giveItems(4420, 1L);
                        string = "nanarin_q0363_09.htm";
                        this.giveExtraReward(questState.getPlayer());
                        questState.exitCurrentQuest(false);
                        questState.playSound("ItemSound.quest_finish");
                        break;
                    }
                    if (n != 6) break;
                    if (questState.getQuestItemsCount(4318) > 0L) {
                        questState.takeItems(4318, 1L);
                    } else if (questState.getQuestItemsCount(4319) > 0L) {
                        questState.takeItems(4319, 1L);
                    } else if (questState.getQuestItemsCount(4320) > 0L) {
                        questState.takeItems(4320, 1L);
                    }
                    string = "nanarin_q0363_10.htm";
                    questState.exitCurrentQuest(true);
                    questState.playSound("ItemSound.quest_giveup");
                    break;
                }
                if (n4 == 30959) {
                    if (n == 4) {
                        int n5 = n2 % 10 * 20;
                        if (Rnd.get((int)100) < n5) {
                            if (questState.getQuestItemsCount(4318) > 0L) {
                                questState.takeItems(4318, 1L);
                            }
                            if (questState.getQuestItemsCount(4319) > 0L) {
                                questState.takeItems(4319, 1L);
                            }
                            if (questState.getQuestItemsCount(4320) > 0L) {
                                questState.takeItems(4320, 1L);
                            }
                            questState.set("sorrowful_sound", 5);
                            questState.setCond(4);
                            string = "barbado_q0363_01.htm";
                            break;
                        }
                        questState.set("sorrowful_sound", 6);
                        questState.setCond(4);
                        string = "barbado_q0363_02.htm";
                        break;
                    }
                    if (n != 5 && n != 6) break;
                    string = "barbado_q0363_03.htm";
                    break;
                }
                if (n4 == 30458) {
                    if (n == 2 && n2 % 100 < 10) {
                        int n6 = n2;
                        questState.set("ex_1_sorrowful_sound", n6 + 11);
                        questState.set("sorrowful_sound", 2);
                        int n7 = Rnd.get((int)3);
                        if (n7 == 0) {
                            string = "poitan_q0363_01.htm";
                            questState.setCond(2);
                            break;
                        }
                        if (n7 == 1) {
                            string = "poitan_q0363_02.htm";
                            questState.setCond(2);
                            break;
                        }
                        if (n7 != 2) break;
                        string = "poitan_q0363_03.htm";
                        questState.setCond(2);
                        break;
                    }
                    if (n < 2 || n2 % 100 < 10) break;
                    string = "poitan_q0363_04.htm";
                    break;
                }
                if (n4 == 30058) {
                    if (n == 2 && n2 % 1000 < 100) {
                        int n8 = n2;
                        questState.set("ex_1_sorrowful_sound", n8 + 101);
                        questState.set("sorrowful_sound", 2);
                        int n9 = Rnd.get((int)3);
                        if (n9 == 0) {
                            string = "holvas_q0363_01.htm";
                            questState.setCond(2);
                            break;
                        }
                        if (n9 == 1) {
                            string = "holvas_q0363_02.htm";
                            questState.setCond(2);
                            break;
                        }
                        if (n9 != 2) break;
                        string = "holvas_q0363_03.htm";
                        questState.setCond(2);
                        break;
                    }
                    if (n < 2 || n2 % 1000 <= 100) break;
                    string = "holvas_q0363_04.htm";
                    break;
                }
                if (n4 == 30057) {
                    if (n == 2 && n2 % 100000 < 10000) {
                        int n10 = n2;
                        questState.set("ex_1_sorrowful_sound", n10 + 10001);
                        questState.set("sorrowful_sound", 2);
                        int n11 = Rnd.get((int)3);
                        if (n11 == 0) {
                            string = "amidol_q0363_01.htm";
                            questState.setCond(2);
                        } else if (n11 == 1) {
                            string = "amidol_q0363_02.htm";
                            questState.setCond(2);
                        } else if (n11 == 2) {
                            string = "amidol_q0363_03.htm";
                            questState.setCond(2);
                        }
                    }
                    if (n < 2 || n2 % 100000 < 10000) break;
                    string = "amidol_q0363_04.htm";
                    break;
                }
                if (n4 == 30595) {
                    if (n == 2 && n2 < 100000) {
                        int n12 = n2;
                        questState.set("ex_1_sorrowful_sound", n12 + 100001);
                        questState.set("sorrowful_sound", 2);
                        int n13 = Rnd.get((int)3);
                        if (n13 == 0) {
                            string = "head_blacksmith_opix_q0363_01.htm";
                            questState.setCond(2);
                            break;
                        }
                        if (n13 == 1) {
                            string = "head_blacksmith_opix_q0363_02.htm";
                            questState.setCond(2);
                            break;
                        }
                        if (n13 != 2) break;
                        string = "head_blacksmith_opix_q0363_03.htm";
                        questState.setCond(2);
                        break;
                    }
                    if (n < 2 || n2 < 100000) break;
                    string = "head_blacksmith_opix_q0363_04.htm";
                    break;
                }
                if (n4 != 30594) break;
                if (n == 2 && n2 % 10000 < 1000) {
                    int n14 = n2;
                    questState.set("ex_1_sorrowful_sound", n14 + 1001);
                    questState.set("sorrowful_sound", 2);
                    int n15 = Rnd.get((int)3);
                    if (n15 == 0) {
                        string = "ranspo_q0363_01.htm";
                        questState.setCond(2);
                        break;
                    }
                    if (n15 == 1) {
                        string = "ranspo_q0363_02.htm";
                        questState.setCond(2);
                        break;
                    }
                    if (n15 != 2) break;
                    string = "ranspo_q0363_03.htm";
                    questState.setCond(2);
                    break;
                }
                if (n < 2 || n2 % 10000 < 1000) break;
                string = "ranspo_q0363_04.htm";
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
