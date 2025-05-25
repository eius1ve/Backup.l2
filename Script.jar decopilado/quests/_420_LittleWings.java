/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;

public class _420_LittleWings
extends Quest
implements ScriptFile {
    private static final int bdB = 30829;
    private static final int bdC = 30610;
    private static final int bdD = 30608;
    private static final int bdE = 30711;
    private static final int bdF = 30747;
    private static final int bdG = 30748;
    private static final int bdH = 30749;
    private static final int bdI = 30750;
    private static final int bdJ = 30751;
    private static final int bdK = 30752;
    private static final int bdL = 20202;
    private static final int bdM = 20231;
    private static final int bdN = 20233;
    private static final int bdO = 20270;
    private static final int bdP = 20551;
    private static final int bdQ = 20580;
    private static final int bdR = 20589;
    private static final int bdS = 20590;
    private static final int bdT = 20591;
    private static final int bdU = 20592;
    private static final int bdV = 20593;
    private static final int bdW = 20594;
    private static final int bdX = 20595;
    private static final int bdY = 20596;
    private static final int bdZ = 20597;
    private static final int bea = 20598;
    private static final int beb = 20599;
    private static final int bec = 1870;
    private static final int bed = 1871;
    private static final int bee = 1873;
    private static final int bef = 1875;
    private static final int beg = 2130;
    private static final int beh = 2131;
    private static final int bei = 3500;
    private static final int bej = 3501;
    private static final int bek = 3502;
    private static final int bel = 3912;
    private static final int bem = 4038;
    private static final int ben = 3499;
    private static final int beo = 3816;
    private static final int bep = 3817;
    private static final int beq = 3818;
    private static final int ber = 3819;
    private static final int bes = 3820;
    private static final int bet = 3821;
    private static final int beu = 3822;
    private static final int bev = 3823;
    private static final int bew = 3824;
    private static final int bex = 3825;
    private static final int bey = 3826;
    private static final int bez = 3827;
    private static final int beA = 3828;
    private static final int beB = 3829;
    private static final int beC = 3830;
    private static final int beD = 3831;
    private static int beE = 0;

    public _420_LittleWings() {
        super(0);
        this.addStartNpc(30829);
        this.addTalkId(new int[]{30610, 30608, 30711, 30747, 30748, 30749, 30750, 30751, 30752});
        this.addKillId(new int[]{20202, 20231, 20233, 20270, 20551, 20580, 20589, 20590, 20591, 20592, 20593, 20594, 20595, 20596, 20597, 20598, 20599});
        this.addQuestItem(new int[]{3499, 3816, 3817, 3818, 3819, 3820, 3821, 3822, 3824, 3826, 3828, 3830, 3823, 3825, 3827, 3829, 3831});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30829) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("little_wings", String.valueOf(1), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "pet_manager_cooper_q0420_02.htm";
            }
        } else if (n == 30610) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "sage_cronos_q0420_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "sage_cronos_q0420_03.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "sage_cronos_q0420_04.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                questState.setCond(2);
                questState.set("little_wings", String.valueOf(2), true);
                questState.giveItems(3818, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "sage_cronos_q0420_05.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                questState.setCond(2);
                questState.set("little_wings", String.valueOf(2), true);
                questState.giveItems(3819, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "sage_cronos_q0420_06.htm";
            } else if (string.equalsIgnoreCase("reply_6")) {
                questState.setCond(2);
                questState.set("little_wings", String.valueOf(11), true);
                questState.giveItems(3818, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "sage_cronos_q0420_12.htm";
            } else if (string.equalsIgnoreCase("reply_7")) {
                questState.setCond(2);
                questState.set("little_wings", String.valueOf(11), true);
                questState.giveItems(3819, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "sage_cronos_q0420_13.htm";
            }
        } else if (n == 30608) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.takeItems(3818, -1L);
                questState.takeItems(1870, 10L);
                questState.takeItems(1871, 10L);
                questState.takeItems(2130, 1L);
                questState.takeItems(1873, 3L);
                questState.takeItems(3820, -1L);
                questState.giveItems(3816, 1L);
                string2 = "marya_q0420_03.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                questState.takeItems(3819, -1L);
                questState.takeItems(1870, 10L);
                questState.takeItems(1871, 10L);
                questState.takeItems(2131, 1L);
                questState.takeItems(1875, 1L);
                questState.takeItems(1873, 5L);
                questState.takeItems(3820, -1L);
                questState.giveItems(3817, 1L);
                string2 = "marya_q0420_05.htm";
            }
        } else if (n == 30711) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "guard_byron_q0420_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getQuestItemsCount(3816) == 1L) {
                    questState.setCond(4);
                    questState.set("little_wings", String.valueOf(4), true);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "guard_byron_q0420_03.htm";
                } else {
                    questState.setCond(4);
                    questState.set("little_wings", String.valueOf(4), true);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "guard_byron_q0420_04.htm";
                }
            }
        } else if (n == 30747) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.takeItems(3816, -1L);
                questState.set("little_wings", String.valueOf(5), true);
                string2 = "fairy_mymyu_q0420_03.htm";
            } else if (string.equalsIgnoreCase("reply_3") && questState.getQuestItemsCount(3817) > 0L) {
                questState.giveItems(3499, 1L);
                questState.takeItems(3817, -1L);
                questState.set("little_wings", String.valueOf(5), true);
                string2 = "fairy_mymyu_q0420_05.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "fairy_mymyu_q0420_06.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                questState.setCond(5);
                questState.set("little_wings", String.valueOf(6), true);
                questState.giveItems(3821, 1L);
                string2 = "fairy_mymyu_q0420_08.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                if (questState.getQuestItemsCount(3499) == 1L) {
                    string2 = "fairy_mymyu_q0420_13.htm";
                }
                int n2 = Rnd.get((int)100);
                if (questState.getQuestItemsCount(3823) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n2 < 45) {
                            questState.giveItems(3500, 1L);
                        } else if (n2 < 75) {
                            questState.giveItems(3501, 1L);
                        } else {
                            questState.giveItems(3502, 1L);
                        }
                    } else if (n2 < 50) {
                        questState.giveItems(3500, 1L);
                    } else if (n2 < 85) {
                        questState.giveItems(3501, 1L);
                    } else {
                        questState.giveItems(3502, 1L);
                    }
                    questState.takeItems(3823, -1L);
                } else if (questState.getQuestItemsCount(3829) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n2 < 55) {
                            questState.giveItems(3500, 1L);
                        } else if (n2 < 85) {
                            questState.giveItems(3501, 1L);
                        } else {
                            questState.giveItems(3502, 1L);
                        }
                    } else if (n2 < 65) {
                        questState.giveItems(3500, 1L);
                    } else if (n2 < 95) {
                        questState.giveItems(3501, 1L);
                    } else {
                        questState.giveItems(3502, 1L);
                    }
                    questState.takeItems(3829, -1L);
                } else if (questState.getQuestItemsCount(3827) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n2 < 60) {
                            questState.giveItems(3500, 1L);
                        } else if (n2 < 90) {
                            questState.giveItems(3501, 1L);
                        } else {
                            questState.giveItems(3502, 1L);
                        }
                    } else if (n2 < 70) {
                        questState.giveItems(3500, 1L);
                    } else {
                        questState.giveItems(3501, 1L);
                    }
                    questState.takeItems(3827, -1L);
                } else if (questState.getQuestItemsCount(3831) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n2 < 70) {
                            questState.giveItems(3500, 1L);
                        } else {
                            questState.giveItems(3501, 1L);
                        }
                    } else if (n2 < 85) {
                        questState.giveItems(3500, 1L);
                    } else {
                        questState.giveItems(3501, 1L);
                    }
                    questState.takeItems(3831, -1L);
                } else if (questState.getQuestItemsCount(3825) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n2 < 90) {
                            questState.giveItems(3500, 1L);
                        } else {
                            questState.giveItems(3501, 1L);
                        }
                    } else {
                        questState.giveItems(3500, 1L);
                    }
                    questState.takeItems(3825, -1L);
                }
                questState.unset("little_wings");
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "fairy_mymyu_q0420_16.htm";
            } else if (string.equalsIgnoreCase("reply_7")) {
                int n3 = Rnd.get((int)100);
                if (questState.getQuestItemsCount(3823) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n3 < 45) {
                            questState.giveItems(3500, 1L);
                        } else if (n3 < 75) {
                            questState.giveItems(3501, 1L);
                        } else {
                            questState.giveItems(3502, 1L);
                        }
                    } else if (n3 < 50) {
                        questState.giveItems(3500, 1L);
                    } else if (n3 < 85) {
                        questState.giveItems(3501, 1L);
                    } else {
                        questState.giveItems(3502, 1L);
                    }
                    questState.takeItems(3823, -1L);
                } else if (questState.getQuestItemsCount(3829) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n3 < 55) {
                            questState.giveItems(3500, 1L);
                        } else if (n3 < 85) {
                            questState.giveItems(3501, 1L);
                        } else {
                            questState.giveItems(3502, 1L);
                        }
                    } else if (n3 < 65) {
                        questState.giveItems(3500, 1L);
                    } else if (n3 < 95) {
                        questState.giveItems(3501, 1L);
                    } else {
                        questState.giveItems(3502, 1L);
                    }
                    questState.takeItems(3829, -1L);
                } else if (questState.getQuestItemsCount(3827) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n3 < 60) {
                            questState.giveItems(3500, 1L);
                        } else if (n3 < 90) {
                            questState.giveItems(3501, 1L);
                        } else {
                            questState.giveItems(3502, 1L);
                        }
                    } else if (n3 < 70) {
                        questState.giveItems(3500, 1L);
                    } else {
                        questState.giveItems(3501, 1L);
                    }
                    questState.takeItems(3827, -1L);
                } else if (questState.getQuestItemsCount(3831) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n3 < 70) {
                            questState.giveItems(3500, 1L);
                        } else {
                            questState.giveItems(3501, 1L);
                        }
                    } else if (n3 < 85) {
                        questState.giveItems(3500, 1L);
                    } else {
                        questState.giveItems(3501, 1L);
                    }
                    questState.takeItems(3831, -1L);
                } else if (questState.getQuestItemsCount(3825) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n3 < 90) {
                            questState.giveItems(3500, 1L);
                        } else {
                            questState.giveItems(3501, 1L);
                        }
                    } else {
                        questState.giveItems(3500, 1L);
                    }
                    questState.takeItems(3825, -1L);
                }
                questState.unset("little_wings");
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "fairy_mymyu_q0420_14.htm";
            } else if (string.equalsIgnoreCase("reply_6")) {
                int n4 = Rnd.get((int)100);
                if (questState.getQuestItemsCount(3823) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n4 < 45) {
                            questState.giveItems(3500, 1L);
                        } else if (n4 < 75) {
                            questState.giveItems(3501, 1L);
                        } else {
                            questState.giveItems(3502, 1L);
                        }
                    } else if (n4 < 50) {
                        questState.giveItems(3500, 1L);
                    } else if (n4 < 85) {
                        questState.giveItems(3501, 1L);
                    } else {
                        questState.giveItems(3502, 1L);
                    }
                    questState.takeItems(3823, -1L);
                } else if (questState.getQuestItemsCount(3829) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n4 < 55) {
                            questState.giveItems(3500, 1L);
                        } else if (n4 < 85) {
                            questState.giveItems(3501, 1L);
                        } else {
                            questState.giveItems(3502, 1L);
                        }
                    } else if (n4 < 65) {
                        questState.giveItems(3500, 1L);
                    } else if (n4 < 95) {
                        questState.giveItems(3501, 1L);
                    } else {
                        questState.giveItems(3502, 1L);
                    }
                    questState.takeItems(3829, -1L);
                } else if (questState.getQuestItemsCount(3827) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n4 < 60) {
                            questState.giveItems(3500, 1L);
                        } else if (n4 < 90) {
                            questState.giveItems(3501, 1L);
                        } else {
                            questState.giveItems(3502, 1L);
                        }
                    } else if (n4 < 70) {
                        questState.giveItems(3500, 1L);
                    } else {
                        questState.giveItems(3501, 1L);
                    }
                    questState.takeItems(3827, -1L);
                } else if (questState.getQuestItemsCount(3831) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n4 < 70) {
                            questState.giveItems(3500, 1L);
                        } else {
                            questState.giveItems(3501, 1L);
                        }
                    } else if (n4 < 85) {
                        questState.giveItems(3500, 1L);
                    } else {
                        questState.giveItems(3501, 1L);
                    }
                    questState.takeItems(3831, -1L);
                } else if (questState.getQuestItemsCount(3825) >= 1L) {
                    if (questState.getQuestItemsCount(3499) == 1L) {
                        if (n4 < 90) {
                            questState.giveItems(3500, 1L);
                        } else {
                            questState.giveItems(3501, 1L);
                        }
                    } else {
                        questState.giveItems(3500, 1L);
                    }
                    questState.takeItems(3825, -1L);
                }
                if (questState.getQuestItemsCount(3499) == 0L) {
                    string2 = "fairy_mymyu_q0420_14.htm";
                } else {
                    if (n4 < 5) {
                        string2 = "fairy_mymyu_q0420_15.htm";
                        questState.giveItems(3912, 1L);
                    } else {
                        string2 = "fairy_mymyu_q0420_15t.htm";
                        questState.giveItems(4038, 20L);
                    }
                    questState.takeItems(3499, -1L);
                }
                questState.unset("little_wings");
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
            }
        } else if (n == 30748) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.setCond(6);
                questState.set("little_wings", String.valueOf(7), true);
                questState.giveItems(3822, 1L);
                questState.takeItems(3821, -1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "drake_exarion_q0420_03.htm";
            }
        } else if (n == 30749) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.setCond(6);
                questState.set("little_wings", String.valueOf(7), true);
                questState.giveItems(3824, 1L);
                questState.takeItems(3821, -1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "drake_zwov_q0420_03.htm";
            }
        } else if (n == 30750) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.setCond(6);
                questState.set("little_wings", String.valueOf(7), true);
                questState.giveItems(3826, 1L);
                questState.takeItems(3821, -1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "drake_kalibran_q0420_03.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                questState.set("little_wings", String.valueOf(8), true);
                questState.takeItems(3827, questState.getQuestItemsCount(3827) - 1L);
                questState.takeItems(3826, -1L);
                string2 = "drake_kalibran_q0420_06.htm";
            }
        } else if (n == 30751) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "wyrm_suzet_q0420_03.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                questState.setCond(6);
                questState.set("little_wings", String.valueOf(7), true);
                questState.giveItems(3828, 1L);
                questState.takeItems(3821, -1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "wyrm_suzet_q0420_04.htm";
            }
        } else if (n == 30752 && string.equalsIgnoreCase("reply_1")) {
            questState.setCond(6);
            questState.set("little_wings", String.valueOf(7), true);
            questState.giveItems(3830, 1L);
            questState.takeItems(3821, -1L);
            questState.playSound("ItemSound.quest_middle");
            string2 = "wyrm_shamhai_q0420_03.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("little_wings");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30829) break;
                if (questState.getPlayer().getLevel() < 35) {
                    string = "pet_manager_cooper_q0420_03.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "pet_manager_cooper_q0420_01.htm";
                break;
            }
            case 2: {
                if (n2 == 30829) {
                    if (n != 1) break;
                    string = "pet_manager_cooper_q0420_04.htm";
                    break;
                }
                if (n2 == 30610) {
                    if (n == 1) {
                        string = "sage_cronos_q0420_01.htm";
                        break;
                    }
                    if ((n == 2 || n == 11) && questState.getQuestItemsCount(3816) == 0L && questState.getQuestItemsCount(3817) == 0L) {
                        string = "sage_cronos_q0420_07.htm";
                        break;
                    }
                    if (n == 2 && (questState.getQuestItemsCount(3816) == 1L || questState.getQuestItemsCount(3817) == 1L)) {
                        questState.setCond(3);
                        questState.set("little_wings", String.valueOf(3), true);
                        questState.playSound("ItemSound.quest_middle");
                        string = "sage_cronos_q0420_08.htm";
                        break;
                    }
                    if (n == 11 && (questState.getQuestItemsCount(3816) == 1L || questState.getQuestItemsCount(3817) == 1L)) {
                        string = "sage_cronos_q0420_14.htm";
                        break;
                    }
                    if (n == 3) {
                        string = "sage_cronos_q0420_09.htm";
                        break;
                    }
                    if (n == 10) {
                        string = "sage_cronos_q0420_10.htm";
                        break;
                    }
                    if (n != 4 || questState.getQuestItemsCount(3816) != 1L && questState.getQuestItemsCount(3817) != 1L) break;
                    string = "sage_cronos_q0420_11.htm";
                    break;
                }
                if (n2 == 30608) {
                    if (!(n != 2 && n != 11 || (questState.getQuestItemsCount(3818) != 1L || questState.getQuestItemsCount(1870) >= 10L && questState.getQuestItemsCount(1871) >= 10L && questState.getQuestItemsCount(2130) != 0L && questState.getQuestItemsCount(1873) >= 3L && questState.getQuestItemsCount(3820) >= 10L) && (questState.getQuestItemsCount(3819) != 1L || questState.getQuestItemsCount(1870) >= 10L && questState.getQuestItemsCount(1871) >= 10L && questState.getQuestItemsCount(2131) != 0L && questState.getQuestItemsCount(1875) != 0L && questState.getQuestItemsCount(1873) >= 5L && questState.getQuestItemsCount(3820) >= 20L))) {
                        string = "marya_q0420_01.htm";
                        break;
                    }
                    if ((n == 2 || n == 11) && questState.getQuestItemsCount(3818) == 1L && questState.getQuestItemsCount(1870) >= 10L && questState.getQuestItemsCount(1871) >= 10L && questState.getQuestItemsCount(2130) >= 1L && questState.getQuestItemsCount(1873) >= 3L && questState.getQuestItemsCount(3820) >= 10L) {
                        string = "marya_q0420_02.htm";
                        break;
                    }
                    if ((n == 2 || n == 11) && questState.getQuestItemsCount(3819) == 1L && questState.getQuestItemsCount(1870) >= 10L && questState.getQuestItemsCount(1871) >= 10L && questState.getQuestItemsCount(2131) >= 1L && questState.getQuestItemsCount(1875) >= 1L && questState.getQuestItemsCount(1873) >= 5L && questState.getQuestItemsCount(3820) >= 20L) {
                        string = "marya_q0420_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3816) != 1L && questState.getQuestItemsCount(3817) != 1L) break;
                    string = "marya_q0420_06.htm";
                    break;
                }
                if (n2 == 30711) {
                    if (n == 3 && (questState.getQuestItemsCount(3816) == 1L || questState.getQuestItemsCount(3817) == 1L)) {
                        string = "guard_byron_q0420_01.htm";
                        break;
                    }
                    if (n == 11 && questState.getQuestItemsCount(3816) == 1L) {
                        questState.setCond(4);
                        questState.set("little_wings", String.valueOf(4), true);
                        questState.playSound("ItemSound.quest_middle");
                        string = "guard_byron_q0420_05.htm";
                        break;
                    }
                    if (n == 11 && questState.getQuestItemsCount(3817) == 1L) {
                        questState.setCond(4);
                        questState.set("little_wings", String.valueOf(4), true);
                        questState.playSound("ItemSound.quest_middle");
                        string = "guard_byron_q0420_06.htm";
                        break;
                    }
                    if (n == 4 && questState.getQuestItemsCount(3816) == 1L) {
                        string = "guard_byron_q0420_07.htm";
                        break;
                    }
                    if (n == 4 && questState.getQuestItemsCount(3817) == 1L) {
                        string = "guard_byron_q0420_08.htm";
                        break;
                    }
                    if (n == 10) {
                        string = "guard_byron_q0420_09.htm";
                        break;
                    }
                    if (n != 11 || questState.getQuestItemsCount(3816) != 0L || questState.getQuestItemsCount(3817) != 0L || questState.getQuestItemsCount(3818) != 1L && questState.getQuestItemsCount(3819) != 1L) break;
                    string = "guard_byron_q0420_10.htm";
                    break;
                }
                if (n2 == 30747) {
                    int n4;
                    if (questState.getQuestItemsCount(3816) == 1L) {
                        string = "fairy_mymyu_q0420_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3817) == 1L) {
                        string = "fairy_mymyu_q0420_04.htm";
                        break;
                    }
                    if (n == 5) {
                        string = "fairy_mymyu_q0420_07.htm";
                        break;
                    }
                    if (n == 6 && questState.getQuestItemsCount(3821) == 1L) {
                        string = "fairy_mymyu_q0420_09.htm";
                        break;
                    }
                    if (n == 7) {
                        if (questState.getQuestItemsCount(3823) < 20L && questState.getQuestItemsCount(3825) < 20L && questState.getQuestItemsCount(3827) < 20L && questState.getQuestItemsCount(3829) < 20L && questState.getQuestItemsCount(3831) < 20L) {
                            string = "fairy_mymyu_q0420_10.htm";
                            break;
                        }
                        string = "fairy_mymyu_q0420_11.htm";
                        break;
                    }
                    if (n == 8) {
                        string = "fairy_mymyu_q0420_12.htm";
                        break;
                    }
                    if (n >= 4 && n <= 8 || questState.getQuestItemsCount(3816) != 0L || questState.getQuestItemsCount(3817) != 0L) break;
                    if ((n4 = ++beE % 3) == 0) {
                        questState.getPlayer().teleToLocation(109816, 40854, -4640);
                        break;
                    }
                    if (n4 == 1) {
                        questState.getPlayer().teleToLocation(108940, 41615, -4643);
                        break;
                    }
                    questState.getPlayer().teleToLocation(110395, 41625, -4642);
                    break;
                }
                if (n2 == 30748) {
                    if (n == 6 && questState.getQuestItemsCount(3821) == 1L) {
                        string = "drake_exarion_q0420_02.htm";
                        break;
                    }
                    if (n == 7 && questState.getQuestItemsCount(3823) < 20L && questState.getQuestItemsCount(3822) == 1L) {
                        string = "drake_exarion_q0420_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3823) >= 20L) {
                        questState.setCond(7);
                        questState.set("little_wings", String.valueOf(8), true);
                        questState.takeItems(3823, questState.getQuestItemsCount(3823) - 1L);
                        questState.takeItems(3822, -1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "drake_exarion_q0420_05.htm";
                        break;
                    }
                    if (n != 8 || questState.getQuestItemsCount(3823) != 1L) break;
                    string = "drake_exarion_q0420_06.htm";
                    break;
                }
                if (n2 == 30749) {
                    if (n == 6 && questState.getQuestItemsCount(3821) == 1L) {
                        string = "drake_zwov_q0420_02.htm";
                        break;
                    }
                    if (n == 7 && questState.getQuestItemsCount(3825) < 20L && questState.getQuestItemsCount(3824) == 1L) {
                        string = "drake_zwov_q0420_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3825) >= 20L) {
                        questState.setCond(7);
                        questState.set("little_wings", String.valueOf(8), true);
                        questState.takeItems(3825, questState.getQuestItemsCount(3825) - 1L);
                        questState.takeItems(3824, -1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "drake_zwov_q0420_05.htm";
                        break;
                    }
                    if (n != 8 || questState.getQuestItemsCount(3825) != 1L) break;
                    string = "drake_zwov_q0420_06.htm";
                    break;
                }
                if (n2 == 30750) {
                    if (n == 6 && questState.getQuestItemsCount(3821) == 1L) {
                        string = "drake_kalibran_q0420_02.htm";
                        break;
                    }
                    if (n == 7 && questState.getQuestItemsCount(3827) < 20L && questState.getQuestItemsCount(3826) == 1L) {
                        string = "drake_kalibran_q0420_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3827) >= 20L) {
                        questState.setCond(7);
                        questState.playSound("ItemSound.quest_middle");
                        string = "drake_kalibran_q0420_05.htm";
                        break;
                    }
                    if (n != 8 || questState.getQuestItemsCount(3827) != 1L) break;
                    string = "drake_kalibran_q0420_07.htm";
                    break;
                }
                if (n2 == 30751) {
                    if (n == 6 && questState.getQuestItemsCount(3821) == 1L) {
                        string = "wyrm_suzet_q0420_02.htm";
                        break;
                    }
                    if (n == 7 && questState.getQuestItemsCount(3829) < 20L && questState.getQuestItemsCount(3828) == 1L) {
                        string = "wyrm_suzet_q0420_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3829) >= 20L) {
                        questState.setCond(7);
                        questState.set("little_wings", String.valueOf(8), true);
                        questState.takeItems(3829, questState.getQuestItemsCount(3829) - 1L);
                        questState.takeItems(3828, -1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "wyrm_suzet_q0420_06.htm";
                        break;
                    }
                    if (n != 8 || questState.getQuestItemsCount(3829) != 1L) break;
                    string = "wyrm_suzet_q0420_07.htm";
                    break;
                }
                if (n2 != 30752) break;
                if (n == 6 && questState.getQuestItemsCount(3821) == 1L) {
                    string = "wyrm_shamhai_q0420_02.htm";
                    break;
                }
                if (n == 7 && questState.getQuestItemsCount(3831) < 20L && questState.getQuestItemsCount(3830) == 1L) {
                    string = "wyrm_shamhai_q0420_04.htm";
                    break;
                }
                if (questState.getQuestItemsCount(3831) >= 20L) {
                    questState.setCond(7);
                    questState.set("little_wings", String.valueOf(8), true);
                    questState.takeItems(3831, questState.getQuestItemsCount(3831) - 1L);
                    questState.takeItems(3830, -1L);
                    questState.playSound("ItemSound.quest_middle");
                    string = "wyrm_shamhai_q0420_05.htm";
                    break;
                }
                if (n != 8 || questState.getQuestItemsCount(3831) != 1L) break;
                string = "wyrm_shamhai_q0420_06.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20202) {
            if (questState.getQuestItemsCount(3830) == 1L && questState.getQuestItemsCount(3831) < 20L && Rnd.get((int)100) < 50) {
                questState.giveItems(3831, 1L);
                if (questState.getQuestItemsCount(3831) >= 19L) {
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20231) {
            if ((questState.getQuestItemsCount(3818) == 1L && questState.getQuestItemsCount(3820) < 10L || questState.getQuestItemsCount(3819) == 1L && questState.getQuestItemsCount(3820) < 20L) && Rnd.get((int)100) < 30) {
                int n2 = 19;
                if (questState.getQuestItemsCount(3818) == 1L) {
                    n2 = 9;
                }
                if (questState.getQuestItemsCount(3820) >= (long)n2) {
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.playSound("ItemSound.quest_itemget");
                }
                questState.giveItems(3820, 1L);
            }
        } else if (n == 20233) {
            if (questState.getQuestItemsCount(3824) == 1L && questState.getQuestItemsCount(3825) < 20L && Rnd.get((int)100) < 50) {
                questState.rollAndGive(3825, 1, 100.0);
                if (questState.getQuestItemsCount(3825) >= 19L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20270) {
            if (questState.getQuestItemsCount(3828) == 1L && questState.getQuestItemsCount(3829) < 20L && Rnd.get((int)100) < 50) {
                questState.rollAndGive(3829, 1, 100.0);
                if (questState.getQuestItemsCount(3829) >= 19L) {
                    questState.playSound("ItemSound.quest_middle");
                }
                Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.I_THOUGHT_ID_CAUGHT_ONE_SHARE_WHEW", (Object[])new Object[0]);
            }
        } else if (n == 20551) {
            if (questState.getQuestItemsCount(3826) == 1L && questState.getQuestItemsCount(3827) < 20L && Rnd.get((int)100) < 50) {
                questState.rollAndGive(3827, 1, 100.0);
                if (questState.getQuestItemsCount(3827) >= 19L) {
                    questState.playSound("ItemSound.quest_middle");
                }
                Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.HEY_EVERYBODY_WATCH_THE_EGGS", (Object[])new Object[0]);
            }
        } else if (n == 20580) {
            if (questState.getQuestItemsCount(3822) == 1L && questState.getQuestItemsCount(3823) < 20L && Rnd.get((int)100) < 50) {
                questState.rollAndGive(3823, 1, 100.0);
                if (questState.getQuestItemsCount(3823) >= 19L) {
                    questState.playSound("ItemSound.quest_middle");
                }
                Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.IF_THE_EGGS_GET_TAKEN_WERE_DEAD", (Object[])new Object[0]);
            }
        } else if ((n == 20589 || n == 20590 || n == 20591 || n == 20592 || n == 20593 || n == 20594 || n == 20595 || n == 20596 || n == 20597 || n == 20598 || n == 20599) && questState.getQuestItemsCount(3817) == 1L && Rnd.get((int)100) < 30) {
            questState.set("little_wings", String.valueOf(10), true);
            questState.takeItems(3817, 1L);
            questState.playSound("ItemSound.quest_middle");
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.THE_STONE_THE_ELVEN_STONE_BROKE", (Object[])new Object[0]);
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
