/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _663_SeductiveWhispers
extends Quest
implements ScriptFile {
    private static final int bDT = 30846;
    private static final int bDU = 20674;
    private static final int bDV = 20678;
    private static final int bDW = 20954;
    private static final int bDX = 20955;
    private static final int bDY = 20956;
    private static final int bDZ = 20957;
    private static final int bEa = 20958;
    private static final int bEb = 20959;
    private static final int bEc = 20960;
    private static final int bEd = 20961;
    private static final int bEe = 20962;
    private static final int bEf = 20963;
    private static final int bEg = 20974;
    private static final int bEh = 20975;
    private static final int bEi = 20976;
    private static final int bEj = 20996;
    private static final int bEk = 20997;
    private static final int bEl = 20998;
    private static final int bEm = 20999;
    private static final int bEn = 21000;
    private static final int bEo = 21001;
    private static final int bEp = 21002;
    private static final int bEq = 21006;
    private static final int bEr = 21007;
    private static final int bEs = 21008;
    private static final int bEt = 21009;
    private static final int bEu = 21010;
    private static final int bEv = 8766;
    private static final int bEw = 955;
    private static final int bEx = 951;
    private static final int bEy = 947;
    private static final int bEz = 948;
    private static final int bEA = 729;
    private static final int bEB = 730;
    private static final int bEC = 4963;
    private static final int bED = 4964;
    private static final int bEE = 4965;
    private static final int bEF = 4966;
    private static final int bEG = 4967;
    private static final int bEH = 4968;
    private static final int bEI = 4969;
    private static final int bEJ = 4970;
    private static final int bEK = 4971;
    private static final int bEL = 4972;
    private static final int bEM = 5000;
    private static final int bEN = 5001;
    private static final int bEO = 5002;
    private static final int bEP = 5003;
    private static final int bEQ = 5004;
    private static final int bER = 5005;
    private static final int bES = 5006;
    private static final int bET = 5007;
    private static final int bEU = 4104;
    private static final int bEV = 4105;
    private static final int bEW = 4106;
    private static final int bEX = 4107;
    private static final int bEY = 4108;
    private static final int bEZ = 4109;
    private static final int bFa = 4110;
    private static final int bFb = 4111;
    private static final int bFc = 4112;
    private static final int bFd = 4113;
    private static final int bFe = 4114;
    private static final int bFf = 4115;
    private static final int bFg = 4116;
    private static final int bFh = 4117;
    private static final int bFi = 4118;
    private static final int bFj = 4119;
    private static final int bFk = 4120;
    private static final int bFl = 4121;

    public _663_SeductiveWhispers() {
        super(1);
        this.addStartNpc(30846);
        this.addKillId(new int[]{20674, 20678, 20954, 20955, 20956, 20957, 20958, 20959, 20960, 20961, 20962, 20963, 20974, 20975, 20976, 20996, 20997, 20998, 20999, 21000, 21001, 21002, 21006, 21007, 21008, 21009, 21010});
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = "no-quest";
        int n = questState.getInt("whispers_of_temptation");
        int n2 = questState.getInt("whispers_of_temptation_ex");
        int n3 = npcInstance.getNpcId();
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        if (n3 == 30846) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("whispers_of_temptation", String.valueOf(1), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "blacksmith_wilbert_q0663_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "blacksmith_wilbert_q0663_01a.htm";
            } else if (string.equalsIgnoreCase("reply_4") && n % 10 <= 4) {
                if (n / 10 < 1) {
                    if (questState.getQuestItemsCount(8766) >= 50L) {
                        questState.takeItems(8766, 50L);
                        questState.set("whispers_of_temptation", String.valueOf(5), true);
                        questState.set("whispers_of_temptation_ex", String.valueOf(0), true);
                        string2 = "blacksmith_wilbert_q0663_09.htm";
                    } else {
                        string2 = "blacksmith_wilbert_q0663_10.htm";
                    }
                } else {
                    n4 = n / 10;
                    n5 = n4 * 10 + 5;
                    questState.set("whispers_of_temptation", String.valueOf(n5), true);
                    questState.set("whispers_of_temptation_ex", String.valueOf(0), true);
                    string2 = "blacksmith_wilbert_q0663_09a.htm";
                }
            } else if (string.equalsIgnoreCase("reply_5") && n % 10 == 5 && n / 1000 == 0) {
                n4 = n2;
                if (n4 < 0) {
                    n4 = 0;
                }
                n5 = n4 % 10;
                n6 = (n4 - n5) / 10;
                int n10 = Rnd.get((int)2) + 1;
                int n11 = Rnd.get((int)5) + 1;
                n9 = n / 10;
                int n12 = n10 * 10 + n11;
                if (n10 == n6) {
                    n7 = n11 + n5;
                    if (n7 % 5 == 0 && n7 != 10) {
                        if (n % 100 / 10 >= 7) {
                            string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_14.htm", questState.getPlayer());
                            string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                            string2 = string2.replace("<?card2pic?>", String.valueOf(n12));
                            string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                            questState.set("whispers_of_temptation", String.valueOf(4), true);
                            questState.giveItems(57, 2384000L);
                            questState.giveItems(729, 1L);
                            questState.giveItems(730, 2L);
                            this.giveExtraReward(questState.getPlayer());
                        } else {
                            string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_13.htm", questState.getPlayer());
                            string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                            string2 = string2.replace("<?card2pic?>", String.valueOf(n12));
                            string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                            string2 = string2.replace("<?wincount?>", String.valueOf(n9 + 1));
                            n8 = n / 10 * 10 + 7;
                            questState.set("whispers_of_temptation", String.valueOf(n8), true);
                        }
                    } else {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_12.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n12));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        questState.set("whispers_of_temptation_ex", String.valueOf(n12), true);
                        n8 = n / 10 * 10 + 6;
                        questState.set("whispers_of_temptation", String.valueOf(n8), true);
                    }
                } else if (n10 != n6) {
                    if (n11 == 5 || n5 == 5) {
                        if (n % 100 / 10 >= 7) {
                            string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_14.htm", questState.getPlayer());
                            string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                            string2 = string2.replace("<?card2pic?>", String.valueOf(n12));
                            string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                            questState.giveItems(57, 2384000L);
                            questState.giveItems(729, 1L);
                            questState.giveItems(730, 2L);
                            this.giveExtraReward(questState.getPlayer());
                            questState.set("whispers_of_temptation", String.valueOf(4), true);
                        } else {
                            string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_13.htm", questState.getPlayer());
                            string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                            string2 = string2.replace("<?card2pic?>", String.valueOf(n12));
                            string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                            string2 = string2.replace("<?wincount?>", String.valueOf(n9 + 1));
                            n8 = n / 10 * 10 + 7;
                            questState.set("whispers_of_temptation", String.valueOf(n8), true);
                        }
                    } else {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_12.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n12));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        n12 = n10 * 10 + n11;
                        questState.set("whispers_of_temptation_ex", String.valueOf(n12), true);
                        n8 = n / 10 * 10 + 6;
                        questState.set("whispers_of_temptation", String.valueOf(n8), true);
                    }
                }
            } else if (string.equalsIgnoreCase("reply_6") && n % 10 == 6 && n / 1000 == 0) {
                n4 = n2;
                if (n4 < 0) {
                    n4 = 0;
                }
                n5 = n4 % 10;
                n6 = (n4 - n5) / 10;
                int n13 = Rnd.get((int)2) + 1;
                int n14 = Rnd.get((int)5) + 1;
                int n15 = n13 * 10 + n14;
                if (n13 == n6) {
                    n7 = n14 + n5;
                    if (n7 % 5 == 0 && n7 != 10) {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_19.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n15));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        questState.set("whispers_of_temptation", String.valueOf(1), true);
                        questState.set("whispers_of_temptation_ex", String.valueOf(0), true);
                    } else {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_18.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n15));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        n15 = n13 * 10 + n14;
                        questState.set("whispers_of_temptation_ex", String.valueOf(n15), true);
                        n8 = n / 10 * 10 + 5;
                        questState.set("whispers_of_temptation", String.valueOf(n8), true);
                    }
                } else if (n13 != n6) {
                    n7 = n13 + n5;
                    n8 = 66310 + n6;
                    n9 = 66310 + n13;
                    if (n14 == 5 || n5 == 5) {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_19.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n15));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        questState.set("whispers_of_temptation", String.valueOf(1), true);
                    } else {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_18.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n15));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        n15 = n13 * 10 + n14;
                        questState.set("whispers_of_temptation_ex", String.valueOf(n15), true);
                        n8 = n / 10 * 10 + 5;
                        questState.set("whispers_of_temptation", String.valueOf(n8), true);
                    }
                }
            } else if (string.equalsIgnoreCase("reply_8") && n % 10 == 7 && n / 1000 == 0) {
                n4 = n / 10;
                n5 = (n4 + 1) * 10 + 4;
                questState.set("whispers_of_temptation", String.valueOf(n5), true);
                questState.set("whispers_of_temptation_ex", String.valueOf(0), true);
                string2 = "blacksmith_wilbert_q0663_20.htm";
            } else if (string.equalsIgnoreCase("reply_9") && n % 10 == 7 && n / 1000 == 0) {
                n4 = n / 10;
                if (n4 == 0) {
                    questState.giveItems(57, 40000L);
                    this.giveExtraReward(questState.getPlayer());
                } else if (n4 == 1) {
                    questState.giveItems(57, 80000L);
                    this.giveExtraReward(questState.getPlayer());
                } else if (n4 == 2) {
                    questState.giveItems(57, 110000L);
                    questState.giveItems(955, 1L);
                    this.giveExtraReward(questState.getPlayer());
                } else if (n4 == 3) {
                    questState.giveItems(57, 199000L);
                    questState.giveItems(951, 1L);
                    this.giveExtraReward(questState.getPlayer());
                } else if (n4 == 4) {
                    this.giveExtraReward(questState.getPlayer());
                    questState.giveItems(57, 388000L);
                    n5 = Rnd.get((int)18) + 1;
                    if (n5 == 1) {
                        questState.giveItems(4963, 1L);
                    } else if (n5 == 2) {
                        questState.giveItems(4964, 1L);
                    } else if (n5 == 3) {
                        questState.giveItems(4965, 1L);
                    } else if (n5 == 4) {
                        questState.giveItems(4966, 1L);
                    } else if (n5 == 5) {
                        questState.giveItems(4967, 1L);
                    } else if (n5 == 6) {
                        questState.giveItems(4968, 1L);
                    } else if (n5 == 7) {
                        questState.giveItems(4969, 1L);
                    } else if (n5 == 8) {
                        questState.giveItems(4970, 1L);
                    } else if (n5 == 9) {
                        questState.giveItems(4971, 1L);
                    } else if (n5 == 10) {
                        questState.giveItems(4972, 1L);
                    } else if (n5 == 11) {
                        questState.giveItems(5000, 1L);
                    } else if (n5 == 12) {
                        questState.giveItems(5001, 1L);
                    } else if (n5 == 13) {
                        questState.giveItems(5002, 1L);
                    } else if (n5 == 14) {
                        questState.giveItems(5003, 1L);
                    } else if (n5 == 15) {
                        questState.giveItems(5004, 1L);
                    } else if (n5 == 16) {
                        questState.giveItems(5005, 1L);
                    } else if (n5 == 17) {
                        questState.giveItems(5006, 1L);
                    } else if (n5 == 18) {
                        questState.giveItems(5007, 1L);
                    }
                } else if (n4 == 5) {
                    questState.giveItems(57, 675000L);
                    this.giveExtraReward(questState.getPlayer());
                    n5 = Rnd.get((int)18) + 1;
                    if (n5 == 1) {
                        questState.giveItems(4104, 12L);
                    } else if (n5 == 2) {
                        questState.giveItems(4113, 12L);
                    } else if (n5 == 3) {
                        questState.giveItems(4112, 12L);
                    } else if (n5 == 4) {
                        questState.giveItems(4108, 12L);
                    } else if (n5 == 5) {
                        questState.giveItems(4111, 12L);
                    } else if (n5 == 6) {
                        questState.giveItems(4106, 12L);
                    } else if (n5 == 7) {
                        questState.giveItems(4109, 12L);
                    } else if (n5 == 8) {
                        questState.giveItems(4107, 12L);
                    } else if (n5 == 9) {
                        questState.giveItems(4105, 12L);
                    } else if (n5 == 10) {
                        questState.giveItems(4110, 12L);
                    } else if (n5 == 11) {
                        questState.giveItems(4114, 13L);
                    } else if (n5 == 12) {
                        questState.giveItems(4115, 13L);
                    } else if (n5 == 13) {
                        questState.giveItems(4120, 13L);
                    } else if (n5 == 14) {
                        questState.giveItems(4118, 13L);
                    } else if (n5 == 15) {
                        questState.giveItems(4116, 13L);
                    } else if (n5 == 16) {
                        questState.giveItems(4117, 13L);
                    } else if (n5 == 17) {
                        questState.giveItems(4119, 13L);
                    } else if (n5 == 18) {
                        questState.giveItems(4121, 13L);
                    }
                } else if (n4 == 6) {
                    questState.giveItems(57, 1284000L);
                    questState.giveItems(947, 2L);
                    questState.giveItems(948, 2L);
                    this.giveExtraReward(questState.getPlayer());
                }
                questState.set("whispers_of_temptation", String.valueOf(1), true);
                questState.set("whispers_of_temptation_ex", String.valueOf(0), true);
                string2 = "blacksmith_wilbert_q0663_21.htm";
            } else if (string.equalsIgnoreCase("reply_10") && n == 1 && n / 1000 == 0) {
                string2 = "blacksmith_wilbert_q0663_21a.htm";
            } else if (string.equalsIgnoreCase("reply_14") && n % 10 == 1) {
                if (questState.getQuestItemsCount(8766) >= 1L) {
                    questState.set("whispers_of_temptation", String.valueOf(1005), true);
                    questState.takeItems(8766, 1L);
                    string2 = "blacksmith_wilbert_q0663_22.htm";
                } else {
                    string2 = "blacksmith_wilbert_q0663_22a.htm";
                }
            } else if (string.equalsIgnoreCase("reply_15") && n == 1005) {
                n4 = n2;
                if (n4 < 0) {
                    n4 = 0;
                }
                n5 = n4 % 10;
                n6 = (n4 - n5) / 10;
                int n16 = Rnd.get((int)2) + 1;
                int n17 = Rnd.get((int)5) + 1;
                int n18 = n16 * 10 + n17;
                if (n16 == n6) {
                    n7 = n17 + n5;
                    n8 = 66310 + n6;
                    n9 = 66310 + n16;
                    if (n7 % 5 == 0 && n7 != 10) {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_25.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n18));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        questState.set("whispers_of_temptation", String.valueOf(1), true);
                        questState.set("whispers_of_temptation_ex", String.valueOf(0), true);
                        questState.giveItems(57, 800L);
                    } else {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_24.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n18));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        questState.set("whispers_of_temptation_ex", String.valueOf(n18), true);
                        questState.set("whispers_of_temptation", String.valueOf(1006), true);
                    }
                } else if (n16 != n6) {
                    if (n17 == 5 || n5 == 5) {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_25.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n18));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        questState.set("whispers_of_temptation", String.valueOf(1), true);
                        questState.set("whispers_of_temptation_ex", String.valueOf(0), true);
                        questState.giveItems(57, 800L);
                    } else {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_24.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n18));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        questState.set("whispers_of_temptation_ex", String.valueOf(n18), true);
                        questState.set("whispers_of_temptation", String.valueOf(1006), true);
                    }
                }
            } else if (string.equalsIgnoreCase("reply_16") && n == 1006) {
                n4 = n2;
                if (n4 < 0) {
                    n4 = 0;
                }
                n5 = n4 % 10;
                n6 = (n4 - n5) / 10;
                int n19 = Rnd.get((int)2) + 1;
                int n20 = Rnd.get((int)5) + 1;
                int n21 = n19 * 10 + n20;
                if (n19 == n6) {
                    n7 = n20 + n5;
                    if (n7 % 5 == 0 && n7 != 10) {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_29.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n21));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        questState.set("whispers_of_temptation", String.valueOf(1), true);
                        questState.set("whispers_of_temptation_ex", String.valueOf(0), true);
                    } else {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_28.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n21));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        n21 = n19 * 10 + n20;
                        questState.set("whispers_of_temptation_ex", String.valueOf(n21), true);
                        questState.set("whispers_of_temptation", String.valueOf(1005), true);
                    }
                } else if (n19 != n6) {
                    n7 = n19 + n5;
                    n8 = 66310 + n6;
                    n9 = 66310 + n19;
                    if (n20 == 5 || n5 == 5) {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_29.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n21));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        questState.set("whispers_of_temptation", String.valueOf(1), true);
                        questState.set("whispers_of_temptation_ex", String.valueOf(0), true);
                    } else {
                        string2 = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_28.htm", questState.getPlayer());
                        string2 = string2.replace("<?card1pic?>", String.valueOf(n4));
                        string2 = string2.replace("<?card2pic?>", String.valueOf(n21));
                        string2 = string2.replace("<?name?>", questState.getPlayer().getName());
                        n21 = n19 * 10 + n20;
                        questState.set("whispers_of_temptation_ex", String.valueOf(n21), true);
                        questState.set("whispers_of_temptation", String.valueOf(1005), true);
                    }
                }
            } else if (string.equalsIgnoreCase("reply_20")) {
                questState.unset("whispers_of_temptation");
                questState.unset("whispers_of_temptation_ex");
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "blacksmith_wilbert_q0663_30.htm";
            } else if (string.equalsIgnoreCase("reply_21")) {
                string2 = "blacksmith_wilbert_q0663_31.htm";
            } else if (string.equalsIgnoreCase("reply_22")) {
                string2 = "blacksmith_wilbert_q0663_32.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("whispers_of_temptation");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30846) break;
                if (questState.getPlayer().getLevel() < 50) {
                    string = "blacksmith_wilbert_q0663_02.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "blacksmith_wilbert_q0663_01.htm";
                break;
            }
            case 2: {
                if (n2 != 30846) break;
                if (n < 4 && n >= 1 && questState.getQuestItemsCount(8766) == 0L) {
                    string = "blacksmith_wilbert_q0663_04.htm";
                    break;
                }
                if (n < 4 && n >= 1 && questState.getQuestItemsCount(8766) > 0L) {
                    string = "blacksmith_wilbert_q0663_05.htm";
                    break;
                }
                if (n % 10 == 4 && n / 1000 == 0) {
                    string = "blacksmith_wilbert_q0663_05a.htm";
                    break;
                }
                if (n % 10 == 5 && n / 1000 == 0) {
                    string = "blacksmith_wilbert_q0663_11.htm";
                    break;
                }
                if (n % 10 == 6 && n / 1000 == 0) {
                    string = "blacksmith_wilbert_q0663_15.htm";
                    break;
                }
                if (n % 10 == 7 && n / 1000 == 0) {
                    int n4 = n % 100;
                    if (n4 / 10 >= 7) {
                        questState.set("whispers_of_temptation", String.valueOf(1), true);
                        questState.giveItems(57, 2384000L);
                        questState.giveItems(729, 1L);
                        questState.giveItems(730, 2L);
                        this.giveExtraReward(questState.getPlayer());
                        string = "blacksmith_wilbert_q0663_17.htm";
                        break;
                    }
                    int n5 = n / 10;
                    string = HtmCache.getInstance().getNotNull("quests/_663_SeductiveWhispers/blacksmith_wilbert_q0663_16.htm", questState.getPlayer());
                    string = string.replace("<?wincount?>", String.valueOf(n5 + 1));
                    break;
                }
                if (n == 1005) {
                    string = "blacksmith_wilbert_q0663_23.htm";
                    break;
                }
                if (n != 1006) break;
                string = "blacksmith_wilbert_q0663_26.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("whispers_of_temptation");
        int n2 = npcInstance.getNpcId();
        if (n2 == 20674) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 80.7);
            }
        } else if (n2 == 20678 || n2 == 20960) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 37.2);
            }
        } else if (n2 == 20954) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 46.0);
            }
        } else if (n2 == 20955) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 53.7);
            }
        } else if (n2 == 20956 || n2 == 21007) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 54.0);
            }
        } else if (n2 == 20957) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 56.5);
            }
        } else if (n2 == 20958) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 42.5);
            }
        } else if (n2 == 20959) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 68.2);
            }
        } else if (n2 == 20961) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 54.7);
            }
        } else if (n2 == 20962) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 52.2);
            }
        } else if (n2 == 20963) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 49.8);
            }
        } else if (n2 == 20974) {
            if (n >= 1 && n <= 4) {
                int n3 = Rnd.get((int)1000);
                if (n3 < 100) {
                    questState.rollAndGive(8766, 2, 100.0);
                } else {
                    questState.rollAndGive(8766, 1, 100.0);
                }
            }
        } else if (n2 == 20975) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 97.5);
            }
        } else if (n2 == 20976) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 82.5);
            }
        } else if (n2 == 20996) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 38.5);
            }
        } else if (n2 == 20997) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 34.2);
            }
        } else if (n2 == 20998) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 37.7);
            }
        } else if (n2 == 20999) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 45.0);
            }
        } else if (n2 == 21000) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 39.5);
            }
        } else if (n2 == 21001) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 53.5);
            }
        } else if (n2 == 21002) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 47.2);
            }
        } else if (n2 == 21006) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 50.2);
            }
        } else if (n2 == 21008) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 69.2);
            }
        } else if (n2 == 21009) {
            if (n >= 1 && n <= 4) {
                questState.rollAndGive(8766, 1, 74.0);
            }
        } else if (n2 == 21010 && n >= 1 && n <= 4) {
            questState.rollAndGive(8766, 1, 59.5);
        }
        return null;
    }
}
