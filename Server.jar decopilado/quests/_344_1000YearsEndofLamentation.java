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

public class _344_1000YearsEndofLamentation
extends Quest
implements ScriptFile {
    private static final int aIw = 30754;
    private static final int aIx = 30756;
    private static final int aIy = 30857;
    private static final int aIz = 30623;
    private static final int aIA = 30704;
    private static final int aIB = 20236;
    private static final int aIC = 20237;
    private static final int aID = 20238;
    private static final int aIE = 20239;
    private static final int aIF = 20272;
    private static final int aIG = 20273;
    private static final int aIH = 20274;
    private static final int aII = 20275;
    private static final int aIJ = 4269;
    private static final int aIK = 4270;
    private static final int aIL = 4271;
    private static final int aIM = 4272;
    private static final int aIN = 4273;
    private static final int aIO = 4044;
    private static final int aIP = 4043;
    private static final int aIQ = 4042;
    private static final int aIR = 1879;
    private static final int aIS = 951;
    private static final int aIT = 885;
    private static final int aIU = 1875;
    private static final int aIV = 952;
    private static final int aIW = 2437;
    private static final int aIX = 1874;
    private static final int aIY = 1887;
    private static final int aIZ = 133;
    private static final int aJa = 1882;
    private static final int aJb = 1881;
    private static final int aJc = 191;

    public _344_1000YearsEndofLamentation() {
        super(0);
        this.addStartNpc(30754);
        this.addTalkId(new int[]{30756, 30857, 30623, 30704});
        this.addKillId(new int[]{20236, 20237, 20238, 20239, 20272, 20273, 20274, 20275});
        this.addQuestItem(new int[]{4269, 4270, 4271, 4272, 4273});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("years_end_of_lamentation");
        int n2 = npcInstance.getNpcId();
        if (n2 == 30754) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("years_end_of_lamentation", 0);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "watcher_antaras_gilmore_q0344_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                questState.set("years_end_of_lamentation", 1);
                string2 = "watcher_antaras_gilmore_q0344_04.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getQuestItemsCount(4269) > 0L) {
                    if ((long)Rnd.get((int)1000) >= questState.getQuestItemsCount(4269)) {
                        questState.giveItems(57, questState.getQuestItemsCount(4269) * 60L);
                        questState.takeItems(4269, -1L);
                        string2 = "watcher_antaras_gilmore_q0344_07.htm";
                    } else {
                        questState.set("years_end_of_lamentation_ex", (int)questState.getQuestItemsCount(4269));
                        questState.takeItems(4269, -1L);
                        questState.set("years_end_of_lamentation", 3);
                        string2 = "watcher_antaras_gilmore_q0344_08.htm";
                    }
                } else {
                    string2 = "watcher_antaras_gilmore_q0344_06t.htm";
                }
            } else if (string.equalsIgnoreCase("reply_5")) {
                if (n == 3) {
                    int n3 = Rnd.get((int)100);
                    if (n3 <= 24) {
                        questState.giveItems(4270, 1L);
                        questState.set("years_end_of_lamentation", 4);
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_middle");
                        string2 = "watcher_antaras_gilmore_q0344_09.htm";
                    } else if (n3 <= 49) {
                        questState.giveItems(4271, 1L);
                        questState.set("years_end_of_lamentation", 4);
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_middle");
                        string2 = "watcher_antaras_gilmore_q0344_10.htm";
                    } else if (n3 <= 74) {
                        questState.giveItems(4272, 1L);
                        questState.set("years_end_of_lamentation", 4);
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_middle");
                        string2 = "watcher_antaras_gilmore_q0344_11.htm";
                    } else {
                        questState.giveItems(4273, 1L);
                        questState.set("years_end_of_lamentation", 4);
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_middle");
                        string2 = "watcher_antaras_gilmore_q0344_12.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("reply_3")) {
                questState.set("years_end_of_lamentation", 1);
                string2 = "watcher_antaras_gilmore_q0344_15.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
                string2 = "watcher_antaras_gilmore_q0344_16.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("years_end_of_lamentation");
        int n2 = questState.getInt("years_end_of_lamentation_ex");
        int n3 = npcInstance.getNpcId();
        int n4 = questState.getState();
        switch (n4) {
            case 1: {
                if (n3 != 30754) break;
                if (questState.getPlayer().getLevel() < 48) {
                    string = "watcher_antaras_gilmore_q0344_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "watcher_antaras_gilmore_q0344_02.htm";
                break;
            }
            case 2: {
                if (n3 == 30754) {
                    if (n == 0) {
                        questState.set("years_end_of_lamentation", 1);
                        string = "watcher_antaras_gilmore_q0344_04.htm";
                        break;
                    }
                    if (n == 1 || n == 2 && questState.getQuestItemsCount(4269) == 0L) {
                        string = "watcher_antaras_gilmore_q0344_05.htm";
                        break;
                    }
                    if (n == 2 && questState.getQuestItemsCount(4269) > 0L) {
                        string = "watcher_antaras_gilmore_q0344_06.htm";
                        break;
                    }
                    if (n == 3) {
                        int n5 = Rnd.get((int)100);
                        if (n5 <= 24) {
                            questState.giveItems(4270, 1L);
                            questState.set("years_end_of_lamentation", 4);
                            questState.setCond(2);
                            questState.playSound("ItemSound.quest_middle");
                            string = "watcher_antaras_gilmore_q0344_09.htm";
                            break;
                        }
                        if (n5 <= 49) {
                            questState.giveItems(4271, 1L);
                            questState.set("years_end_of_lamentation", 4);
                            questState.setCond(2);
                            questState.playSound("ItemSound.quest_middle");
                            string = "watcher_antaras_gilmore_q0344_10.htm";
                            break;
                        }
                        if (n5 <= 74) {
                            questState.giveItems(4272, 1L);
                            questState.set("years_end_of_lamentation", 4);
                            questState.setCond(2);
                            questState.playSound("ItemSound.quest_middle");
                            string = "watcher_antaras_gilmore_q0344_11.htm";
                            break;
                        }
                        questState.giveItems(4273, 1L);
                        questState.set("years_end_of_lamentation", 4);
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_middle");
                        string = "watcher_antaras_gilmore_q0344_12.htm";
                        break;
                    }
                    if (n == 4) {
                        string = "watcher_antaras_gilmore_q0344_13.htm";
                        break;
                    }
                    if (n < 5 || n > 8) break;
                    if (n == 5) {
                        questState.giveItems(57, (long)(n2 * 50 + 1500));
                    } else if (n == 6) {
                        questState.giveItems(57, (long)(n2 * 50));
                        questState.giveItems(4044, 1L);
                    } else if (n == 7) {
                        questState.giveItems(57, (long)(n2 * 50));
                        questState.giveItems(4043, 1L);
                    } else if (n == 8) {
                        questState.giveItems(57, (long)(n2 * 50));
                        questState.giveItems(4042, 1L);
                    }
                    questState.set("years_end_of_lamentation_ex", 0);
                    questState.set("years_end_of_lamentation", 1);
                    string = "watcher_antaras_gilmore_q0344_14.htm";
                    break;
                }
                if (n3 == 30756) {
                    if (questState.getQuestItemsCount(4270) >= 1L) {
                        int n6 = Rnd.get((int)100);
                        if (n6 <= 39) {
                            questState.giveItems(1879, 55L);
                        } else if (n6 <= 89) {
                            questState.giveItems(951, 1L);
                        } else {
                            questState.giveItems(885, 1L);
                        }
                        questState.takeItems(4270, -1L);
                        questState.set("years_end_of_lamentation", 5);
                        string = "sir_kristof_rodemai_q0344_01.htm";
                        break;
                    }
                    if (n != 5) break;
                    string = "sir_kristof_rodemai_q0344_02.htm";
                    break;
                }
                if (n3 == 30857) {
                    if (questState.getQuestItemsCount(4273) >= 1L) {
                        int n7 = Rnd.get((int)100);
                        if (n7 <= 49) {
                            questState.giveItems(1875, 19L);
                        } else if (n7 <= 69) {
                            questState.giveItems(952, 5L);
                        } else {
                            questState.giveItems(2437, 1L);
                        }
                        questState.takeItems(4273, -1L);
                        questState.set("years_end_of_lamentation", 8);
                        string = "highpriest_orven_q0344_01.htm";
                        break;
                    }
                    if (n != 8) break;
                    string = "highpriest_orven_q0344_02.htm";
                    break;
                }
                if (n3 == 30623) {
                    if (questState.getQuestItemsCount(4271) >= 1L) {
                        int n8 = Rnd.get((int)100);
                        if (n8 <= 52) {
                            questState.giveItems(1874, 25L);
                        } else if (n8 <= 76) {
                            questState.giveItems(1887, 10L);
                        } else if (n8 <= 98) {
                            questState.giveItems(951, 1L);
                        } else {
                            questState.giveItems(133, 1L);
                        }
                        questState.takeItems(4271, -1L);
                        questState.set("years_end_of_lamentation", 6);
                        string = "duelist_kaien_q0344_01.htm";
                        break;
                    }
                    if (n != 6) break;
                    string = "duelist_kaien_q0344_02.htm";
                    break;
                }
                if (n3 != 30704) break;
                if (questState.getQuestItemsCount(4272) >= 1L) {
                    int n9 = Rnd.get((int)100);
                    if (n9 <= 47) {
                        questState.giveItems(1882, 70L);
                    } else if (n9 <= 97) {
                        questState.giveItems(1881, 50L);
                    } else {
                        questState.giveItems(191, 1L);
                    }
                    questState.takeItems(4272, -1L);
                    questState.set("years_end_of_lamentation", 7);
                    string = "high_prefect_garvarentz_q0344_01.htm";
                    break;
                }
                if (n != 7) break;
                string = "high_prefect_garvarentz_q0344_02.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("years_end_of_lamentation");
        int n2 = npcInstance.getNpcId();
        if (n2 == 20236 || n2 == 20272) {
            if ((n == 1 || n == 2) && Rnd.get((int)100) < 58) {
                questState.rollAndGive(4269, 1, 100.0);
                questState.set("years_end_of_lamentation", 2);
            }
        } else if (n2 == 20237 || n2 == 20273) {
            if ((n == 1 || n == 2) && Rnd.get((int)100) < 78) {
                questState.rollAndGive(4269, 1, 100.0);
                questState.set("years_end_of_lamentation", 2);
            }
        } else if (n2 == 20238 || n2 == 20274) {
            if ((n == 1 || n == 2) && Rnd.get((int)100) < 75) {
                questState.rollAndGive(4269, 1, 100.0);
                questState.set("years_end_of_lamentation", 2);
            }
        } else if (!(n2 != 20239 && n2 != 20275 || n != 1 && n != 2 || Rnd.get((int)100) >= 79)) {
            questState.rollAndGive(4269, 1, 100.0);
            questState.set("years_end_of_lamentation", 2);
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
