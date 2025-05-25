/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Player
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
import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.ScriptFile;

public class _214_TrialOfScholar
extends Quest
implements ScriptFile {
    private static final int YQ = 30070;
    private static final int YR = 30071;
    private static final int YS = 30103;
    private static final int YT = 30111;
    private static final int YU = 30115;
    private static final int YV = 30230;
    private static final int YW = 30316;
    private static final int YX = 30458;
    private static final int YY = 30461;
    private static final int YZ = 30608;
    private static final int Za = 30609;
    private static final int Zb = 30610;
    private static final int Zc = 30611;
    private static final int Zd = 30612;
    private static final int Ze = 20068;
    private static final int Zf = 20158;
    private static final int Zg = 20201;
    private static final int Zh = 20235;
    private static final int Zi = 20269;
    private static final int Zj = 20279;
    private static final int Zk = 20552;
    private static final int Zl = 20554;
    private static final int Zm = 20567;
    private static final int Zn = 20580;
    private static final int Zo = 2674;
    private static final int Zp = 2675;
    private static final int Zq = 2676;
    private static final int Zr = 2677;
    private static final int Zs = 2678;
    private static final int Zt = 2679;
    private static final int Zu = 2680;
    private static final int Zv = 2681;
    private static final int Zw = 2682;
    private static final int Zx = 2683;
    private static final int Zy = 2684;
    private static final int Zz = 2685;
    private static final int ZA = 2686;
    private static final int ZB = 2687;
    private static final int ZC = 2688;
    private static final int ZD = 2689;
    private static final int ZE = 2690;
    private static final int ZF = 2691;
    private static final int ZG = 2692;
    private static final int ZH = 2693;
    private static final int ZI = 2694;
    private static final int ZJ = 2695;
    private static final int ZK = 2696;
    private static final int ZL = 2697;
    private static final int ZM = 2698;
    private static final int ZN = 2699;
    private static final int ZO = 2700;
    private static final int ZP = 2701;
    private static final int ZQ = 2702;
    private static final int ZR = 2703;
    private static final int ZS = 2704;
    private static final int ZT = 2705;
    private static final int ZU = 2706;
    private static final int ZV = 2707;
    private static final int ZW = 2708;
    private static final int ZX = 2709;
    private static final int ZY = 2710;
    private static final int ZZ = 2711;
    private static final int aaa = 2713;
    private static final int aab = 2714;
    private static final int aac = 2715;
    private static final int aad = 2716;
    private static final int aae = 2717;
    private static final int aaf = 2718;
    private static final int aag = 2719;
    private static final int aah = 2720;
    private static final int aai = 7562;

    public _214_TrialOfScholar() {
        super(0);
        this.addStartNpc(30461);
        this.addTalkId(new int[]{30070, 30608, 30071, 30609, 30115, 30610, 30111, 30230, 30316, 30611, 30103, 30458, 30612});
        this.addKillId(new int[]{20068, 20158, 20201, 20235, 20269, 20279, 20552, 20554, 20567, 20580});
        this.addQuestItem(new int[]{2675, 2676, 2677, 2678, 2679, 2680, 2681, 2682, 2683, 2684, 2685, 2686, 2687, 2688, 2689, 2690, 2691, 2692, 2693, 2694, 2695, 2696, 2697, 2698, 2699, 2700, 2701, 2702, 2703, 2704, 2705, 2706, 2707, 2708, 2709, 2710, 2711, 2713, 2714, 2715, 2716, 2717, 2718, 2719, 2720});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        Player player = questState.getPlayer();
        if (n == 30461) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if ((player.getClassId() == ClassId.wizard || player.getClassId() == ClassId.elven_wizard || player.getClassId() == ClassId.dark_wizard) && player.getLevel() >= 35) {
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    if (questState.getQuestItemsCount(2675) == 0L) {
                        questState.giveItems(2675, 1L);
                    }
                    questState.setCond(1);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "magister_mirien_q0214_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2676) == 1L && questState.getQuestItemsCount(2698) == 1L && questState.getQuestItemsCount(2676) >= 1L) {
                questState.setCond(19);
                questState.takeItems(2698, -1L);
                questState.takeItems(2676, 1L);
                questState.giveItems(2677, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "magister_mirien_q0214_10.htm";
            }
        } else if (n == 30070) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.setCond(2);
                questState.giveItems(2689, 1L);
                questState.giveItems(2692, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "sylvain_q0214_02.htm";
            }
        } else if (n == 30608) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(2692) >= 1L) {
                    questState.setCond(3);
                    questState.takeItems(2692, 1L);
                    questState.giveItems(2679, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "marya_q0214_02.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "marya_q0214_07.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(2683) >= 1L) {
                    questState.setCond(7);
                    questState.takeItems(2683, 1L);
                    questState.giveItems(2682, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "marya_q0214_08.htm";
                }
            } else if (string.equalsIgnoreCase("reply_4") && questState.getQuestItemsCount(2686) >= 1L) {
                questState.setCond(13);
                questState.takeItems(2687, -1L);
                questState.takeItems(2686, 1L);
                questState.giveItems(2688, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "marya_q0214_14.htm";
            }
        } else if (n == 30071) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2685) >= 1L) {
                questState.setCond(10);
                questState.takeItems(2685, 1L);
                questState.giveItems(2686, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "lucas_q0214_04.htm";
            }
        } else if (n == 30609) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "astrologer_creta_q0214_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "astrologer_creta_q0214_03.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "astrologer_creta_q0214_04.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                if (questState.getQuestItemsCount(2680) >= 1L) {
                    questState.setCond(6);
                    questState.takeItems(2680, 1L);
                    questState.giveItems(2683, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "astrologer_creta_q0214_05.htm";
                }
            } else if (string.equalsIgnoreCase("reply_5")) {
                string2 = "astrologer_creta_q0214_08.htm";
            } else if (string.equalsIgnoreCase("reply_6")) {
                if (questState.getQuestItemsCount(2682) >= 1L) {
                    questState.setCond(8);
                    questState.takeItems(2682, 1L);
                    questState.giveItems(2684, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "astrologer_creta_q0214_09.htm";
                }
            } else if (string.equalsIgnoreCase("reply_7")) {
                string2 = "astrologer_creta_q0214_13.htm";
            } else if (string.equalsIgnoreCase("reply_8") && questState.getQuestItemsCount(2700) >= 1L) {
                questState.setCond(22);
                questState.takeItems(2700, 1L);
                questState.giveItems(2701, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "astrologer_creta_q0214_14.htm";
            }
        } else if (n == 30115) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "jurek_q0214_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                questState.setCond(16);
                questState.giveItems(2694, 1L);
                questState.giveItems(2690, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "jurek_q0214_03.htm";
            }
        } else if (n == 30610) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "sage_cronos_q0214_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "sage_cronos_q0214_03.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "sage_cronos_q0214_04.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "sage_cronos_q0214_05.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                string2 = "sage_cronos_q0214_06.htm";
            } else if (string.equalsIgnoreCase("reply_6")) {
                string2 = "sage_cronos_q0214_07.htm";
            } else if (string.equalsIgnoreCase("reply_7")) {
                string2 = "sage_cronos_q0214_08.htm";
            } else if (string.equalsIgnoreCase("reply_8")) {
                string2 = "sage_cronos_q0214_09.htm";
            } else if (string.equalsIgnoreCase("reply_9")) {
                questState.setCond(20);
                questState.giveItems(2691, 1L);
                questState.giveItems(2699, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "sage_cronos_q0214_10.htm";
            } else if (string.equalsIgnoreCase("reply_10")) {
                string2 = "sage_cronos_q0214_13.htm";
            } else if (string.equalsIgnoreCase("reply_11") && questState.getQuestItemsCount(2706) >= 1L && questState.getQuestItemsCount(2707) >= 1L && questState.getQuestItemsCount(2708) >= 1L && questState.getQuestItemsCount(2709) >= 1L) {
                questState.setCond(31);
                questState.takeItems(2706, -1L);
                questState.takeItems(2707, -1L);
                questState.takeItems(2708, -1L);
                questState.takeItems(2709, -1L);
                questState.takeItems(2691, -1L);
                questState.takeItems(2705, -1L);
                questState.takeItems(2703, -1L);
                questState.giveItems(2720, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "sage_cronos_q0214_14.htm";
            }
        } else if (n == 30111) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "dieter_q0214_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "dieter_q0214_03.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "dieter_q0214_04.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                if (questState.getQuestItemsCount(2699) >= 1L) {
                    questState.setCond(21);
                    questState.takeItems(2699, 1L);
                    questState.giveItems(2700, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "dieter_q0214_05.htm";
                }
            } else if (string.equalsIgnoreCase("reply_5")) {
                string2 = "dieter_q0214_08.htm";
            } else if (string.equalsIgnoreCase("reply_6") && questState.getQuestItemsCount(2701) >= 1L) {
                questState.setCond(23);
                questState.takeItems(2701, 1L);
                questState.giveItems(2702, 1L);
                questState.giveItems(2703, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "dieter_q0214_09.htm";
            }
        } else if (n == 30230) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2702) >= 1L) {
                questState.setCond(24);
                questState.takeItems(2702, 1L);
                questState.giveItems(2704, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "trader_edroc_q0214_02.htm";
            }
        } else if (n == 30316) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2704) >= 1L) {
                questState.setCond(25);
                questState.takeItems(2704, 1L);
                questState.giveItems(2706, 1L);
                questState.giveItems(2713, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "warehouse_keeper_raut_q0214_02.htm";
            }
        } else if (n == 30611) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "drunkard_treaf_q0214_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "drunkard_treaf_q0214_03.htm";
            } else if (string.equalsIgnoreCase("reply_3") && questState.getQuestItemsCount(2713) >= 1L) {
                questState.setCond(26);
                questState.takeItems(2713, 1L);
                questState.giveItems(2705, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "drunkard_treaf_q0214_04.htm";
            }
        } else if (n == 30103) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "valkon_q0214_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "valkon_q0214_03.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                questState.giveItems(2710, 1L);
                string2 = "valkon_q0214_04.htm";
            }
        } else if (n == 30612) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "sage_kasian_q0214_03.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                questState.setCond(28);
                questState.giveItems(2715, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "sage_kasian_q0214_04.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                questState.setCond(30);
                questState.takeItems(2715, -1L);
                questState.takeItems(2716, -1L);
                questState.takeItems(2717, -1L);
                questState.takeItems(2718, -1L);
                questState.takeItems(2719, -1L);
                questState.takeItems(2711, -1L);
                questState.giveItems(2709, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "sage_kasian_q0214_07.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        Player player = questState.getPlayer();
        switch (n2) {
            case 1: {
                if (n != 30461) break;
                if (player.getClassId() != ClassId.wizard && player.getClassId() != ClassId.elven_wizard && player.getClassId() != ClassId.dark_wizard) {
                    string = "magister_mirien_q0214_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if ((player.getClassId() == ClassId.wizard || player.getClassId() == ClassId.elven_wizard || player.getClassId() == ClassId.dark_wizard) && player.getLevel() < 35) {
                    string = "magister_mirien_q0214_02.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "magister_mirien_q0214_03.htm";
                break;
            }
            case 2: {
                if (n == 30461) {
                    if (questState.getQuestItemsCount(2675) == 1L && questState.getQuestItemsCount(2693) == 0L) {
                        string = "magister_mirien_q0214_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) == 1L && questState.getQuestItemsCount(2693) == 1L) {
                        string = "magister_mirien_q0214_06.htm";
                        questState.setCond(15);
                        questState.playSound("ItemSound.quest_middle");
                        questState.takeItems(2693, -1L);
                        questState.takeItems(2675, 1L);
                        questState.giveItems(2676, 1L);
                        break;
                    }
                    if (questState.getQuestItemsCount(2676) == 1L && questState.getQuestItemsCount(2698) == 0L) {
                        string = "magister_mirien_q0214_07.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2676) == 1L && questState.getQuestItemsCount(2698) == 1L) {
                        string = "magister_mirien_q0214_08.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2678) == 1L) {
                        if (questState.getPlayer().getLevel() < 36) {
                            string = "magister_mirien_q0214_11.htm";
                            break;
                        }
                        questState.setCond(19);
                        questState.takeItems(2678, 1L);
                        questState.giveItems(2677, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "magister_mirien_q0214_12.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) != 1L) break;
                    if (questState.getQuestItemsCount(2720) == 0L) {
                        string = "magister_mirien_q0214_13.htm";
                        break;
                    }
                    questState.addExpAndSp(80265L, 30000L);
                    questState.giveItems(7562, 8L);
                    questState.takeItems(2720, -1L);
                    questState.takeItems(2677, 1L);
                    questState.giveItems(2674, 1L);
                    string = "magister_mirien_q0214_14.htm";
                    this.giveExtraReward(player);
                    questState.playSound("ItemSound.quest_finish");
                    questState.exitCurrentQuest(false);
                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                    break;
                }
                if (n == 30070) {
                    if (questState.getQuestItemsCount(2675) == 1L && questState.getQuestItemsCount(2689) == 0L && questState.getQuestItemsCount(2693) == 0L) {
                        string = "sylvain_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2688) == 0L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2675) >= 1L) {
                        string = "sylvain_q0214_03.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2688) >= 1L) {
                        questState.setCond(14);
                        questState.takeItems(2689, -1L);
                        questState.takeItems(2688, -1L);
                        questState.giveItems(2693, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "sylvain_q0214_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2693) >= 1L && questState.getQuestItemsCount(2689) == 0L) {
                        string = "sylvain_q0214_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2676) < 1L && questState.getQuestItemsCount(2677) < 1L) break;
                    string = "sylvain_q0214_06.htm";
                    break;
                }
                if (n == 30608) {
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2692) >= 1L) {
                        string = "marya_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2679) >= 1L) {
                        string = "marya_q0214_03.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2681) >= 1L) {
                        questState.setCond(5);
                        questState.takeItems(2681, 1L);
                        questState.giveItems(2680, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "marya_q0214_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2680) >= 1L) {
                        string = "marya_q0214_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2683) >= 1L) {
                        string = "marya_q0214_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2682) >= 1L) {
                        string = "marya_q0214_09.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2684) >= 1L) {
                        questState.setCond(9);
                        questState.takeItems(2684, 1L);
                        questState.giveItems(2685, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "marya_q0214_10.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2685) >= 1L) {
                        string = "marya_q0214_11.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2686) >= 1L && questState.getQuestItemsCount(2687) < 5L) {
                        questState.setCond(11);
                        questState.playSound("ItemSound.quest_middle");
                        string = "marya_q0214_12.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2686) >= 1L && questState.getQuestItemsCount(2687) >= 5L) {
                        string = "marya_q0214_13.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2688) >= 1L) {
                        string = "marya_q0214_15.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2693) >= 1L || questState.getQuestItemsCount(2676) >= 1L) {
                        string = "marya_q0214_16.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) == 1L && questState.getQuestItemsCount(2710) == 0L) {
                        string = "marya_q0214_17.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) != 1L || questState.getQuestItemsCount(2710) != 1L) break;
                    questState.takeItems(2710, 1L);
                    questState.giveItems(2714, 1L);
                    string = "marya_q0214_18.htm";
                    break;
                }
                if (n == 30071) {
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2679) >= 1L) {
                        questState.setCond(4);
                        questState.takeItems(2679, 1L);
                        questState.giveItems(2681, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "lucas_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && (questState.getQuestItemsCount(2680) >= 1L || questState.getQuestItemsCount(2683) >= 1L || questState.getQuestItemsCount(2682) >= 1L || questState.getQuestItemsCount(2684) >= 1L || questState.getQuestItemsCount(2681) >= 1L)) {
                        string = "lucas_q0214_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2685) >= 1L) {
                        string = "lucas_q0214_03.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2686) >= 1L) {
                        if (questState.getQuestItemsCount(2687) < 5L) {
                            string = "lucas_q0214_05.htm";
                            break;
                        }
                        string = "lucas_q0214_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2693) < 1L && questState.getQuestItemsCount(2676) < 1L && questState.getQuestItemsCount(2677) < 1L && questState.getQuestItemsCount(2688) < 1L) break;
                    string = "lucas_q0214_07.htm";
                    break;
                }
                if (n == 30609) {
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2680) >= 1L) {
                        string = "astrologer_creta_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2683) >= 1L) {
                        string = "astrologer_creta_q0214_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && questState.getQuestItemsCount(2682) >= 1L) {
                        string = "astrologer_creta_q0214_07.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) >= 1L && questState.getQuestItemsCount(2689) >= 1L && (questState.getQuestItemsCount(2684) >= 1L || questState.getQuestItemsCount(2685) >= 1L || questState.getQuestItemsCount(2686) >= 1L)) {
                        string = "astrologer_creta_q0214_10.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2688) >= 1L || questState.getQuestItemsCount(2693) >= 1L || questState.getQuestItemsCount(2676) >= 1L) {
                        string = "astrologer_creta_q0214_11.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) >= 1L && questState.getQuestItemsCount(2700) >= 1L) {
                        string = "astrologer_creta_q0214_12.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) != 1L || questState.getQuestItemsCount(2700) != 0L) break;
                    string = "astrologer_creta_q0214_15.htm";
                    break;
                }
                if (n == 30115) {
                    if (questState.getQuestItemsCount(2676) == 1L && questState.getQuestItemsCount(2690) == 0L && questState.getQuestItemsCount(2698) == 0L) {
                        string = "jurek_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2676) == 1L && questState.getQuestItemsCount(2694) == 1L) {
                        if (questState.getQuestItemsCount(2695) + questState.getQuestItemsCount(2696) + questState.getQuestItemsCount(2697) < 12L) {
                            string = "jurek_q0214_04.htm";
                            break;
                        }
                        questState.setCond(18);
                        questState.takeItems(2694, -1L);
                        questState.takeItems(2695, -1L);
                        questState.takeItems(2696, -1L);
                        questState.takeItems(2697, -1L);
                        questState.takeItems(2690, 1L);
                        questState.giveItems(2698, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "jurek_q0214_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2676) >= 1L && questState.getQuestItemsCount(2698) >= 1L && questState.getQuestItemsCount(2690) == 0L) {
                        string = "jurek_q0214_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2675) < 1L && questState.getQuestItemsCount(2677) < 1L) break;
                    string = "jurek_q0214_07.htm";
                    break;
                }
                if (n == 30610) {
                    if (questState.getQuestItemsCount(2677) == 1L && questState.getQuestItemsCount(2691) == 0L && questState.getQuestItemsCount(2720) == 0L) {
                        string = "sage_cronos_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) >= 1L && questState.getQuestItemsCount(2691) >= 1L) {
                        if (questState.getQuestItemsCount(2706) >= 1L && questState.getQuestItemsCount(2707) >= 1L && questState.getQuestItemsCount(2708) >= 1L && questState.getQuestItemsCount(2709) >= 1L) {
                            string = "sage_cronos_q0214_12.htm";
                            break;
                        }
                        string = "sage_cronos_q0214_11.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) != 1L || questState.getQuestItemsCount(2720) != 1L || questState.getQuestItemsCount(2691) != 0L) break;
                    string = "sage_cronos_q0214_15.htm";
                    break;
                }
                if (n == 30111) {
                    if (questState.getQuestItemsCount(2677) >= 1L && questState.getQuestItemsCount(2691) >= 1L && questState.getQuestItemsCount(2699) >= 1L) {
                        string = "dieter_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) >= 1L && questState.getQuestItemsCount(2691) >= 1L && questState.getQuestItemsCount(2700) >= 1L) {
                        string = "dieter_q0214_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) >= 1L && questState.getQuestItemsCount(2691) >= 1L && questState.getQuestItemsCount(2701) >= 1L) {
                        string = "dieter_q0214_07.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) >= 1L && questState.getQuestItemsCount(2691) >= 1L && questState.getQuestItemsCount(2703) >= 1L && questState.getQuestItemsCount(2702) >= 1L) {
                        string = "dieter_q0214_10.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) >= 1L && questState.getQuestItemsCount(2691) >= 1L && questState.getQuestItemsCount(2703) >= 1L && questState.getQuestItemsCount(2704) >= 1L) {
                        string = "dieter_q0214_11.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2677) >= 1L && questState.getQuestItemsCount(2691) >= 1L && questState.getQuestItemsCount(2703) >= 1L && questState.getQuestItemsCount(2702) == 0L && questState.getQuestItemsCount(2704) == 0L) {
                        if (questState.getQuestItemsCount(2706) >= 1L && questState.getQuestItemsCount(2707) >= 1L && questState.getQuestItemsCount(2708) >= 1L && questState.getQuestItemsCount(2709) >= 1L) {
                            string = "dieter_q0214_13.htm";
                            break;
                        }
                        string = "dieter_q0214_12.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2720) != 1L) break;
                    string = "dieter_q0214_15.htm";
                    break;
                }
                if (n == 30230) {
                    if (questState.getQuestItemsCount(2703) >= 1L && questState.getQuestItemsCount(2702) >= 1L) {
                        string = "trader_edroc_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2703) >= 1L && questState.getQuestItemsCount(2704) >= 1L) {
                        string = "trader_edroc_q0214_03.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2703) != 1L || questState.getQuestItemsCount(2713) < 1L && questState.getQuestItemsCount(2705) < 1L) break;
                    string = "trader_edroc_q0214_04.htm";
                    break;
                }
                if (n == 30316) {
                    if (questState.getQuestItemsCount(2703) >= 1L && questState.getQuestItemsCount(2704) >= 1L) {
                        string = "warehouse_keeper_raut_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2703) >= 1L && questState.getQuestItemsCount(2706) >= 1L && questState.getQuestItemsCount(2713) >= 1L) {
                        string = "warehouse_keeper_raut_q0214_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2703) < 1L || questState.getQuestItemsCount(2706) < 1L || questState.getQuestItemsCount(2705) < 1L) break;
                    string = "warehouse_keeper_raut_q0214_05.htm";
                    break;
                }
                if (n == 30611) {
                    if (questState.getQuestItemsCount(2703) >= 1L && questState.getQuestItemsCount(2706) >= 1L && questState.getQuestItemsCount(2713) >= 1L) {
                        string = "drunkard_treaf_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2705) < 1L && questState.getQuestItemsCount(2720) < 1L) break;
                    string = "drunkard_treaf_q0214_05.htm";
                    break;
                }
                if (n == 30103) {
                    if (questState.getQuestItemsCount(2705) == 1L && questState.getQuestItemsCount(2710) == 0L && questState.getQuestItemsCount(2714) == 0L && questState.getQuestItemsCount(2707) == 0L) {
                        string = "valkon_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2705) == 1L && questState.getQuestItemsCount(2710) == 1L && questState.getQuestItemsCount(2714) == 0L && questState.getQuestItemsCount(2707) == 0L) {
                        string = "valkon_q0214_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2705) == 1L && questState.getQuestItemsCount(2710) == 0L && questState.getQuestItemsCount(2714) == 1L && questState.getQuestItemsCount(2707) == 0L) {
                        questState.takeItems(2714, 1L);
                        questState.giveItems(2707, 1L);
                        string = "valkon_q0214_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2705) != 1L || questState.getQuestItemsCount(2710) != 0L || questState.getQuestItemsCount(2714) != 0L || questState.getQuestItemsCount(2707) != 1L) break;
                    string = "valkon_q0214_07.htm";
                    break;
                }
                if (n == 30458) {
                    if (questState.getQuestItemsCount(2705) == 1L && questState.getQuestItemsCount(2711) == 0L && questState.getQuestItemsCount(2715) == 0L && questState.getQuestItemsCount(2709) == 0L) {
                        questState.giveItems(2711, 1L);
                        string = "blacksmith_poitan_q0214_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2705) >= 1L && questState.getQuestItemsCount(2711) >= 1L && questState.getQuestItemsCount(2715) == 0L && questState.getQuestItemsCount(2709) == 0L) {
                        string = "blacksmith_poitan_q0214_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2705) >= 1L && questState.getQuestItemsCount(2711) >= 1L && questState.getQuestItemsCount(2715) >= 1L && questState.getQuestItemsCount(2709) == 0L) {
                        string = "blacksmith_poitan_q0214_03.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2705) < 1L || questState.getQuestItemsCount(2709) < 1L || questState.getQuestItemsCount(2711) != 0L || questState.getQuestItemsCount(2715) != 0L) break;
                    string = "blacksmith_poitan_q0214_04.htm";
                    break;
                }
                if (n != 30612) break;
                if (questState.getQuestItemsCount(2705) >= 1L && questState.getQuestItemsCount(2711) >= 1L && questState.getQuestItemsCount(2715) == 0L) {
                    if (questState.getQuestItemsCount(2706) >= 1L && questState.getQuestItemsCount(2707) >= 1L && questState.getQuestItemsCount(2708) >= 1L) {
                        string = "sage_kasian_q0214_02.htm";
                        break;
                    }
                    questState.setCond(27);
                    questState.playSound("ItemSound.quest_middle");
                    string = "sage_kasian_q0214_01.htm";
                    break;
                }
                if (questState.getQuestItemsCount(2705) >= 1L && questState.getQuestItemsCount(2711) >= 1L && questState.getQuestItemsCount(2715) >= 1L) {
                    if (questState.getQuestItemsCount(2716) + questState.getQuestItemsCount(2717) + questState.getQuestItemsCount(2718) + questState.getQuestItemsCount(2719) < 32L) {
                        string = "sage_kasian_q0214_05.htm";
                        break;
                    }
                    string = "sage_kasian_q0214_06.htm";
                    break;
                }
                if (questState.getQuestItemsCount(2711) != 0L || questState.getQuestItemsCount(2711) != 0L || questState.getQuestItemsCount(2715) != 0L || questState.getQuestItemsCount(2705) != 1L || questState.getQuestItemsCount(2706) < 1L || questState.getQuestItemsCount(2707) < 1L || questState.getQuestItemsCount(2708) < 1L || questState.getQuestItemsCount(2709) < 1L) break;
                string = "sage_kasian_q0214_08.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20068) {
            if (questState.getQuestItemsCount(2676) == 1L && questState.getQuestItemsCount(2690) == 1L && questState.getQuestItemsCount(2694) == 1L && questState.getQuestItemsCount(2695) < 5L) {
                questState.rollAndGive(2695, 1, 50.0);
                if (questState.getQuestItemsCount(2695) >= 5L && questState.getQuestItemsCount(2696) >= 5L && questState.getQuestItemsCount(2697) >= 2L) {
                    questState.setCond(17);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20158) {
            if (questState.getQuestItemsCount(2705) == 1L && questState.getQuestItemsCount(2711) == 1L && questState.getQuestItemsCount(2715) == 1L && questState.getQuestItemsCount(2717) < 12L) {
                questState.rollAndGive(2717, 1, 100.0);
                if (questState.getQuestItemsCount(2716) >= 10L && questState.getQuestItemsCount(2717) >= 12L && questState.getQuestItemsCount(2718) >= 5L && questState.getQuestItemsCount(2719) >= 5L) {
                    questState.setCond(29);
                } else if (questState.getQuestItemsCount(2717) >= 12L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20201) {
            if (questState.getQuestItemsCount(2705) == 1L && questState.getQuestItemsCount(2711) == 1L && questState.getQuestItemsCount(2715) == 1L && questState.getQuestItemsCount(2716) < 10L) {
                questState.rollAndGive(2716, 1, 100.0);
                if (questState.getQuestItemsCount(2716) >= 10L && questState.getQuestItemsCount(2717) >= 12L && questState.getQuestItemsCount(2718) >= 5L && questState.getQuestItemsCount(2719) >= 5L) {
                    questState.setCond(29);
                } else if (questState.getQuestItemsCount(2716) >= 10L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20235 || n == 20279) {
            if (questState.getQuestItemsCount(2676) == 1L && questState.getQuestItemsCount(2690) == 1L && questState.getQuestItemsCount(2694) == 1L && questState.getQuestItemsCount(2697) < 2L) {
                questState.rollAndGive(2697, 1, 50.0);
                if (questState.getQuestItemsCount(2695) >= 5L && questState.getQuestItemsCount(2696) >= 5L && questState.getQuestItemsCount(2697) >= 2L) {
                    questState.setCond(17);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20269) {
            if (questState.getQuestItemsCount(2676) == 1L && questState.getQuestItemsCount(2690) == 1L && questState.getQuestItemsCount(2694) == 1L && questState.getQuestItemsCount(2696) < 5L) {
                questState.rollAndGive(2696, 1, 50.0);
                if (questState.getQuestItemsCount(2695) >= 5L && questState.getQuestItemsCount(2696) >= 5L && questState.getQuestItemsCount(2697) >= 2L) {
                    questState.setCond(17);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20552) {
            if (questState.getQuestItemsCount(2705) == 1L && questState.getQuestItemsCount(2711) == 1L && questState.getQuestItemsCount(2715) == 1L && questState.getQuestItemsCount(2718) < 5L) {
                questState.rollAndGive(2718, 1, 100.0);
                if (questState.getQuestItemsCount(2716) >= 10L && questState.getQuestItemsCount(2717) >= 12L && questState.getQuestItemsCount(2718) >= 5L && questState.getQuestItemsCount(2719) >= 5L) {
                    questState.setCond(29);
                } else if (questState.getQuestItemsCount(2718) >= 5L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20554) {
            if (questState.getQuestItemsCount(2677) == 1L && questState.getQuestItemsCount(2691) == 1L && questState.getQuestItemsCount(2705) == 1L && questState.getQuestItemsCount(2708) == 0L && Rnd.get((int)100) < 30) {
                questState.giveItems(2708, 1L);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20567) {
            if (questState.getQuestItemsCount(2705) == 1L && questState.getQuestItemsCount(2711) == 1L && questState.getQuestItemsCount(2715) == 1L && questState.getQuestItemsCount(2719) < 5L) {
                questState.rollAndGive(2719, 1, 100.0);
                if (questState.getQuestItemsCount(2716) >= 10L && questState.getQuestItemsCount(2717) >= 12L && questState.getQuestItemsCount(2718) >= 5L && questState.getQuestItemsCount(2719) >= 5L) {
                    questState.setCond(29);
                } else if (questState.getQuestItemsCount(2719) >= 5L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20580 && questState.getQuestItemsCount(2675) == 1L && questState.getQuestItemsCount(2689) == 1L && questState.getQuestItemsCount(2686) == 1L && questState.getQuestItemsCount(2687) < 5L) {
            questState.rollAndGive(2687, 1, 50.0);
            if (questState.getQuestItemsCount(2687) >= 5L) {
                questState.setCond(12);
                questState.playSound("ItemSound.quest_middle");
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
