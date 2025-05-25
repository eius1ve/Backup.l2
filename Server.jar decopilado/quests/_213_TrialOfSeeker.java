/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.ScriptFile;

public class _213_TrialOfSeeker
extends Quest
implements ScriptFile {
    private static final int XZ = 30106;
    private static final int Ya = 30064;
    private static final int Yb = 30684;
    private static final int Yc = 30715;
    private static final int Yd = 30526;
    private static final int Ye = 20080;
    private static final int Yf = 20088;
    private static final int Yg = 20158;
    private static final int Yh = 20198;
    private static final int Yi = 20211;
    private static final int Yj = 20234;
    private static final int Yk = 20249;
    private static final int Yl = 20270;
    private static final int Ym = 20495;
    private static final int Yn = 20580;
    private static final int Yo = 2647;
    private static final int Yp = 2648;
    private static final int Yq = 2649;
    private static final int Yr = 2650;
    private static final int Ys = 2651;
    private static final int Yt = 2652;
    private static final int Yu = 2653;
    private static final int Yv = 2654;
    private static final int Yw = 2655;
    private static final int Yx = 2656;
    private static final int Yy = 2657;
    private static final int Yz = 2658;
    private static final int YA = 2659;
    private static final int YB = 2660;
    private static final int YC = 2661;
    private static final int YD = 2662;
    private static final int YE = 2663;
    private static final int YF = 2664;
    private static final int YG = 2665;
    private static final int YH = 2666;
    private static final int YI = 2667;
    private static final int YJ = 2668;
    private static final int YK = 2669;
    private static final int YL = 2670;
    private static final int YM = 2671;
    private static final int YN = 2672;
    private static final int YO = 2673;
    private static final int YP = 7562;

    public _213_TrialOfSeeker() {
        super(0);
        this.addStartNpc(30106);
        this.addTalkId(new int[]{30064, 30684, 30715, 30526});
        this.addKillId(new int[]{20080, 20088, 20158, 20198, 20211, 20234, 20249, 20270, 20495, 20580});
        this.addQuestItem(new int[]{2647, 2648, 2649, 2650, 2658, 2651, 2659, 2652, 2661, 2662, 2663, 2664, 2665, 2667, 2666, 2672, 2653, 2654, 2655, 2656, 2657, 2660, 2668, 2669, 2670, 2671});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30106) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                if (questState.getQuestItemsCount(2647) == 0L) {
                    questState.giveItems(2647, 1L);
                }
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "dufner_q0213_05.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "dufner_q0213_04.htm";
            }
        } else if (n == 30064) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "terry_q0213_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getQuestItemsCount(2647) >= 1L) {
                    questState.setCond(2);
                    questState.takeItems(2647, 1L);
                    questState.giveItems(2648, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "terry_q0213_03.htm";
                }
            } else if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(2648) >= 1L) {
                    questState.setCond(4);
                    questState.takeItems(2653, -1L);
                    questState.takeItems(2648, 1L);
                    questState.giveItems(2649, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "terry_q0213_06.htm";
                }
            } else if (string.equalsIgnoreCase("reply_4")) {
                questState.setCond(6);
                questState.takeItems(2654, -1L);
                questState.takeItems(2655, -1L);
                questState.takeItems(2656, -1L);
                questState.takeItems(2657, -1L);
                questState.takeItems(2649, -1L);
                questState.giveItems(2650, 1L);
                questState.giveItems(2658, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "terry_q0213_10.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                string2 = "terry_q0213_16.htm";
            } else if (string.equalsIgnoreCase("reply_6") && questState.getQuestItemsCount(2665) >= 1L) {
                questState.setCond(15);
                questState.takeItems(2665, 1L);
                questState.giveItems(2667, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "terry_q0213_18.htm";
            }
        } else if (n == 30684) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "trader_viktor_q0213_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "trader_viktor_q0213_03.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "trader_viktor_q0213_04.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                if (questState.getQuestItemsCount(2650) >= 1L) {
                    questState.setCond(7);
                    questState.takeItems(2650, 1L);
                    questState.giveItems(2651, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "trader_viktor_q0213_05.htm";
                }
            } else if (string.equalsIgnoreCase("reply_5")) {
                string2 = "trader_viktor_q0213_06.htm";
            } else if (string.equalsIgnoreCase("reply_6")) {
                string2 = "trader_viktor_q0213_07.htm";
            } else if (string.equalsIgnoreCase("reply_7")) {
                string2 = "trader_viktor_q0213_08.htm";
            } else if (string.equalsIgnoreCase("reply_8")) {
                string2 = "trader_viktor_q0213_09.htm";
            } else if (string.equalsIgnoreCase("reply_9")) {
                string2 = "trader_viktor_q0213_10.htm";
            } else if (string.equalsIgnoreCase("reply_10")) {
                questState.setCond(9);
                questState.giveItems(2659, 1L);
                questState.takeItems(2650, -1L);
                questState.takeItems(2658, -1L);
                questState.takeItems(2652, -1L);
                questState.takeItems(2651, -1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "trader_viktor_q0213_11.htm";
            } else if (string.equalsIgnoreCase("reply_11")) {
                questState.setCond(11);
                questState.takeItems(2659, -1L);
                questState.takeItems(2660, -1L);
                questState.giveItems(2661, 1L);
                questState.giveItems(2662, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "trader_viktor_q0213_15.htm";
            }
        } else if (n == 30715) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.setCond(12);
                questState.takeItems(2661, -1L);
                questState.takeItems(2662, -1L);
                questState.giveItems(2663, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "magister_marina_q0213_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                questState.setCond(14);
                questState.takeItems(2664, -1L);
                questState.giveItems(2665, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "magister_marina_q0213_05.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        ClassId classId = questState.getPlayer().getClassId();
        switch (n2) {
            case 1: {
                if (n != 30106) break;
                if (questState.getPlayer().getLevel() < 35 || classId != ClassId.rogue && classId != ClassId.elven_scout && classId != ClassId.assassin) {
                    string = "dufner_q0213_02.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "dufner_q0213_03.htm";
                break;
            }
            case 2: {
                if (n == 30106) {
                    if (questState.getQuestItemsCount(2647) == 1L && questState.getQuestItemsCount(2672) == 0L) {
                        string = "dufner_q0213_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2647) == 0L && questState.getQuestItemsCount(2672) == 0L) {
                        string = "dufner_q0213_07.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2647) != 0L || questState.getQuestItemsCount(2672) != 1L) break;
                    questState.giveItems(2673, 1L);
                    questState.addExpAndSp(72126L, 11000L);
                    if (!questState.getPlayer().getVarB("prof2.1")) {
                        questState.giveItems(7562, 8L);
                        questState.getPlayer().setVar("prof2.1", "1", -1L);
                    }
                    this.giveExtraReward(questState.getPlayer());
                    questState.playSound("ItemSound.quest_finish");
                    questState.exitCurrentQuest(false);
                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                    questState.unset("trial_of_seeker");
                    string = "dufner_q0213_08.htm";
                    break;
                }
                if (n == 30064) {
                    if (questState.getQuestItemsCount(2647) == 1L) {
                        string = "terry_q0213_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2648) == 1L) {
                        if (questState.getQuestItemsCount(2653) == 0L) {
                            string = "terry_q0213_04.htm";
                            break;
                        }
                        string = "terry_q0213_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2649) == 1L) {
                        if (questState.getQuestItemsCount(2649) != 1L) break;
                        if (questState.getQuestItemsCount(2654) + questState.getQuestItemsCount(2655) + questState.getQuestItemsCount(2656) + questState.getQuestItemsCount(2657) < 4L) {
                            string = "terry_q0213_08.htm";
                            break;
                        }
                        string = "terry_q0213_09.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2650) == 1L) {
                        string = "terry_q0213_11.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2651) == 1L) {
                        questState.setCond(8);
                        questState.takeItems(2651, -1L);
                        questState.giveItems(2652, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "terry_q0213_12.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2652) == 1L) {
                        string = "terry_q0213_13.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2659) == 1L || questState.getQuestItemsCount(2662) == 1L || questState.getQuestItemsCount(2663) == 1L || questState.getQuestItemsCount(2664) == 1L) {
                        string = "terry_q0213_14.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2665) == 1L) {
                        string = "terry_q0213_15.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2666) == 1L) {
                        if (questState.getPlayer().getLevel() < 36) {
                            string = "terry_q0213_20.htm";
                            break;
                        }
                        questState.setCond(15);
                        questState.takeItems(2666, 1L);
                        questState.giveItems(2667, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "terry_q0213_21.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2667) == 1L) {
                        if (questState.getQuestItemsCount(2668) + questState.getQuestItemsCount(2669) + questState.getQuestItemsCount(2670) + questState.getQuestItemsCount(2671) < 4L) {
                            string = "terry_q0213_22.htm";
                            break;
                        }
                        questState.setCond(17);
                        questState.giveItems(2672, 1L);
                        questState.takeItems(2667, -1L);
                        questState.takeItems(2668, -1L);
                        questState.takeItems(2669, -1L);
                        questState.takeItems(2670, -1L);
                        questState.takeItems(2671, -1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "terry_q0213_23.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2672) != 1L) break;
                    string = "terry_q0213_24.htm";
                    break;
                }
                if (n == 30684) {
                    if (questState.getQuestItemsCount(2650) == 1L) {
                        string = "trader_viktor_q0213_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2652) == 1L) {
                        string = "trader_viktor_q0213_12.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2659) == 1L) {
                        if (questState.getQuestItemsCount(2660) < 10L) {
                            string = "trader_viktor_q0213_13.htm";
                            break;
                        }
                        string = "trader_viktor_q0213_14.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2661) == 1L && questState.getQuestItemsCount(2662) == 1L) {
                        string = "trader_viktor_q0213_16.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2663) == 1L && questState.getQuestItemsCount(2664) == 1L && questState.getQuestItemsCount(2665) == 1L && questState.getQuestItemsCount(2672) == 1L) {
                        string = "trader_viktor_q0213_17.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2651) != 1L) break;
                    string = "trader_viktor_q0213_05.htm";
                    break;
                }
                if (n == 30715) {
                    if (questState.getQuestItemsCount(2661) == 1L && questState.getQuestItemsCount(2662) == 1L) {
                        string = "magister_marina_q0213_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2663) == 1L) {
                        string = "magister_marina_q0213_03.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2664) == 1L) {
                        string = "magister_marina_q0213_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2665) != 1L) break;
                    string = "magister_marina_q0213_06.htm";
                    break;
                }
                if (n != 30526) break;
                if (questState.getQuestItemsCount(2663) == 1L) {
                    questState.setCond(13);
                    questState.takeItems(2663, -1L);
                    questState.giveItems(2664, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string = "blacksmith_bronp_q0213_01.htm";
                    break;
                }
                if (questState.getQuestItemsCount(2664) != 1L) break;
                string = "blacksmith_bronp_q0213_02.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20080) {
            if (questState.getQuestItemsCount(2649) == 1L && questState.getQuestItemsCount(2656) == 0L && Rnd.get((int)100) < 25) {
                questState.giveItems(2656, 1L);
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(2654) >= 1L && questState.getQuestItemsCount(2655) >= 1L && questState.getQuestItemsCount(2657) >= 1L) {
                    questState.setCond(5);
                }
            }
        } else if (n == 20088) {
            if (questState.getQuestItemsCount(2667) == 1L && questState.getQuestItemsCount(2670) == 0L && Rnd.get((int)100) < 25) {
                questState.giveItems(2670, 1L);
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(2668) >= 1L && questState.getQuestItemsCount(2669) >= 1L && questState.getQuestItemsCount(2671) >= 1L) {
                    questState.setCond(16);
                }
            }
        } else if (n == 20158) {
            if (questState.getQuestItemsCount(2659) == 1L && questState.getQuestItemsCount(2660) < 10L) {
                questState.rollAndGive(2660, 1, 25.0);
                if (questState.getQuestItemsCount(2660) >= 10L) {
                    questState.playSound("ItemSound.quest_middle");
                    questState.setCond(10);
                }
            }
        } else if (n == 20198) {
            if (questState.getQuestItemsCount(2648) == 1L && questState.getQuestItemsCount(2653) == 0L && Rnd.get((int)100) < 10) {
                questState.setCond(3);
                questState.giveItems(2653, 1L);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20211) {
            if (questState.getQuestItemsCount(2649) == 1L && questState.getQuestItemsCount(2654) == 0L && Rnd.get((int)100) < 25) {
                questState.giveItems(2654, 1L);
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(2655) >= 1L && questState.getQuestItemsCount(2656) >= 1L && questState.getQuestItemsCount(2657) >= 1L) {
                    questState.setCond(5);
                }
            }
        } else if (n == 20234) {
            if (questState.getQuestItemsCount(2667) == 1L && questState.getQuestItemsCount(2668) == 0L && Rnd.get((int)100) < 25) {
                questState.giveItems(2668, 1L);
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(2669) >= 1L && questState.getQuestItemsCount(2670) >= 1L && questState.getQuestItemsCount(2671) >= 1L) {
                    questState.setCond(16);
                }
            }
        } else if (n == 20249) {
            if (questState.getQuestItemsCount(2649) == 1L && questState.getQuestItemsCount(2657) == 0L && Rnd.get((int)100) < 25) {
                questState.giveItems(2657, 1L);
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(2654) >= 1L && questState.getQuestItemsCount(2655) >= 1L && questState.getQuestItemsCount(2656) >= 1L) {
                    questState.setCond(5);
                }
            }
        } else if (n == 20270) {
            if (questState.getQuestItemsCount(2667) == 1L && questState.getQuestItemsCount(2669) == 0L && Rnd.get((int)100) < 25) {
                questState.giveItems(2669, 1L);
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(2668) >= 1L && questState.getQuestItemsCount(2670) >= 1L && questState.getQuestItemsCount(2671) >= 1L) {
                    questState.setCond(16);
                }
            }
        } else if (n == 20495) {
            if (questState.getQuestItemsCount(2649) == 1L && questState.getQuestItemsCount(2655) == 0L && Rnd.get((int)100) < 25) {
                questState.giveItems(2655, 1L);
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(2654) >= 1L && questState.getQuestItemsCount(2656) >= 1L && questState.getQuestItemsCount(2657) >= 1L) {
                    questState.setCond(5);
                }
            }
        } else if (n == 20580 && questState.getQuestItemsCount(2667) == 1L && questState.getQuestItemsCount(2671) == 0L && Rnd.get((int)100) < 25) {
            questState.giveItems(2671, 1L);
            questState.playSound("ItemSound.quest_middle");
            if (questState.getQuestItemsCount(2668) >= 1L && questState.getQuestItemsCount(2669) >= 1L && questState.getQuestItemsCount(2670) >= 1L) {
                questState.setCond(16);
            }
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
